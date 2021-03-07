
package com.group3.Assignment30.views;
import com.group3.Assignment30.model.entity.Cart;
import com.group3.Assignment30.model.entity.Product;
import java.io.Serializable;
import java.util.HashMap;
import javax.annotation.PostConstruct;
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
    
   public void addItemToCart(Product product){
        if (cart.getCartInventory() != null) {
            HashMap<Product,Integer> cartItems = cart.getCartInventory();
            if(cartItems.containsKey(product)){
                cartItems.put(product, cartItems.get(product) + 1);
            }else{
                cartItems.put(product, 1);
            }
            cart.setCartInventory(cartItems);
        }else{
            HashMap<Product,Integer> cartItems = new HashMap<>();
            cartItems.put(product, 1);
            cart.setCartInventory(cartItems);
        }
    }
}
