package com.task.demo.controller;

import com.task.demo.dto.UniqueWordDto;
import com.task.demo.service.HTMLParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskController {

    private final HTMLParserService htmlParserService;

    @PostMapping("task")
    public List<UniqueWordDto> getWords(@RequestParam("url") String url) {

        return htmlParserService.getUniqueWords(url);

    }
}
