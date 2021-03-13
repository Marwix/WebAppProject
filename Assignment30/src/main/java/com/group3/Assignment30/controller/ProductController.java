
import com.group3.Assignment30.model.dao.ProductDAO;
import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.views.ProductBackingBean;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
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
        getAllProducts();
    }
    
    public void getAllProducts(){
        List<Product> products = productDAO.findAll();
        productBackingBean.setProducts(products);
    }
    
    public void getProductsLike(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String,String> params = facesContext.getExternalContext().getRequestParameterMap();
        String userInput = params.get("action");
        
        List<Product> products = productDAO.getProductLike(userInput);
        productBackingBean.setProducts(products);
    }
    
    public void sortAllSOrtedByPrice() {
       
        productBackingBean.setProducts(productDAO.findAllSortedByPrice(productBackingBean.isSortToggleOrder()));
        productBackingBean.setSortToggleOrder(!productBackingBean.isSortToggleOrder());
    }
    
    public void getAllSOrtedByStars() {
        
    }
    
    public void getAllSOrtedByDateAdded() {
        
    }
}