package com.task.demo.controller;

import com.task.demo.service.HTMLParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final HTMLParserService htmlParserService;

    @PostMapping("task")
    public void getWords(@RequestParam("url") String url){
        htmlParserService.getUniqueWords(url);
    }
}
