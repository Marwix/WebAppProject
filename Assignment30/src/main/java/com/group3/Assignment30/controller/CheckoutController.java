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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
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
    private CouponDAO couponDAO;
    
    @EJB
    private CustomerDAO customerDAO;

    @EJB
    private PurchaseDAO purchaseDAO;

    //loads customer info and sends it to backingbean
    @PostConstruct
    public void init() 
    {
        try {
          int activeUserID = (int) sessionContextController.getAttribu("user_id");
            List<Customer> customerInfo = customerDAO.getUserInformationByID(activeUserID);

            Customer customer = customerInfo.get(0);
            
            //setting customer in checkout to correct info
            checkoutBackingBean.setCustomer(customer);
        } 
        catch (NullPointerException e) {
        System.err.println("User not logged in. So the website cant get its userID from SessionContext");
        };
    } 
    
   public void payNow() throws IOException {
       HashMap<Product,Integer> cartInventory = checkoutBackingBean.getCart().getCartInventory();
       double price;
       
       if (cartInventory.isEmpty())
           
           return;

       Customer customer = checkoutBackingBean.getCustomer();
      int orderidForThisPurchase = purchaseDAO.getMaxOrderID() + 1;

        
       for (Product product : cartInventory.keySet()) {
          Purchase purchase = new Purchase();
          
          purchase.setOrder_id(orderidForThisPurchase);
          if(checkoutBackingBean.CouponApplied()){
              price = product.getPrice()* product.getPriceMultiplier() * checkoutBackingBean.getCoupon().getPriceMultiplier();
              BigDecimal bd = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
              price = bd.doubleValue();
           purchase.setPrice(price);
          }else{
              price = product.getPrice()* product.getPriceMultiplier();
              BigDecimal bd = new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
              price = bd.doubleValue();
              purchase.setPrice(price);
          }
          purchase.setCustomer(customer);
          purchase.setProducts(product);
          purchase.setTime(LocalDate.now());
          purchase.setCount(cartInventory.get(product));
          purchaseDAO.create(purchase);
      }
       
      // Reset states.
      if(checkoutBackingBean.resetCartAndCoupon()){
          // Go to payment result and forward to product/main page again.
          ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
          ec.redirect(ec.getRequestContextPath() + "/" + "paymentresult.xhtml");
          cartBackingBean.setCount(0);
      }
    }
   
    // Check if coupon code is valid.
    public void checkValidCoupon()
    {
        String code = checkoutBackingBean.getCoupon().getCouponCode();
        List<Coupon> couponCode = couponDAO.getCouponByCode(code);
        
        if (couponCode.size() > 0)   
        {
           String confirm =  checkoutBackingBean.applyCoupon(couponCode.get(0));
            if (confirm.equals("Coupon code applied!")) {
                sendNotification(FacesMessage.SEVERITY_INFO, "Coupon code applied!");
            }else if (confirm.equals("Better code applied!")) {
                sendNotification(FacesMessage.SEVERITY_INFO, "Better code applied!");
            }
        } 
        else {
            sendNotification(FacesMessage.SEVERITY_INFO, "Not a valid code!");
        }
    }
    
    // Send message about invalid input.
    public void sendNotification(FacesMessage.Severity severity, String message){
            FacesMessage fMessage = new FacesMessage();
            fMessage.setSeverity(severity);
            fMessage.setSummary(message);
            FacesContext.getCurrentInstance().addMessage(null,fMessage);
    }
    
    // Remove item from checkoutpage cart.
    public void removeItemCart() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String,String> params = facesContext.getExternalContext().getRequestParameterMap();
        String id = params.get("action");
       
        List<Product> product = productDAO.getProductByID(Integer.parseInt(id));
        
        // Remove amount of items from cart icon.
        cartBackingBean.removeItemFromCart(product.get(0));
        
        // Send the product id to remove from cart.
        checkoutBackingBean.removeItemCart(product.get(0));
   }
}
