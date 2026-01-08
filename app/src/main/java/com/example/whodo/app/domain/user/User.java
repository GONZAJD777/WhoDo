package com.example.whodo.app.domain.user;

import com.example.whodo.app.domain.Location;
import com.example.whodo.app.domain.Phone;
import lombok.*;

import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private String id;
    private String authId; // Identificador de autenticación
    private String address;
    private String birthday;
    private String createDate;
    private String deleteDate;
    private String description;
    private String email;
    private List<String> languages;
    @Builder.Default
    private Location location = new Location();
    private String name;
    private String password;
    @Builder.Default
    private Phone phone = new Phone();
    private String profilePicture;
    private List<String> specialization;
    private Integer state;
    private Integer type;
    private String fcmToken;
    @Builder.Default
    private UserScore userScore = new UserScore();

    // --- Nested class ---
    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserScore {
        private Double appearanceScore;
        private String avgCompletionTime;
        private String avgTariff;
        private Double cleanlinessScore;
        private String overallScore;
        private Double qualityScore;
        private Double speedScore;
    }

    /** Deep copy usando Gson */
    public User deepCopy() {
        com.google.gson.Gson gson = new com.google.gson.GsonBuilder().create();
        return gson.fromJson(gson.toJson(this), User.class);
    }
}
