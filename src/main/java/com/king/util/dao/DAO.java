package com.king.util.dao;

import org.apache.ibatis.session.SqlSessionFactory;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 通用DAO接口，常规操作都可以使用此接口进行操作
 * @author: shenshuiliang
 * @create: 2019-08-26 21:40
 **/
public interface DAO<T, PK extends Serializable> {
    /**
     * 新增记录，对象中的数据是什么数据库就保存什么
     *
     * @param t
     * @return
     */
    int insert(T t);

    /**
     * 新增记录,对象中字段为null 数据库保持不变
     *
     * @param t
     * @return
     */
    int insertSelective(T t);

    /**
     * 删除数据库中的数据
     *
     * @param id
     * @return
     */
    Integer deleteByPrimaryKey(PK id);

    /**
     * clone型编辑数据保存
     *
     * @param t
     * @throws Exception
     */
    int updateByPrimaryKey(T t);

    /**
     * 合并型编辑数据保存
     *
     * @param t
     * @throws Exception
     */
    int updateByPrimaryKeySelective(T t);

    /**
     * 根据ID得到具体对象实例
     *
     * @param id
     * @return T
     * @throws Exception
     */
    T selectByPrimaryKey(PK id);

    /* =====================实现mybatis工具生成方法 end======================*/


    /**
     * 根据实体条件进行查询数据
     *
     * @param t
     * @return
     * @throws Exception
     */
    List<T> searchByEntity(T t);

    /**
     * 根据实体条件进行分页查询数据
     *
     * @param page
     * @param t
     * @return
     * @throws Exception
     */
    List<T> searchByEntity(Page page, T t);

    /**
     * 自定义新增操作
     *
     * @param param
     * @param statement
     * @return
     */
    int executeInsertMethod(Object param, String statement);

    /**
     * 自定义修改操作
     *
     * @param param
     * @param statement
     * @return
     */
    int executeUpdateMethod(Object param, String statement);

    /**
     * 自定义删除
     *
     * @param param
     * @param statement
     * @return
     */
    int executeDeleteMethod(Object param, String statement);

    /**
     * 自定义查询操作，返回一条记录
     *
     * @param param
     * @param statement
     * @return
     */
    <R> R executeSelectOneMethod(Object param, String statement, Class<R> r);

    /**
     * 自定义查询操作，不带分页
     *
     * @param param
     * @param statement
     * @return
     */
    <R> List<R> executeListMethod(Object param, String statement, Class<R> r);

    /**
     * 自定义查询操作，带分页
     *
     * @param param
     * @param statement
     * @return
     */
    <R> List<R> executeListMethod(Object param, String statement, Page page, String pageStatement, Class<R> r);

    /**
     * 设置nameSpace
     *
     * @param prefix
     */
    void setPrefix(String prefix);

    /**
     * 设置sessionfactory
     *
     * @param sqlSessionFactory
     */
    void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory);
}
