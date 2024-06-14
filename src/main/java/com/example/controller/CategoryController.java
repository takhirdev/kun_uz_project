package com.example.controller;

import com.example.dto.category.CategoryCreateDTO;
import com.example.dto.category.CategoryDTO;
import com.example.enums.Language;
import com.example.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "/admin/create")
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryCreateDTO dto) {
        CategoryDTO response = categoryService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<CategoryDTO>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping("/lang")
    public ResponseEntity<List<CategoryDTO>> getAllByLang(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language lang) {
        List<CategoryDTO> dtoList = categoryService.getAllByLang(lang);
        return ResponseEntity.ok(dtoList);
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<CategoryDTO> updateRegion(@PathVariable Integer id,
                                                    @Valid @RequestBody CategoryCreateDTO dto) {
        CategoryDTO response = categoryService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        Boolean result = categoryService.delete(id);
        return ResponseEntity.ok(result);
    }
}
