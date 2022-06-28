package com.example.whodo;


public class User {

    private String NAME;
    private int AGE;
    private String EMAIL;
    private String ADDRESS;
    private double LATITUDE;
    private double LONGITUDE;
    private String PHONE;
    private String TYPE;
    private String PASSWORD;

    public User() {}

    public User(String name, int age, String email,String password, String address, double latitude, double longitude, String phone, String type) {
        this.NAME=name;
        this.AGE=age;
        this.EMAIL=email;
        this.PASSWORD=password;
        this.ADDRESS=address;
        this.LATITUDE=latitude;
        this.LONGITUDE=longitude;
        this.PHONE=phone;
        this.TYPE=type;

    }

    public String getName() {
        return NAME;
    }
    public void setName(String name){
        this.NAME=name;
    }

    public int getAge() {
        return AGE;
    }
    public void setAge(int age){
        this.AGE=age;
    }

    public String getEmail() {
        return EMAIL;
    }
    public void setEmail(String email){this.EMAIL=email;}

    public String getPassword() {
        return PASSWORD;
    }
    public void setPassword(String password){
        this.PASSWORD=password;
    }

    public String getAddress() {
        return ADDRESS;
    }
    public void setAddress(String address){
        this.ADDRESS=address;
    }

    public double getLatitude() {
        return LATITUDE;
    }
    public void setLatitude(double latitude){
        this.LATITUDE=latitude;
    }

    public double getLongitude() {
        return LONGITUDE;
    }
    public void setLongitude(double longitude){
        this.LONGITUDE=longitude;
    }


    public String getPhone() {
        return PHONE;
    }
    public void setPhone(String phone){
        this.PHONE=phone;
    }

    public String getType() {
        return TYPE;}
    public void setType(String type){
        this.TYPE=type;
    }




    //The best way of storing double values in SharedPreferences without losing precision is:

    //Transform to bit representation to store it as long:

    //        prefsEditor.putLong("Latitude", Double.doubleToLongBits(location.getLatitude()));
    //To retrieve, transfrom from bit representation to double:

    //double latitude = Double.longBitsToDouble(prefs.getLong("Latitude", 0);
    //However, I think that if you want to store a big amount of points is better using a SQLite database than saving each coordinate in a Key-Value pair in SharedPreferences or serialize the array in XML and write it to a file or to SharedPreferences. The database offers you the advantage of loading in memory only the points within the area you are displaying in the map, so you can save memory.



}
