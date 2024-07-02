package com.example.whodo.domain.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

public class UserMapper {

    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setUid(userDTO.getUid());
        user.setName(userDTO.getName());
        user.setBirthday(userDTO.getBirthday());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        user.setLatitude(userDTO.getLatitude());
        user.setLongitude(userDTO.getLongitude());
        //user.setGeohash(userDTO.getGeohash());
        user.setPhone(userDTO.getPhone());
        user.setPhone_ccn(userDTO.getPhone_ccn());
        user.setType(userDTO.getType());
        user.setPassword(userDTO.getPassword());
        user.setCreateDate(userDTO.getCreateDate());
        user.setDeleteDate(userDTO.getDeleteDate());
        user.setState(userDTO.getState());
        user.setIsValidated(userDTO.getIsValidated());
        user.setProfilePicture(userDTO.getProfilePicture());
        user.setLanguages(userDTO.getLanguages());
        user.setDescription(userDTO.getDescription());
        user.setSpecialization(userDTO.getSpecialization());
        // Asumiendo que tienes un método para convertir UserSpecRating a la entidad correspondiente
        user.setUserScore(UserSpecRatingMapper.toEntity(userDTO.getUserScore()));

        return user;
    }

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUid(user.getUid());
        userDTO.setName(user.getName());
        userDTO.setBirthday(user.getBirthday());
        userDTO.setEmail(user.getEmail());
        userDTO.setAddress(user.getAddress());
        userDTO.setLatitude(user.getLatitude());
        userDTO.setLongitude(user.getLongitude());
        //userDTO.setGeohash(user.getGeohash());
        userDTO.setPhone(user.getPhone());
        userDTO.setPhone_ccn(user.getPhone_ccn());
        userDTO.setType(user.getType());
        userDTO.setPassword(user.getPassword());
        userDTO.setCreateDate(user.getCreateDate());
        userDTO.setDeleteDate(user.getDeleteDate());
        userDTO.setState(user.getState());
        userDTO.setIsValidated(user.getIsValidated());
        userDTO.setProfilePicture(user.getProfilePicture());
        userDTO.setLanguages(user.getLanguages());
        userDTO.setDescription(user.getDescription());
        userDTO.setSpecialization(user.getSpecialization());

        // Asumiendo que tienes un método para convertir la entidad de puntuación a UserSpecRating DTO
        // y que User tiene un método getUserScore que devuelve una entidad que puede ser mapeada.
        userDTO.setUserScore(UserSpecRatingMapper.toDTO(user.getUserScore()));

        return userDTO;
    }

    public static LiveData<User> toEntityLiveData(LiveData<UserDTO> userDTOLiveData) {
        MediatorLiveData<User> userLiveData = new MediatorLiveData<>();
        userLiveData.addSource(userDTOLiveData, userDTO -> {
            if (userDTO != null) {
                User user = toEntity(userDTO);
                userLiveData.setValue(user);
            }
        });
        return userLiveData;
    }

    public static LiveData<UserDTO> toDTOLiveData(LiveData<User> userLiveData) {
        MediatorLiveData<UserDTO> userDTOLiveData = new MediatorLiveData<>();
        userDTOLiveData.addSource(userLiveData, userld -> {
            if (userld != null) {
                UserDTO user = toDTO(userld);
                userDTOLiveData.setValue(user);
            }
        });
        return userDTOLiveData;
    }

}
