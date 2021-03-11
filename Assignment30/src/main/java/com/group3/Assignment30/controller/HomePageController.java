
package com.group3.Assignment30.controller;

import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.views.HomePageBackingBean;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;

@Data
@Named
@ViewScoped
public class HomePageController implements Serializable{
    @EJB
    private ProductDAO productDAO;
    
    @Inject
    private HomePageBackingBean homePageBackingBean;
    
    @PostConstruct
    public void init() {
        List<Product> products = productDAO.getHighestRatingProduct(4);
        homePageBackingBean.setProducts(products);
       
    }
    
}
