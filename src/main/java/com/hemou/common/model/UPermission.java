package com.hemou.common.model;

import java.io.Serializable;

/**
 * (UPermission)实体类
 *
 * @author hemou
 * @since 2021-01-13 18:14:01
 */
public class UPermission implements Serializable {
    private static final long serialVersionUID = 435490767988730838L;

    private Long id;
    /**
     * url地址
     */
    private String url;
    /**
     * url描述
     */
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}