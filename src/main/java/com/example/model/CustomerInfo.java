package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfo {
    private String courseName;
    private Date purchasedDate;
    private int amount;
    private String location;
}
