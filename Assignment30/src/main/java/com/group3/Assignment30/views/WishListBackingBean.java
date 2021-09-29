package com.group3.Assignment30.views;

import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.model.models.WishList;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lombok.Data;


@Data
@Named
@SessionScoped
public class WishListBackingBean implements Serializable {
    
    @EJB
    private WishList wishList;
    
    public void addItemToWishList (Product product){
        if (wishList.getWishListInventory() != null)
        {
            HashMap<Product,Integer> listItems = wishList.getWishListInventory();
            if(listItems.containsKey(product))
            {
                listItems.put(product, listItems.get(product) + 1);
            } 
            else
            {
                listItems.put(product, 1);
            }
            wishList.setWishListInventory(listItems);            
        }
        else 
        {
            HashMap<Product,Integer> listItems = new HashMap<>();
            listItems.put(product, 1);
            wishList.setWishListInventory(listItems);
        }
    }
    
    
    public List<Product> getKeyList(){  
        try 
        {    
            return new ArrayList<>(wishList.getWishListInventory().keySet());
        } 
        catch (NullPointerException e) 
        {
            return new ArrayList<Product>();
        }
    }
    
}
    
    
    

