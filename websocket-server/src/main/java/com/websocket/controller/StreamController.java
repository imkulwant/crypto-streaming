package com.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StreamController {

    @GetMapping("/stream")
    public String streamingPage(Model model) {
        return "index";
    }

}
