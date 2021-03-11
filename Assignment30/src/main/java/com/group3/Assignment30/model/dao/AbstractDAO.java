
package com.group3.Assignment30.model.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractDAO<T> {
    private final Class<T> entityType;
    protected abstract EntityManager getEntityManager();
    protected abstract JPAQueryFactory getJPAQueryFactory();
    
    public long count() {
        final CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        final CriteriaQuery cq = builder.createQuery();
        final Root<T> rt = cq.from(entityType);
        
        cq.select(builder.count(rt));
        
        final Query q = getEntityManager().createQuery(cq);
        return ((long) q.getSingleResult());
    }
    
    public void create(T entity) {
        getEntityManager().persist(entity);
    }
    
    public List<T> findAll(){
        final CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityType));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
    
    
}
