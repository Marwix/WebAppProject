package com.group3.Assignment30.model.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable{
    
    @Id
    @GeneratedValue
    public int prodoct_id;

    private String product_name;
    private double price;
    private double priceMultiplier = 1;
    // Image? clob and blob

    private int fullStar;

    private String color;
    private String measurements;
    private String weight;
    private String description;

}