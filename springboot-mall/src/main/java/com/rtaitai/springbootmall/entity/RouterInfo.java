package com.rtaitai.springbootmall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "router_info")
@AllArgsConstructor
@NoArgsConstructor
public class RouterInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String url;

    private Integer parentId;

    /**
     * 1 目录 2 菜单 3 按钮
     */
    private Integer level;

    private String code;

    private Boolean isLocked;

}
