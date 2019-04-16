package cn.clouddisk.controller;

import cn.clouddisk.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@RequiresUser
@Controller
public class ChatController {

    @GetMapping("/chat")
    public String chat(ModelMap map){
        map.put("fromUser", ShiroUtils.getUsername());
        return "chatroom";
    }
}
