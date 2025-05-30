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
    private String birthday;
    private String createDate;
    private String deleteDate;
    private String description;
    private String email;
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

    // Constructor vacío requerido por MongoDB
    public User() {
        this.location = new Location();   //Se inicializa con un objeto vacio para evitar errores de nullpointer exception en mapeo
        this.phone = new Phone();         //Se inicializa con un objeto vacio para evitar errores de nullpointer exception en mapeo
        this.userScore = new UserScore(); //Se inicializa con un objeto vacio para evitar errores de nullpointer exception en mapeo
    }



    public User(String authId) {
        this.authId = authId;
        this.location = new Location();   //Se inicializa con un objeto vacio para evitar errores de nullpointer exception en mapeo
        this.phone = new Phone();         //Se inicializa con un objeto vacio para evitar errores de nullpointer exception en mapeo
        this.userScore = new UserScore(); //Se inicializa con un objeto vacio para evitar errores de nullpointer exception en mapeo
    }

    // User creation constructor, para el primer login
    public User(String authId, String name, String email, String password,Integer state, Integer type, List<String> specialization, List<String> languages, String createDate) {
        this.authId=authId;
        this.name=name;
        this.email=email;
        this.password=password;
        this.state=state;
        this.type=type;
        this.specialization=specialization;
        this.languages=languages;
        this.createDate=createDate;
        this.location = new Location(0.0,0.0,null);   //Se inicializa con un objeto vacio para evitar errores de nullpointer exception en mapeo
        this.phone = new Phone();         //Se inicializa con un objeto vacio para evitar errores de nullpointer exception en mapeo
        this.userScore = new UserScore(); //Se inicializa con un objeto vacio para evitar errores de nullpointer exception en mapeo
    }

    //Constructor para mapeo
    public User(User user) {
        this.id = user.id;
        this.authId = user.authId;
        this.address = user.address;
        this.birthday = user.birthday;
        this.createDate = user.createDate;
        this.deleteDate = user.deleteDate;
        this.description = user.description;
        this.email = user.email;
        this.languages = user.languages;
        this.location = user.location;
        this.name = user.name;
        this.password = user.password;
        this.phone = user.phone;
        this.profilePicture = user.profilePicture;
        this.specialization = user.specialization;
        this.state = user.state;
        this.type = user.type;
        this.userScore = user.userScore;
    }

    // Getters y Setters completos
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAuthId() { return authId; }
    public void setAuthId(String authId) { this.authId = authId; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getBirthday() { return birthday; }
    public void setBirthday(String birthday) { this.birthday = birthday; }

    public String getCreateDate() { return createDate; }
    public void setCreateDate(String createDate) { this.createDate = createDate; }

    public String getDeleteDate() { return deleteDate; }
    public void setDeleteDate(String deleteDate) { this.deleteDate = deleteDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

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
        private Double latitude;
        private Double longitude;
        private String geohash; // Ahora correctamente agrupado con la ubicación

        public Location() {}

        public Location(double latitude, double longitude, String geohash) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.geohash = geohash;
        }

        public Double getLatitude() { return latitude; }
        public void setLatitude(Double latitude) { this.latitude = latitude; }

        public Double getLongitude() { return longitude; }
        public void setLongitude(Double longitude) { this.longitude = longitude; }

        public String getGeohash() { return geohash; }
        public void setGeohash(String geohash) { this.geohash = geohash; }

        @Override
        public String toString() {
            return "Location{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    ", geohash='" + geohash + '\'' +
                    '}';
        }
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

        @Override
        public String toString() {
            return "Phone{" +
                    "number='" + number + '\'' +
                    ", ccn='" + ccn + '\'' +
                    '}';
        }
    }

    public static class UserScore {
        private Double appearanceScore;
        private String avgCompletionTime;
        private String avgTariff;
        private Double cleanlinessScore;
        private String overallScore;
        private Double qualityScore;
        private Double speedScore;

        public UserScore() {}

        public UserScore(Double appearanceScore, String avgCompletionTime, String avgTariff,
                         Double cleanlinessScore, String overallScore, Double qualityScore, Double speedScore) {
            this.appearanceScore = appearanceScore;
            this.avgCompletionTime = avgCompletionTime;
            this.avgTariff = avgTariff;
            this.cleanlinessScore = cleanlinessScore;
            this.overallScore = overallScore;
            this.qualityScore = qualityScore;
            this.speedScore = speedScore;
        }

        public Double getAppearanceScore() { return appearanceScore; }
        public void setAppearanceScore(Double appearanceScore) { this.appearanceScore = appearanceScore; }

        public String getAvgCompletionTime() { return avgCompletionTime; }
        public void setAvgCompletionTime(String avgCompletionTime) { this.avgCompletionTime = avgCompletionTime; }

        public String getAvgTariff() { return avgTariff; }
        public void setAvgTariff(String avgTariff) { this.avgTariff = avgTariff; }

        public Double getCleanlinessScore() { return cleanlinessScore; }
        public void setCleanlinessScore(Double cleanlinessScore) { this.cleanlinessScore = cleanlinessScore; }

        public String getOverallScore() { return overallScore; }
        public void setOverallScore(String overallScore) { this.overallScore = overallScore; }

        public Double getQualityScore() { return qualityScore; }
        public void setQualityScore(Double qualityScore) { this.qualityScore = qualityScore; }

        public Double getSpeedScore() { return speedScore; }
        public void setSpeedScore(Double speedScore) { this.speedScore = speedScore; }

        @Override
        public String toString() {
            return "UserScore{" +
                    "appearanceScore=" + appearanceScore +
                    ", avgCompletionTime='" + avgCompletionTime + '\'' +
                    ", avgTariff='" + avgTariff + '\'' +
                    ", cleanlinessScore=" + cleanlinessScore +
                    ", overallScore='" + overallScore + '\'' +
                    ", qualityScore=" + qualityScore +
                    ", speedScore=" + speedScore +
                    '}';
        }
    }

    public User deepCopy() {
        Gson gson = new GsonBuilder().create(); // Serializador JSON
        return gson.fromJson(gson.toJson(this), User.class); // Serializa y deserializa creando una copia nueva
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id='" + id + '\'' +
                ", authId='" + authId + '\'' +
                ", address='" + address + '\'' +
                ", birthday='" + birthday + '\'' +
                ", createDate='" + createDate + '\'' +
                ", deleteDate='" + deleteDate + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", languages=" + languages +
                ", location=" + location.toString() +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phone=" + phone.toString() +
                ", profilePicture='" + profilePicture + '\'' +
                ", specialization=" + specialization +
                ", state=" + state +
                ", type=" + type +
                ", userScore=" + userScore.toString() +
                '}';
    }
}