package com.github.earchitecture.reuse.service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.domain.Specification;

import com.github.earchitecture.reuse.model.annotation.GreaterThan;
import com.github.earchitecture.reuse.model.annotation.GreaterThanOrEqualTo;
import com.github.earchitecture.reuse.model.annotation.Ignore;
import com.github.earchitecture.reuse.model.annotation.LessThan;

import com.github.earchitecture.reuse.model.annotation.LessThanOrEqualTo;
import com.github.earchitecture.reuse.model.annotation.StartsWith;
import com.github.earchitecture.reuse.model.annotation.UseForeignKeySearch;
import com.github.earchitecture.reuse.model.annotation.UseLikeForSearch;
import com.github.earchitecture.reuse.model.entity.Identifiable;

/**
 * Classe que descreve implementação de pesquisa generica para consultas com criteria
 * 
 * @author <a href="cleber.ccosta@axxiom.com.br">Cleber Barcelos Costa</a>
 */
public class Specs {
  private final Log logger = LogFactory.getLog(getClass());
  protected static Specs specs = new Specs();

  public Specs() {
  }

  /**
   * Recupera por atributos da classe.
   * 
   * @param clazz
   *          clazz
   * @param entity
   *          entity
   * @param <T> tipo da entidade
   * @param <B> tipo da primary key
   * @return ret {@link Specification}
   */
  public static <T, B> Specification<T> byExample(final Class<T> clazz, final B entity) {
    return specs.new ByExampleSpecification<T, B>(entity);
  }

  /**
   * Implementação de specificarion por exemplo.
   *
   * @param <T>
   *          t
   * @param <B>
   *          b
   */
  public class ByExampleSpecification<T, B> implements Specification<T> {
    private final B entity;

    public ByExampleSpecification(B entity) {
      this.entity = entity;
    }

    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
      List<Predicate> predicates = this.findPredicate(root, query, cb, this.entity, null);
      Predicate ret = null;
      if (predicates != null && !predicates.isEmpty()) {
        for (Predicate predicate : predicates) {
          if (ret == null) {
            ret = cb.and(predicate);
          } else {
            ret = cb.and(ret, predicate);
          }
        }
      }
      return ret;
    }

