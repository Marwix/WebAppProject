package com.group3.Assignment30.controller;

import com.group3.Assignment30.model.dao.CouponDAO;
import com.group3.Assignment30.model.dao.CustomerDAO;
import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.dao.PurchaseDAO;
import com.group3.Assignment30.model.entity.Coupon;
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
import javax.faces.application.FacesMessage;
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
    private CouponDAO couponDAO;
    
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
        priceMultiplier = 1;
        checkoutBackingBean.setCoupon("");
            
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
        String code = checkoutBackingBean.getCoupon();
        
        List<Coupon> couponCodes = couponDAO.getCouponByCode(code);
        if (couponCodes.size() > 0)
        {
           if (priceMultiplier != 1) {
               if (priceMultiplier < couponCodes.get(0).getPriceMultiplier()) {
                   priceMultiplier = couponCodes.get(0).getPriceMultiplier();
                   System.out.println("Better code applied.");
                   sendNotification(FacesMessage.SEVERITY_INFO, "Better code applied!");
               }
           }
           else {
               priceMultiplier = couponCodes.get(0).getPriceMultiplier();
               sendNotification(FacesMessage.SEVERITY_INFO, "Coupon code applied!");
               System.out.println("Coupon code applied.");
           }  
        }
        else
        {
            sendNotification(FacesMessage.SEVERITY_INFO, "Not a valid code!");
            System.out.println("Not a valid code. ");
        }
    }
    
    public void sendNotification(FacesMessage.Severity severity, String message){
            FacesMessage fMessage = new FacesMessage();
            fMessage.setSeverity(severity);
            fMessage.setSummary(message);
            FacesContext.getCurrentInstance().addMessage(null,fMessage);
    }
    
    // Check if coupon is applied or not.
    public boolean CouponApplied() {
        return priceMultiplier != 1; 
    }
    
    // Remove item from checkoutpage cart.
    public void removeItemCart() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String,String> params = facesContext.getExternalContext().getRequestParameterMap();
        String id = params.get("action");
       
        HashMap<Product,Integer> listOfItemsCart = cartBackingBean.getCart();
        
        for (Product product : listOfItemsCart.keySet())
        {
            if (product.getProdoct_id() == Integer.parseInt(id))
            {
                listOfItemsCart.remove(product);
                cartBackingBean.setCart(listOfItemsCart);
            }
        }
        
        // Refresh
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest)ec.getRequest()).getRequestURI());
    }
}
