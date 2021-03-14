package com.group3.Assignment30.model.daotest;

import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.entity.Product;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
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
        p1.setPriceMultiplier(0.7);
        p1.setFullStar(1);
        p1.setColor("Blue");
        p1.setMeasurements("3mx3mx3m");
        p1.setDescription("TEST");
                
        p2 = new Product();
        p2.setProduct_name("Catapult");
        p2.setPrice(30000);
        p2.setFullStar(2);
        p2.setColor("Green");
        p2.setMeasurements("3mx3mx3m");
        p2.setDescription("TEST");
        
        p3 = new Product();
        p3.setProduct_name("Catapult");
        p3.setPrice(30000);
        p3.setFullStar(3);
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
    
    @Test
    public void checkCorrectAmountInserted() {
        
        
        long Amount = productDAO.count();
        
        assertEquals(4L, Amount);
    }
    
    @Test
    public void getCorrectRange(){
        List<Product> l1 = productDAO.getXUniqueProducts(1);
        assertTrue(l1.size() == 1);
        
        l1 = productDAO.getXUniqueProducts(2);
        assertTrue(l1.size() == 2);
    }
    
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
    public void checkIfGetProductByName() {
        List<Product> retrivedProducts = productDAO.getProductByName("Catapult");
        assertTrue(retrivedProducts.contains(p1));
        assertTrue(retrivedProducts.contains(p2));
        assertTrue(retrivedProducts.contains(p3));
        assertFalse(retrivedProducts.contains(p4));
    }
    
    
    @Test
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
    
    @Test
    public void getHighestRatingProduct(){
        List<Product> productList = productDAO.getHighestRatingProduct(3);
        assertTrue(productList.size() == 3);
        assertEquals(p4, productList.get(0));
        assertEquals(p3, productList.get(1));
        assertEquals(p2, productList.get(2));
        
    }
    
    @Test
    public void getProductLikeTest(){
        List<Product> productList = productDAO.getProductLike("plA");
        assertTrue(productList.size() == 1);
        assertEquals(p4, productList.get(0));
        
        productList = productDAO.getProductLike("AtAPuLt");
        assertTrue(productList.size() == 3);
        assertTrue(productList.contains(p1));
        assertTrue(productList.contains(p2));
        assertTrue(productList.contains(p3));
    }
    
    @Test
    public void setProductSaleTest(){
        //Change once
        double oldSale = p1.getPriceMultiplier();
        productDAO.setProductSale(p1.getProdoct_id(), 10);
        double newSale = productDAO.getProductByID(p1.getProdoct_id()).get(0).getPriceMultiplier();
        assertNotEquals(oldSale, newSale, 0.1);
        assertEquals(newSale, 0.9, 0.01);
        
        // Change twice on the same product
        oldSale = newSale;
        productDAO.setProductSale(p1.getProdoct_id(), 55);
        newSale = productDAO.getProductByID(p1.getProdoct_id()).get(0).getPriceMultiplier();
        assertNotEquals(oldSale, newSale, 0.01);
        assertEquals(newSale,0.45, 0.01);
        
        //Change on a different product
        oldSale = p3.getPriceMultiplier();
        productDAO.setProductSale(p3.getProdoct_id(), 99);
        newSale = productDAO.getProductByID(p3.getProdoct_id()).get(0).getPriceMultiplier();
        assertNotEquals(oldSale, newSale, 0.01);
        assertEquals(newSale, 0.01, 0.01);
    }
    
    @Test
    public void getEntityManagerTest(){
        
    }
    
    @Test
    public void findAllSortedByPriceTest(){
        List<Product> sortedByDescending = new ArrayList<Product>();
        List<Product> sortedByAscending = new ArrayList<Product>();
        
        sortedByDescending.add(p4);
        sortedByDescending.add(p3);
        sortedByDescending.add(p2);
        sortedByDescending.add(p1);
        
        sortedByAscending.add(p1);
        sortedByAscending.add(p3);
        sortedByAscending.add(p2);
        sortedByAscending.add(p4);
        assertEquals(sortedByDescending, productDAO.findAllSortedByPrice(true));
        assertEquals(sortedByAscending, productDAO.findAllSortedByPrice(false));
    
    }
    
    @Test
    public void findAllSortedByStarsTest(){
        List<Product> sortedByDescending = new ArrayList<Product>();
        List<Product> sortedByAscending = new ArrayList<Product>();
        
        sortedByDescending.add(p4);
        sortedByDescending.add(p3);
        sortedByDescending.add(p2);
        sortedByDescending.add(p1);
        
        sortedByAscending.add(p1);
        sortedByAscending.add(p2);
        sortedByAscending.add(p3);
        sortedByAscending.add(p4);
        assertEquals(sortedByDescending, productDAO.findAllSortedByStars(true));
        assertEquals(sortedByAscending, productDAO.findAllSortedByStars(false));
    
    }
    
    @Test
    public void findAllSortedByDateAddedTest(){
        List<Product> sortedByDescending = new ArrayList<Product>();
        List<Product> sortedByAscending = new ArrayList<Product>();
        
        sortedByDescending.add(p4);
        sortedByDescending.add(p3);
        sortedByDescending.add(p2);
        sortedByDescending.add(p1);
        
        sortedByAscending.add(p1);
        sortedByAscending.add(p2);
        sortedByAscending.add(p3);
        sortedByAscending.add(p4);
        assertEquals(sortedByDescending, productDAO.findAllSortedByDateAdded(true));
        assertEquals(sortedByAscending, productDAO.findAllSortedByDateAdded(false));
    
    }
    
    @After
    public void cleanup(){
       productDAO.remove(p1);
       productDAO.remove(p2);
       productDAO.remove(p3);
       productDAO.remove(p4);
    }
    
}
