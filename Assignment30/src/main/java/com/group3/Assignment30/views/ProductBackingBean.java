package com.group3.Assignment30.views;


import com.group3.Assignment30.model.entity.Product;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;
import lombok.Data;


@Data
@Named
@ViewScoped
public class ProductBackingBean implements Serializable {
    
    
    @NotEmpty private List<Product> products; 
    private boolean sortToggleOrder = false;
    private String searchString;
    
    public void orderProductsByPrice(){
        if (sortToggleOrder)
            products.sort((Product p1, Product p2) -> (int)(p2.getPrice()*p2.getPriceMultiplier())-(int)(p1.getPrice()*p1.getPriceMultiplier()));
        else 
            products.sort((Product p1, Product p2) -> (int)(p1.getPrice()*p1.getPriceMultiplier())-(int)(p2.getPrice()*p2.getPriceMultiplier()));
        sortToggleOrder = !sortToggleOrder;
    }
    
    public void orderProductsByStars(){
        if (sortToggleOrder)
            products.sort((Product p1, Product p2) -> p2.getFullStar()-p1.getFullStar());
        else 
            products.sort((Product p1, Product p2) -> p1.getFullStar()-p2.getFullStar());
        sortToggleOrder = !sortToggleOrder;
    }
    
    public void orderProductsDateAdded(){
        if (sortToggleOrder)
            products.sort((Product p1, Product p2) -> p2.getProdoct_id()-p1.getProdoct_id());
        else 
            products.sort((Product p1, Product p2) -> p1.getProdoct_id()-p2.getProdoct_id());
        sortToggleOrder = !sortToggleOrder;
    }
    
    
    
}
