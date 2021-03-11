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
import com.group3.Assignment30.service.MessageCenter;
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
    
    
    
    public void addProduct(){
        Product product = adminBackinBean.getProduct();
        
        if (product.getPrice() <= 0){
            MessageCenter.SendPageMessage(FacesMessage.SEVERITY_ERROR, "Price must be >0", "productForm:product-price");
            return;
        }
        
        if (product.getFullStar() < 1 || product.getFullStar() > 5 ) {
            MessageCenter.SendPageMessage(FacesMessage.SEVERITY_ERROR, "Stars must be between 1-5 (inclusive)", "productForm:stars");
            return;
        }
        
        if (productExists(product)){
            MessageCenter.SendPageMessage(FacesMessage.SEVERITY_ERROR, "Product already exists in database");
            return;
        }
        
        try {
            productDAO.create(product);
            MessageCenter.SendPageMessage(FacesMessage.SEVERITY_INFO, "Successfully added product to database");
            
        } catch (Exception e) {
            MessageCenter.SendPageMessage(FacesMessage.SEVERITY_FATAL, "Error while adding product");
            System.out.println("###########################################");
            System.out.println("ERROR ENCOUNTERED IN AdminController");
            System.out.println("###########################################");
            e.printStackTrace();
        }
        adminBackinBean.setProduct(new Product());
    }
    
    public void addCoupon(){
        
        // move to view
        for (Coupon c: adminBackinBean.getCouponList()){
            if(c.getCouponCode().equals(adminBackinBean.getCoupon().getCouponCode())){
                MessageCenter.SendPageMessage(FacesMessage.SEVERITY_ERROR, "Coupon code already used!");
                return;
            }
        }
        // Intentionally ugly to get prettier DB values
        couponDAO.createCoupon(adminBackinBean.getCoupon().getCouponCode(), (int) adminBackinBean.getCoupon().getPriceMultiplier());
        refreshCoupons();
    }
    
    public void removeCoupon() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
        String couponCode = params.get("action");
        
        couponDAO.deleteCouponByCouponCode(couponCode);
        refreshCoupons();
    }
    
    public void addSale(){
        if (productDAO.getProductByID(adminBackinBean.getProduct().getProdoct_id()).size() != 1) {
            MessageCenter.SendPageMessage(FacesMessage.SEVERITY_ERROR, "Product does not exist!", "sales-form:product-id");
            return;
        }
        long res = productDAO.setProductSale(adminBackinBean.getProduct().getProdoct_id(), (int)adminBackinBean.getProduct().getPriceMultiplier());
        if (res == 1L) {
            MessageCenter.SendPageMessage(FacesMessage.SEVERITY_INFO, "Sale successfully added!");
        } else {
            MessageCenter.SendPageMessage(FacesMessage.SEVERITY_ERROR, "Unable to add sale " + res + " products were edited");
            return;
        }
        refreshProducts();
    }
    
    public void removeSale(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
        String product_id = params.get("prod_id");
        
        productDAO.setProductSale(Integer.parseInt(product_id), 0);
        refreshProducts();
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
