package com.group3.Assignment30.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
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
    // Image? clob and blob

    //Assuming we can do loops 
    private int fullStar;

    private String color;
    private String measurements;
    private String weight;
    private String description;

}