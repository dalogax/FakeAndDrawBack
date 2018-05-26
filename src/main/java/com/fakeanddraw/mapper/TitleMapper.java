package com.fakeanddraw.mapper;

import com.fakeanddraw.dto.MasterTitleDto;
import com.fakeanddraw.model.entity.MasterTitle;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TitleMapper{

    TitleMapper INSTANCE = Mappers.getMapper(TitleMapper.class);

    MasterTitleDto masterTitleToMasterTitleDto(MasterTitle masterTitle);
    
}