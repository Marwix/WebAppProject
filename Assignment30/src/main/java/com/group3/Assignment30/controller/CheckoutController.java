package com.group3.Assignment30.controller;

import com.group3.Assignment30.model.dao.CustomerDAO;
import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.dao.PurchaseDAO;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.model.entity.Purchase;
import com.group3.Assignment30.views.CartBackingBean;
import com.group3.Assignment30.views.CheckoutBackingBean;
import java.io.IOException;
import javax.faces.context.ExternalContext;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import lombok.Data;
import org.primefaces.PrimeFaces;

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
    private double priceMultiplier = 1;
    
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
        } 
        catch (NullPointerException e) 
        {
           HashMap<Product,Integer> listProducts = new HashMap<Product,Integer>();
            checkoutBackingBean.setProducts(listProducts);   
        }     
    } 
    
    public void addOrder() throws IOException {
        if (checkoutBackingBean.getProducts().size() == 0)
            return;
        
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
            
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(ec.getRequestContextPath() + "/" + "paymentResult.xhtml");
    }
    
    // Retrieve all items added to cart and display them at checkout.
    public List<Product> getKeyList()
    {  try {
            
        return new ArrayList<>(checkoutBackingBean.getProducts().keySet());
        } catch (NullPointerException e) {
            return new ArrayList<Product>();
        }
    }
  
    
    // Count the number of products available. 
    public int getCount(Product product)
    {
        HashMap<Product,Integer> listOfProducts = checkoutBackingBean.getProducts();
        return listOfProducts.get(product);
    }
    
    // Add price of all items in cart and return total.
    public double getTotalPrice()
    {
        double totalPrice = 0;
        List<Product> items = getKeyList();
        for (Product s : items)   
        {
            totalPrice += s.getPrice();
        }
        return totalPrice;
    }
    
    // Check if coupon code is valid.
    public void checkValidCoupon()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String,String> params = facesContext.getExternalContext().getRequestParameterMap();
        String couponCode = params.get("action");
        
        // Test data.
        priceMultiplier = 0.8;
    }
    
    public boolean CouponApplied() {
        return priceMultiplier != 1; 
    }
}
