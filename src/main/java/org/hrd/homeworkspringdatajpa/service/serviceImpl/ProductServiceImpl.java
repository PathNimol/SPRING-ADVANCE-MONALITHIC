package org.hrd.homeworkspringdatajpa.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.hrd.homeworkspringdatajpa.exception.NotFoundException;
import org.hrd.homeworkspringdatajpa.model.dto.requst.ProductRequest;
import org.hrd.homeworkspringdatajpa.model.dto.response.PageResponse;
import org.hrd.homeworkspringdatajpa.model.dto.response.ProductResponse;
import org.hrd.homeworkspringdatajpa.model.enity.Product;
import org.hrd.homeworkspringdatajpa.model.enums.ProductProperty;
import org.hrd.homeworkspringdatajpa.repository.ProductRepository;
import org.hrd.homeworkspringdatajpa.service.ProductService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public PageResponse<ProductResponse> getAllProducts(int page, int size, ProductProperty productProperty, Sort.Direction direction) {
        if (page < 1) page = 1;
        if (size < 1) size = 10;

        Page<Product> productPage = productRepository.findAll(
                PageRequest.of(page - 1, size, Sort.by(direction, productProperty.getFieldName()))
        );

        List<ProductResponse> productResponses = productPage.map(Product::toResponse).toList();

        PageResponse.Pagination pagination = new PageResponse.Pagination(
                productPage.getTotalElements(),
                productPage.getNumber() + 1,
                productPage.getSize(),
                productPage.getTotalPages()
        );

        return new PageResponse<>(productResponses, pagination);
    }

    @Override
    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + productId));
        return product.toResponse();
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {

        try {
            Product product = new Product();
            product.setName(trim(request.getName()));
            product.setUnitPrice(request.getUnitPrice());
            product.setDescription(trim(request.getDescription()));

            return productRepository.save(product).toResponse();
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Product with the same name already exists");
        }
    }

    @Override
    public ProductResponse updateProductById(Long productId, ProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + productId));

        product.setName(trim(request.getName()));
        product.setUnitPrice(request.getUnitPrice());
        product.setDescription(trim(request.getDescription()));

        try {
            return productRepository.save(product).toResponse();
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("Product with the same name already exists");
        }
    }

    @Override
    public void deleteProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + productId));
        productRepository.delete(product);
    }

    //Trim all input
    private String trim(String value) {
        return value == null ? null : value.trim();
    }
}
