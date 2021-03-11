package com.group3.Assignment30.views;
import com.group3.Assignment30.model.models.Cart;
import com.group3.Assignment30.model.entity.Product;
import java.io.Serializable;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lombok.Data;

@Data
@Named
@SessionScoped
public class CartBackingBean implements Serializable{
    
   @EJB
   private Cart cart;
   private int count;
   
   public void addItemToCart(Product product) {
        if (cart.getCartInventory() != null) 
        {
            HashMap<Product,Integer> cartItems = cart.getCartInventory();
            if(cartItems.containsKey(product)){
                cartItems.put(product, cartItems.get(product) + 1);
            }else{
                cartItems.put(product, 1);
            }
            cart.setCartInventory(cartItems);
        }
        else
        {
            HashMap<Product,Integer> cartItems = new HashMap<>();
            cartItems.put(product, 1);
            cart.setCartInventory(cartItems);
        }
        
        // Add every item into count.
        count++;
   }
   
   // Return amount of items currently in cart.
   public String getAmountItemsCart() {
       if (count != 0) 
           return String.valueOf(this.count);
       else {
           return "";
       }
   }
   
   // Remove amount of number item(s) from cart on cart icon.
   public void removeItemFromCart(Product item) {
       HashMap<Product,Integer> cartItems = cart.getCartInventory();
       if (cartItems.containsKey(item))
       {
           this.count -= cartItems.get(item);
       }
   }
}
