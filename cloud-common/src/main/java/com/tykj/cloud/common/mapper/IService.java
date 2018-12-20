package com.tykj.cloud.common.mapper;

import java.io.Serializable;
import java.util.List;

import com.tykj.cloud.common.util.PageInfo;
import tk.mybatis.mapper.entity.Example;

/**
 * 通用接口
 * @Description: 
 * @author lukew
 * @email 13507615840@163.com
 * @date 2018年4月2日 下午10:19:17 
 * @param <T>
 */
public interface IService<T> {

	/**
	 * 根据key 查询
	 * @param key
	 * @return
	 */
    T selectByKey(Serializable key);

	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	int save(T entity);

	/**
	 * 根据key删除
	 * @param key
	 * @return
	 */
    int deleteById(Serializable key);

	/**
	 * 根据对象删除 delete table_name where column = value .....
	 * @param t
	 * @return
	 */
	int delete(T t);

	/**
	 * 更新所有列
	 * @param entity
	 * @return
	 */
    int updateAll(T entity);

	/**
	 * 更新非空列
	 * @param entity
	 * @return
	 */
	int updateNotNull(T entity);

	/**
	 * 样例查询
	 * @param example
	 * @return
	 */
    List<T> selectByExample(Example example);

    /**
     * 根据唯一键批量删除
     * @param list
     */
	void deleteByIds(List<Serializable> list);

	/**
	 * 根据条件判断相同对象是否存在
	 * @param uid 主键id
	 * @param list 条件查询到的数据
	 * @return
	 */
	boolean check(Serializable uid, List<T> list);

	/**
	 * 自动构建样例查询
	 * @param t
	 * @param isLike
	 * @return
	 */
	List<T> findByModel(T t, boolean isLike);

	/**
	 * 自动构建样例查询 带分页
	 * @param page
	 * @param t
	 * @param isLike
	 * @return
	 */
	PageInfo<T> findByModel(PageInfo<T> page, T t, boolean isLike);

	/**
	 * 查询全部数据
	 * @return
	 */
	List<T> selectAll();
}
