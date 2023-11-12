package com.example.domain.user.model;

import lombok.Data;
import java.util.Date;


@Data
public class MUser {

    private String  userId;
    private String  passWord;
    private String  userName;
    private Date    birthday;
    private Integer age;
    private Integer gender;
    private Integer departmentId;
    private String  role;

}
