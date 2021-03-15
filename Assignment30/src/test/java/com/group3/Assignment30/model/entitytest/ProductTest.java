
package com.group3.Assignment30.model.entitytest;
import com.group3.Assignment30.model.entity.Product;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ProductTest {
    Product product;
    
    @Test
    public void createProductNoArgTest(){
        product = new Product();
        assertNotEquals(product, null);
        assertEquals(null,product.getProduct_name());
        assertTrue(0.0 == product.getPrice());
        assertTrue(1.0 == product.getPriceMultiplier());
        assertTrue(0 == product.getFullStar());
        assertEquals(null,product.getColor());
        assertEquals(null, product.getMeasurements());
        assertEquals(null, product.getWeight());
        assertEquals(null, product.getDescription());
    }
    
    @Test
    public void createProductAllArgsTest(){
        product = new Product(999, "PizzaItem", 500, 1, 3, "Green", "1x1x1", "2kg", "desc");
        assertEquals(999, product.getProdoct_id());
        assertEquals("PizzaItem", product.getProduct_name());
        assertTrue(500 == product.getPrice());
        assertTrue(1.0 == product.getPriceMultiplier());
        assertTrue(3 == product.getFullStar());
        assertEquals("Green", product.getColor());
        assertEquals("1x1x1", product.getMeasurements());
        assertEquals("2kg", product.getWeight());
        assertEquals("desc", product.getDescription());
    }
}
