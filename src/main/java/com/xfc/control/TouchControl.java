package com.xfc.control;

import com.xfc.untils.PdfReader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TouchControl {

    @RequestMapping("/test")
    public String test(){
        return "hello";
    }

    @RequestMapping("/pdfout")
    public void pdfOut(){
        PdfReader pdfReader = new PdfReader();
        pdfReader.pdfFindOut();
    }
}
