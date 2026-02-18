package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(repository.findAll(PageRequest.of(page, limit)).getContent());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        return repository.findById(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(repository.findByCategory(category));
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Product>> getByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(repository.findByBrand(brand));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(repository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword));
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> priceRange(@RequestParam Double min, @RequestParam Double max) {
        return ResponseEntity.ok(repository.findByPriceBetween(min, max));
    }

    @GetMapping("/in-stock")
    public ResponseEntity<List<Product>> inStock() {
        return ResponseEntity.ok(repository.findByStockQuantityGreaterThan(0));
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(product));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product updated) {
        return repository.findById(productId)
                .map(p -> {
                    p.setName(updated.getName());
                    p.setDescription(updated.getDescription());
                    p.setPrice(updated.getPrice());
                    p.setCategory(updated.getCategory());
                    p.setBrand(updated.getBrand());
                    p.setStockQuantity(updated.getStockQuantity());
                    return ResponseEntity.ok(repository.save(p));
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<Product> updateStock(@PathVariable Long productId, @RequestParam int quantity) {
        return repository.findById(productId)
                .map(p -> {
                    p.setStockQuantity(quantity);
                    return ResponseEntity.ok(repository.save(p));
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        if (repository.existsById(productId)) {
            repository.deleteById(productId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
