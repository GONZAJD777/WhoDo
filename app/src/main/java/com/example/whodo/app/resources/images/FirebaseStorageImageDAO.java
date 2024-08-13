package com.example.whodo.app.resources.images;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.whodo.app.Callback;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FirebaseStorageImageDAO implements ImagesDao<ImageDTO>{

    private final String TAG="FirebaseStorageImageDAO";
    //IMAGES STORAGE REF
    private final FirebaseStorage mStorageInstance = FirebaseStorage.getInstance();
    private final StorageReference mStorageReference = mStorageInstance.getReference();
    private final StorageReference mImageStorageRef = mStorageReference.child("WHODO-IMAGES/APP-IMAGES");


    @Override
    public void getServIconNames(Callback<List<String>> callback) {
        StorageReference mImagesRef = mImageStorageRef.child("SERV-ICON-IMAGES");

        // Obtén una lista de referencias a los archivos dentro de la carpeta "MAP-ICONS"
        mImagesRef.listAll().addOnSuccessListener(listResult -> {
            List<String> mImageList = new ArrayList<>();
            for (StorageReference fileRef : listResult.getItems()) {
                // Agrega el nombre del archivo a la lista
                mImageList.add(fileRef.getName());
            }
            callback.onSuccess(mImageList);
        }).addOnFailureListener(e -> {
            callback.onError(e);
            Log.e(TAG, "Error al listar archivos: " + e.getMessage());
        });

    }

    @Override
    public void getServIconImages(List<String> pFileName, Callback<List<ImageDTO>> callback) {
        StorageReference mImagesRef = mImageStorageRef.child("SERV-ICON-IMAGES");
        List<ImageDTO> bitmapList = new ArrayList<>();

        // Itera sobre las referencias y descarga cada imagen
        for (String fileName : pFileName) {
            StorageReference imageRef = mImagesRef.child(fileName); // Crea la referencia para cada archivo

            imageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener(bytes -> {
                // Convierte los bytes a un objeto Bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                ImageDTO mImageDTO = new ImageDTO(bitmap,fileName);
                bitmapList.add(mImageDTO);

                // Actualiza el LiveData con la lista de bitmaps
                // mapIconsLiveData.setValue(bitmapList);
                callback.onSuccess(bitmapList);
            }).addOnFailureListener(e -> {
                Log.e(TAG, "Error al descargar imagen: " + e.getMessage());
                // Maneja el error aquí si es necesario
            });
        }

    }

}
