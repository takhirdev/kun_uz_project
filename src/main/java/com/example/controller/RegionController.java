package com.example.controller;

import com.example.dto.region.RegionCreateDTO;
import com.example.dto.region.RegionDTO;
import com.example.enums.Language;
import com.example.enums.ProfileRole;
import com.example.service.RegionService;
import com.example.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;
    @Autowired
    private SecurityUtil securityUtil;

    @PostMapping(value = "/create")
    public ResponseEntity<RegionDTO> create(@RequestHeader("Authorization") String token,
                                            @Valid @RequestBody RegionCreateDTO region) {
        securityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        RegionDTO response = regionService.create(region);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<RegionDTO>> getAll(@RequestHeader("Authorization") String token) {
        securityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        return ResponseEntity.ok(regionService.getAll());
    }

    @GetMapping("/lang")
    public ResponseEntity<List<RegionDTO>> getAllByLang(@RequestHeader(value = "Accept-Language", defaultValue = "UZ") Language lang) {
        List<RegionDTO> regionDTOList = regionService.getAllByLang(lang);
        return ResponseEntity.ok(regionDTOList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RegionDTO> updateRegion(@RequestHeader("Authorization") String token,
                                                  @PathVariable Integer id,
                                                  @Valid @RequestBody RegionCreateDTO dto) {
        securityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        RegionDTO response = regionService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@RequestHeader("Authorization") String token,
                                          @PathVariable Integer id) {
        securityUtil.getJwtDTO(token, ProfileRole.ROLE_ADMIN);
        Boolean result = regionService.delete(id);
        return ResponseEntity.ok(result);
    }
}
