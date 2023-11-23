package com.example.whodo.crud;
import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import com.example.whodo.BusinessClasses.User;
import com.example.whodo.BusinessClasses.WorkOrder;
import com.example.whodo.MainActivity;
import com.example.whodo.SingletonUser;
import com.firebase.geofire.GeoFireUtils;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.Objects;


public class CRUD {
    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference UserDBRef = mDatabaseReference.child("USERS");
    DatabaseReference WorkOrderDBRef =  mDatabaseReference.child("WORK_ORDERS");


    @SuppressLint("LongLogTag")
    public void CreateUser(User pUser){
        Log.i("Operaciones CRUD.CreateUser()",  "Se creara el usuario en la base con ID:" + "USERS/"+pUser.getUid() );
        UserDBRef.child(pUser.getUid()).setValue(pUser);
    }
    public void CreateWorkOrder(WorkOrder pWorkOrder){
        WorkOrderDBRef.push().setValue(pWorkOrder);
    }
    //Utility to add fields faster
    public void CreateUserField(){

       /** mDatabaseReference.child("6XE3O26f6AQpbUgg5GJsqYTVk1R2").child("appereanceScore").removeValue();
        mDatabaseReference.child("userScore").child("cleanlinessScore").removeValue();
        mDatabaseReference.child("userScore").child("speedScore").removeValue();
        mDatabaseReference.child("userScore").child("qualityScore").removeValue();
        mDatabaseReference.child("userScore").child("avgTariff").removeValue();
        mDatabaseReference.child("userScore").child("avgCompletionTime").removeValue();
        mDatabaseReference.child("userScore").child("overallScore").removeValue();**/
        /**mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                              @Override
                                                              public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                                  for (DataSnapshot ds : snapshot.getChildren()) {
                                                                      ;
                                                                     // assert UserSS != null;
                                                                      mDatabaseReference.child(ds.getKey()).child("/userScore").child("appereanceScore").setValue("0.0");
                                                                      mDatabaseReference.child(ds.getKey()).child("/userScore").child("cleanlinessScore").setValue("0.0");
                                                                      mDatabaseReference.child(ds.getKey()).child("/userScore").child("speedScore").setValue("0.0");
                                                                      mDatabaseReference.child(ds.getKey()).child("/userScore").child("qualityScore").setValue("0.0");
                                                                      mDatabaseReference.child(ds.getKey()).child("/userScore").child("avgTariff").setValue("0sat");
                                                                      mDatabaseReference.child(ds.getKey()).child("/userScore").child("avgCompletionTime").setValue("0hs");
                                                                      mDatabaseReference.child(ds.getKey()).child("/userScore").child("overallScore").setValue("0.0 (0)");
                                                                  }

                                                              }

                                                              @Override
                                                              public void onCancelled(@NonNull DatabaseError error) {

                                                              }

                                                }
                );*/

    }

