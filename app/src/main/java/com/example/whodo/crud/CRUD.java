package com.example.whodo.crud;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.whodo.BusinessClasses.User;
import com.example.whodo.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

public class CRUD {


    public void CreateUser(User p_User)  {
        Log.i("Operaciones ReadUser",  "String recibido como parametro " + "USERS/"+p_User.getUid() );
        //(String p_name, int p_birthdate, String p_email, String p_address, double p_latitude, double p_longitude, String p_phone, String p_type,String p_password)
        //User User = new User (NAME, BIRTHDATE, EMAIL,ADDRESS,LATITUDE,LONGITUDE,PHONE,TYPE,PASSWORD);
                DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("USERS").child(p_User.getUid());
                mDatabaseReference.setValue(p_User);
    }


    public User ReadUser (String email) {

        final User[] User = {new User()};
        Log.i("Operaciones ReadUser",  "String recibido como parametro " + email );


        FirebaseDatabase mDatabase=FirebaseDatabase.getInstance();
        DatabaseReference DBRef= mDatabase.getReference("USERS");


        DBRef.orderByChild("email").equalTo(email.toUpperCase()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {

                    User UserSS = ds.getValue(User.class);
                    User[0] = UserSS;


                    Log.d("firebase",
                            UserSS.getEmail() + " , " +
                            UserSS.getAddress() + " , " +
                            UserSS.getName() + " , " +
                            UserSS.getBirthday());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        Log.i("Operaciones ReadUser",  "RETURN " + User[0].getName() );
        return User[0];
    }

    public void UpdateUser()
    {

    }
    public void DeleteUser()
    {

    }


}
