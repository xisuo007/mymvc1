package com.king.util.dao.impl;

import com.king.util.dao.DAO;
import com.king.util.dao.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 通用DAO实现，常规数据库动作直接可以重用，只需要进行配置即可
 * @author: shenshuiliang
 * @create: 2019-08-26 21:40
 **/
@Component
public class MybatisDaoImpl <T, PK extends Serializable> extends BaseMybatisDAO<T, PK> implements DAO<T, PK> {
    protected Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * 命名空间
     */
    private String   prefix               = null;
    /**
     * 保存
     */
    private String   insert               = "insert";
    /**
     * 保存
     */
    private String   insertSelective      = "insertSelective";

    /**
     * 删除
     */
    private String   deleteByPrimaryKey   = "deleteByPrimaryKey";
    /**
     * 克隆编辑
     */
    private String   updateByPrimaryKey        = "updateByPrimaryKey";
    /**
     * 合并编辑
     */
    private String   updateByPrimaryKeySelective       = "updateByPrimaryKeySelective";
    /**
     * 根据ID取实例
     */
    private String   selectByPrimaryKey   = "selectByPrimaryKey";
    /**
     * 查询
     */
    private String   searchByEntity      = "searchByEntity";
    /**
     * 查询Count
     */
    private String   searchByEntityCount = "searchByEntityCount";

    /**
     * 获取命名空间
     */
    protected String getPrefix() {
        if (null == this.prefix) {
            log.debug("没有获得命名空间名称");
        }
        return this.prefix;
    }
    @Override
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    /**
     * @param
     */
    protected String bulidStatements(String st) {
        StringBuffer sb = new StringBuffer(getPrefix());
        sb.append('.').append(st);
        log.debug(sb.toString());
        return sb.toString();
    }

    @Override
    public int insert(T t) {
        return create(bulidStatements(insert), t);
    }
    @Override
    public int insertSelective(T t) {
        return create(bulidStatements(insertSelective), t);
    }
    @Override
    public Integer deleteByPrimaryKey(PK id) {
        return delete(bulidStatements(deleteByPrimaryKey), id);
    }
    @Override
    public int updateByPrimaryKey(T t) {
        return edit(bulidStatements(updateByPrimaryKey), t);
    }
    @Override
    public int updateByPrimaryKeySelective(T t) {
        return edit(bulidStatements(updateByPrimaryKeySelective), t);
    }
    @Override
    public T selectByPrimaryKey(PK id) {
        return getEntityById(bulidStatements(selectByPrimaryKey), id);
    }
    @Override
    public List<T> searchByEntity(T t) {
        return searchEntityList(bulidStatements(searchByEntity), null, null, t);
    }
    @Override
    public List<T> searchByEntity(Page page, T t) {
        return searchEntityList(bulidStatements(searchByEntity), bulidStatements(searchByEntityCount), page, t);
    }
    @Override
    public int executeInsertMethod(Object param, String statement) {
        return executeInsertMethodImpl(param, bulidStatements(statement));
    }
    @Override
    public int executeUpdateMethod(Object param, String statement) {
        return executeUpdateMethodImpl(param, bulidStatements(statement));
    }

    @Override
    public int executeDeleteMethod(Object param, String statement) {
        return executeDeleteMethodImpl(param, bulidStatements(statement));
    }

    @Override
    public <R> R executeSelectOneMethod(Object param, String statement,Class<R> r) {
        return executeSelectOneMethodImpl(param, bulidStatements(statement),r);
    }
    @Override
    public <R> List<R> executeListMethod(Object param, String statement, Class<R> r) {
        return executeListMethodImpl(param, bulidStatements(statement), null, null,r);
    }
    @Override
    public <R> List<R> executeListMethod(Object param, String statement, Page page, String pageStatement, Class<R> r) {
        return executeListMethodImpl(param, bulidStatements(statement), page, bulidStatements(pageStatement),r);
    }
}
