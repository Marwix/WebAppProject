
package com.group3.Assignment30.model.backingbeantest;

import com.group3.Assignment30.model.entity.Coupon;
import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.views.AdminBackingBean;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class AdminBackingBeanTest {
    AdminBackingBean adminBackingBean;
    
    @Before
    public void init(){
        adminBackingBean = new AdminBackingBean();
    }
    
    @Test
    public void initTest(){
        adminBackingBean.init();
        assertNotEquals(null, adminBackingBean.getProduct());
        assertEquals(Product.class, adminBackingBean.getProduct().getClass());
        
        assertNotEquals(null, adminBackingBean.getCoupon());
        assertEquals(Coupon.class, adminBackingBean.getCoupon().getClass());
    }
    
    @Test
    public void getSetTest(){
        Product p1 = new Product();
        p1.setProduct_name("First prod");
        
        Product p2 = new Product();
        p2.setProduct_name("First prod");
        
        Coupon c1 = new Coupon();
        c1.setCouponCode("Coupon1");
        
        Coupon c2 = new Coupon();
        c2.setCouponCode("Coupon2");
        
        assertEquals(null, adminBackingBean.getProduct());
        adminBackingBean.setProduct(p1);
        assertEquals(p1, adminBackingBean.getProduct());
        
        assertEquals(null, adminBackingBean.getCoupon());
        adminBackingBean.setCoupon(c1);
        assertEquals(c1, adminBackingBean.getCoupon());
        
        List<Product> prodList = new ArrayList<Product>();
        prodList.add(p1);
        prodList.add(p2);
        assertEquals(null, adminBackingBean.getProductList());
        adminBackingBean.setProductList(prodList);
        assertNotEquals(null, adminBackingBean.getProductList());
        assertTrue(2 == adminBackingBean.getProductList().size());
        assertTrue(adminBackingBean.getProductList().contains(p2));
        assertTrue(adminBackingBean.getProductList().contains(p1));
        
        List<Coupon> couponList = new ArrayList<Coupon>();
        couponList.add(c1);
        couponList.add(c2);
        assertEquals(null, adminBackingBean.getCouponList());
        adminBackingBean.setCouponList(couponList);
        assertNotEquals(null, adminBackingBean.getCouponList());
        assertTrue(2 == adminBackingBean.getProductList().size());
        assertTrue(adminBackingBean.getCouponList().contains(c2));
    }
    
}
