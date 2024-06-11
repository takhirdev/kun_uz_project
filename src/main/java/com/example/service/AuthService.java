package com.example.service;


import com.example.dto.SmsHistoryDTO;
import com.example.dto.auth.RegistrationDTO;
import com.example.dto.auth.LoginDTO;
import com.example.dto.profile.ProfileDTO;
import com.example.entity.ProfileEntity;
import com.example.entity.SmsHistoryEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exception.AppBadException;
import com.example.repository.ProfileRepository;
import com.example.repository.SmsHistoryRepository;
import com.example.service.sender.EmailSenderService;
import com.example.service.sender.SmsSenderService;
import com.example.util.JwtUtil;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private EmailHistoryService emailHistoryService;
    @Autowired
    private SmsSenderService smsSenderService;
    @Autowired
    private SmsHistoryService smsHistoryService;
    @Autowired
    private JwtUtil jwtUtil;

    public String registrWithEmail(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (optional.isPresent()) {
            throw new AppBadException("Email already exists");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        entity.setCreatedDate(LocalDateTime.now());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);

        ProfileEntity saved = profileRepository.save(entity);
        emailSenderService.sendEmail(saved.getId(), saved.getEmail());
        return "To complete your registration please verify your email.";
    }

    public String registrWithPhone(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(dto.getPhone());
        if (optional.isPresent()) {
            throw new AppBadException("phone already exists");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        entity.setCreatedDate(LocalDateTime.now());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);

        profileRepository.save(entity);
        smsSenderService.sendSms(dto.getPhone());
        return "To complete your registration please verify your sms.";
    }

    public String verifyWithEmail(Integer userId) {
        ProfileEntity entity = profileRepository.findById(userId)
                .orElseThrow(() -> new AppBadException("User not found"));

        emailHistoryService.isNotExpiredEmail(entity.getEmail());  // check for expiration date

        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration not completed");
        }
        profileRepository.updateStatus(userId, ProfileStatus.ACTIVE);
        return "registration finished successfully ";
    }

    public String verifyWithSms(SmsHistoryDTO dto) {
        ProfileEntity profileEntity = profileRepository.findByPhoneAndVisibleTrue(dto.getPhone())
                .orElseThrow(() -> new AppBadException("User not found"));

        if (!profileEntity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration not completed");
        }

        SmsHistoryEntity smsEntity = smsHistoryRepository.findTopByPhoneOrderByCreatedDateDesc(dto.getPhone())
                        .orElseThrow(() -> new AppBadException("phone not found"));

        smsHistoryService.isExpired(smsEntity.getPhone()); // check for expiration time

        if (!smsEntity.getMessage().equals(dto.getMessage())) {
            throw new AppBadException("wrong password");
        }

        profileRepository.updateStatus(profileEntity.getId(), ProfileStatus.ACTIVE);
        return "registration finished successfully ";
    }

    public String resendEmail(String email) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(email);
        if (optional.isEmpty()) {
            throw new AppBadException("Email not exists");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration not completed");
        }

        emailHistoryService.checkEmailLimit(email);
        emailSenderService.sendEmail(entity.getId(), email);
        return "To complete your registration please verify your email.";
    }

    public String resendSms(String phone) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(phone);
        if (optional.isEmpty()) {
            throw new AppBadException("phone not exists");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration not completed");
        }
        smsHistoryService.checkSmsLimit(phone); // check for limit
        smsSenderService.sendSms(phone);
        return "To complete your registration please verify your email.";
    }

    public ProfileDTO loginWithEmail(LoginDTO dto) {
        ProfileEntity entity = profileRepository.findByEmailAndVisibleTrue(dto.getEmail())
                .orElseThrow(() -> new AppBadException("User not found"));

        if (!entity.getPassword().equals(MD5Util.getMd5(dto.getPassword()))) {
            throw new AppBadException("Wrong password");
        }
        if (entity.getStatus().equals(ProfileStatus.NOT_ACTIVE) || entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("User should be  active");
        }
        ProfileDTO response = new ProfileDTO();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setSurname(entity.getSurname());
        response.setPhone(entity.getPhone());
        response.setCreatedDate(entity.getCreatedDate());
        response.setJwt(jwtUtil.generateToken(entity.getId(),entity.getEmail(), entity.getRole()));
        return response;
    }

    public ProfileDTO loginWithPhone(LoginDTO dto) {
        ProfileEntity entity = profileRepository.findByPhoneAndVisibleTrue(dto.getPhone())
                .orElseThrow(() -> new AppBadException("User not found"));

        if (!entity.getPassword().equals(MD5Util.getMd5(dto.getPassword()))) {
            throw new AppBadException("Wrong password");
        }

        if (entity.getStatus().equals(ProfileStatus.NOT_ACTIVE) || entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("User should be  active");
        }
        ProfileDTO response = new ProfileDTO();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setSurname(entity.getSurname());
        response.setPhone(entity.getPhone());
        response.setCreatedDate(entity.getCreatedDate());
        response.setJwt(jwtUtil.generateToken(entity.getId(),entity.getEmail(), entity.getRole()));
        return response;
    }
}
