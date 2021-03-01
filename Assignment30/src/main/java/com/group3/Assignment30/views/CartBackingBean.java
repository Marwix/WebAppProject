
package com.group3.Assignment30.views;


import com.group3.Assignment30.model.entity.Product;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.print.attribute.HashAttributeSet;
import javax.validation.constraints.NotEmpty;
import lombok.Data;


@Data
@Named
@SessionScoped
public class CartBackingBean implements Serializable{
    @NotEmpty private HashMap<Product,Integer> cart;
    private int id;
    private int uniqueItems;
    
    
}
