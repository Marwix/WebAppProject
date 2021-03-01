package com.group3.Assignment30.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    public int prodoct_id;

    private String product_name;
    private int price;
    // Image? clob and blob

    //Assuming we can do loops 
    private int fullStar;
    private int halfStar;

    private String color;
    private String height;
    private String width;
    private String length;
    private String weight;
    private String value;
    private String description;

}