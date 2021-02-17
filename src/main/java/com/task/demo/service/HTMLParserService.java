package com.task.demo.service;

import com.task.demo.dto.UniqueWordDto;

import java.util.List;

public interface HTMLParserService {

    List<UniqueWordDto> getUniqueWords(String url);

}
