package com.group3.Assignment30.model.daotest;

import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.entity.Product;
import java.util.List;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ProductDAOTest {
    
    
    private Product p1;
    private Product p2;
    private Product p3;
    private Product p4;
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(ProductDAO.class, Product.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @EJB
    private ProductDAO productDAO;

    @Before
    public void init() {
        p1 = new Product();
        p1.setProduct_name("Catapult");
        p1.setPrice(30000);
        p1.setFullStar(4);
        p1.setColor("Blue");
        p1.setMeasurements("3mx3mx3m");
        p1.setDescription("TEST");
                
        p2 = new Product();
        p2.setProduct_name("Catapult");
        p2.setPrice(30000);
        p2.setFullStar(4);
        p2.setColor("Green");
        p2.setMeasurements("3mx3mx3m");
        p2.setDescription("TEST");
        
        p3 = new Product();
        p3.setProduct_name("Catapult");
        p3.setPrice(30000);
        p3.setFullStar(4);
        p3.setColor("Red");
        p3.setMeasurements("3mx3mx3m");
        p3.setDescription("TEST");
        
        p4 = new Product();
        p4.setProduct_name("Plane");
        p4.setPrice(700000);
        p4.setFullStar(4);
        p4.setColor("Metal");
        p4.setMeasurements("25mx12mx7m");
        p4.setDescription("TEST");
        
        productDAO.create(p1);
        productDAO.create(p2);
        productDAO.create(p3);
        productDAO.create(p4);
    }
    //@InSequence(0) bestämmer ordningen
    @InSequence(0)
    @Test
    public void checkCorrectAmountInserted() {
        
        
        long Amount = productDAO.count();
        
        assertEquals(4L, Amount);
    }
    @InSequence(1)
    @Test
    public void checkIfWeGetCorrectProductWithID() {
        
        Product retrived1;
        Product retrived2;
        
        List<Product> retrievedProduct = productDAO.getProductByID(p4.prodoct_id);
        List<Product> retrievedProduct2 = productDAO.getProductByID(p3.prodoct_id);
        
        retrived1 = retrievedProduct.get(0);
        retrived2 = retrievedProduct2.get(0);
        
        assertEquals(p4, retrived1);
        assertEquals(p3, retrived2);
    }
    
    @Test
    @InSequence(2)
    public void checkIfGetProductByName() {
        List<Product> retrivedProducts = productDAO.getProductByName("Catapult");
        assertTrue(retrivedProducts.contains(p1));
        assertTrue(retrivedProducts.contains(p2));
        assertTrue(retrivedProducts.contains(p3));
        assertFalse(retrivedProducts.contains(p4));
    }
    
    
    @Test
    @InSequence(3)
    public void checkIfProductRemoved() {
        List<Product> retrivedProducts = productDAO.findAll();
        assertTrue(retrivedProducts.contains(p1));
        assertTrue(retrivedProducts.contains(p2));
        assertTrue(retrivedProducts.contains(p3));
        assertTrue(retrivedProducts.contains(p4));
        
        productDAO.remove(p2);
        
        List<Product> retrivedProductsAfterRemoved = productDAO.findAll();
        
        assertTrue(retrivedProductsAfterRemoved.contains(p1));
        assertFalse(retrivedProductsAfterRemoved.contains(p2));
        assertTrue(retrivedProductsAfterRemoved.contains(p3));
        assertTrue(retrivedProductsAfterRemoved.contains(p4));
        
    }
    
    @After
    public void cleanup(){
       productDAO.cleanAll();
    }
    
}
