package com.example.domain.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "m_department")
public class Department {
    @Id
    private Integer departmentId;
    private String departmentName;

    /** CSV文字列の作成. */
    public String toCsv() {
        String csv = departmentId + ", " + departmentName + "\r\n";
        return csv;
    }
}
