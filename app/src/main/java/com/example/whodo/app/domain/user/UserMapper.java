package com.example.whodo.app.domain.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class UserMapper {

    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setBirthday(userDTO.getBirthday());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
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

        user.getLocation().setLatitude(userDTO.getLocation().getLatitude());
        user.getLocation().setLongitude(userDTO.getLocation().getLongitude());

        user.getPhone().setCcn(userDTO.getPhone().getCcn());
        user.getPhone().setNumber(userDTO.getPhone().getNumber());

        user.getUserScore().setAppearanceScore(userDTO.getUserScore().getAppearanceScore());
        user.getUserScore().setCleanlinessScore(userDTO.getUserScore().getCleanlinessScore());
        user.getUserScore().setQualityScore(userDTO.getUserScore().getQualityScore());
        user.getUserScore().setSpeedScore(userDTO.getUserScore().getSpeedScore());
        user.getUserScore().setOverallScore(userDTO.getUserScore().getOverallScore());
        user.getUserScore().setAvgCompletionTime(userDTO.getUserScore().getAvgCompletionTime());
        user.getUserScore().setAvgTariff(userDTO.getUserScore().getAvgTariff());


        return user;
    }

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setBirthday(user.getBirthday());
        userDTO.setEmail(user.getEmail());
        userDTO.setAddress(user.getAddress());
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

        userDTO.getLocation().setLatitude(user.getLocation().getLatitude());
        userDTO.getLocation().setLongitude(user.getLocation().getLongitude());

        userDTO.getPhone().setCcn(user.getPhone().getCcn());
        userDTO.getPhone().setNumber(user.getPhone().getNumber());

        userDTO.getUserScore().setAppearanceScore(user.getUserScore().getAppearanceScore());
        userDTO.getUserScore().setCleanlinessScore(user.getUserScore().getCleanlinessScore());
        userDTO.getUserScore().setQualityScore(user.getUserScore().getQualityScore());
        userDTO.getUserScore().setSpeedScore(user.getUserScore().getSpeedScore());
        userDTO.getUserScore().setOverallScore(user.getUserScore().getOverallScore());
        userDTO.getUserScore().setAvgCompletionTime(user.getUserScore().getAvgCompletionTime());
        userDTO.getUserScore().setAvgTariff(user.getUserScore().getAvgTariff());

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
