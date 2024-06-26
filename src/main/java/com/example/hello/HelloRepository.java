package com.example.hello;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HelloRepository {

    public final String space = " ";
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("null")
    public Map<String, Object> findById(String id) {

    StringBuilder query = new StringBuilder();
    query.append("SELECT *").append(space);
    query.append("FROM employee").append(space);
    query.append("WHERE id=?");

    // 検索実行
    return jdbcTemplate.queryForMap(query.toString(), id);
    }
}
