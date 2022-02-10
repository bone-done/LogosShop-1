package com.vansisto.logosshop.service;

import com.vansisto.logosshop.domain.ProductDTO;

public interface ProductService extends BaseService<ProductDTO> {
    ProductDTO createByProductIdInOrderById(Long productId, Long orderId);
}
