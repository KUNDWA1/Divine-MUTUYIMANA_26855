package com.example.demo.controller;

import com.example.demo.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private List<Product> products = new ArrayList<>();

    // Constructor: 10 sample products
    public ProductController() {
        products.add(new Product(1L, "iPhone 14", "Apple smartphone", 999.0, "Electronics", 10, "Apple"));
        products.add(new Product(2L, "Galaxy S23", "Samsung smartphone", 899.0, "Electronics", 5, "Samsung"));
        products.add(new Product(3L, "MacBook Pro", "Apple laptop", 1999.0, "Computers", 3, "Apple"));
        products.add(new Product(4L, "Dell XPS", "Windows laptop", 1500.0, "Computers", 0, "Dell"));
        products.add(new Product(5L, "AirPods", "Wireless earbuds", 199.0, "Accessories", 15, "Apple"));
        products.add(new Product(6L, "Sony Headphones", "Noise cancelling", 299.0, "Accessories", 8, "Sony"));
        products.add(new Product(7L, "Smart TV", "4K LED TV", 1200.0, "Electronics", 4, "LG"));
        products.add(new Product(8L, "Keyboard", "Mechanical keyboard", 120.0, "Accessories", 20, "Logitech"));
        products.add(new Product(9L, "Mouse", "Wireless mouse", 80.0, "Accessories", 0, "Logitech"));
        products.add(new Product(10L, "Tablet", "Android tablet", 400.0, "Electronics", 6, "Samsung"));
    }

    // GET /api/products?page=&limit=
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int limit) {

        int start = page * limit;
        int end = Math.min(start + limit, products.size());

        if (start >= products.size()) {
            return ResponseEntity.ok(new ArrayList<>());
        }

        return ResponseEntity.ok(products.subList(start, end));
    }

    // GET /api/products/{productId}
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        for (Product p : products) {
            if (p.getProductId().equals(productId)) {
                return ResponseEntity.ok(p);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // GET /api/products/category/{category}
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable String category) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                result.add(p);
            }
        }
        return ResponseEntity.ok(result);
    }

    // GET /api/products/brand/{brand}
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Product>> getByBrand(@PathVariable String brand) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getBrand().equalsIgnoreCase(brand)) {
                result.add(p);
            }
        }
        return ResponseEntity.ok(result);
    }

    // GET /api/products/search?keyword=
    @GetMapping("/search")
    public ResponseEntity<List<Product>> search(@RequestParam String keyword) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                p.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(p);
            }
        }
        return ResponseEntity.ok(result);
    }

    // GET /api/products/price-range?min=&max=
    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> priceRange(@RequestParam Double min, @RequestParam Double max) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getPrice() >= min && p.getPrice() <= max) {
                result.add(p);
            }
        }
        return ResponseEntity.ok(result);
    }

    // GET /api/products/in-stock
    @GetMapping("/in-stock")
    public ResponseEntity<List<Product>> inStock() {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getStockQuantity() > 0) {
                result.add(p);
            }
        }
        return ResponseEntity.ok(result);
    }

    // POST /api/products
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        products.add(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    // PUT /api/products/{productId}
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long productId,
            @RequestBody Product updated) {

        for (Product p : products) {
            if (p.getProductId().equals(productId)) {
                p.setName(updated.getName());
                p.setDescription(updated.getDescription());
                p.setPrice(updated.getPrice());
                p.setCategory(updated.getCategory());
                p.setBrand(updated.getBrand());
                p.setStockQuantity(updated.getStockQuantity());
                return ResponseEntity.ok(p);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // PATCH /api/products/{productId}/stock?quantity=
    @PatchMapping("/{productId}/stock")
    public ResponseEntity<Product> updateStock(
            @PathVariable Long productId,
            @RequestParam int quantity) {

        for (Product p : products) {
            if (p.getProductId().equals(productId)) {
                p.setStockQuantity(quantity);
                return ResponseEntity.ok(p);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // DELETE /api/products/{productId}
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        for (Product p : products) {
            if (p.getProductId().equals(productId)) {
                products.remove(p);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
