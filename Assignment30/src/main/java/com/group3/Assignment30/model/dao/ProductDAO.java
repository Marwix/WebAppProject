
package com.group3.Assignment30.model.dao;

import com.group3.Assignment30.model.entity.Product;
import com.group3.Assignment30.model.entity.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;


@Stateless
public class ProductDAO extends AbstractDAO<Product> {
    @Getter @PersistenceContext(unitName = "BigStoreDB")
    private EntityManager em;
    private JPAQueryFactory queryFactory;
    private QProduct product;
    
    public ProductDAO() {
        super(Product.class);
        product = QProduct.product;
    }
    

    public List<Product> getProductByName(String name){
 
        List<Product> p = getJPAQueryFactory().selectFrom(product).where(product.product_name.eq(name)).fetch();
        
        return p;
        
    }
    
    // CREATE TEST FOR THIS METHOD!
    public List<Product> getXUniqueProducts(long count){
 
        List<Product> temp = getJPAQueryFactory().selectFrom(product).orderBy(product.product_name.desc()).fetch();
        
        List<Product> p = new ArrayList<Product>();
        
        String current = "";
        for (Product prod: temp) {
            if (!prod.getProduct_name().equals(current)){
                p.add(prod);
                current = prod.getProduct_name();
                if (p.size() == count) break;
            }
        }
        return p;
    }
   
    public List<Product>  getProductByID(int product_id){
        
        List<Product> p = getJPAQueryFactory().selectFrom(product).where(product.prodoct_id.eq(product_id)).fetch();
        
        return p;
    
    }
    
    public List<Product> getHighestRatingProduct (int count) {
        
        List<Product> products = getJPAQueryFactory().selectFrom(product).orderBy(product.fullStar.desc()).limit(count).fetch();
        
       return products; 
    }
    
     public List<Product> getProductLike(String userInput){

        List<Product> p = getJPAQueryFactory().selectFrom(product).where(product.product_name.likeIgnoreCase("%"+userInput+"%")).fetch();
        return p;
    }

    public long setProductSale(int prod_id, int newSale){

        return getJPAQueryFactory().update(product).where(product.prodoct_id.eq(prod_id)).set(product.priceMultiplier, ((double)(100-newSale))/100).execute();
    }
    
    public void cleanAll(){
        em.createQuery("DELETE FROM Product where 1=1").executeUpdate();
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected JPAQueryFactory getJPAQueryFactory() {
        queryFactory = new JPAQueryFactory(em);
       return queryFactory;
    }
  
    
}
