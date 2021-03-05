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
    
}
