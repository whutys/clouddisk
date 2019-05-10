package cn.clouddisk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonsController {

    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/index")
    public String index2(){
        return "index";
    }
    @GetMapping("/commons")
    public String commons(){
        return "commons";
    }

    @GetMapping("/help")
    public String help(){
        return "help";
    }
}
