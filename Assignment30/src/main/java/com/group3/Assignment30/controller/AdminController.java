/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.Assignment30.controller;

import com.group3.Assignment30.model.dao.CustomerDAO;
import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.views.AdminBackingBean;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;

@Data
@Named
@ViewScoped
public class AdminController implements Serializable{
    
    @Inject
    private AdminBackingBean adminBackinBean;
    
    @EJB
    private ProductDAO productDAO;
    @EJB 
    private CustomerDAO customerDAO;
    
    private SessionContextController sessionContextController = SessionContextController.getInstance();
    private int activeUserID;
    
    // Prepare to display products at the bottom
    @PostConstruct
    public void init(){
        List<Product> products = productDAO.findAll();
        adminBackinBean.setProductList(products);
    }
    
    
    // Assert that the logged in user is Admin
    public boolean authorize(){
        System.out.println("Authorizing");
        try {
            activeUserID = (int) sessionContextController.getAttribu("user_id");
        } catch (Exception e) {
            return false;
        }
        List<Customer> customerInfo = customerDAO.getUserInformationByID(activeUserID);
        
        return customerInfo.get(0).isAdminAccess();
    }
    
    public void clearForm(){
        
        adminBackinBean.setProductName("");
        adminBackinBean.setPrice(0);
        adminBackinBean.setStars(0);
        adminBackinBean.setColor("");
        adminBackinBean.setMeasurements("");
        adminBackinBean.setWeight("");
        adminBackinBean.setDescription("");
        
    }
    
    public void addProduct(){
        Product product = new Product();
        product.setProduct_name(adminBackinBean.getProductName());
        
        if (adminBackinBean.getPrice() <= 0){
            sendWarning(FacesMessage.SEVERITY_ERROR, "Price must be >0");
            return;
        }
        product.setPrice(adminBackinBean.getPrice());
        
        if (adminBackinBean.getStars() < 1 || adminBackinBean.getStars() > 5 ) {
            sendWarning(FacesMessage.SEVERITY_ERROR, "Stars must be within 1-5 range");
        }
        product.setFullStar(adminBackinBean.getStars());
        
        product.setColor(adminBackinBean.getColor());
        product.setMeasurements(adminBackinBean.getMeasurements());
        product.setWeight(adminBackinBean.getWeight());
        product.setDescription(adminBackinBean.getDescription());
        
        try {
            productDAO.create(product);
            sendWarning(FacesMessage.SEVERITY_INFO, "Successfully added product to database");
            
        } catch (Exception e) {
            sendWarning(FacesMessage.SEVERITY_FATAL, "Error while adding product");
            System.out.println("###########################################");
            System.out.println("ERROR ENCOUNTERED IN AdminController ROW 73");
            System.out.println("###########################################");
            System.out.println(e.getStackTrace());
        }
            
    }
    
    private void sendWarning(FacesMessage.Severity severity, String message){
            FacesMessage fMessage = new FacesMessage();
            fMessage.setSeverity(severity);
            fMessage.setSummary(message);
            FacesContext.getCurrentInstance().addMessage(null,fMessage);
    }
}
