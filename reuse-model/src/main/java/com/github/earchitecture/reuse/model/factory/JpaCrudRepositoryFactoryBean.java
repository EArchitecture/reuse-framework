package com.github.earchitecture.reuse.model.factory;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import com.github.earchitecture.reuse.model.repository.JpaCrudRepositoryImpl;

public class JpaCrudRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable> extends JpaRepositoryFactoryBean<R, T, I> {

  public JpaCrudRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
    super(repositoryInterface);
  }

  @Override
  protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
    return new BaseRepositoryFactory<T, I>(em);
  }

  private static class BaseRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {
    private final EntityManager em;

    public BaseRepositoryFactory(EntityManager em) {
      super(em);
      this.em = em;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object getTargetRepository(RepositoryInformation metadata) {
      return new JpaCrudRepositoryImpl<T, I>((Class<T>) metadata.getDomainType(), this.em);
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
      return JpaCrudRepositoryImpl.class;
    }
  }

}
