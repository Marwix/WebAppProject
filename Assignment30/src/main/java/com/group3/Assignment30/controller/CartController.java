
package com.group3.Assignment30.controller;

import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.views.CartBackingBean;
import java.io.Serializable;
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
         
         System.out.println("" + id);
         
        List<Product> products = productDAO.getProductByID(id);
        System.out.println(products);
        
        try{
            List<Product> cartItems = cartBackingBean.getProducts();
            
            cartItems.add(products.get(0));
            cartBackingBean.setProducts(cartItems);
            
        }catch(Exception e){
            cartBackingBean.setProducts(products);
        }
        
        updateCartValue(cartBackingBean.getProducts());
    }
    
    public void updateCartValue(List<Product> products){
        
        for(Product p : products){
            
        }
        
    }
    
    
}
