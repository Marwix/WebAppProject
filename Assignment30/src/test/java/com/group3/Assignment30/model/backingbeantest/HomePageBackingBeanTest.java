package com.group3.Assignment30.model.backingbeantest;

import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.views.HomePageBackingBean;
import java.util.ArrayList;
import java.util.List;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class HomePageBackingBeanTest {
    HomePageBackingBean homePageBackingBean;
    private Product product1;
   
    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class)
                .addClasses(Product.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Before
    public void init() {
        homePageBackingBean = new HomePageBackingBean();

        product1 = new Product(1, "Catapult", 100, 1, 3, "blue", "test", "20kg", "test description 1");
 
    }

    @Test
    public void getterAndSetterTest() {
       
        List<Product> products = new ArrayList<Product>();
        
        assertEquals(null,homePageBackingBean.getProducts());
        products.add(product1);
        homePageBackingBean.setProducts(products);
        
        assertEquals(products,homePageBackingBean.getProducts());
    }
}
