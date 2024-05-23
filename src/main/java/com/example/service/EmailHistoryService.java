package com.example.service;

import com.example.dto.EmailHistoryDTO;
import com.example.entity.EmailHistoryEntity;
import com.example.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmailHistoryService {
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    public void save(String email, String message){
        EmailHistoryEntity entity = new EmailHistoryEntity();
        entity.setEmail(email);
        entity.setMessage(message);
        emailHistoryRepository.save(entity);
    }

    public List<EmailHistoryDTO> getAllByEmail(String email) {
        List<EmailHistoryDTO> dtoList = emailHistoryRepository.findAllByEmail(email)
                .stream()
                .map(this::toDTO)
                .toList();
        return dtoList;
    }

    public List<EmailHistoryDTO> getAllByGivenDate(LocalDate date) {
        LocalDate from = date;
        LocalDate to = date.plusDays(1);
        List<EmailHistoryDTO> dtoList = emailHistoryRepository.findAllByGivenDate(from, to)
                .stream()
                .map(this::toDTO)
                .toList();
        return dtoList;
    }

    public Page<EmailHistoryDTO> pagination(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<EmailHistoryEntity> entityPage = emailHistoryRepository.findAllBy(pageable);

        List<EmailHistoryDTO> dtoList = entityPage.getContent().stream()
                .map(this::toDTO)
                .toList();

        long totalElements = entityPage.getTotalElements();

        return new PageImpl<EmailHistoryDTO>(dtoList,pageable,totalElements);
    }

    private EmailHistoryDTO toDTO(EmailHistoryEntity entity) {
        EmailHistoryDTO dto = new EmailHistoryDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setMessage(entity.getMessage());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
