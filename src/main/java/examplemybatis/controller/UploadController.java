package examplemybatis.controller;

import examplemybatis.pojo.Result;
import examplemybatis.tool.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Slf4j
@RestController
public class UploadController {

    private AliOSSUtils aliOSSUtils;

    @Autowired
    public UploadController(AliOSSUtils aliOSSUtils){
        this.aliOSSUtils = aliOSSUtils;
    }

    //本地存储
    /*@PostMapping("/upload")
    public Result upload(String username, Integer age, MultipartFile image) throws Exception {
        log.info("文件上传: {}, {}, {}",username,age,image);

        String originalFilename = image.getOriginalFilename();

        int index = originalFilename.lastIndexOf(".");
        String extname = originalFilename.substring(index);
        String newFilename = UUID.randomUUID() + extname; //采用随机UUID来确保重复文件不重名
        log.info("新文件名：{}",newFilename);

        image.transferTo(new File("D:\\TempPlace\\" + newFilename));

        return Result.success();
    }*/

    //阿里云存储
    @PostMapping("/upload")
    public Result upload(MultipartFile image) throws IOException {
        log.info("文件上传: {}",image);

        String url = aliOSSUtils.upload(image);
        log.info("文件上传完成");

        return Result.success(url);
    }
}
