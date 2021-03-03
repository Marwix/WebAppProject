
package com.group3.Assignment30.model.daotest;

import com.group3.Assignment30.model.dao.CouponDAO;
import com.group3.Assignment30.model.entity.Coupon;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CouponDAOTest {
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(CouponDAO.class, Coupon.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @EJB
    private CouponDAO couponDAO;
    
    private Coupon c1;
    private Coupon c2;
    private Coupon c3;
    
    @Before
    public void init(){
       c1 = new Coupon("COUPON1", 0.88);
       c2 = new Coupon("COUPON2", 0.75);
       c3 = new Coupon("COUPON3", 0.95);
    }
    
    @InSequence(0)
    @Test
    public void createCouponTest(){
        couponDAO.createCoupon("COUPON1", 12);
        couponDAO.createCoupon("COUPON2", 25);
        couponDAO.createCoupon("COUPON3", 5);
        
        assertEquals(couponDAO.count(), 3L);
    }
    
    @InSequence(1)
    @Test
    public void getCouponByCodeTest(){
        Coupon coupon = couponDAO.getCouponByCode("COUPON1").get(0);
        assertEquals(c1, coupon);
        assertNotEquals(c2, coupon);
        assertNotEquals(c3, coupon);
        
        coupon = couponDAO.getCouponByCode("COUPON2").get(0);
        assertEquals(c2, coupon);
        assertNotEquals(c1, coupon);
        assertNotEquals(c3, coupon);
        
        coupon = couponDAO.getCouponByCode("COUPON3").get(0);
        assertEquals(c3, coupon);
        assertNotEquals(c2, coupon);
        assertNotEquals(c1, coupon);
        
    }
    
}
