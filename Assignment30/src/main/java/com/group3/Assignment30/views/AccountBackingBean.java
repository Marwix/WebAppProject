
package com.group3.Assignment30.views;


import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.model.entity.Purchase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Named
@ViewScoped
public class AccountBackingBean implements Serializable{
  
   Customer customer;
   @NotEmpty String oldpassword; 
   @NotEmpty String password; 
   
   @NotEmpty private List<List<Purchase>> purchases;
   
   // Show order history in profile page.
   public void showOrderHistory(List<Purchase> listOfOrders) 
   {
       listOfOrders.sort(Comparator.comparing(Purchase::getOrder_id).reversed()); 
       int curOrder = listOfOrders.isEmpty() ? null : listOfOrders.get(0).getOrder_id();
       List<Purchase> order = new ArrayList<Purchase>();
       List<List<Purchase>> orders = new ArrayList<List<Purchase>>();
       
       for (Purchase cur : listOfOrders)
       {
           if (cur.getOrder_id() == curOrder){
             order.add(cur);
           } else {
              orders.add(order);
              order = new ArrayList<Purchase>();
              order.add(cur);
              curOrder = cur.getOrder_id();
           }
       }
       
       orders.add(orders.size(), order);
       this.purchases = orders;
   }
   
   public double getTotalPriceOfOrder(List<Purchase> order) {
       double totalPrice = 0;
       for (Purchase s : order)
       {
           totalPrice += s.getPrice() * s.getCount();
       }
       return totalPrice;
   }
}
