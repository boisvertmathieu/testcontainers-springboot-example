package com.example.demo.mapper;

import com.example.demo.model.User;
import com.example.demo.repository.entity.UserEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-11T15:19:47-0500",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.jar, environment: Java 19.0.2 (Homebrew)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User convertirUserEntityVersUser(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        User user = new User();

        if ( userEntity.getId() != null ) {
            user.setId( userEntity.getId().toString() );
        }
        user.setFirstName( userEntity.getFirstName() );
        user.setLastName( userEntity.getLastName() );
        user.setEmail( userEntity.getEmail() );
        user.setCreatedAt( userEntity.getCreatedAt() );
        user.setCreatedBy( userEntity.getCreatedBy() );
        user.setUpdatedAt( userEntity.getUpdatedAt() );
        user.setUpdatedBy( userEntity.getUpdatedBy() );

        return user;
    }

    @Override
    public List<User> convertirListeUserEntityVersListeUser(List<UserEntity> userEntities) {
        if ( userEntities == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( userEntities.size() );
        for ( UserEntity userEntity : userEntities ) {
            list.add( convertirUserEntityVersUser( userEntity ) );
        }

        return list;
    }

    @Override
    public UserEntity convertirUserVersUserEntity(User user) {
        if ( user == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        if ( user.getId() != null ) {
            userEntity.setId( UUID.fromString( user.getId() ) );
        }
        userEntity.setFirstName( user.getFirstName() );
        userEntity.setLastName( user.getLastName() );
        userEntity.setEmail( user.getEmail() );
        userEntity.setCreatedAt( user.getCreatedAt() );
        userEntity.setCreatedBy( user.getCreatedBy() );
        userEntity.setUpdatedAt( user.getUpdatedAt() );
        userEntity.setUpdatedBy( user.getUpdatedBy() );

        return userEntity;
    }

    @Override
    public List<UserEntity> convertirListeUserVersListeUserEntity(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserEntity> list = new ArrayList<UserEntity>( users.size() );
        for ( User user : users ) {
            list.add( convertirUserVersUserEntity( user ) );
        }

        return list;
    }
}
