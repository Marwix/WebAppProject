
package com.group3.Assignment30.model.dao;

import com.group3.Assignment30.model.entity.Coupon;
import com.group3.Assignment30.model.entity.QCoupon;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.Getter;

@Stateless
public class CouponDAO extends AbstractDAO<Coupon>{
    @Getter @PersistenceContext(unitName = "BigStoreDB")
    private EntityManager em;
    private JPAQueryFactory queryFactory;
    private  QCoupon coupon;
    
    public CouponDAO() {
        super(Coupon.class);
        coupon = QCoupon.coupon;
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Coupon> getCouponByCode(String couponCode){
 
        List<Coupon> coupons = getJPAQueryFactory().selectFrom(coupon).where(coupon.couponCode.eq(couponCode)).fetch();
        return coupons;
    }
    
    public void createCoupon(String couponCode, int salePercentage){
        // Ugly math for salePercentage to get "nicer" date
        super.create(new Coupon(couponCode, ((double)(100-salePercentage))/100));
    }
    
    public void deleteCouponByCouponCode(String couponCode){
      
        
       getJPAQueryFactory().delete(coupon).where(coupon.couponCode.eq(couponCode)).execute();
    }

    @Override
    protected JPAQueryFactory getJPAQueryFactory() {
        queryFactory = new JPAQueryFactory(em);
       return queryFactory;
    }
}
