/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.Assignment30.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable{
    
    @Id
    @GeneratedValue
    private int user_id;
    
    private String first_name;
    private String last_name;
    //Make unique
    @Column(unique=true)
    private String email;
    private String password;
    private String phonenumber;
    private String city;
    private String adress;
    private String postal_code;
    
     //alt: 2
    //private List<Order> order_list;
}
