package com.king.util.dao.impl;

import com.github.pagehelper.PageHelper;
import com.king.util.dao.Page;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * @param <T>  实体类
 * @param <PK> 主键类
 * @description: 基本DAO层操作抽象父类
 * @author: shenshuiliang
 * @create: 2019-08-26 21:23
 **/
public class BaseMybatisDAO<T, PK extends Serializable> extends SqlSessionDaoSupport {
    /**
     * 设置sqlSessionFactory
     */
    @Override
    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    /**
     * 新增数据动作
     *
     * @param statement
     * @param entity
     */
    protected Integer create(String statement, T entity) {
        return getSqlSession().insert(statement, entity);
    }

    /**
     * 根据ID列表进行删除，可以删除一个或多个
     *
     * @param statement
     * @param id
     * @return
     */
    protected Integer delete(String statement, PK id) {
        return getSqlSession().delete(statement, id);
    }

    /**
     * 更新数据
     *
     * @param statement
     * @param entity
     */
    protected Integer edit(String statement, T entity) {
        return getSqlSession().update(statement, entity);
    }

    /**
     * 根据ID进行查询实体的一个实例
     *
     * @param statement
     * @param id
     * @return
     */
    protected T getEntityById(String statement, PK id) {
        return getSqlSession().selectOne(statement, id);
    }

    /**
     * 根据实体参数进行查询
     *
     * @param statement
     * @param pagestatement
     * @param page
     * @param entity
     * @return
     */
    protected List<T> searchEntityList(String statement, String pagestatement, Page page, T entity) {
        if (null == page) {
            // 不需要分页
            return getSqlSession().selectList(statement, entity);
        } else {// 需要分页
            // 得到总记录数
            Integer totalRows = count(pagestatement, entity);
            page.setTotalRows(totalRows);
            // 分页查询
            // 分页计算
            page.init(totalRows, page.getPageSize(), page.getCurrentPage());
            if (page.isFlag()) {
                PageHelper.offsetPage(page.getOffset1(), page.getLimit1(), false);
            } else {
                PageHelper.startPage(page.getCurrentPage(), page.getPageSize(), false);
            }
            return getSqlSession().selectList(statement, entity);
        }
    }

    /**
     * 根据实体进行count
     *
     * @param statement
     * @param entity
     * @return
     */
    protected Integer count(String statement, T entity) {
        return getSqlSession().selectOne(statement, entity);
    }


    protected int executeInsertMethodImpl(Object param, String statement) {
        return getSqlSession().insert(statement, param);
    }

    protected int executeUpdateMethodImpl(Object param, String statement) {
        return getSqlSession().update(statement, param);

    }


    protected int executeDeleteMethodImpl(Object param, String statement) {
        return getSqlSession().delete(statement, param);
    }

    protected <R> R executeSelectOneMethodImpl(Object param, String statement, Class<R> r) {
        return getSqlSession().selectOne(statement, param);
    }

    protected <R> List<R> executeListMethodImpl(Object param, String statement, Page page, String pageStatement, Class<R> r) {
        if (null == page) {
            // 不需要分页
            return getSqlSession().selectList(statement, param);
        } else {// 需要分页
            // 得到总记录数
            int totalRows = getSqlSession().selectOne(pageStatement, param);
            page.setTotalRows(totalRows);
            // 分页计算
            page.init(totalRows, page.getPageSize(), page.getCurrentPage());
            if (page.isFlag()) {
                PageHelper.offsetPage(page.getOffset1(), page.getLimit1(), false);
            } else {
                PageHelper.startPage(page.getCurrentPage(), page.getPageSize(), false);
            }
            return getSqlSession().selectList(statement, param);
        }
    }
}
