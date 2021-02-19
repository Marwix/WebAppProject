
package com.group3.Assignment30.views;

import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.entity.Product;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Data;

@Data
@Named
@ViewScoped
public class CarouselView implements Serializable{
    @EJB
    private ProductDAO productDAO;
    
    private List<Product> products;
    
    @PostConstruct
    private void init() {
        products = new ArrayList<>(productDAO.getXUniqueProducts(3));
    }
}
