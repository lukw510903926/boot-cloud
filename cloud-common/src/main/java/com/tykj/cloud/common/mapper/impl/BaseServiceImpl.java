
package com.tykj.cloud.common.mapper.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import com.tykj.cloud.common.mapper.IService;
import com.tykj.cloud.common.util.PageInfo;
import com.tykj.cloud.common.util.ReflectionUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageHelper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;


/**
 * tk.mapper 通用mapper简单封装
 *
 * @author lukew
 * @eamil 13507615840@163.com
 * @create 2018-07-23 21:11
 **/
public class BaseServiceImpl<T> implements IService<T> {

    private Class<?> entityClass;

    private List<Field> fields;

    public BaseServiceImpl() {
        entityClass = ReflectionUtils.getClassGenricType(this.getClass());
        fields = ReflectionUtils.getFields(entityClass);
    }

    @Autowired
    protected Mapper<T> mapper;

    public Mapper<T> getMapper() {
        return mapper;
    }

    @Override
    public List<T> selectAll() {

        return this.mapper.selectAll();
    }

    @Override
    public T selectByKey(Serializable key) {

        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public int save(T entity) {
        return mapper.insert(entity);
    }

    @Override
    public int deleteById(Serializable key) {
        return mapper.deleteByPrimaryKey(key);
    }

    @Override
    public int delete(T t) {
        return mapper.delete(t);
    }

    @Override
    public void deleteByIds(List<Serializable> list) {

        if (CollectionUtils.isNotEmpty(list)) {
            for (Serializable key : list) {
                this.mapper.deleteByPrimaryKey(key);
            }
        }
    }

    @Override
    public int updateAll(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateNotNull(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public List<T> selectByExample(Example example) {
        return mapper.selectByExample(example);
    }

    @Override
    public List<T> findByModel(T t, boolean isLike) {

        Example example = this.getExample(t, isLike);
        return this.selectByExample(example);
    }

    @Override
    public PageInfo<T> findByModel(PageInfo<T> page, T t, boolean isLike) {

        Example example = this.getExample(t, isLike);
        if (page != null) {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            example.orderBy("id").desc();
        }
        return new PageInfo<T>(this.selectByExample(example));
    }

    private Example getExample(T t, boolean isLike) {

        Example example = new Example(entityClass);
        Example.Criteria criteria = example.createCriteria();
        for (Field field : fields) {
            String property = field.getName();
            Class<?> type = field.getType();
            Object value = ReflectionUtils.getter(t, property);
            if (value != null) {
                if (type == String.class && isLike) {
                    criteria.andLike(property, "%" + value + "%");
                } else {
                    criteria.andEqualTo(property, value);
                }
            }
        }
        return example;
    }

    @Override
    public boolean check(Serializable uid, List<T> list) {

        if (CollectionUtils.isEmpty(list)) {
            return true;
        }
        int size = list.size();
        if (size > 1) {
            return false;
        }
        T t = list.get(0);
        String oid = ReflectionUtils.getter(t, "id") + "";
        return oid.equals(uid);
    }
}
