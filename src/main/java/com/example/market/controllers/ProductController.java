package com.example.market.controllers;

import com.example.market.models.Image;
import com.example.market.models.Product;
import com.example.market.models.User;
import com.example.market.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Console;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getProducts(@RequestParam(name = "searchWord", required = false) String title, Principal principal) {
        Map<String, Object> response = new HashMap<>();
        response.put("products", productService.listProducts(title));
        response.put("user", productService.getUserByPrincipal(principal));
        response.put("searchWord", title);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/product/{id}")
    public Product productInfo(@PathVariable Long id, Principal principal) {
        Product product = productService.getProductById(id);
        product.setUser(productService.getUserByPrincipal(principal));
        product.setImages(new ArrayList<>());
        return product;
    }

    @PostMapping("/product/create")
    public ResponseEntity<?> createProduct(@RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2,
                                           @RequestParam("file3") MultipartFile file3, Product product, Principal principal) throws IOException {
        productService.saveProduct(principal, product, file1, file2, file3);
        return ResponseEntity.ok().body("Product created successfully.");
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id, Principal principal) {
        productService.deleteProduct(productService.getUserByPrincipal(principal), id);
        return ResponseEntity.ok().body("Product deleted successfully.");
    }

    @GetMapping("/my/products")
    public List<Product> userProducts(Principal principal) {
        User user = productService.getUserByPrincipal(principal);
        return user.getProducts();
    }
}
