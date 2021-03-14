
package com.group3.Assignment30.model.backingbeantest;

import com.group3.Assignment30.controller.AccountPageController;
import com.group3.Assignment30.model.entity.Customer;
import com.group3.Assignment30.views.AccountBackingBean;
import java.io.File;
import javax.validation.constraints.AssertTrue;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class test {
    
    private static final String WEBAPP_SRC = "src/main/webapp";
    
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "login.war")
            .addClasses(AccountBackingBean.class, AccountPageController.class, Customer.class)
            .addAsWebResource(new File(WEBAPP_SRC, "accountpage.xhtml"))
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsWebInfResource(
                new StringAsset("<faces-config version=\"2.0\"/>"),
                "faces-config.xml");
    }
    
    @Test
    public void test(){
        assertEquals(true, true);
    }
    
    
}
