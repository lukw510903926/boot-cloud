package com.cloud.api.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lukew
 * @ClassName: DictValue
 * @Description: 枚举值
 * @email 13507615840@163.com
 * @date 2017年12月5日 下午9:25:04
 */
public class DictValue implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 字典分类id
     */
    private String dictTypeId;

    /**
     * 字典分类名称
     */
    private DictType dictType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改时间
     */
    private Date modified;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDictTypeId() {
        return dictTypeId;
    }

    public void setDictTypeId(String dictTypeId) {
        this.dictTypeId = dictTypeId;
    }

    public DictType getDictType() {
        return dictType;
    }

    public void setDictType(DictType dictType) {
        this.dictType = dictType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
}
