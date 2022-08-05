package com.devonfw.application.service.rest.mapper;

import com.devonfw.application.domain.model.BookingEntity;
import com.devonfw.application.domain.model.TableEntity;
import com.devonfw.application.service.rest.model.BookingDto;
import com.devonfw.application.service.rest.model.TableDto;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

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