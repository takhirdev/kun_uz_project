package com.example.controller;

import com.example.dto.region.RegionCreateDTO;
import com.example.dto.region.RegionDTO;
import com.example.enums.Language;
import com.example.service.RegionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {

    private final RegionService regionService;

    public RegionController(RegionService regionService) {
        this.regionService = regionService;
    }

    @PostMapping(value = "/admin/create")
    public ResponseEntity<RegionDTO> create(@Valid @RequestBody RegionCreateDTO region) {
        RegionDTO response = regionService.create(region);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<RegionDTO>> getAll() {
        return ResponseEntity.ok(regionService.getAll());
    }

    @GetMapping("/lang")
    public ResponseEntity<List<RegionDTO>> getAllByLang(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language lang) {
        List<RegionDTO> regionDTOList = regionService.getAllByLang(lang);
        return ResponseEntity.ok(regionDTOList);
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<RegionDTO> updateRegion(@PathVariable Integer id,
                                                  @Valid @RequestBody RegionCreateDTO dto) {
        RegionDTO response = regionService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        Boolean result = regionService.delete(id);
        return ResponseEntity.ok(result);
    }
}
