package com.lyhyl.restaurant.Controller;

import com.lyhyl.restaurant.common.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * 文件上传下载
 */
@RestController
@RequestMapping("/common")

public class CommonController {

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file){
        //获得文件名  123.jpg
        String originalFilename = file.getOriginalFilename();
        //获取后缀名 .jpg
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //使用uuid重新生成文件名 jkkldsjkldfhjklsd.jpg
        String fileName = UUID.randomUUID().toString() + suffix;
        String basePath = null;
        try {
          //获取项目路径
            File directory = new File("src\\main\\resources\\static\\images\\");
            basePath = directory.getCanonicalPath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //创建目录对象
        File file1 = new File(basePath);
        if (!file1.exists()){
            file1.mkdir();
        }

        try {
            file.transferTo(new File(basePath+"/"+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.success(fileName);
    }
    @GetMapping("/download")
    public void downLoad(String name, HttpServletResponse response){
        try {
            File directory = new File("src\\main\\resources\\static\\images\\");
            String basePath = directory.getCanonicalPath();
            //通过输入流 读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath+"/"+name));
            //通过输出流将文件写回浏览器，展示图片、
            ServletOutputStream outputStream = response.getOutputStream();
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes))!= -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
            outputStream.close();
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
