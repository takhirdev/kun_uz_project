package com.example.controller;

import com.example.dto.type.TypesCreateDTO;
import com.example.dto.type.TypesDTO;
import com.example.enums.Language;
import com.example.service.TypesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/types")
public class TypeController {
    @Autowired
    private TypesService typesService;

    @PostMapping(value = "/admin/create")
    public ResponseEntity<TypesDTO> create(@Valid @RequestBody TypesCreateDTO dto) {
        TypesDTO response = typesService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<TypesDTO>> getAll() {
        return ResponseEntity.ok(typesService.getAll());
    }

    @GetMapping("/lang")
    public ResponseEntity<List<TypesDTO>> getAllByLang(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language lang) {
        List<TypesDTO> dtoList = typesService.getAllByLang(lang);
        return ResponseEntity.ok(dtoList);
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<TypesDTO> updateRegion(@PathVariable Integer id,
                                                 @Valid @RequestBody TypesCreateDTO dto) {
        TypesDTO response = typesService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        Boolean result = typesService.delete(id);
        return ResponseEntity.ok(result);
    }
}
