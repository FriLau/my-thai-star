package com.devonfw.application.service.rest.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.devonfw.application.domain.model.TableEntity;
import com.devonfw.application.service.rest.model.TableDto;

@Mapper(componentModel ="cdi")
public interface TableMapper {

    TableDto mapTo(TableEntity tableEntity);

    TableEntity mapTo(TableDto tableDto);

    List<TableDto> mapList(List<TableEntity> entityList);

    default Page<TableDto> map(Page<TableEntity> entityList) {

        List<TableDto> list = mapList(entityList.getContent());
        return new PageImpl<>(list, entityList.getPageable(), entityList.getTotalElements());
    }
}