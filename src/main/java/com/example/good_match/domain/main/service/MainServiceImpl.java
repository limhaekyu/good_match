package com.example.good_match.domain.main.service.impl;

import com.example.good_match.domain.main.dto.response.MainResponseDto;
import com.example.good_match.domain.main.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {

    @Override
    public List<MainResponseDto> showMainInfo() {
        return null;
    }
}
