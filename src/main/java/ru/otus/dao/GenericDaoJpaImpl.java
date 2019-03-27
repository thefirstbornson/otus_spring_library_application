package ru.otus.dao;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public abstract class GenericDaoJpaImpl<T>  implements GenericDao<T>{

    @PersistenceContext
    EntityManager em;

    final Class<T> entityClass;

    public GenericDaoJpaImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T findById(long id) {
        return em.find(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        CriteriaQuery<T> c =
                em.getCriteriaBuilder().createQuery(entityClass);
        c.select(c.from(entityClass));
        return em.createQuery(c).getResultList();
    }

    @Override
    public Long getCount() {
        CriteriaQuery<Long> c =
                em.getCriteriaBuilder().createQuery(Long.class);
        c.select(em.getCriteriaBuilder().count(c.from(entityClass)));
        return em.createQuery(c).getSingleResult();
    }

    @Override
    @Transactional
    public T save(T entity){
        em.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public T update(T entity){
        return em.merge(entity);

    }

    @Override
    @Transactional
    public void delete(T entity){
        em.remove(em.contains(entity) ? entity : em.merge(entity));
    }
}
