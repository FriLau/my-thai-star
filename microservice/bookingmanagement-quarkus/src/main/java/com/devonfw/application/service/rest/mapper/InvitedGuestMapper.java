package com.devonfw.application.service.rest.mapper;

import java.util.List;

import com.devonfw.application.domain.model.BookingEntity;
import com.devonfw.application.service.rest.model.BookingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.devonfw.application.domain.model.InvitedGuestEntity;
import com.devonfw.application.service.rest.model.InvitedGuestDto;

@Mapper(componentModel ="cdi")
public interface InvitedGuestMapper {

    InvitedGuestDto mapTo(InvitedGuestEntity invitedGuestEntity);

    InvitedGuestEntity mapTo(InvitedGuestDto invitedGuestDto);

    List<InvitedGuestDto> mapList(List<InvitedGuestEntity> invitedGuestList);

    default Page<InvitedGuestDto> map(Page<InvitedGuestEntity> invitedGuestList) {

        List<InvitedGuestDto> list = mapList(invitedGuestList.getContent());
        return new PageImpl<>(list, invitedGuestList.getPageable(), invitedGuestList.getTotalElements());
    }
}
