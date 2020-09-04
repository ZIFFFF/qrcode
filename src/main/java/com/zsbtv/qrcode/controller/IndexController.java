package com.zsbtv.qrcode.controller;

import com.zsbtv.qrcode.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;


/**
 * @author ZZF
 * @date 2020/9/3
 */

@Controller
public class IndexController {

    @Autowired
    private ScanService scanService;

    @RequestMapping("/index")
    public String Index() {
        return "index";
    }

    @RequestMapping("/QRcodeDecode")
    @ResponseBody
    public HashMap<String, Object> QRcodeDecode(MultipartHttpServletRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        String file = request.getParameter("img");
        try {
            if (scanService.Base64ToImage(file, "tmp.jpg")) {
                result.put("status", 200);
                result.put("data", scanService.decodeBar("tmp.jpg"));
                return result;
            } else {
                result.put("status", 400);
                result.put("data", "Unrecognized");
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", 400);
            result.put("data", "The picture is not clear");
            return result;
        }
    }


}
