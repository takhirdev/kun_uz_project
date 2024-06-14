package com.example.controller;

import com.example.config.SecurityConfig;
import com.example.dto.JwtDTO;
import com.example.dto.profile.ProfileCreateDTO;
import com.example.dto.profile.ProfileDTO;
import com.example.dto.profile.ProfileFilterDTO;
import com.example.dto.profile.ProfileUpdateDTO;
import com.example.entity.ProfileEntity;
import com.example.service.ProfileService;
import com.example.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

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
    public ResponseEntity<ProfileDTO> updateCurrent(@RequestBody ProfileUpdateDTO profile) {

        Integer profileId  = SecurityUtil.getProfileId();
        ProfileDTO response = profileService.update(profileId, profile);
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
