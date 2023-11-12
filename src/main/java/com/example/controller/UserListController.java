package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.application.service.UserApplicationService;
import com.example.domain.user.model.MUser;
import com.example.domain.user.service.UserService;
import com.example.form.UserListForm;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserListController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserApplicationService appService;

    /** ユーザー一覧画面を表示 */
    @GetMapping("/list")
    public String getUserList(@ModelAttribute UserListForm form, Model model) {

        // formをMUserクラスに変換
        MUser user = modelMapper.map(form, MUser.class);

        // ユーザー一覧取得
        List<MUser> userList = userService.getUsers(user);

        // Modelに登録
        model.addAttribute("userList", userList);

        // ユーザー一覧画面を表示
        return "user/list";
    }

    /** ユーザー検索処理 */
    @PostMapping("/list")
    public String postUserList(@ModelAttribute UserListForm form, Model model) {

        // formをMUserクラスに変換
        MUser user = modelMapper.map(form, MUser.class);

        // ユーザー検索
        List<MUser> userList = userService.getUsers(user);

        // Modelに登録
        model.addAttribute("userList", userList);

        // ユーザー一覧画面を表示
        return "user/list";
    }

    /** ユーザー一覧ダウンロード処理 */
    @PostMapping("/list/download")
    public ResponseEntity<byte[]> downloadUserList(@ModelAttribute UserListForm form)
            throws IOException {
        // formをMUserクラスに変換
        MUser user = modelMapper.map(form, MUser.class);

        // ユーザー検索
        List<MUser> userList = userService.getUsers(user);

        // CSVファイル保存
        String fileName = "user.csv";
        appService.saveUserCsv(userList, fileName);

        // CSVファイル取得
        byte[] bytes = appService.getCsv(fileName);

        HttpHeaders header = new HttpHeaders();

        // HTTPヘッダーの設定
        header.add("Content-Type", MediaType.ALL_VALUE + "; charset=utf-8");
        header.setContentDispositionFormData("filename", fileName);

        return new ResponseEntity<>(bytes, header, HttpStatus.OK);
    }

    /** zipファイルダウンロード処理 */
    @PostMapping("/list/download/zip")
    public void downloadZip(@ModelAttribute UserListForm form, HttpServletResponse response)
            throws IOException {
        // formをMUserクラスに変換
        MUser user = modelMapper.map(form, MUser.class);

        // ユーザー検索
        List<MUser> userList = userService.getUsers(user);

        List<String> fileNameList = new ArrayList<>();
        // ユーザーCSVファイル保存
        String userFileName = "user.csv";
        appService.saveUserCsv(userList, userFileName);
        fileNameList.add(userFileName);

        // 部署CSVファイル保存
        String departmentFileName = "department.csv";
        appService.saveDepartmentCsv(userList, departmentFileName);
        fileNameList.add(departmentFileName);

        // ヘッダー設定
        String zipFileName = "sample.zip";
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + zipFileName);

        // zipファイルダウンロード
        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
            for (String fileName : fileNameList) {
                try (InputStream is = appService.getInputStream(fileName)) {
                    // zipファイルに格納
                    zos.putNextEntry(new ZipEntry(fileName));
                    StreamUtils.copy(is, zos);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
