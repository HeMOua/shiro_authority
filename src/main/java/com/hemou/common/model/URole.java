package com.hemou.common.model;

import java.io.Serializable;

/**
 * (URole)实体类
 *
 * @author hemou
 * @since 2021-01-12 18:46:05
 */
public class URole implements Serializable {
    private static final long serialVersionUID = 876596406312836695L;

    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色类型
     */
    private String type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}