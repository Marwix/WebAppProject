
package com.group3.Assignment30.model.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Customer implements Serializable{
    
    @Id
    @GeneratedValue
    private int user_id;
    
    private String first_name;
    private String last_name;

    @Column(unique=true)
    private String email;
    private String password;
    private String phonenumber;
    private String city;
    private String adress;
    private String postal_code;
    private byte[] salt;
    private boolean adminAccess = false;
}
