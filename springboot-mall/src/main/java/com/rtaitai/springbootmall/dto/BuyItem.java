package com.rtaitai.springbootmall.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuyItem {

    @NotNull
    private Integer productId;

    @NotNull
    private Integer quantity;
}
