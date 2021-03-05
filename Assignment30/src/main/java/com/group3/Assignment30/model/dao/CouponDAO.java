
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
    
    public CouponDAO() {
        super(Coupon.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public List<Coupon> getCouponByCode(String couponCode){
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        QCoupon coupon = QCoupon.coupon;
        
        List<Coupon> coupons = queryFactory.selectFrom(coupon).where(coupon.couponCode.eq(couponCode)).fetch();
        return coupons;
    }
    
    public void createCoupon(String couponCode, double salePercentage){
        super.create(new Coupon(couponCode, 1-(salePercentage/100)));
    }
    
    public void deleteCouponByCouponCode(String couponCode){
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        QCoupon coupon = QCoupon.coupon;
        
        queryFactory.delete(coupon).where(coupon.couponCode.eq(couponCode)).execute();
    }
}
