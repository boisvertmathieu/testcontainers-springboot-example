package com.example.demo.mapper;

import com.example.demo.model.User;
import com.example.demo.repository.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {


    User convertirUserEntityVersUser(UserEntity userEntity);

    List<User> convertirListeUserEntityVersListeUser(List<UserEntity> userEntities);


    UserEntity convertirUserVersUserEntity(User user);

    List<UserEntity> convertirListeUserVersListeUserEntity(List<User> users);

}
