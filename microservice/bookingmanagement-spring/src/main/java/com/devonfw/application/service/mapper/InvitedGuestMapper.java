package com.devonfw.application.service.mapper;

import com.devonfw.application.domain.model.InvitedGuestEntity;
import com.devonfw.application.service.model.InvitedGuestDto;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

//@Mapper(componentModel ="cdi", builder = @Builder(disableBuilder = true))
//TODO
@Mapper
public interface InvitedGuestMapper {

    InvitedGuestDto mapTo(InvitedGuestEntity invitedGuestEntity);

    InvitedGuestEntity mapTo(InvitedGuestDto invitedGuestDto);

    List<InvitedGuestDto> mapList(List<InvitedGuestEntity> invitedGuestList);

    default Page<InvitedGuestDto> map(Page<InvitedGuestEntity> invitedGuestList) {

        List<InvitedGuestDto> list = mapList(invitedGuestList.getContent());
        return new PageImpl<>(list, invitedGuestList.getPageable(), invitedGuestList.getTotalElements());
    }
}
