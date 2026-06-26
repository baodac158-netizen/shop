package examplemybatis.controller;

import org.springframework.ai.document.Document;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import java.util.List;
import java.util.Map;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

@RestController
public class AiController {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    @Value("classpath:knowledge")
    Resource textFile;

    public AiController(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }


    @GetMapping("/ai/chat")
    public String chat(@RequestParam String msg, @RequestParam String userId) {
        // userId: 模拟用户ID，比如 "user_123"
        // 这样 AI 就知道这段记忆属于谁了
        return chatClient.prompt(msg)
                .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, userId)) // 告诉 AI 这是哪个用户的记忆
                .call()
                .content();
    }

    @GetMapping("/ai/load")
    public String loadData() {
        // 1. 读取文件
        TextReader textReader = new TextReader(textFile);
        List<Document> documents = textReader.get();

        // 2. 存入向量数据库 (这一步会自动调用 Embedding 模型把字变成向量)
        vectorStore.add(documents);

        return "数据加载成功！我记住了。";
    }

    /**
     * 接口2：RAG 提问
     */
    @GetMapping("/ai/rag")
    public String ragChat(@RequestParam String msg) {
        // 1. 先去向量库里搜，搜出最相似的上下文
        List<Document> similarDocuments = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(msg) // 设置问题
                        .topK(2)    // 设置只要前2条
                        .build()
        );

        // 2. 把搜到的内容提取成字符串
        List<String> contentList = similarDocuments.stream()
                .map(Document::getText)
                .toList();

        // 3. 构造 Prompt 模板
        String promptTemplate = """
                你是一个智能助手。请根据下面的【背景信息】来回答用户的问题。
                如果你不知道，就说不知道，不要瞎编。
                
                【背景信息】：
                {context}
                
                【用户问题】：
                {question}
                """;

        // 4. 填充模板
        PromptTemplate template = new PromptTemplate(promptTemplate);
        Prompt prompt = template.create(Map.of(
                "context", contentList.toString(),
                "question", msg
        ));

        // 5. 调用 AI
        return chatClient.prompt(prompt).call().content();
    }
}

