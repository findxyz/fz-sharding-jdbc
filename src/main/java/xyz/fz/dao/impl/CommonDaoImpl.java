package xyz.fz.dao.impl;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import xyz.fz.dao.CommonDao;
import xyz.fz.dao.PagerData;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by fz on 2015/11/7.
 */
@SuppressWarnings("unchecked")
@Component
public class CommonDaoImpl implements CommonDao {

    private static Logger logger = LoggerFactory.getLogger(CommonDaoImpl.class);

    private static final Set<String> MAP_CLAZZ = new HashSet<String>() {{
        add("java.util.Map");
        add("java.util.LinkedHashMap");
        add("java.util.HashMap");
    }};

    private static final Set<String> PRIMITIVE_CLAZZ = new HashSet<String>() {{
        add("java.lang.String");
        add("java.lang.Number");
        add("java.lang.Integer");
        add("java.lang.Long");
        add("java.math.BigInteger");
        add("java.math.BigDecimal");
    }};

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public <T> List<T> queryList(String hql, Map<String, Object> params) {
        try {
            Query query = entityManager.createQuery(hql);
            queryParamsSet(query, params);
            return query.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public <T> PagerData<T> queryPagerData(String countHql, String hql, Map<String, Object> params, int currentPage, int pageSize) {
        // currentPage begin from index 1
        currentPage -= 1;
        try {
            PagerData pagerData = new PagerData();
            Long count = querySingle(countHql, params);
            Query query = entityManager.createQuery(hql);
            queryParamsSet(query, params);
            query.setFirstResult(currentPage * pageSize);
            query.setMaxResults(pageSize);
            pagerData.setTotalCount(count);
            pagerData.setData(query.getResultList());
            return pagerData;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public <T> T querySingle(String hql, Map<String, Object> params) {
        try {
            Query query = entityManager.createQuery(hql);
            queryParamsSet(query, params);
            return (T) query.getSingleResult();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public <T> T findById(Class<T> clazz, Object id) {
        try {
            return entityManager.find(clazz, id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }


    @Override
    public <T> List<T> queryListBySql(String sql, Map<String, Object> params, Class<T> clazz) {
        try {
            Query query = getSqlQuery(sql, params, clazz);
            return query.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public <T> PagerData<T> queryPagerDataBySql(String countSql, String sql, Map<String, Object> params, int currentPage, int pageSize, Class<T> clazz) {
        // currentPage begin from index 1
        currentPage -= 1;
        try {
            sql += " limit " + (currentPage * pageSize) + ", " + pageSize;
            PagerData pagerData = new PagerData();
            BigInteger count = querySingleBySql(countSql, params, BigInteger.class);
            Query query = getSqlQuery(sql, params, clazz);
            pagerData.setData(query.getResultList());
            pagerData.setTotalCount(count.longValue());
            return pagerData;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public <T> T querySingleBySql(String sql, Map<String, Object> params, Class<T> clazz) {
        try {
            Query query = getSqlQuery(sql, params, clazz);
            return (T) query.getSingleResult();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    private Query getSqlQuery(String sql, Map<String, Object> params, Class clazz) {
        Query query;
        boolean isEntity = clazz.isAnnotationPresent(Entity.class);
        if (isEntity) {
            query = clazzQuery(sql, params, clazz);
        } else if (MAP_CLAZZ.contains(clazz.getName())) {
            query = mapQuery(sql, params);
        } else if (PRIMITIVE_CLAZZ.contains(clazz.getName())) {
            query = primitiveQuery(sql, params);
        } else {
            query = beanQuery(sql, params, clazz);
        }
        return query;
    }

    private Query clazzQuery(String sql, Map<String, Object> params, Class clazz) {
        Query query = entityManager.createNativeQuery(sql, clazz);
        queryParamsSet(query, params);
        return query;
    }

    private Query mapQuery(String sql, Map<String, Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        queryParamsSet(query, params);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query;
    }

    private Query primitiveQuery(String sql, Map<String, Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        queryParamsSet(query, params);
        return query;
    }

    private Query beanQuery(String sql, Map<String, Object> params, Class clazz) {
        Query query = entityManager.createNativeQuery(sql);
        queryParamsSet(query, params);
        query.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(clazz));
        return query;
    }

    private void queryParamsSet(Query query, Map<String, Object> params) {
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public void save(Object entity) {
        try {
            entityManager.persist(entity);
            /*
             * Make an instance managed and persistent.
             * @param entity  entity instance
             * @throws EntityExistsException if the entity already exists.
             * (If the entity already exists, the <code>EntityExistsException</code> may
             * be thrown when the persist operation is invoked, or the
             * <code>EntityExistsException</code> or another <code>PersistenceException</code> may be
             * thrown at flush or commit time.)
             * @throws IllegalArgumentException if the instance is not an
             *         entity
             * @throws TransactionRequiredException if there is no transaction when
             *         invoked on a container-managed entity manager of that is of type
             *         <code>PersistenceContextType.TRANSACTION</code>
             */
            // public void persist(Object entity);
            entityManager.flush();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public void batchSave(Collection entities) {
        int i = 0;
        int batchSize = 10;
        for (Object o : entities) {
            entityManager.persist(o);
            i++;
            if (i % batchSize == 0) {
                // Flush a batch of inserts and release memory.
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

    @Override
    public void update(Object entity) {
        try {
            entityManager.merge(entity);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public void delete(Object entity) {
        try {
            entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public int execute(String hql, Map<String, Object> params) {
        try {
            Query query = entityManager.createQuery(hql);
            return execute0(query, params);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public int executeBySql(String sql, Map<String, Object> params) {
        try {
            Query query = entityManager.createNativeQuery(sql);
            return execute0(query, params);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    private int execute0(Query query, Map<String, Object> params) {
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query.executeUpdate();
    }
}
