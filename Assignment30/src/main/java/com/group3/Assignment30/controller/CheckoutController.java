package com.group3.Assignment30.controller;

import com.group3.Assignment30.model.dao.CustomerDAO;
import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.dao.PurchaseDAO;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.model.entity.Purchase;
import com.group3.Assignment30.views.CartBackingBean;
import com.group3.Assignment30.views.CheckoutBackingBean;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;

@Data
@Named
@SessionScoped
public class CheckoutController implements Serializable {
    
    @Inject
    private CheckoutBackingBean checkoutBackingBean;
    
    @Inject
    private CartBackingBean cartBackingBean;
    
    private int activeUserID;
    private SessionContextController sessionContextController = SessionContextController.getInstance();
    
    @EJB
    private ProductDAO productDAO;
    
    @EJB
    private CustomerDAO customerDAO;

    @EJB
    private PurchaseDAO purchaseDAO;
    
    private Customer customer;
    
    @PostConstruct
    public void init() 
    {
        try {
            
            activeUserID = (int) sessionContextController.getAttribu("user_id");
            List<Customer> customerInfo = customerDAO.getUserInformationByID(activeUserID);

            customer = customerInfo.get(0);
            checkoutBackingBean.setEmail(customer.getEmail());
            checkoutBackingBean.setFirstname(customer.getFirst_name());
            checkoutBackingBean.setLastname(customer.getLast_name());
            checkoutBackingBean.setAddress(customer.getAdress());
            checkoutBackingBean.setCity(customer.getCity());
            checkoutBackingBean.setPostcode(customer.getPostal_code());
            checkoutBackingBean.setLastname(customer.getLast_name());
            checkoutBackingBean.setPhonenumber(customer.getPhonenumber());
            
            // Add test data to grid table
            HashMap<Product,Integer> listProducts = cartBackingBean.getCart();
            checkoutBackingBean.setProducts(listProducts);     
        } catch (Exception e) {
        }     
    } 
    
    public void addOrder() {
        HashMap<Product,Integer> listOfProducts = checkoutBackingBean.getProducts();
        int orderidForThisPurchase = purchaseDAO.getMaxOrderID() + 1;
        
        for (Product product : listOfProducts.keySet()) {
            Purchase purchase = new Purchase();
            
            purchase.setOrder_id(orderidForThisPurchase);
            purchase.setCustomer(customer);
            purchase.setProducts(product);
            purchase.setTime(LocalDate.now());
            purchase.setCount(listOfProducts.get(product));
            
            
            purchaseDAO.create(purchase);
        }
        
        cartBackingBean.setCart(new HashMap<Product, Integer>());
        checkoutBackingBean.setProducts(new HashMap<Product, Integer>());
    }
    
    public List<Product> getKeyList(){
        return new ArrayList<>(checkoutBackingBean.getProducts().keySet());
    }
    public int getCount(Product product){
        HashMap<Product,Integer> listOfProducts = checkoutBackingBean.getProducts();
        return listOfProducts.get(product);
    }
    
}
