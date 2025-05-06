package com.example.whodo.app.domain.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class User {

    private String id;
    private String authId; // Identificador de autenticación
    private String address;
    private Date birthday;
    private Date createDate;
    private Date deleteDate;
    private String description;
    private String email;
    private Boolean isValidated;
    private List<String> languages;
    private Location location; // Geohash dentro de Location
    private String name;
    private String password;
    private Phone phone;
    private String profilePicture;
    private List<String> specialization;
    private Integer state;
    private Integer type;
    private UserScore userScore;

    public User() {} // Constructor vacío requerido por MongoDB

    public User(String id, String authId, String address, Date birthday, Date createDate, Date deleteDate,
                String description, String email, boolean isValidated, List<String> languages,
                Location location, String name, String password, Phone phone, String profilePicture,
                List<String> specialization, int state, int type, UserScore userScore) {
        this.id = id;
        this.authId = authId;
        this.address = address;
        this.birthday = birthday;
        this.createDate = createDate;
        this.deleteDate = deleteDate;
        this.description = description;
        this.email = email;
        this.isValidated = isValidated;
        this.languages = languages;
        this.location = location;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.profilePicture = profilePicture;
        this.specialization = specialization;
        this.state = state;
        this.type = type;
        this.userScore = userScore;
    }

    public User(String id) {
        this.id = id;
    }

    public User(User user) {
    }

    public User(String authId, String name, String email, String password) {
        this.authId=authId;
        this.name=name;
        this.email=email;
        this.password=password;
    }

    // Getters y Setters completos
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAuthId() { return authId; }
    public void setAuthId(String authId) { this.authId = authId; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Date getBirthday() { return birthday; }
    public void setBirthday(Date birthday) { this.birthday = birthday; }

    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    public Date getDeleteDate() { return deleteDate; }
    public void setDeleteDate(Date deleteDate) { this.deleteDate = deleteDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Boolean getIsValidated() { return isValidated; }
    public void setIsValidated(Boolean validated) { isValidated = validated; }

    public List<String> getLanguages() { return languages; }
    public void setLanguages(List<String> languages) { this.languages = languages; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Phone getPhone() { return phone; }
    public void setPhone(Phone phone) { this.phone = phone; }

    public String getProfilePicture() { return profilePicture; }
    public void setProfilePicture(String profilePicture) { this.profilePicture = profilePicture; }

    public List<String> getSpecialization() { return specialization; }
    public void setSpecialization(List<String> specialization) { this.specialization = specialization; }

    public Integer getState() { return state; }
    public void setState(Integer state) { this.state = state; }

    public Integer getType() { return type; }
    public void setType(Integer type) { this.type = type; }

    public UserScore getUserScore() { return userScore; }
    public void setUserScore(UserScore userScore) { this.userScore = userScore; }

    // Subclases para datos anidados
    public static class Location {
        private double latitude;
        private double longitude;
        private String geohash; // Ahora correctamente agrupado con la ubicación

        public Location() {}

        public Location(double latitude, double longitude, String geohash) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.geohash = geohash;
        }

        public double getLatitude() { return latitude; }
        public void setLatitude(double latitude) { this.latitude = latitude; }

        public double getLongitude() { return longitude; }
        public void setLongitude(double longitude) { this.longitude = longitude; }

        public String getGeohash() { return geohash; }
        public void setGeohash(String geohash) { this.geohash = geohash; }
    }

    public static class Phone {
        private String number;
        private String ccn;

        public Phone() {}

        public Phone(String number, String ccn) {
            this.number = number;
            this.ccn = ccn;
        }

        public String getNumber() { return number; }
        public void setNumber(String number) { this.number = number; }

        public String getCcn() { return ccn; }
        public void setCcn(String ccn) { this.ccn = ccn; }
    }

    public static class UserScore {
        private double appearanceScore;
        private String avgCompletionTime;
        private String avgTariff;
        private double cleanlinessScore;
        private String overallScore;
        private double qualityScore;
        private double speedScore;

        public UserScore() {}

        public UserScore(double appearanceScore, String avgCompletionTime, String avgTariff,
                         double cleanlinessScore, String overallScore, double qualityScore, double speedScore) {
            this.appearanceScore = appearanceScore;
            this.avgCompletionTime = avgCompletionTime;
            this.avgTariff = avgTariff;
            this.cleanlinessScore = cleanlinessScore;
            this.overallScore = overallScore;
            this.qualityScore = qualityScore;
            this.speedScore = speedScore;
        }

        public double getAppearanceScore() { return appearanceScore; }
        public void setAppearanceScore(double appearanceScore) { this.appearanceScore = appearanceScore; }

        public String getAvgCompletionTime() { return avgCompletionTime; }
        public void setAvgCompletionTime(String avgCompletionTime) { this.avgCompletionTime = avgCompletionTime; }

        public String getAvgTariff() { return avgTariff; }
        public void setAvgTariff(String avgTariff) { this.avgTariff = avgTariff; }

        public double getCleanlinessScore() { return cleanlinessScore; }
        public void setCleanlinessScore(double cleanlinessScore) { this.cleanlinessScore = cleanlinessScore; }

        public String getOverallScore() { return overallScore; }
        public void setOverallScore(String overallScore) { this.overallScore = overallScore; }

        public double getQualityScore() { return qualityScore; }
        public void setQualityScore(double qualityScore) { this.qualityScore = qualityScore; }

        public double getSpeedScore() { return speedScore; }
        public void setSpeedScore(double speedScore) { this.speedScore = speedScore; }
    }




    public User deepCopy() {
        Gson gson = new GsonBuilder().create(); // Serializador JSON
        return gson.fromJson(gson.toJson(this), User.class); // Serializa y deserializa creando una copia nueva
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}