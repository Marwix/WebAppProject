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
        p1 = new Product(1, "Catapult", 30000, 4, 1, "Blue", "3m","3m","3m", "3m","HIGH", "TEST");
        p2 = new Product(2, "Catapult", 30000, 4, 1, "Green", "3m","3m","3m", "3m","HIGH", "TEST");
        p3 = new Product(3, "Catapult", 30000, 4, 1, "Red", "3m","3m","3m", "3m","HIGH", "TEST");
        p4 = new Product(4, "Plane", 30000, 4, 1, "Red", "3m","3m","3m", "3m","HIGH", "TEST");
    }
    //@InSequence(0) bestämmer ordningen
    @InSequence(0)
    @Test
    public void checkCorrectAmountInserted() {
        productDAO.create(p1);
        productDAO.create(p2);
        productDAO.create(p3);
        productDAO.create(p4);
        
        long Amount = productDAO.count();
        
        assertEquals(4L, Amount);
    }
    @InSequence(1)
    @Test
    public void checkIfWeGetCorrectProductWithID() {
        
        Product retrived1;
        Product retrived2;
        
        List<Product> retrievedProduct = productDAO.getProductByID(4);
        List<Product> retrievedProduct2 = productDAO.getProductByID(3);
        
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
    
    
}
