package com.example.whodo.domain.user;

import com.google.firebase.encoders.annotations.Encodable;

import java.io.UnsupportedEncodingException;

public class UserDTO {

    private String Uid;
    private String Name;
    private long Birthday;
    private String Email;
    private String Address;
    private double Latitude;
    private double Longitude;
    //private String Geohash;
    private String Phone;
    private String Phone_ccn;
    private String Type;
    private String Password;
    private long CreateDate;
    private long DeleteDate;
    private int State;
    private int isValidated;
    private String ProfilePicture;
    private String Languages;
    private String Description;
    private String Specialization;
    private UserSpecRatingDTO UserScore;

    public UserDTO() {
    }


    public UserDTO(String pUid, String pName, long pBirthday, String pEmail, String pAddress,
                     double pLatitude, double pLongitude,
                     String pPhone,
                     String pPhone_ccn, String pType, String pPassword, long pCreateDate, long pDeleteDate,
                     int pState, int pIsValidated, String pProfilePicture, String pLanguages, String pDescription, String pSpecialization) {
        Uid = pUid;
        Name = pName;
        Birthday = pBirthday;
        Email = pEmail;
        Address = pAddress;
        Latitude = pLatitude;
        Longitude = pLongitude;
        Phone = pPhone;
        Phone_ccn = pPhone_ccn;
        Type = pType;
        Password = pPassword;
        CreateDate = pCreateDate;
        DeleteDate = pDeleteDate;
        State = pState;
        isValidated = pIsValidated;
        ProfilePicture = pProfilePicture;
        Languages = pLanguages;
        Description = pDescription;
        Specialization = pSpecialization;
        UserScore = new UserSpecRatingDTO(); // Asumiendo que quieres inicializar UserScore aquí también
    }

    @Encodable.Ignore
    public UserSpecRatingDTO getUserScore() {
        return UserScore;
    }
    @Encodable.Ignore
    public void setUserScore(UserSpecRatingDTO pUserSpecRating){ UserScore=pUserSpecRating; }

    // @PropertyName("Uid")
    public String getUid() {
        return Uid;
    }
    public void setUid(String pUid){
        Uid=pUid;
    }

    // @PropertyName("Name")
    public String getName() {
        return Name;
    }
    public void setName(String pName){
        Name=pName;
    }

    // @PropertyName("Birthday")
    public long getBirthday() {
        return Birthday;
    }
    public void setBirthday(long pBirthday){
        Birthday=pBirthday;
    }

    // @PropertyName("Email")
    public String getEmail() {
        return Email;
    }
    public void setEmail(String pEmail){Email=pEmail;}

    //@PropertyName("Password")
    public String getPassword() {
        return Password;
    }
    public void setPassword(String pPassword){
        Password=pPassword;
    }

    //@PropertyName("Address")
    public String getAddress() {
        return Address;
    }
    public void setAddress(String pAddress){
        Address=pAddress;
    }

    // @PropertyName("Latitude")
    public double getLatitude() {
        return Latitude;
    }
    public void setLatitude(double pLatitude){
        Latitude=pLatitude;
    }

    //@PropertyName("Longitude")
    public double getLongitude() {
        return Longitude;
    }
    public void setLongitude(double pLongitude){
        Longitude=pLongitude;
    }

    // @PropertyName("Phone")
    public String getPhone() {
        return Phone;
    }
    public void setPhone(String pPhone){
        Phone=pPhone;
    }

    public String getPhone_ccn() {return Phone_ccn;}
    public void setPhone_ccn(String pPhone_ccn){
        Phone_ccn=pPhone_ccn;
    }

    public String getType() {return Type;}
    public void setType(String pType){
        Type=pType;
    }

    public long getCreateDate() {return CreateDate;}
    public void setCreateDate(long pCreateDate){
        CreateDate=pCreateDate;
    }

    public long getDeleteDate() {return DeleteDate;}
    public void setDeleteDate(long pDeleteDate){
        DeleteDate=pDeleteDate;
    }

    public Integer getState() {return State;}
    public void setState(Integer pState){
        State=pState;
    }

    public Integer getIsValidated() {return isValidated;}
    public void setIsValidated(Integer pisValidated){
        isValidated=pisValidated;
    }

    public String getProfilePicture() {return ProfilePicture;}
    public void setProfilePicture(String pProfilePicture){ ProfilePicture=pProfilePicture; }

    public String getLanguages() {return Languages;}
    public void setLanguages(String pLanguages){
        Languages=pLanguages;
    }

    public String getDescription() {return Description;}
    public void setDescription(String pDescription){
        Description=pDescription;
    }

    public String getSpecialization() {return Specialization;}
    public void setSpecialization(String pSpecialization){
        Specialization=pSpecialization;
    }

}
