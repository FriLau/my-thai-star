package com.devonfw.application.service.rest.mapper;

import com.devonfw.application.domain.model.BookingEntity;
import com.devonfw.application.service.rest.model.BookingDto;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel ="cdi")
public interface BookingMapper {

    BookingDto mapTo(BookingEntity bookingEntity);

    BookingEntity mapTo(BookingDto bookingDto);

    List<BookingDto> mapList(List<BookingEntity> entityList);

    default Page<BookingDto> map(Page<BookingEntity> entityList) {

        List<BookingDto> list = mapList(entityList.getContent());
        return new PageImpl<>(list, entityList.getPageable(), entityList.getTotalElements());
    }
}
