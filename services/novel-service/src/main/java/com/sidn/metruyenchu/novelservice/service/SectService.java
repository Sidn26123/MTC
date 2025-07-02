package com.sidn.metruyenchu.novelservice.service;

import com.sidn.metruyenchu.novelservice.dto.request.sect.SectCreationRequest;
import com.sidn.metruyenchu.novelservice.dto.response.SectResponse;
import com.sidn.metruyenchu.novelservice.entity.Sect;
import com.sidn.metruyenchu.novelservice.exception.AppException;
import com.sidn.metruyenchu.novelservice.exception.ErrorCode;
import com.sidn.metruyenchu.novelservice.mapper.SectMapper;
import com.sidn.metruyenchu.novelservice.repository.SectRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level  = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SectService {
    SectRepository sectRepository;
    SectMapper sectMapper;

    //CRUD sect
    //get all sect
    public List<SectResponse> getAllSect() {
        return sectRepository.findAll().stream()
                .map(sectMapper::toSectResponse)
                .collect(Collectors.toList());
    }

    //get sect by id
    public SectResponse getSectById(String sectId) {
        return sectRepository.findById(sectId)
                .map(sectMapper::toSectResponse)
                .orElseThrow(() -> new AppException(ErrorCode.SECT_NOT_FOUND)
                );
    }

    //create sect
    public SectResponse createSect(SectCreationRequest request) {
        Sect sect = sectMapper.toSect(request);

        sectRepository.findByName(sect.getName())
                .ifPresent(checkSect -> {
                    throw new AppException(ErrorCode.SECT_ALREADY_EXISTED);
                });
        try {
            sect = sectRepository.save(sect);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return sectMapper.toSectResponse(sect);
    }

    public SectResponse updateSect(String sectId, SectCreationRequest request) {
        Sect sect = sectRepository.findById(sectId)
                .orElseThrow(() -> new AppException(ErrorCode.SECT_NOT_FOUND));
        sect.setName(request.getName());
        sectRepository.findByName(sect.getName())
                .ifPresent(checkSect -> {
                    throw new AppException(ErrorCode.SECT_ALREADY_EXISTED);
                });
        try {
            sect = sectRepository.save(sect);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }

        return sectMapper.toSectResponse(sect);
    }

    public void deleteSect(String sectId) {
        Sect sect = sectRepository.findById(sectId)
                .orElseThrow(() -> new AppException(ErrorCode.SECT_NOT_FOUND));
        sect.setIsDeleted(true);
        try {
            sectRepository.save(sect);
        } catch (Exception exception) {
            throw new AppException(ErrorCode.UNKNOWN_ERROR);
        }
    }

    public List<SectResponse> getSectByName(String name) {
        return sectRepository.findByName(name).stream()
                .map(sectMapper::toSectResponse)
                .collect(Collectors.toList());
    }

    public List<SectResponse> getActivatingList() {
        return sectRepository.findAllByIsActiveAndIsDeleted(true, false).stream()
                .map(sectMapper::toSectResponse)
                .collect(Collectors.toList());
    }
}
