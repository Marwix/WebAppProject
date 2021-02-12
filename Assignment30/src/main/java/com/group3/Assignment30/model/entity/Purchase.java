/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.Assignment30.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Purchase implements Serializable{
    @Id
    private int order_id;
    
    private LocalDate time;
    
    @ManyToOne //gör klasserna så enkla som möjligt!!
    private Customer customer;
    
    //aldrig typen i namn dålig form!!!!!!
    @ManyToMany() //försök att skapa 1 relation åtgången!!
    @JoinTable(name = "purchesehistory",
            joinColumns = @JoinColumn(name = "purchese"),
            inverseJoinColumns = @JoinColumn(name = "product"))
    private List<Product> products;
    
    //private BigDecimal TotalCost;
}
