package com.example.whodo.app.domain.user;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;
import java.util.Optional;

public class UserMapper {

    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setId(userDTO.getId());
        user.setAuthId(userDTO.getAuthId());
        user.setName(userDTO.getName());
        user.setBirthday(userDTO.getBirthday());
        user.setCreateDate(userDTO.getCreateDate());
        user.setCreateDate(userDTO.getCreateDate());
        user.setDeleteDate(userDTO.getDeleteDate());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        user.setType(userDTO.getType());
        user.setPassword(userDTO.getPassword());
        user.setState(userDTO.getState());
        user.setProfilePicture(userDTO.getProfilePicture());
        user.setLanguages(userDTO.getLanguages());
        user.setDescription(userDTO.getDescription());
        user.setSpecialization(userDTO.getSpecialization());

        // Manejo seguro de objetos anidados
        Optional.ofNullable(userDTO.getLocation()).ifPresent(location -> {
            user.getLocation().setLatitude(location.getLatitude());
            user.getLocation().setLongitude(location.getLongitude());
        });

        Optional.ofNullable(userDTO.getPhone()).ifPresent(phone -> {
            user.getPhone().setCcn(phone.getCcn());
            user.getPhone().setNumber(phone.getNumber());
        });

        Optional.ofNullable(userDTO.getUserScore()).ifPresent(score -> {
            user.getUserScore().setAppearanceScore(score.getAppearanceScore());
            user.getUserScore().setCleanlinessScore(score.getCleanlinessScore());
            user.getUserScore().setQualityScore(score.getQualityScore());
            user.getUserScore().setSpeedScore(score.getSpeedScore());
            user.getUserScore().setOverallScore(score.getOverallScore());
            user.getUserScore().setAvgCompletionTime(score.getAvgCompletionTime());
            user.getUserScore().setAvgTariff(score.getAvgTariff());
        });

        return user;
    }

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setAuthId(user.getAuthId());
        userDTO.setName(user.getName());
        userDTO.setBirthday(user.getBirthday());
        userDTO.setEmail(user.getEmail());
        userDTO.setAddress(user.getAddress());
        userDTO.setType(user.getType());
        userDTO.setPassword(user.getPassword());
        userDTO.setCreateDate(user.getCreateDate());
        userDTO.setDeleteDate(user.getDeleteDate());
        userDTO.setState(user.getState());
        userDTO.setProfilePicture(user.getProfilePicture());
        userDTO.setLanguages(user.getLanguages());
        userDTO.setDescription(user.getDescription());
        userDTO.setSpecialization(user.getSpecialization());

        // Manejo seguro de objetos anidados
        Optional.ofNullable(user.getLocation()).ifPresent(location -> {
            userDTO.getLocation().setLatitude(location.getLatitude());
            userDTO.getLocation().setLongitude(location.getLongitude());
        });

        Optional.ofNullable(user.getPhone()).ifPresent(phone -> {
            userDTO.getPhone().setCcn(phone.getCcn());
            userDTO.getPhone().setNumber(phone.getNumber());
        });

        Optional.ofNullable(user.getUserScore()).ifPresent(score -> {
            userDTO.getUserScore().setAppearanceScore(score.getAppearanceScore());
            userDTO.getUserScore().setCleanlinessScore(score.getCleanlinessScore());
            userDTO.getUserScore().setQualityScore(score.getQualityScore());
            userDTO.getUserScore().setSpeedScore(score.getSpeedScore());
            userDTO.getUserScore().setOverallScore(score.getOverallScore());
            userDTO.getUserScore().setAvgCompletionTime(score.getAvgCompletionTime());
            userDTO.getUserScore().setAvgTariff(score.getAvgTariff());
        });

        return userDTO;
    }

    public static LiveData<User> toEntityLiveData(LiveData<UserDTO> userDTOLiveData) {
        MutableLiveData<User> userLiveData = new MutableLiveData<>();
        userDTOLiveData.observeForever(userDTO -> {
            userLiveData.setValue(toEntity(userDTO));
        });
        return userLiveData;
    }

    public static LiveData<UserDTO> toDTOLiveData(LiveData<User> userLiveData) {
        MutableLiveData<UserDTO> userDTOLiveData = new MutableLiveData<>();
        userLiveData.observeForever(user -> {
            userDTOLiveData.setValue(toDTO(user));
        });
        return userDTOLiveData;
    }
}