    /**
     * Recupera predicate e sub predicate.
     * 
     * @param root
     *          root
     * @param query
     *          query
     * @param cb
     *          cb
     * @param entity
     *          entity
     * @param expression expression
     * @return List de predicate
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<Predicate> findPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb, Object entity, Path<Object> expression) {
      List<Predicate> predicates = new ArrayList<Predicate>();
      PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(entity.getClass());
      for (PropertyDescriptor pd : pds) {
        try {
          /* Caso seja Transient ou Ignore não vai ser considerado para pesquisa de banco de dados. */
          if (pd.getReadMethod().isAnnotationPresent(Transient.class) || pd.getReadMethod().isAnnotationPresent(Ignore.class)) {
            continue;
          }
          /* Caso seja getClass, ignora a leitura */
          if (pd.getReadMethod() != null && !pd.getName().equalsIgnoreCase("class")) {
            Object val = pd.getReadMethod().invoke(entity);
            if ((val != null && !(val instanceof ArrayList)) || (val instanceof ArrayList && !((ArrayList<?>) val).isEmpty())) {
              Field field = this.getField(entity.getClass(), pd.getName());
              /* Trata regra para enum */
              if (val.getClass().isEnum()) {
                Expression exp = this.getExpressionParam(root, expression, pd.getName());
                predicates.add(cb.equal(exp, val));
                continue;
                /* Trata regra para Foreign Key */
              } else if (expression == null
                  && (pd.getReadMethod().isAnnotationPresent(UseForeignKeySearch.class) || field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(OneToOne.class))) {
                List<Predicate> predis = this.findPredicate(root, query, cb, val, root.get(pd.getName()));
                if (predis != null && !predis.isEmpty()) {
                  predicates.addAll(predis);
                }
                continue;
              } else if (val instanceof String) {
                /* Se for string e for em branco e para desconsiderar esta condição */
                if (StringUtils.isNotBlank((CharSequence) val)) {
                  this.stringProcessor(root, cb, pd, (String) val, predicates);
                }
                continue;
              } else if (val instanceof Comparable) {
                Class<? extends Comparable> clazz = (Class<? extends Comparable>) val.getClass();
                if (this.comparableProcessor(root, cb, predicates, root.getModel(), pd, val, clazz)) {
                  continue;
                }
              }
              if (!(val instanceof Identifiable) && !(val instanceof Collection)) {
                predicates.add(cb.equal(this.getExpressionParam(root, expression, pd.getName()), val));
              }
            }
          }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {
          logger.warn("Erro ao montar clausula para: " + pd.getName(), exception);
        } catch (NoSuchFieldException | SecurityException exception) {
          logger.warn("Erro ao montar clausula para: " + pd.getName(), exception);
        }
      }
      return predicates;
    }

    private Field getField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
      Field field = null;
      try {
        field = clazz.getDeclaredField(fieldName);
      } catch (NoSuchFieldException e) {
        field = this.getField(clazz.getSuperclass(), fieldName);
      }
      return field;
    }

    /**
     * Trata expressão de path, para considerar hierarquia de objeto.
     * 
     * @param root
     *          Root element
     * @param path
     *          path de subobjetos
     * @param name
     *          nome da propriedade
     * @return path da propriedade
     */
    private Path<Object> getExpressionParam(Root<T> root, Path<Object> path, String name) {
      if (path != null) {
        return path.get(name);
      } else {
        return root.get(name);
      }
    }

    /**
     * Processa condição para string.
     * 
     * @param cb
     *          {@link CriteriaBuilder}
     * @param pd
     *          {@link PropertyDescriptor}
     * @param value
     *          {@link String}
     * @param predicates
     *          Lista de Predicate a serem adicionados na query
     * 
     */
    @SuppressWarnings("unchecked")
    private void stringProcessor(Root<T> root, CriteriaBuilder cb, PropertyDescriptor pd, String value, List<Predicate> predicates) {
      EntityType<T> _entity = root.getModel();
      Expression<String> exp = (Expression<String>) root.get(_entity.getSingularAttribute(pd.getName()));
      /* Trata condição que inicia com a palavrea */
      StartsWith annStart = pd.getReadMethod().getAnnotation(StartsWith.class);
      if (annStart != null) {
        /* Ignora espaço */
        if (annStart.trim()) {
          exp = cb.trim(exp);
          value = value.trim();
        }
        /* Trata lowercase caso seja verdadeiro */
        if (annStart.lowercase()) {
          exp = cb.lower(exp);
          value = value.toLowerCase();
        }
        predicates.add(cb.like(exp, value + "%"));
      }
      /* Trata condição de like */
      UseLikeForSearch annLike = pd.getReadMethod().getAnnotation(UseLikeForSearch.class);
      if (annLike != null) {
        /* Ignora espaço */
        if (annLike.trim()) {
          exp = cb.trim(exp);
          value = value.trim();
        }
        /* Trata lowercase caso seja verdadeiro */
        if (annLike.lowercase()) {
          exp = cb.lower(exp);
          value = value.toLowerCase();
        }
        predicates.add(cb.like(exp, "%" + value + "%"));
      }
      if (annLike == null && annStart == null) {
        predicates.add(cb.equal(exp, value));
      }
    }

    @SuppressWarnings("unchecked")
    private <C extends Comparable<? super C>> boolean comparableProcessor(Root<T> root, CriteriaBuilder cb, List<Predicate> predicates, EntityType<T> _entity, PropertyDescriptor pd, Object val,
        Class<C> clazz) {
      boolean ret = false;
      if (pd.getReadMethod().isAnnotationPresent(GreaterThanOrEqualTo.class)) {
        String field = pd.getReadMethod().getAnnotation(GreaterThanOrEqualTo.class).field();
        predicates.add(cb.lessThanOrEqualTo(root.get(_entity.getSingularAttribute(field, clazz)), (C) val));
        ret = true;
      } else if (pd.getReadMethod().isAnnotationPresent(LessThanOrEqualTo.class)) {
        String field = pd.getReadMethod().getAnnotation(LessThanOrEqualTo.class).field();
        predicates.add(cb.greaterThanOrEqualTo(root.get(_entity.getSingularAttribute(field, clazz)), (C) val));
        ret = true;
      } else if (pd.getReadMethod().isAnnotationPresent(GreaterThan.class)) {
        String field = pd.getReadMethod().getAnnotation(GreaterThan.class).field();
        predicates.add(cb.lessThan(root.get(_entity.getSingularAttribute(field, clazz)), (C) val));
        ret = true;
      } else if (pd.getReadMethod().isAnnotationPresent(LessThan.class)) {
        String field = pd.getReadMethod().getAnnotation(LessThan.class).field();
        predicates.add(cb.greaterThan(root.get(_entity.getSingularAttribute(field, clazz)), (C) val));
        ret = true;
      }
      return ret;
    }
  }
}
