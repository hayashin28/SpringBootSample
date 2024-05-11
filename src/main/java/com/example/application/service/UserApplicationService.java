package com.example.application.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.example.domain.user.model.MUser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserApplicationService {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ResourceLoader resourceLoader;

    /** ファイル保存先 */
    private String filePath = "C:\\work";

    private static final String SEPARATOR = File.separator;

    /** 性別のMapを生成する */
    @SuppressWarnings("null")
    public Map<String, Integer> getGenderMap(Locale locale) {
        Map<String, Integer> genderMap = new LinkedHashMap<>();
        String male = messageSource.getMessage("male", null, locale);
        String female = messageSource.getMessage("female", null, locale);
        genderMap.put(male, 1);
        genderMap.put(female, 2);
        return genderMap;
    }

    /** ユーザーリストをCSVに保存する */
    public void saveUserCsv(List<MUser> userList, String fileName) throws IOException {
  
        // ファイル保存先パス作成
        Path path = Paths.get(filePath + SEPARATOR + fileName);
        log.info("savePath={}", path.toAbsolutePath());

     }

    /** CSVファイル取得. */
    public byte[] getCsv(String fileName) throws IOException {
        // パス
        String path = "file:" + filePath + SEPARATOR + fileName;

        // ファイル取得
        Resource resource = resourceLoader.getResource(path);
        File file = resource.getFile();
        log.info("readPath={}", file.toPath().toAbsolutePath());

        // byte配列取得
        return Files.readAllBytes(file.toPath());
    }

    /** 部署リストのCSVを作成する */
    public void saveDepartmentCsv(List<MUser> userList, String fileName) throws IOException {

        // ファイル保存先パス作成
        Path path = Paths.get(filePath + SEPARATOR + fileName);
        log.info("savePath={}", path.toAbsolutePath());

    }

    /** InputStream取得 */
    public InputStream getInputStream(String fileName) throws IOException {
        // パス
        String path = "file:" + filePath + SEPARATOR + fileName;
        // Resource取得
        Resource resource = resourceLoader.getResource(path);
        // InputStream取得
        return resource.getInputStream();
    }
}
