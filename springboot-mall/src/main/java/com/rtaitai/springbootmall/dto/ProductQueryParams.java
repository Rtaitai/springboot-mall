package com.rtaitai.springbootmall.dto;

import com.rtaitai.springbootmall.constant.ProductCategory;
import lombok.Data;
import org.springframework.data.relational.core.sql.In;

@Data
public class ProductQueryParams {

    private ProductCategory category;

    private String search;

    private String orderBy;

    private String sort;

    private Integer limit;

    private Integer offset;
}
