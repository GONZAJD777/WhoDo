package com.example.whodo.app.domain.user;

import com.example.whodo.app.domain.Location;
import com.example.whodo.app.domain.Phone;

import java.util.ArrayList;
import java.util.List;

public class UserFactory {

    /** Crear usuario solo con authId (defensivo) */
    public static User withAuthId(String authId) {
        return User.builder()
                .authId(authId)
                .location(new Location())
                .phone(new Phone())
                .userScore(new User.UserScore())
                .build();
    }

    /** Crear usuario en primer login */
    public static User newLoginUser(String authId,
                                    String name,
                                    String email,
                                    String password,
                                    Integer state,
                                    Integer type,
                                    List<String> specialization,
                                    List<String> languages,
                                    String createDate) {
        return User.builder()
                .authId(authId)
                .name(name)
                .email(email)
                .password(password)
                .state(state)
                .type(type)
                .specialization(specialization != null ? specialization : new ArrayList<>())
                .languages(languages != null ? languages : new ArrayList<>())
                .createDate(createDate)
                .location(new Location(0.0, 0.0, null))
                .phone(new Phone())
                .userScore(new User.UserScore())
                .build();
    }

    /** Copiar usuario existente (shallow copy) */
    public static User copyUser(User user) {
        if (user == null) return null;
        return User.builder()
                .id(user.getId())
                .authId(user.getAuthId())
                .address(user.getAddress())
                .birthday(user.getBirthday())
                .createDate(user.getCreateDate())
                .deleteDate(user.getDeleteDate())
                .description(user.getDescription())
                .email(user.getEmail())
                .languages(user.getLanguages())
                .location(user.getLocation())
                .name(user.getName())
                .password(user.getPassword())
                .phone(user.getPhone())
                .profilePicture(user.getProfilePicture())
                .specialization(user.getSpecialization())
                .state(user.getState())
                .type(user.getType())
                .userScore(user.getUserScore())
                .build();
    }

    /** Crear usuario vacío (placeholder) */
    public static User emptyUser() {
        return User.builder()
                .location(new Location())
                .phone(new Phone())
                .userScore(new User.UserScore())
                .build();
    }
}
