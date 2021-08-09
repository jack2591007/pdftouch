package com.xfc.control;

import com.xfc.untils.PdfReader;
import org.apache.pdfbox.text.TextPosition;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;

@RestController
public class TouchControl {

    @RequestMapping("/test")
    public String test(){
        return "hello";
    }

    @RequestMapping(value = "/pdfout", produces = {
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_GIF_VALUE,
            MediaType.IMAGE_PNG_VALUE})
    public BufferedImage pdfOut(HttpServletResponse response) {

        response.setContentType("image/png");

        String filepath = "D:\\下载\\年报财务报表.pdf";
        Rectangle rectangle = new Rectangle(0, 0, 150, 150);
        BufferedImage bufferedImage = PdfReader.readRectangelImage(filepath, 1, rectangle);

        return bufferedImage;
    }

    @RequestMapping("/pdfToImage")
    public  String pdfConvertImage(){
        String inPath = "D:\\下载\\年报财务报表.pdf";
        String outPath = "D:\\测试文件\\";
        return PdfReader.savePerImage(inPath,outPath);
    }
}
