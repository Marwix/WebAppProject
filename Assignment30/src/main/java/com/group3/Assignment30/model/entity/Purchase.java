
package com.group3.Assignment30.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Purchase implements Serializable{

    private int order_id;
    
    private LocalDate time;
    
    @ManyToOne 
    private Customer customer;
    

    private Product products;
    
    private int count;
    
    private double price = 0;
    
    @Id
    @GeneratedValue
    private long id;
}
