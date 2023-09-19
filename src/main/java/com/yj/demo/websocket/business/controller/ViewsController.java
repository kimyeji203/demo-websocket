package com.yj.demo.websocket.business.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewsController
{
    @GetMapping("/simple/chat")
    public String getSimpleChat()
    {
        return "simple_chat";
    }

}
