package ru.otus.dao;

import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Book;

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
    public abstract List<T> findAll();

    @Override
    public abstract Long getCount() ;

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
        em.remove(em.merge(entity));
    }
}
