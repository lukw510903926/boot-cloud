package com.tykj.cloud.common.mapper;
/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import java.io.Serializable;
import java.util.List;
import com.github.pagehelper.PageInfo;
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

    T selectByKey(String key);

    int save(T entity);

    int deleteById(String key);
    
    int delete(T t);

    int updateAll(T entity);

    int updateNotNull(T entity);

    List<T> selectByExample(Example example);

    /**
     * 根据唯一键批量删除
     * @param list
     */
	void deleteByIds(List<String> list);

	/**
	 * 根据条件判断相同对象是否存在
	 * @param uid
	 * @param list
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

	PageInfo<T> findByModel(PageInfo<T> page, T t, boolean isLike);

	List<T> selectAll();

}
