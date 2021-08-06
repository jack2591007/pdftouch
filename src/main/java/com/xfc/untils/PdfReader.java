package com.xfc.untils;

import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class  PdfReader {

    public static final String REGION_NAME = "content";

    public void pdfFindOut(){
        PDDocument document = null;
        String filepath = "G:\\下载\\年报财务报表.pdf";
        File pdfFile = new File(filepath);
        try {
            // 方式一：

             InputStream input = null;
             input = new FileInputStream( pdfFile );
             //加载 pdf 文档
             PDFParser parser = new PDFParser(new RandomAccessBuffer(input));
             parser.parse();
             document = parser.getPDDocument();


            // 方式二：
            /**
            document=PDDocument.load(pdfFile);
             **/
            // 获取页码
            int pages = document.getNumberOfPages();

            // 读文本内容
            PDFTextStripper stripper=new PDFTextStripper();
            // 设置按顺序输出
            stripper.setSortByPosition(true);
            stripper.setStartPage(1);
            stripper.setEndPage(pages);
            String content = stripper.getText(document);
            System.out.println(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据指定文件页码的指定区域读取文字
     *
     * @param filePath PDF文件路径
     * @param iPage PDF页码
     * @param textRrect 读取文字的区域
     * @return 文字内容
     */
    public static String readRectangelText(String filePath, int iPage, Rectangle textRrect) {

        String textContent = "";

        try(PDDocument pdfDoc = PDDocument.load(new File(filePath))) {
            // 获取指定的PDF页
            PDPage pdfPage = pdfDoc.getPage(iPage);

            // 获取指定位置的文字（文字剥离器）
            PDFTextStripperByArea textStripper = new PDFTextStripperByArea();
            textStripper.setSortByPosition(true);
            textStripper.addRegion(REGION_NAME, textRrect);
            textStripper.extractRegions(pdfPage);

            textContent = textStripper.getTextForRegion(REGION_NAME);

            // 释放资源
            pdfDoc.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return textContent;

    }

    /**
     * 根据指定文件页码的指定区域读取图片
     *
     * @param filePath PDF文件路径
     * @param iPage PDF页码
     * @param imgRrect 读取图片的区域
     * @return 图片内容
     */
    public static BufferedImage readRectangelImage(String filePath, int iPage, Rectangle imgRrect) {

        BufferedImage bufImage = null;
        try(PDDocument pdfDoc = PDDocument.load(new File(filePath))) {
            // 获取渲染器，主要用来后面获取BufferedImage
            PDFRenderer pdfRenderer = new PDFRenderer(pdfDoc);
            // 截取指定位置生产图片
            bufImage = pdfRenderer.renderImage(iPage).getSubimage(imgRrect.x,imgRrect.y,imgRrect.width,imgRrect.height);

            // 释放资源
            pdfDoc.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return bufImage;
    }


}
