package com.example.whodo.crud;

import static android.content.ContentValues.TAG;

import static com.example.whodo.MainActivity.getLoggedUser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.whodo.BusinessClasses.User;
import com.example.whodo.LoginActivity;
import com.example.whodo.MainActivity;
import com.example.whodo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Semaphore;

public class CRUD {


    @SuppressLint("LongLogTag")
    public void CreateUser(User p_User)  {
        Log.i("Operaciones CRUD.CreateUser()",  "Se creara el usuario en la base con ID:" + "USERS/"+p_User.getUid() );
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("USERS").child(p_User.getUid());
        mDatabaseReference.setValue(p_User);
    }
    public static void ReadUser(String uid) {

        Log.i("Operaciones ReadUser",  "String recibido como parametro " + uid );
        FirebaseDatabase mDatabase=FirebaseDatabase.getInstance();
        DatabaseReference DBRef= mDatabase.getReference("USERS");
        DBRef.orderByChild("uid").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {

                            User UserSS = ds.getValue(User.class);
                            assert UserSS != null;
                            MainActivity.UpdateLoggedUser(UserSS);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
        });

    }
    public void UpdateUser(){}
    public void DeleteUser(){}

    public void uploadProfileImage(Uri p_Uri, String p_User)
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("WHODO-IMAGES/PROFILE-PICTURE/" + p_User);

        // Get the data from an ImageView as bytes
        //Profile_Picture.setDrawingCacheEnabled(true);
       // Profile_Picture.buildDrawingCache();

        UploadTask uploadTask = imagesRef.putFile(p_Uri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.i("Operacion ProfileFragment.uploadProfileImage()", "Falla la carga de la imagen " + exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Log.i("Operacion ProfileFragment.uploadProfileImage()", "Uctualizacion de imagen de perfil Exitosa" );
            }
        });



    }
}
