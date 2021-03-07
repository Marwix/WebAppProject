
package com.group3.Assignment30.views;


import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.model.entity.Purchase;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Named
@ViewScoped
public class AccountBackingBean implements Serializable{
  
   Customer customer;
   @NotEmpty String oldpassword; 
   @NotEmpty String password; 
   
   @NotEmpty private List<Purchase> purchases;
   
   @NotEmpty private HashMap<Integer,List<Purchase> > testorder;
   @NotEmpty private List<Integer> orderids;
   
   @PostConstruct
   public void init() {
       testorder = new HashMap<Integer, List<Purchase>>();
       orderids = new ArrayList<Integer>();
   }
   
   public void showOrderHistory(List<Purchase> listOfOrders) {
       this.purchases = listOfOrders;
       
       List<Purchase> temp = new ArrayList<Purchase>();
       
       int current = 0;
       int previous = listOfOrders.get(0).getOrder_id();
       
       for (Purchase order : listOfOrders)
       {
           current = order.getOrder_id();
           if (current == previous) {
               temp.add(order);
           } 
           
           if (current != previous) {
               testorder.put(previous, temp);
               temp = new ArrayList<Purchase>();
               temp.add(order);
               orderids.add(previous);
               previous = current;
           }
       }
       
       //System.out.println(testorder.keySet());
   }
}
