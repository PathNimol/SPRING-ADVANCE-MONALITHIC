package org.hrd.homeworkspringdatajpa.service;

import org.hrd.homeworkspringdatajpa.model.dto.requst.ProductRequest;
import org.hrd.homeworkspringdatajpa.model.dto.response.PageResponse;
import org.hrd.homeworkspringdatajpa.model.dto.response.ProductResponse;
import org.hrd.homeworkspringdatajpa.model.enums.ProductProperty;
import org.springframework.data.domain.Sort;

public interface ProductService {
    PageResponse<ProductResponse> getAllProducts(int page, int size, ProductProperty productProperty, Sort.Direction direction);

    ProductResponse getProductById(Long productId);

    ProductResponse createProduct(ProductRequest request);

    ProductResponse updateProductById(Long productId, ProductRequest request);

    void deleteProductById(Long productId);
}
