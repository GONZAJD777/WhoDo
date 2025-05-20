package com.example.whodo.app.domain.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserApiRestRequestDTO {

    private String id;
    private String authId; // Identificador de autenticación
    private String address;
    private UserDate birthday;
    private UserDate createDate;
    private UserDate deleteDate;
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

    public UserApiRestRequestDTO() {
        this.location = new Location();
        this.phone = new Phone();
        this.userScore = new UserScore();
    } // Constructor vacío requerido por MongoDB


    // Getters y Setters completos
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAuthId() { return authId; }
    public void setAuthId(String authId) { this.authId = authId; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public UserDate getBirthday() { return birthday; }
    public void setBirthday(UserDate birthday) { this.birthday = birthday; }

    public UserDate getCreateDate() { return createDate; }
    public void setCreateDate(UserDate createDate) { this.createDate = createDate; }

    public UserDate getDeleteDate() { return deleteDate; }
    public void setDeleteDate(UserDate deleteDate) { this.deleteDate = deleteDate; }

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

        public Location(Double latitude, Double longitude, String geohash) {
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
        private Double appearanceScore;
        private Double speedScore;
        private Double cleanlinessScore;
        private Double qualityScore;
        private String overallScore;
        private String avgTariff;
        private String avgCompletionTime;


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
    }

    public static class UserDate {
        private Date dateUtc; // Fecha almacenada en UTC
        private String timeZoneOffset; // Desplazamiento horario en formato ±hh:mm

        public UserDate() {}

        // 🔹 Constructor que recibe la fecha en formato ISO 8601 y la convierte
        public UserDate(String isoDate) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
                this.dateUtc = formatter.parse(isoDate); // Parsear la fecha
                this.timeZoneOffset = isoDate.substring(isoDate.length() - 6); // Extraer el offset
            } catch (ParseException e) {
                throw new RuntimeException("Error al convertir la fecha: " + e.getMessage());
            }
        }

        public UserDate(Date dateUtc, String timeZoneOffset) {
            this.dateUtc = dateUtc;
            this.timeZoneOffset = timeZoneOffset;
        }

        public Date getDateUtc() { return dateUtc; }
        public void setDateUtc(Date dateUtc) { this.dateUtc = dateUtc; }

        public String getTimeZoneOffset() { return timeZoneOffset; }
        public void setTimeZoneOffset(String timeZoneOffset) { this.timeZoneOffset = timeZoneOffset; }
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", authId='" + authId + '\'' +
                ", address='" + address + '\'' +
                ", birthday=" + birthday +
                ", createDate=" + createDate +
                ", deleteDate=" + deleteDate +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", languages=" + languages +
                ", location=" + location +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", phone=" + phone +
                ", profilePicture='" + profilePicture + '\'' +
                ", specialization=" + specialization +
                ", state=" + state +
                ", type=" + type +
                ", userScore=" + userScore +
                '}';
    }
}