
import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.views.ProductBackingBean;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Data;


@Data
@Named
@ViewScoped
public class ProductController implements Serializable {
    
    @EJB
    private ProductDAO productDAO;
    
    @Inject
    private ProductBackingBean productBackingBean;
    
    @PostConstruct
    public void init() {
        List<Product> products = productDAO.findAll();
        productBackingBean.setProducts(products);
    }

    
}