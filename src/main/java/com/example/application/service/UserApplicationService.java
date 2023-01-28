package com.example.application.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class UserApplicationService {

    /** 性別のMapを生成する */
    public Map<String,Integer> getGenderMap() {
    
        Map<String, Integer> getGenderMap = new LinkedHashMap<>();
        getGenderMap.put("男性", 1);
        getGenderMap.put("女ぁ", 2);
        
        return getGenderMap;
    }
}
