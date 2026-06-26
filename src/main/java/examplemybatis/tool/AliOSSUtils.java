package examplemybatis.tool;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.UUID;

/**
 * 阿里云 OSS 工具类
 */
@Data
@Component
public class AliOSSUtils {

//    @Value("${aliyun.oss.endpoint}")
//    private String endpoint;
//    @Value("${aliyun.oss.accessKeyId}")
//    private String accessKeyId;
//    @Value("${aliyun.oss.accessKeySecret}")
//    private String accessKeySecret;
//    @Value("${aliyun.oss.bucketName}")
//    private String bucketName;

    @Autowired
    private AliOSSUtilsProperties aliOSSUtilsProperties;

    public String upload(MultipartFile file) throws IOException {

        String endpoint = aliOSSUtilsProperties.getEndpoint();
        String accessKeyId = aliOSSUtilsProperties.getAccessKeyId();
        String accessKeySecret = aliOSSUtilsProperties.getAccessKeySecret();
        String bucketName = aliOSSUtilsProperties.getBucketName();

        InputStream inputStream = file.getInputStream();

        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));

        // 初始化OSS客户端
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件
        ossClient.putObject(bucketName, fileName, inputStream);

        // 拼接返回的文件访问路径
        String url = "https://" + bucketName + ".oss-cn-guangzhou.aliyuncs.com/" + fileName;

        // 关闭 OSS 客户端
        ossClient.shutdown();

        return url;
    }
}
