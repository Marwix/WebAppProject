
package com.group3.Assignment30.views;
import com.group3.Assignment30.model.models.Cart;
import com.group3.Assignment30.model.entity.Coupon;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.model.entity.Product;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lombok.Data;

@Data
@Named
@SessionScoped
public class CheckoutBackingBean implements Serializable {
    
    private Customer customer;
    private Coupon coupon;
    @EJB
    private Cart cart;
    
    @PostConstruct
    public void Init(){
        coupon = new Coupon();
        coupon.setCouponCode("");
        coupon.setPriceMultiplier(1);
    }
    
    public List<Product> getKeyList()
    {  try {
            
        return new ArrayList<>(cart.getCartInventory().keySet());
        } catch (NullPointerException e) {
            return new ArrayList<Product>();
        }
    }
    
    public int getCount(Product product)
    {
        return cart.getCartInventory().get(product);
    }
    
    public void removeItemCart(Product product){
        if(cart.getCartInventory().containsKey(product)){
            cart.getCartInventory().remove(product);
        }

        if (cart.getCartInventory().isEmpty()) {
            coupon = new Coupon();
            coupon.setCouponCode("");
            coupon.setPriceMultiplier(1);
        }
    }
    
    public String applyCoupon(List<Coupon> couponCode)
    {
        Coupon newCoupon = couponCode.get(0);

           if (coupon.getPriceMultiplier() != 1) {
               
               if (coupon.getPriceMultiplier() < newCoupon.getPriceMultiplier()) {
                   coupon = newCoupon;
                   return "Better code applied!";
               }
           }else {
               coupon = newCoupon;
               return "Coupon code applied!";
           }  
           return "";
    }
    
    // Check if coupon is applied or not.
    public boolean CouponApplied() {
        return coupon.getPriceMultiplier() != 1; 
    }
    
      //Add price of all items in cart and return total.
    public double getTotalPrice()
    {
        double totalPrice = 0;
        
        List<Product> items = getKeyList();
        
        for (Product s : items)   
        {
            totalPrice += s.getPrice() * s.getPriceMultiplier() * getCount(s);
        }
        return totalPrice;
    }
    public boolean resetCartAndCoupon(){
        
       cart.setCartInventory(new HashMap<Product, Integer>());
       coupon.setPriceMultiplier(1);
       return true;
    }
}
