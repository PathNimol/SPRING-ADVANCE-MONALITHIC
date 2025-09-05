package org.hrd.homeworkspringdatajpa.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hrd.homeworkspringdatajpa.base.ApiResponse;
import org.hrd.homeworkspringdatajpa.model.dto.requst.ProductRequest;
import org.hrd.homeworkspringdatajpa.model.dto.response.PageResponse;
import org.hrd.homeworkspringdatajpa.model.dto.response.ProductResponse;
import org.hrd.homeworkspringdatajpa.model.enums.ProductProperty;
import org.hrd.homeworkspringdatajpa.service.ProductService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @Operation(summary = "List all products (paginated")
    public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> getAllProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam ProductProperty productProperty,
            @RequestParam Sort.Direction direction
    ) {
        PageResponse<ProductResponse> productPage = productService.getAllProducts(page, size, productProperty, direction);

        // Wrap in ApiResponse
        ApiResponse<PageResponse<ProductResponse>> response = ApiResponse.<PageResponse<ProductResponse>>builder()
                .message("All products fetched successfully!")
                .payload(productPage)
                .status("FOUNDED")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{product-id}")
    @Operation(summary = "Get a product by ID")
    public  ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable("product-id") Long productId) {
        ApiResponse<ProductResponse> response = ApiResponse.<ProductResponse>builder()
                .message("Product with ID: " + productId + " fetched successfully!")
                .payload(productService.getProductById(productId))
                .status("FOUNDED")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create new product")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest request) {
        ApiResponse<ProductResponse> response = ApiResponse.<ProductResponse>builder()
                .message("Product created successfully")
                .payload(productService.createProduct(request))
                .status("CREATED")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{product-id}")
    @Operation(summary = "Update a product by ID")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProductById(@PathVariable("product-id") Long productId, @Valid @RequestBody ProductRequest request) {
        ApiResponse<ProductResponse> response = ApiResponse.<ProductResponse>builder()
                .message("Product with ID: " + productId + " updated successfully!")
                .payload(productService.updateProductById(productId, request))
                .status("UPDATED")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{product-id}")
    @Operation(summary = "Delete a product by ID")
    public ResponseEntity<ApiResponse<ProductResponse>> deleteProductById(@PathVariable("product-id") Long productId) {
        productService.deleteProductById(productId);
        ApiResponse<ProductResponse> response = ApiResponse.<ProductResponse>builder()
                .message("Product with ID: " + productId + " deleted successfully!")
                .payload(null)
                .status("DELETED")
                .build();
        return ResponseEntity.ok(response);
    }
}
