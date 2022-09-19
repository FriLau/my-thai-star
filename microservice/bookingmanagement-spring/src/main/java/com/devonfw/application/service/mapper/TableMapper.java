package com.devonfw.application.service.mapper;

import com.devonfw.application.domain.model.TableEntity;
import com.devonfw.application.service.model.TableDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

//@Mapper(componentModel ="cdi", builder = @Builder(disableBuilder = true))
//TODO
@Mapper(builder = @Builder(disableBuilder = true))
public interface TableMapper {

    TableDto mapTo(TableEntity tableEntity);

    TableEntity mapTo(TableDto tableDto);

    List<TableDto> mapList(List<TableEntity> entityList);

    default Page<TableDto> map(Page<TableEntity> entityList) {

        List<TableDto> list = mapList(entityList.getContent());
        return new PageImpl<>(list, entityList.getPageable(), entityList.getTotalElements());
    }
}