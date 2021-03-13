package com.group3.Assignment30.model.models;

import com.group3.Assignment30.model.entity.Product;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Named
@Singleton
public class Cart {
    @NotEmpty private HashMap<Product,Integer> cartInventory;
    
    @PostConstruct
    public void init() {
        cartInventory = new HashMap<Product,Integer>();
    }
    private int uniqueItems;
    
}
