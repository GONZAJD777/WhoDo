package com.example.whodo.BusinessClasses;


import android.net.Uri;

public class User  {

    private String Uid;             // Uid registered by Firebase Auth tool and ID identifier in Firebase DB
    private String Name;            // Name or Nick Used for User
    private long Birthday;           // Date Used to calculate the Age of User
    private String Email;           // Email asociated to Account and used to log in.
    private String Address;         // Phisical address registered for customer and providers
    private double Latitude;        // Latitude of User Address, no current Location used to pin in the map
    private double Longitude;       // Longitude of User Address, no current Location used to pin in the map
    private String Phone;           // Phone Number asociated with the account

    private String Phone_ccn;           // CCN Phone Number asociated with the account
    private String Type;            // Customer or Provider
    private String Password;        // Account Password registered in the Account
    private long CreateDate;         // Account Creation Date
    private long DeleteDate;         // Account Logic Deletion Date
    private int State;              // Flag that indicates the state of account (0=deleted, 1=active,2..3 reserved for further use)
    private int isValidated;        // Flag that indicate if account Email was validated or not (1=true,0=false)
    private String ProfilePicture;

    private String Languages;

    private String Description;

    public User ()
   {}
   public User(String Uid,String Name,String Email,String Password) {

        this.Uid=Uid;
        this.Name=Name;
        this.Birthday=19000101;
        this.Email=Email;
        this.Address="";
        this.Latitude=0.0;
        this.Longitude=0.0;
        this.Phone="";
        this.Phone_ccn="-";
        this.Type="CUSTOMER";
        this.Password=Password;
        this.CreateDate=19700101;
        this.DeleteDate=20991231;
        this.State=1;
        this.isValidated=0;
        String defaultProfilePicture_CloudFile = "Android-Logo-2008-2014.png";
        this.ProfilePicture="https://firebasestorage.googleapis.com/v0/b/whodo-2f534.appspot.com/o/WHODO-IMAGES%2FPROFILE-PICTURE%2F"+ defaultProfilePicture_CloudFile +"?alt=media&token=a7f64bef-77ed-40b3-b62d-e44d986ac2da";
        this.Languages="";
        this.Description="";
    }


    // @PropertyName("Uid")
    public String getUid() {
        return Uid;
    }
    public void setUid(String Uid){
        this.Uid=Uid;
    }

   // @PropertyName("Name")
    public String getName() {
        return Name;
    }
    public void setName(String Name){
        this.Name=Name;
    }

   // @PropertyName("Birthday")
    public long getBirthday() {
        return Birthday;
    }
    public void setBirthday(long Birthday){
        this.Birthday=Birthday;
    }

   // @PropertyName("Email")
    public String getEmail() {
        return Email;
    }
    public void setEmail(String Email){this.Email=Email;}

    //@PropertyName("Password")
    public String getPassword() {
        return Password;
    }
    public void setPassword(String Password){
        this.Password=Password;
    }

    //@PropertyName("Address")
    public String getAddress() {
        return Address;
    }
    public void setAddress(String Address){
        this.Address=Address;
    }

   // @PropertyName("Latitude")
    public double getLatitude() {
        return Latitude;
    }
    public void setLatitude(double Latitude){
        this.Latitude=Latitude;
    }

    //@PropertyName("Longitude")
    public double getLongitude() {
        return Longitude;
    }
    public void setLongitude(double Longitude){
        this.Longitude=Longitude;
    }

   // @PropertyName("Phone")
    public String getPhone() {
        return Phone;
    }
    public void setPhone(String Phone){
        this.Phone=Phone;
    }

    public String getPhone_ccn() {return Phone_ccn;}
    public void setPhone_ccn(String Phone_ccn){
        this.Phone_ccn=Phone_ccn;
    }

    public String getType() {return Type;}
    public void setType(String Type){
        this.Type=Type;
    }

    public long getCreateDate() {return CreateDate;}
    public void setCreateDate(long CreateDate){
        this.CreateDate=CreateDate;
    }

    public long getDeleteDate() {return DeleteDate;}
    public void setDeleteDate(long DeleteDate){
        this.DeleteDate=DeleteDate;
    }

    public Integer getState() {return State;}
    public void setState(Integer State){
        this.State=State;
    }

    public Integer getIsValidated() {return isValidated;}
    public void setIsValidated(Integer isValidated){
        this.isValidated=isValidated;
    }

    public String getProfilePicture() {return ProfilePicture;}
    public void setProfilePicture(String profilePicture){
        this.ProfilePicture=profilePicture;
    }

    public String getLanguages() {return Languages;}
    public void setLanguages(String Languages){
        this.Languages=Languages;
    }
    public String getDescription() {return Description;}
    public void setDescription(String Description){
        this.Description=Description;
    }

    //The best way of storing double values in SharedPreferences without losing precision is:

    //Transform to bit representation to store it as long:

    //        prefsEditor.putLong("Latitude", Double.doubleToLongBits(location.getLatitude()));
    //To retrieve, transfrom from bit representation to double:

    //double latitude = Double.longBitsToDouble(prefs.getLong("Latitude", 0);
    //However, I think that if you want to store a big amount of points is better using a SQLite database than saving each coordinate in a Key-Value pair in SharedPreferences or serialize the array in XML and write it to a file or to SharedPreferences. The database offers you the advantage of loading in memory only the points within the area you are displaying in the map, so you can save memory.



}
