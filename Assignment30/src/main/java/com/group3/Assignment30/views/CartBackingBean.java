
package com.group3.Assignment30.views;


import com.group3.Assignment30.model.entity.Product;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;
import lombok.Data;


@Data
@Named
@SessionScoped
public class CartBackingBean implements Serializable{
    @NotEmpty private List<Product> products;
    
    private int id;
    private int uniqueItems;
    
    
}
