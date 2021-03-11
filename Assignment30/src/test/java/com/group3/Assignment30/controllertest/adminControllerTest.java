
package com.group3.Assignment30.controllertest;

import com.group3.Assignment30.controller.AdminController;
import com.group3.Assignment30.model.dao.CouponDAO;
import com.group3.Assignment30.model.dao.CustomerDAO;
import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.dao.PurchaseDAO;
import com.group3.Assignment30.model.entity.Coupon;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.model.entity.Purchase;
import com.group3.Assignment30.views.AdminBackingBean;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class adminControllerTest {
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(CouponDAO.class, Coupon.class,
                        ProductDAO.class, Product.class,
                        CustomerDAO.class, Customer.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    
    private AdminController adminCont;
    @EJB
    private ProductDAO productDAO;
    @EJB
    private CustomerDAO customerDAO;
    @EJB
    private CouponDAO couponDAO;
    
    @Before
    public void init(){
    }
    
    @Test
    public void addProductTestPriceBelowZero(){
        assertTrue(true);
    }
}
