package com.example.service;

import com.example.dto.SmsHistoryDTO;
import com.example.entity.SmsHistoryEntity;
import com.example.exception.AppBadException;
import com.example.repository.SmsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class SmsHistoryService {
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;

    public void save(SmsHistoryDTO dto) {
        SmsHistoryEntity entity = new SmsHistoryEntity();
        entity.setPhone(dto.getPhone());
        entity.setMessage(dto.getMessage());
        smsHistoryRepository.save(entity);
    }

    public List<SmsHistoryDTO> getByPhone(String phone) {
        List<SmsHistoryDTO> dtoList = smsHistoryRepository.findAllByPhone(phone).stream()
                .map(this::toDTO)
                .toList();
        return dtoList;
    }

    public List<SmsHistoryDTO> getAllByGivenDate(LocalDate date) {
        LocalDate from = date;
        LocalDate to = date.plusDays(1);
        List<SmsHistoryDTO> dtoList = smsHistoryRepository.findAllByGivenDate(from, to).stream()
                .map(this::toDTO)
                .toList();
        return dtoList;
    }

    public Page<SmsHistoryDTO> pagination(Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by("createdDate").descending());
        Page<SmsHistoryEntity> entityPage = smsHistoryRepository.findAllBy(pageable);

        List<SmsHistoryDTO> dtoList = entityPage.getContent().stream()
                .map(this::toDTO)
                .toList();
        long totalElements = entityPage.getTotalElements();

        return new PageImpl<SmsHistoryDTO>(dtoList,pageable,totalElements);
    }

    private SmsHistoryDTO toDTO(SmsHistoryEntity entity) {
        SmsHistoryDTO dto = new SmsHistoryDTO();
        dto.setId(entity.getId());
        dto.setPhone(entity.getPhone());
        dto.setMessage(entity.getMessage());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public void checkSmsLimit(String phone){
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusMinutes(1);

        Long count = smsHistoryRepository.countByPhoneAndCreatedDateBetween(phone, from, to);
        if(count >= 2){
            throw new AppBadException("Sms limit reached. Please try after some time");
        }
    }

    public void isExpired(String phone){
        Optional<SmsHistoryEntity> optional = smsHistoryRepository.findTopByPhoneOrderByCreatedDateDesc(phone);
        if(optional.isEmpty()){
            throw new AppBadException("Sms history not found");
        }
        SmsHistoryEntity entity = optional.get();
        if(entity.getCreatedDate().plusMinutes(3).isBefore(LocalDateTime.now())){
            throw new AppBadException("Confirmation time expired");
        }
    }
}
