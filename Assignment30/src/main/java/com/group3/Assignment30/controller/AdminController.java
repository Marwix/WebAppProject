/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.group3.Assignment30.controller;

import com.group3.Assignment30.model.dao.CouponDAO;
import com.group3.Assignment30.model.dao.CustomerDAO;
import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.entity.Coupon;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.views.AdminBackingBean;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
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
    @EJB
    private CouponDAO couponDAO;
    
    private SessionContextController sessionContextController = SessionContextController.getInstance();
    private int activeUserID;
    
    
    @PostConstruct
    public void init(){
        refreshProducts();
        refreshCoupons();
        
    }
    
    private void refreshCoupons(){
        adminBackinBean.setCouponList(couponDAO.findAll());
    }
    
    private void refreshProducts(){
        adminBackinBean.setProductList(productDAO.findAll());
    }
    
    
    // Assert that the logged in user is Admin
    public boolean authorize(){
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
            return;
        }
        product.setFullStar(adminBackinBean.getStars());
        
        product.setColor(adminBackinBean.getColor());
        product.setMeasurements(adminBackinBean.getMeasurements());
        product.setWeight(adminBackinBean.getWeight());
        product.setDescription(adminBackinBean.getDescription());
        
        if (productExists(product)){
            sendWarning(FacesMessage.SEVERITY_ERROR, "Product already exists in database");
            return;
        }
        
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
        clearForm();
    }
    
    public void addCoupon(){
        couponDAO.createCoupon(adminBackinBean.getNewCouponCode(), adminBackinBean.getNewCouponMultiplier());
        refreshCoupons();
    }
    
    public void removeCoupon() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
        String couponCode = params.get("action");
        
        couponDAO.deleteCouponByCouponCode(couponCode);
        refreshCoupons();
    }
    
    private void sendWarning(FacesMessage.Severity severity, String message){
            FacesMessage fMessage = new FacesMessage();
            fMessage.setSeverity(severity);
            fMessage.setSummary(message);
            FacesContext.getCurrentInstance().addMessage(null,fMessage);
    }
    
    private boolean productExists(Product product){
        List<Product> existinProducts = productDAO.getProductByName(product.getProduct_name());
        for (Product p: existinProducts){
            if (p.getProduct_name().equals(product.getProduct_name()) &&
                p.getColor().equals(product.getColor()) &&
                p.getPrice() == product.getPrice() &&
                p.getDescription().equals(product.getDescription()))
                return true;
            }
        return false;
        }
}
