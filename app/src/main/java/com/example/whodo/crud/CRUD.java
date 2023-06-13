package com.example.whodo.crud;
import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import com.example.whodo.BusinessClasses.User;
import com.example.whodo.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.Objects;


public class CRUD {
    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("USERS");

    @SuppressLint("LongLogTag")
    public void CreateUser(User p_User){
        Log.i("Operaciones CRUD.CreateUser()",  "Se creara el usuario en la base con ID:" + "USERS/"+p_User.getUid() );
        mDatabaseReference.child(p_User.getUid()).setValue(p_User);
    }
    @SuppressLint("LongLogTag")
    public void UpdateUser(User p_User){

        Log.i("WhoDo-Log",  "Operacion CRUD.UpdateUser() Se actualizara el usuario:" + "USERS/"+p_User.getUid() );

        if (!Objects.equals(p_User.getAddress(), MainActivity.getLoggedUserSnapshot().getAddress())) {
            mDatabaseReference.child(p_User.getUid()).child("address").setValue(p_User.getAddress());
            Log.i("WhoDo-Log","UpdateUser DIRECCION ANTIGUA: " + MainActivity.getLoggedUserSnapshot().getAddress() );
            Log.i("WhoDo-Log","UpdateUser DIRECCION NUEVA: "+ p_User.getAddress()  );
        }
        if (!Objects.equals(p_User.getBirthday(), MainActivity.getLoggedUserSnapshot().getBirthday())) {
            mDatabaseReference.child(p_User.getUid()).child("birthday").setValue(p_User.getBirthday());
            Log.i("WhoDo-Log","UpdateUser CUMPLEAÑOS ANTIGUA: "+MainActivity.getLoggedUserSnapshot().getBirthday() );
            Log.i("WhoDo-Log","UpdateUser CUMPLEAÑOS NUEVA: "+ p_User.getBirthday());
        }
        if (!Objects.equals(p_User.getDescription(), MainActivity.getLoggedUserSnapshot().getDescription())) {
            mDatabaseReference.child(p_User.getUid()).child("description").setValue(p_User.getDescription());
            Log.i("WhoDo-Log","UpdateUser DESCRIPCION ANTIGUA: "+MainActivity.getLoggedUserSnapshot().getDescription() );
            Log.i("WhoDo-Log","UpdateUser DESCRIPCION NUEVA: "+ p_User.getDescription());
        }
        if (!Objects.equals(p_User.getEmail(), MainActivity.getLoggedUserSnapshot().getEmail())) {
            mDatabaseReference.child(p_User.getUid()).child("email").setValue(p_User.getEmail());
            Log.i("WhoDo-Log","UpdateUser CORREO ANTIGUA: "+MainActivity.getLoggedUserSnapshot().getEmail() );
            Log.i("WhoDo-Log","UpdateUser CORREO NUEVA: "+ p_User.getEmail());
        }
        if (!Objects.equals(p_User.getIsValidated(), MainActivity.getLoggedUserSnapshot().getIsValidated())) {
            mDatabaseReference.child(p_User.getUid()).child("isValidated").setValue(p_User.getIsValidated());
            Log.i("WhoDo-Log","UpdateUser iSVALIDATED ANTIGUA: "+MainActivity.getLoggedUserSnapshot().getIsValidated() );
            Log.i("WhoDo-Log","UpdateUser iSVALIDATED NUEVA: "+ p_User.getIsValidated());
        }
        if (!Objects.equals(p_User.getLanguages(), MainActivity.getLoggedUserSnapshot().getLanguages())) {
            mDatabaseReference.child(p_User.getUid()).child("languages").setValue(p_User.getLanguages());
            Log.i("WhoDo-Log","UpdateUser IDIOMAS ANTIGUA: "+MainActivity.getLoggedUserSnapshot().getLanguages() );
            Log.i("WhoDo-Log","UpdateUser IDIOMAS NUEVA: "+ p_User.getLanguages());
        }
        if (!Objects.equals(p_User.getLatitude(), MainActivity.getLoggedUserSnapshot().getLatitude())) {
            mDatabaseReference.child(p_User.getUid()).child("latitude").setValue(p_User.getLatitude());
            Log.i("WhoDo-Log","UpdateUser LATITUD ANTIGUA: "+MainActivity.getLoggedUserSnapshot().getLatitude() );
            Log.i("WhoDo-Log","UpdateUser LATITUD NUEVA: "+ p_User.getLatitude());
        }
        if (!Objects.equals(p_User.getLongitude(), MainActivity.getLoggedUserSnapshot().getLongitude())) {
            mDatabaseReference.child(p_User.getUid()).child("longitude").setValue(p_User.getLongitude());
            Log.i("WhoDo-Log","UpdateUser LONGITUD ANTIGUA: "+MainActivity.getLoggedUserSnapshot().getLongitude() );
            Log.i("WhoDo-Log","UpdateUser LONGITUD NUEVA: "+ p_User.getLongitude());
        }
        if (!Objects.equals(p_User.getName(), MainActivity.getLoggedUserSnapshot().getName())) {
            mDatabaseReference.child(p_User.getUid()).child("name").setValue(p_User.getName());
            Log.i("WhoDo-Log","UpdateUser NOMBRE ANTIGUA: "+MainActivity.getLoggedUserSnapshot().getName() );
            Log.i("WhoDo-Log","UpdateUser NOMBRE NUEVA: "+ p_User.getName());
        }
        if (!Objects.equals(p_User.getPassword(), MainActivity.getLoggedUserSnapshot().getPassword())) {
            mDatabaseReference.child(p_User.getUid()).child("password").setValue(p_User.getPassword());
            Log.i("WhoDo-Log","UpdateUser PASSWORD ANTIGUA: "+MainActivity.getLoggedUserSnapshot().getPassword() );
            Log.i("WhoDo-Log","UpdateUser PASSWORD NUEVA: "+ p_User.getPassword());
        }
        if (!Objects.equals(p_User.getPhone(), MainActivity.getLoggedUserSnapshot().getPhone())) {
            mDatabaseReference.child(p_User.getUid()).child("phone").setValue(p_User.getPhone());
            Log.i("WhoDo-Log","UpdateUser TELEFONO ANTIGUA: "+MainActivity.getLoggedUserSnapshot().getPhone() );
            Log.i("WhoDo-Log","UpdateUser TELEFONO NUEVA: "+ p_User.getPhone());
        }
        if (!Objects.equals(p_User.getPhone_ccn(), MainActivity.getLoggedUserSnapshot().getPhone_ccn())) {
            mDatabaseReference.child(p_User.getUid()).child("phone_ccn").setValue(p_User.getPhone_ccn());
            Log.i("WhoDo-Log","UpdateUser CCN ANTIGUA: "+MainActivity.getLoggedUserSnapshot().getPhone_ccn() );
            Log.i("WhoDo-Log","UpdateUser CCN NUEVA: "+ p_User.getPhone_ccn());
        }
        if (!Objects.equals(p_User.getProfilePicture(), MainActivity.getLoggedUserSnapshot().getProfilePicture())) {
            uploadProfileImage(p_User.getProfilePicture(),p_User.getUid());
            Log.i("WhoDo-Log","UpdateUser IMAGEN ANTIGUA: "+MainActivity.getLoggedUserSnapshot().getProfilePicture() );
            Log.i("WhoDo-Log","UpdateUser IMAGEN NUEVA: "+ p_User.getProfilePicture());
        }
        if (!Objects.equals(p_User.getType(), MainActivity.getLoggedUserSnapshot().getType())) {
            mDatabaseReference.child(p_User.getUid()).child("type").setValue(p_User.getType());
            Log.i("WhoDo-Log ", "UpdateUser TIPO ANTIGUA: "+MainActivity.getLoggedUserSnapshot().getType() );
            Log.i("WhoDo-Log","UpdateUser TIPO NUEVA: "+ p_User.getType());
        }

    }
    public void DeleteUser(){}
    public void uploadProfileImage(String p_Uri, String p_User){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("WHODO-IMAGES/PROFILE-PICTURE/" + p_User);


        Uri myUri = Uri.parse(p_Uri);
        UploadTask uploadTask = imagesRef.putFile(myUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Log.i("WhoDo-Log", "Operacion ProfileFragment.uploadProfileImage() Falla la carga de la imagen " + exception);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                updateUserImage(imagesRef,p_User);

                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
                Log.i("WhoDo-Log", "Operacion ProfileFragment.uploadProfileImage() Uctualizacion de imagen de perfil Exitosa" );
            }
        });



    }
    public void updateUserImage(StorageReference imagesRef, String p_User){
        imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                mDatabaseReference.child(p_User).child("profilePicture").setValue(uri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
                Log.i("WhoDo-Log", "Operacion updateUserImage():" +exception );
            }
        });
    }
}
