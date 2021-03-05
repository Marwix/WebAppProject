
package com.group3.Assignment30.controller;

import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.views.CartBackingBean;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import lombok.Data;


@Data
@Named
@SessionScoped
public class CartController implements Serializable{
    
    @Inject
    private CartBackingBean cartBackingBean;
    
    @EJB 
    private ProductDAO productDAO;
    private int countItems;
    
    // Add product from productpage to cart.
    public void addToCart() throws IOException{
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
        
        // Add number to cart.
        countItems++;
        
        // Refresh current page.
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest)ec.getRequest()).getRequestURI());
    }
    
    // Update amount of items added to cart on icon.
    public String getAmount() {
        if (countItems > 0) {
            return String.valueOf(countItems);
        }
        else {
            return "";
        }
    }
    
    public int amount() {
        return this.countItems;
    }
    
    // Set amount of items (For reset or optional value).
    public void setAmount(int value) {
        countItems = value;
    }
}
