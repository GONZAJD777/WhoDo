package com.example.whodo.BusinessClasses;

import com.google.firebase.database.PropertyName;
import com.google.firebase.encoders.annotations.Encodable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class User  {

    private String Uid;             // Uid registered by Firebase Auth tool and ID identifier in Firebase DB
    private String Name;            // Name or Nick Used for User
    private long Birthday;           // Date Used to calculate the Age of User
    private String Email;           // Email asociated to Account and used to log in.
    private String Address;         // Phisical address registered for customer and providers
    private double Latitude;        // Latitude of User Address, no current Location used to pin in the map
    private double Longitude;       // Longitude of User Address, no current Location used to pin in the map
    private String Geohash;
    private String Phone;           // Phone Number asociated with the account
    private String Phone_ccn;           // CCN Phone Number asociated with the account
    private String Type;            // Customer=1 or Provider=2
    private String Password;        // Account Password registered in the Account
    private long CreateDate;         // Account Creation Date
    private long DeleteDate;         // Account Logic Deletion Date
    private int State;              // Flag that indicates the state of account (0=deleted, 1=active,2..3 reserved for further use)
    private int isValidated;        // Flag that indicate if account Email was validated or not (1=true,0=false)
    private String ProfilePicture;
    private String Languages;
    private String Description;
    private String Specialization; // just apllies and is shown to those user who change their type to PROVIDERS
    //***********************************************************************************************************************
    //This atributes have no need to be update, this will just be consumed to show customers a preview of prividers skills
    private UserSpecRating UserScore;



   public User(){

   }

   public User(String pUid,String pName,String pEmail,String pPassword) {

        Uid=pUid;
        Name=pName;
        Birthday=19000101;
        Email=pEmail;
        Address="";
        Latitude=0.0;
        Longitude=0.0;
        Geohash="";
        Phone="";
        Phone_ccn="-";
        Type="1";
        Password=pPassword;
        CreateDate=19700101;
        DeleteDate=20991231;
        State=1;
        isValidated=0;
        String defaultProfilePicture_CloudFile = "Android-Logo-2008-2014.png";
        ProfilePicture="https://firebasestorage.googleapis.com/v0/b/whodo-2f534.appspot.com/o/WHODO-IMAGES%2FPROFILE-PICTURE%2F"+ defaultProfilePicture_CloudFile +"?alt=media&token=a7f64bef-77ed-40b3-b62d-e44d986ac2da";
        Languages="";
        Description="";
        Specialization="";
        UserScore=new UserSpecRating();
    }

    @Encodable.Ignore
    public UserSpecRating getUserScore() {
        return UserScore;
    }
    @Encodable.Ignore
    public void setUserScore(UserSpecRating pUserSpecRating){ UserScore=pUserSpecRating; }

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

    // @PropertyName("GeoHash")
    public String getGeohash() { return Geohash; }
    public void setGeohash(String pGeoHash){
        Geohash=pGeoHash;
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

   /** public String getAppereanceScore() {
        return AppereanceScore;
    }
    public void setAppereanceScore(String pAppereanceScore){ AppereanceScore=pAppereanceScore; }

    public String getCleanlinessScore() {
        return CleanlinessScore;
    }
    public void setCleanlinessScore(String pCleanlinessScore){ CleanlinessScore=pCleanlinessScore; }

    public String getSpeedScore() {
        return SpeedScore;
    }
    public void setSpeedScore(String pSpeedScore){ SpeedScore=pSpeedScore; }

    public String getQualityScore() {
        return QualityScore;
    }
    public void setQualityScore(String pQualityScore){ QualityScore=pQualityScore; }

    public String getAvgTariff() {
        return AvgTariff;
    }
    public void setAvgTariff(String pAvgTariff){ AvgTariff=pAvgTariff; }

    public String getAvgCompletionTime() {
        return AvgCompletionTime;
    }
    public void setAvgCompletionTime(String pAvgCompletionTime){ AvgCompletionTime=pAvgCompletionTime; }

    public String getOverallScore() {
        return OverallScore;
    }
    public void setOverallScore(String pOverallScore){ OverallScore=pOverallScore; }*/

    //The best way of storing double values in SharedPreferences without losing precision is:

    //Transform to bit representation to store it as long:

    //        prefsEditor.putLong("Latitude", Double.doubleToLongBits(location.getLatitude()));
    //To retrieve, transfrom from bit representation to double:

    //double latitude = Double.longBitsToDouble(prefs.getLong("Latitude", 0);
    //However, I think that if you want to store a big amount of points is better using a SQLite database than saving each coordinate in a Key-Value pair in SharedPreferences or serialize the array in XML and write it to a file or to SharedPreferences. The database offers you the advantage of loading in memory only the points within the area you are displaying in the map, so you can save memory.



}
