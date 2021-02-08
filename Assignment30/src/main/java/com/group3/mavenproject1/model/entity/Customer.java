/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.mavenproject1.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable{
    @Id
    private int user_id;
    
    //Make unique
    @Column(unique=true)
    private String email;
    
    private String password;
    private String first_name;
    private String last_name;
    private String phonenumber;
    private String city;
    private String region;
    
    
}
