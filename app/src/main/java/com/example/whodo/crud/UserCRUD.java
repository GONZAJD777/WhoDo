package com.example.whodo.crud;

import com.example.whodo.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserCRUD {

    private String NAME;
    private int AGE;
    private String EMAIL;
    private String ADDRESS;
    private double LATITUDE;
    private double LONGITUDE;
    private String PHONE;
    private String TYPE;
    private String PASSWORD;
    private User User;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseReference;

    public UserCRUD ()
    {
        FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference();
        mDatabase.goOffline();
    }


    public void CreateUser()
    {
        mDatabase.goOnline();
        //User user = new User ("Paul", 23, "Paul@gmail.com","CHACABUCO 44",-31.4228, -62.1802,"3512043546","CUSTOMER");
        mDatabaseReference = mDatabase.getReference (). child ("USER").child(User.getEmail());
        mDatabaseReference.setValue (User);
        mDatabase.goOffline();
    }
    public User ReadUser ()
    {
        return User;
    }
    public void UpdateUser()
    {

    }
    public void DeleteUser()
    {

    }


}
