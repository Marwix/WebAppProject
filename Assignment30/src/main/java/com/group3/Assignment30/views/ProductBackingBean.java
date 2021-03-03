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
    
    
    private int id;

    @NotEmpty private String product_name;
    private int price;
   
    private int fullStar;

    @NotEmpty private String color;
    @NotEmpty private String height;
    @NotEmpty private String width;
    @NotEmpty private String length;
    @NotEmpty private String weight;
    @NotEmpty private String value;
    @NotEmpty private String description;
    
    @NotEmpty private List<Product> products; 
    
}
