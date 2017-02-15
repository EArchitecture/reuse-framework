package com.github.earchitecture.reuse.model.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.Assert;

/**
 * {@link CrudRepositoryImpl} Implmentação generica de repositorio.
 *
 * @param <T>
 *          Tipo do Objeto
 * @param <P>
 *          Tipo da PK (Primary Key)
 */
public class CrudRepositoryImpl<T, P extends Serializable> extends SimpleJpaRepository<T, P> implements CrudRepository<T, P> {
  private EntityManager em;

  /**
   * Creates a new {@link CrudRepositoryImpl} to manage objects of the given {@link JpaEntityInformation}.
   * 
   * @param entityInformation
   *          must not be {@literal null}.
   * @param entityManager
   *          must not be {@literal null}.
   */
  public CrudRepositoryImpl(JpaEntityInformation<T, P> entityInformation, EntityManager entityManager) {
    super(entityInformation, entityManager);
    this.em = entityManager;
  }

  /**
   * Creates a new {@link CrudRepositoryImpl} to manage objects of the given DomainClass.
   * 
   * @param domainClass
   *          must not be {@literal null}.
   * @param entityManager
   *          must not be {@literal null}.
   */
  public CrudRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
    super(domainClass, entityManager);
    this.em = entityManager;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.model.repository.CrudRepository#refresh(java.lang.Object)
   */
  @Override
  public void refresh(T entity) {
    em.refresh(entity);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.model.repository.CrudRepository#evict(java.lang.Object)
   */
  @Override
  public void evict(T entity) {
    Session session = em.unwrap(Session.class);
    session.evict(entity);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.model.repository.CrudRepository#findAll(com.github.earchitecture.reuse.model.repository.QuerySpecification)
   */
  @Override
  public List<T> findAll(QuerySpecification<T> querySpecs) {
    List<T> listT = new ArrayList<T>();
    List<Tuple> tupleResult = getQuery(querySpecs).getResultList();
    for (Tuple t : tupleResult) {
      listT.add(querySpecs.toObject(t));
    }
    return listT;
  }

  /**
   * Creates a {@link TypedQuery} for the given {@link QuerySpecification} .
   * 
   * @param querySpecs
   *          can be {@literal null}.
   * @return TypedQuery
   */
  protected TypedQuery<Tuple> getQuery(QuerySpecification<T> querySpecs) {
    CriteriaBuilder builder = em.getCriteriaBuilder();
    Assert.notNull(querySpecs);
    CriteriaQuery<Tuple> cq = builder.createTupleQuery();
    CriteriaQuery<Tuple> query = querySpecs.toPredicate(cq, builder);

    return applyRepositoryMethodMetadata(em.createQuery(query));
  }

  private TypedQuery<Tuple> applyRepositoryMethodMetadata(TypedQuery<Tuple> query) {

    if (super.getRepositoryMethodMetadata() == null) {
      return query;
    }
    LockModeType type = super.getRepositoryMethodMetadata().getLockModeType();
    TypedQuery<Tuple> toReturn = type == null ? query : query.setLockMode(type);
    applyQueryHints(toReturn);
    return toReturn;
  }

  private void applyQueryHints(Query query) {
    for (Entry<String, Object> hint : getQueryHints().entrySet()) {
      query.setHint(hint.getKey(), hint.getValue());
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.github.earchitecture.reuse.model.repository.CrudRepository#sessionClear()
   */
  @Override
  public void sessionClear() {
    Session session = em.unwrap(Session.class);
    session.clear();
  }

}
