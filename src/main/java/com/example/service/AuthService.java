package com.example.service;


import com.example.dto.SmsHistoryDTO;
import com.example.dto.auth.RegistrationDTO;
import com.example.dto.profile.LoginDTO;
import com.example.dto.profile.ProfileDTO;
import com.example.entity.ProfileEntity;
import com.example.entity.SmsHistoryEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.exception.AppBadException;
import com.example.repository.ProfileRepository;
import com.example.repository.SmsHistoryRepository;
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

    public String registrWithEmail(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (optional.isPresent()) {
            throw new AppBadException("Email already exists");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMd5(dto.getPassword()));
        entity.setCreatedDate(LocalDateTime.now());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setStatus(ProfileStatus.REGISTRATION);

        profileRepository.save(entity);
        emailSenderService.sendEmail(entity.getId(),entity.getEmail());
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
        Optional<ProfileEntity> optional = profileRepository.findById(userId);
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }
        ProfileEntity entity = optional.get();
        emailHistoryService.isNotExpiredEmail(entity.getEmail());  // check for expiration date
        if (!entity.getVisible() || !entity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration not completed");
        }
        profileRepository.updateStatus(userId, ProfileStatus.ACTIVE);
        return "registration finished successfully ";
    }

    public String verifyWithSms(SmsHistoryDTO dto) {
        Optional<ProfileEntity> optionalProfile = profileRepository.findByPhoneAndVisibleTrue(dto.getPhone());
        if (optionalProfile.isEmpty()) {
            throw new AppBadException("User not found");
        }
        ProfileEntity profileEntity = optionalProfile.get();
        if (!profileEntity.getStatus().equals(ProfileStatus.REGISTRATION)) {
            throw new AppBadException("Registration not completed");
        }

        Optional<SmsHistoryEntity> optionalSms = smsHistoryRepository.findTopByPhoneOrderByCreatedDateDesc(dto.getPhone());
        if (optionalSms.isEmpty()){
            throw new AppBadException("phone not found");
        }
        SmsHistoryEntity smsEntity = optionalSms.get();
        smsHistoryService.isExpired(smsEntity.getPhone()); // check for expiration time
        if (!smsEntity.getMessage().equals(dto.getMessage())){
            throw new AppBadException("wrong password");
        }

        profileRepository.updateStatus(profileEntity.getId(), ProfileStatus.ACTIVE);
        return "registration finished successfully ";
    }

    public String resendEmail(String email){
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(email);
        if (optional.isEmpty()){
            throw new AppBadException("Email not exists");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)){
            throw new AppBadException("Registration not completed");
        }

        emailHistoryService.checkEmailLimit(email);
        emailSenderService.sendEmail(entity.getId(),email);
        return "To complete your registration please verify your email.";
    }

    public String resendSms(String phone) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(phone);
        if (optional.isEmpty()){
            throw new AppBadException("phone not exists");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(ProfileStatus.REGISTRATION)){
            throw new AppBadException("Registration not completed");
        }
        smsHistoryService.checkSmsLimit(phone); // check for limit
        smsSenderService.sendSms(phone);
        return "To complete your registration please verify your email.";
    }

    public ProfileDTO loginWithEmail(LoginDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndVisibleTrue(dto.getEmail());
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getPassword().equals(MD5Util.getMd5(dto.getPassword()))) {
            throw new AppBadException("Wrong password");
        }
        if(entity.getStatus().equals(ProfileStatus.NOT_ACTIVE)||entity.getStatus().equals(ProfileStatus.REGISTRATION)){
            throw new AppBadException("User should be  active");
        }
        ProfileDTO response = new ProfileDTO();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setSurname(entity.getSurname());
        response.setPhone(entity.getPhone());
        response.setCreatedDate(entity.getCreatedDate());
        return response;
    }

    public ProfileDTO loginWithPhone(LoginDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByPhoneAndVisibleTrue(dto.getPhone());
        if (optional.isEmpty()) {
            throw new AppBadException("User not found");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getPassword().equals(MD5Util.getMd5(dto.getPassword()))) {
            throw new AppBadException("Wrong password");
        }
        if(entity.getStatus().equals(ProfileStatus.NOT_ACTIVE)||entity.getStatus().equals(ProfileStatus.REGISTRATION)){
            throw new AppBadException("User should be  active");
        }
        ProfileDTO response = new ProfileDTO();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setSurname(entity.getSurname());
        response.setPhone(entity.getPhone());
        response.setCreatedDate(entity.getCreatedDate());
        return response;
    }
}
