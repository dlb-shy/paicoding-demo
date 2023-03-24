package com.sun.controller;


import com.google.zxing.WriterException;
import com.sun.util.QRCodeUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Controller
public class QRCodeController {

    @GetMapping("/qrcode")
    public void qrcode(HttpServletResponse response) throws IOException, WriterException {
        String content = "http://www.baidu.com"; // 要生成二维码的内容
        int width = 300; // 二维码图片宽度
        int height = 300; // 二维码图片高度
        String imagePath = "qrcode_login/src/main/resources/images/qrcode.png"; // 二维码图片存放路径
        // 生成二维码图片
        QRCodeUtil.generateQRCode(content, width, height, imagePath);
        // 输出二维码图片到浏览器
        BufferedImage image = ImageIO.read(new File(imagePath));
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        ImageIO.write(image, "png", response.getOutputStream());
    }
}
