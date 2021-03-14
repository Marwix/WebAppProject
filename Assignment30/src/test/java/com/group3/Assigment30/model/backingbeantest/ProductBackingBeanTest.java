package com.group3.Assigment30.model.backingbeantest;

import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.views.ProductBackingBean;
import java.util.ArrayList;
import java.util.List;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Before;
import org.junit.Test;

public class ProductBackingBeanTest {
    ProductBackingBean bean;
    
    private Product p1;
    private Product p2;
    private Product p3;
    
    List<Product> products = new ArrayList<Product>();
    
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(ProductDAO.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    @Before
    public void init() {
        bean = new ProductBackingBean();
        
        p1 = new Product(1, "name", 100, 1, 4, "blue", "100x100", "100", "Prod1");
        p2 = new Product(2, "allan", 200, 0.95, 1, "blue", "100x100", "100", "Prod1");
        p3 = new Product(3, "boy", 300, 0.5, 3, "blue", "100x100", "100", "Prod1");

        products.add(p1);
        products.add(p2);
        products.add(p3);
        bean.setProducts(products);
        assertEquals(products, bean.getProducts());
        assertNotEquals(null, bean.getProducts());
        
        bean.setSearchString("SearchTest");
        assertEquals("SearchTest", bean.getSearchString());   
    }
    
    @Test
    public void orderProductsByPriceTest() {
        
        assertEquals(false, bean.isSortToggleOrder());
        bean.orderProductsByPrice();
        for (Product s : products) {
            System.out.println(s);
        }

    } 
    
    @Test
    public void orderProductsByStarsTest() {
        
        assertEquals(false, bean.isSortToggleOrder());
        bean.orderProductsByStars();
        
        // First order validate.
        assertEquals(p2, bean.getProducts().get(0));
        assertEquals(p3, bean.getProducts().get(1));
        assertEquals(p1, bean.getProducts().get(2));
        
        assertEquals(true, bean.isSortToggleOrder());
        bean.orderProductsByStars();
        
        // Reverse order validate.
        assertEquals(p1, bean.getProducts().get(0));
        assertEquals(p3, bean.getProducts().get(1));
        assertEquals(p2, bean.getProducts().get(2));
    } 
    
    @Test
    public void orderProductsDateAddedTest() {
        //bean.orderProductsDateAdded();
    } 
}
