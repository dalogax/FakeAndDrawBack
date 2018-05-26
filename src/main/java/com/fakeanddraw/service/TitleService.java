package com.fakeanddraw.service;

import java.util.List;
import java.util.stream.Collectors;

import com.fakeanddraw.dto.MasterTitleDto;
import com.fakeanddraw.mapper.TitleMapper;
import com.fakeanddraw.model.repository.TitleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TitleService{

    @Autowired
    private TitleRepository titleRepository;

    public List<MasterTitleDto> getMasterTitles(int numTitles){
        return this.titleRepository.getMasterTitles(numTitles).stream()
                                  .map(masterTitle -> TitleMapper.INSTANCE.masterTitleToMasterTitleDto(masterTitle))
                                  .collect(Collectors.toList());

    }
}