    @SuppressLint("LongLogTag")
    public void UpdateUser(User pUser,User pUserSnapshot){

        Log.i("WhoDo-Log",  "Operacion CRUD.UpdateUser() Se actualizara el usuario:" + "USERS/"+pUser.getUid() );

        if (!Objects.equals(pUser.getAddress(), pUserSnapshot.getAddress())) {
            UserDBRef.child(pUser.getUid()).child("address").setValue(pUser.getAddress());
            Log.i("WhoDo-Log","UpdateUser DIRECCION ANTIGUA: " + pUserSnapshot.getAddress() );
            Log.i("WhoDo-Log","UpdateUser DIRECCION NUEVA: "+ pUser.getAddress()  );
        }

        if (!Objects.equals(pUser.getBirthday(), pUserSnapshot.getBirthday())) {
            UserDBRef.child(pUser.getUid()).child("birthday").setValue(pUser.getBirthday());
            Log.i("WhoDo-Log","UpdateUser CUMPLEAÑOS ANTIGUA: "+pUserSnapshot.getBirthday() );
            Log.i("WhoDo-Log","UpdateUser CUMPLEAÑOS NUEVA: "+ pUser.getBirthday());
        }

        if (!Objects.equals(pUser.getDescription(), pUserSnapshot.getDescription())) {
            UserDBRef.child(pUser.getUid()).child("description").setValue(pUser.getDescription());
            Log.i("WhoDo-Log","UpdateUser DESCRIPCION ANTIGUA: "+pUserSnapshot.getDescription() );
            Log.i("WhoDo-Log","UpdateUser DESCRIPCION NUEVA: "+ pUser.getDescription());
        }
        if (!Objects.equals(pUser.getEmail(), pUserSnapshot.getEmail())) {
            UserDBRef.child(pUser.getUid()).child("email").setValue(pUser.getEmail());
            Log.i("WhoDo-Log","UpdateUser CORREO ANTIGUA: "+pUserSnapshot.getEmail() );
            Log.i("WhoDo-Log","UpdateUser CORREO NUEVA: "+ pUser.getEmail());
        }
        if (!Objects.equals(pUser.getIsValidated(), pUserSnapshot.getIsValidated())) {
            UserDBRef.child(pUser.getUid()).child("isValidated").setValue(pUser.getIsValidated());
            Log.i("WhoDo-Log","UpdateUser iSVALIDATED ANTIGUA: "+pUserSnapshot.getIsValidated() );
            Log.i("WhoDo-Log","UpdateUser iSVALIDATED NUEVA: "+ pUser.getIsValidated());
        }
        if (!Objects.equals(pUser.getLanguages(), pUserSnapshot.getLanguages())) {
            UserDBRef.child(pUser.getUid()).child("languages").setValue(pUser.getLanguages());
            Log.i("WhoDo-Log","UpdateUser IDIOMAS ANTIGUA: "+pUserSnapshot.getLanguages() );
            Log.i("WhoDo-Log","UpdateUser IDIOMAS NUEVA: "+ pUser.getLanguages());
        }
        if (!Objects.equals(pUser.getLatitude(), pUserSnapshot.getLatitude())) {

            String hash = GeoFireUtils.getGeoHashForLocation(new GeoLocation(pUser.getLatitude(), pUser.getLongitude()));
            UserDBRef.child(pUser.getUid()).child("geohash").setValue(hash);
            UserDBRef.child(pUser.getUid()).child("latitude").setValue(pUser.getLatitude());
            Log.i("WhoDo-Log","UpdateUser GEOHASH: " + hash);
            Log.i("WhoDo-Log","UpdateUser LATITUD ANTIGUA: " + pUserSnapshot.getLatitude() );
            Log.i("WhoDo-Log","UpdateUser LATITUD NUEVA: "+ pUser.getLatitude());
        }
        if (!Objects.equals(pUser.getLongitude(), pUserSnapshot.getLongitude())) {
            UserDBRef.child(pUser.getUid()).child("longitude").setValue(pUser.getLongitude());
            Log.i("WhoDo-Log","UpdateUser LONGITUD ANTIGUA: " + pUserSnapshot.getLongitude() );
            Log.i("WhoDo-Log","UpdateUser LONGITUD NUEVA: " + pUser.getLongitude());
        }
        if (!Objects.equals(pUser.getGeohash(), pUserSnapshot.getGeohash())) {
            UserDBRef.child(pUser.getUid()).child("geohash").setValue(pUser.getGeohash());
            Log.i("WhoDo-Log","UpdateUser GEOHASH ANTIGUA: "+ pUserSnapshot.getGeohash());
            Log.i("WhoDo-Log","UpdateUser GEOHASH NUEVA: "+pUser.getGeohash());
        }
        if (!Objects.equals(pUser.getName(), pUserSnapshot.getName())) {
            UserDBRef.child(pUser.getUid()).child("name").setValue(pUser.getName());
            Log.i("WhoDo-Log","UpdateUser NOMBRE ANTIGUA: "+pUserSnapshot.getName() );
            Log.i("WhoDo-Log","UpdateUser NOMBRE NUEVA: "+ pUser.getName());
        }
        if (!Objects.equals(pUser.getPassword(), pUserSnapshot.getPassword())) {
            UserDBRef.child(pUser.getUid()).child("password").setValue(pUser.getPassword());
            Log.i("WhoDo-Log","UpdateUser PASSWORD ANTIGUA: "+pUserSnapshot.getPassword() );
            Log.i("WhoDo-Log","UpdateUser PASSWORD NUEVA: "+ pUser.getPassword());
        }
        if (!Objects.equals(pUser.getPhone(), pUserSnapshot.getPhone())) {
            UserDBRef.child(pUser.getUid()).child("phone").setValue(pUser.getPhone());
            Log.i("WhoDo-Log","UpdateUser TELEFONO ANTIGUA: "+pUserSnapshot.getPhone() );
            Log.i("WhoDo-Log","UpdateUser TELEFONO NUEVA: "+ pUser.getPhone());
        }
        if (!Objects.equals(pUser.getPhone_ccn(), pUserSnapshot.getPhone_ccn())) {
            UserDBRef.child(pUser.getUid()).child("phone_ccn").setValue(pUser.getPhone_ccn());
            Log.i("WhoDo-Log","UpdateUser CCN ANTIGUA: "+pUserSnapshot.getPhone_ccn() );
            Log.i("WhoDo-Log","UpdateUser CCN NUEVA: "+ pUser.getPhone_ccn());
        }
        if (!Objects.equals(pUser.getProfilePicture(), pUserSnapshot.getProfilePicture())) {
            uploadProfileImage(pUser.getProfilePicture(),pUser.getUid());
            Log.i("WhoDo-Log","UpdateUser IMAGEN ANTIGUA: "+pUserSnapshot.getProfilePicture() );
            Log.i("WhoDo-Log","UpdateUser IMAGEN NUEVA: "+ pUser.getProfilePicture());
        }
        if (!Objects.equals(pUser.getType(), pUserSnapshot.getType())) {
            UserDBRef.child(pUser.getUid()).child("type").setValue(pUser.getType());
            Log.i("WhoDo-Log ", "UpdateUser TIPO ANTIGUA: "+pUserSnapshot.getType() );
            Log.i("WhoDo-Log","UpdateUser TIPO NUEVA: "+ pUser.getType());
        }
        if (!Objects.equals(pUser.getSpecialization(), pUserSnapshot.getSpecialization())) {
            UserDBRef.child(pUser.getUid()).child("specialization").setValue(pUser.getSpecialization());
            Log.i("WhoDo-Log ", "UpdateUser ESPECIALIZACION ANTIGUA: "+pUserSnapshot.getSpecialization() );
            Log.i("WhoDo-Log","UpdateUser ESPECIALIZACION NUEVA: "+ pUser.getSpecialization());
        }

    }
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
                //luego de actualizar la base y el SnapshotLoggedUser, se actualizan las variable del LoggedUser
                SingletonUser.getInstance().setProfilePicture(uri.toString());
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
