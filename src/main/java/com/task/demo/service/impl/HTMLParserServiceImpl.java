package com.task.demo.service.impl;

import com.task.demo.dto.UniqueWordDto;
import com.task.demo.entity.UniqueWord;
import com.task.demo.repository.UniqueWordRepository;
import com.task.demo.service.HTMLParserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HTMLParserServiceImpl implements HTMLParserService {


    private static final String FILE_NAME = "myFile.txt";
    private final static String SEPARATOR = "[.,!\\?\";:\\[\\]()_<>^'{}|«»…·/\\n\\r\\t]";
    private String lastUrl = null;

    private final UniqueWordRepository uniqueWordRepository;

    @Transactional
    @Override
    @SneakyThrows
    public List<UniqueWordDto> getUniqueWords(String url) {
        if (!lastUrl.equals(url)) {
            lastUrl = url;
            try {
                savePage(url);
                extractText();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }
        }

        return uniqueWordRepository.findAll().stream().map(word -> {
            UniqueWordDto uniqueWordDto = new UniqueWordDto();
            uniqueWordDto.setName(word.getName());
            uniqueWordDto.setCount(word.getCount());
            return uniqueWordDto;
        }).collect(Collectors.toList());
    }

    private void savePage(String url) throws IOException {
        //Очищаем файл
        try (FileWriter writer = new FileWriter(FILE_NAME, false)) {
            writer.write("");
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            URL myUrl = new URL(url);
            String line = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(myUrl.openStream()));
            try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
                while ((line = in.readLine()) != null) {
                    writer.write(line);
                }
                writer.flush();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                throw ex;
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private void extractText() throws IOException {
        Map<String, Integer> uniqueWords = new HashMap<>();
        Arrays.stream(Jsoup.parse(new File("myFile.txt"), "UTF-8").text()
                .replaceAll(SEPARATOR, " ").toUpperCase()
                .split(" ")).collect(Collectors.toList()).forEach(word -> {
            if (uniqueWords.containsKey(word)) {
                Integer count = uniqueWords.get(word) + 1;
                uniqueWords.replace(word, count);
            } else {
                uniqueWords.put(word, 1);
            }
        });

        uniqueWords.remove("");
        uniqueWordRepository.deleteAll();
        uniqueWords.forEach((key, value) -> {
            UniqueWord word = new UniqueWord();
            word.setName(key);
            word.setCount(value);
            uniqueWordRepository.save(word);
        });
    }
}
