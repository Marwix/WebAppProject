package com.group3.Assignment30.controller;

import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.views.WishListBackingBean;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;

@Data
@Named
@SessionScoped
public class WishListController implements Serializable{
    
    @EJB
    private ProductDAO productDAO;
        
    @Inject 
    private WishListBackingBean wishListBackingBean;

    @PostConstruct
    public void init() {
        addToWishList();
    }
    
    public void addToWishList(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String,String> params = facesContext.getExternalContext().getRequestParameterMap();
        String action = params.get("action");            
        
        
        try 
        { 
            int id = Integer.parseInt(action);
            List<Product> products = productDAO.getProductByID(id);
            
            wishListBackingBean.addItemToWishList(products.get(0));
            
        } catch (NumberFormatException nfe) 
        {
            nfe.printStackTrace();
        }


  
        //wishListBackingBean.addItemToWishList(products.get(0));
        
    }
    
    
}
