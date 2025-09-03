package edu.ubb.biblioSpring.backend.api.assembler;

import edu.ubb.biblioSpring.backend.dto.in.UserInDto;
import edu.ubb.biblioSpring.backend.dto.out.UserOutDto;
import edu.ubb.biblioSpring.backend.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler {
    public UserOutDto modelToDto(User model){
        UserOutDto newDto=new UserOutDto();
        newDto.setId(model.getId());
        newDto.setUuid(model.getUuid());
        newDto.setUsername(model.getUsername());
        return newDto;
    }
    public User dtoToModel(UserInDto dto){
        User newUser=new User();
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(dto.getPassword());

        return newUser;
    }

}
