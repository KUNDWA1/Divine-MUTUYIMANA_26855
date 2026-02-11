package com.example.demo.controller;

import com.example.demo.model.MenuItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final List<MenuItem> menu = new ArrayList<>();

    // Constructor: add 8 sample menu items
    public MenuController() {
        menu.add(new MenuItem(1L, "Spring Rolls", "Crispy vegetable rolls", 5.0, "Appetizer", true));
        menu.add(new MenuItem(2L, "Caesar Salad", "Fresh romaine with dressing", 6.5, "Appetizer", true));
        menu.add(new MenuItem(3L, "Grilled Chicken", "Served with mashed potatoes", 12.0, "Main Course", true));
        menu.add(new MenuItem(4L, "Spaghetti Bolognese", "Classic Italian pasta", 11.0, "Main Course", false));
        menu.add(new MenuItem(5L, "Chocolate Cake", "Rich chocolate dessert", 6.0, "Dessert", true));
        menu.add(new MenuItem(6L, "Cheesecake", "Creamy New York style", 6.5, "Dessert", true));
        menu.add(new MenuItem(7L, "Coke", "Chilled soft drink", 2.0, "Beverage", true));
        menu.add(new MenuItem(8L, "Coffee", "Freshly brewed coffee", 3.0, "Beverage", false));
    }

    // GET /api/menu
    @GetMapping
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        return ResponseEntity.ok(menu); // 200
    }

    // GET /api/menu/{id}
    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        for (MenuItem item : menu) {
            if (item.getId().equals(id)) {
                return ResponseEntity.ok(item); // 200
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
    }

    // GET /api/menu/category/{category}
    @GetMapping("/category/{category}")
    public ResponseEntity<List<MenuItem>> getMenuByCategory(@PathVariable String category) {
        List<MenuItem> result = new ArrayList<>();
        for (MenuItem item : menu) {
            if (item.getCategory().equalsIgnoreCase(category)) {
                result.add(item);
            }
        }
        return ResponseEntity.ok(result); // 200
    }

    // GET /api/menu/available?available=true
    @GetMapping("/available")
    public ResponseEntity<List<MenuItem>> getAvailableItems(@RequestParam boolean available) {
        List<MenuItem> result = new ArrayList<>();
        for (MenuItem item : menu) {
            if (item.isAvailable() == available) {
                result.add(item);
            }
        }
        return ResponseEntity.ok(result); // 200
    }

    // GET /api/menu/search?name={name}
    @GetMapping("/search")
    public ResponseEntity<List<MenuItem>> searchByName(@RequestParam String name) {
        List<MenuItem> result = new ArrayList<>();
        for (MenuItem item : menu) {
            if (item.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(item);
            }
        }
        return ResponseEntity.ok(result); // 200
    }

    // POST /api/menu
    @PostMapping
    public ResponseEntity<MenuItem> addMenuItem(@RequestBody MenuItem menuItem) {
        menu.add(menuItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(menuItem); // 201
    }

    // PUT /api/menu/{id}/availability
    @PutMapping("/{id}/availability")
    public ResponseEntity<MenuItem> toggleAvailability(@PathVariable Long id) {
        for (MenuItem item : menu) {
            if (item.getId().equals(id)) {
                item.setAvailable(!item.isAvailable());
                return ResponseEntity.ok(item); // 200
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
    }

    // DELETE /api/menu/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        for (MenuItem item : menu) {
            if (item.getId().equals(id)) {
                menu.remove(item);
                return ResponseEntity.noContent().build(); // 204
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
    }
}
