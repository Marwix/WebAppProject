
package com.group3.Assignment30.controller;

import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.views.CartBackingBean;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;


@Data
@Named
@SessionScoped
public class CartController implements Serializable{
    
    @Inject
    private CartBackingBean cartBackingBean;
    
    @EJB 
    private ProductDAO productDAO;
    
    public void addToCart(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String,String> params = facesContext.getExternalContext().getRequestParameterMap();
        String action = params.get("action");

         int id = Integer.parseInt(action);
         
         
        List<Product> products = productDAO.getProductByID(id);
        System.out.println(products);
        
        if(cartBackingBean.getCart() != null){
            HashMap<Product,Integer> cartItems = cartBackingBean.getCart();
            if(cartItems.containsKey(products.get(0))){
                cartItems.put(products.get(0), cartItems.get(products.get(0)) + 1);
            }else{
                cartItems.put(products.get(0), 1);
            }
            cartBackingBean.setCart(cartItems);
        }else{
            HashMap<Product,Integer> cartItems = new HashMap<>();
            cartItems.put(products.get(0), 1);
            cartBackingBean.setCart(cartItems);
        }
        
    }
    
    public void updateCartValue(List<Product> products){
        
        for(Product p : products){
            
        }
        
    }
    
    
}
