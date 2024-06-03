package com.example.controller;

import com.example.dto.JwtDTO;
import com.example.dto.profile.ProfileCreateDTO;
import com.example.dto.profile.ProfileDTO;
import com.example.dto.profile.ProfileFilterDTO;
import com.example.dto.profile.ProfileUpdateDTO;
import com.example.enums.ProfileRole;
import com.example.service.ProfileService;
import com.example.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private SecurityUtil securityUtil;

    @PostMapping(value = "/admin/create")
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileCreateDTO dto) {
        ProfileDTO response = profileService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/admin/all")
    ResponseEntity<Page<ProfileDTO>> getAll(@RequestParam Integer pageNumber,
                                            @RequestParam Integer pageSize) {
        Page<ProfileDTO> response = profileService.getAll(pageNumber - 1, pageSize);
        return ResponseEntity.ok(response);

    }

    @PutMapping(value = "/update/current")   // update own details
    public ResponseEntity<ProfileDTO> updateCurrent(@RequestBody ProfileUpdateDTO profile,
                                                    @RequestHeader("Authorization") String token) {
        JwtDTO dto = securityUtil.getJwtDTO(token);
        ProfileDTO response = profileService.update(dto.getId(), profile);
        return ResponseEntity.ok(response);
    }


    @PutMapping(value = "/admin/update/{id}")
    public ResponseEntity<ProfileDTO> update(@PathVariable Integer id,
                                             @RequestBody ProfileUpdateDTO profile) {
        ProfileDTO response = profileService.update(id, profile);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/admin/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id) {
        Boolean response = profileService.delete(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/admin/filter")
    ResponseEntity<Page<ProfileDTO>> filter(@RequestBody ProfileFilterDTO dto,
                                            @RequestParam Integer pageNumber,
                                            @RequestParam Integer pageSize) {
        Page<ProfileDTO> response = profileService.filter(dto, pageNumber - 1, pageSize);
        return ResponseEntity.ok(response);
    }

}
