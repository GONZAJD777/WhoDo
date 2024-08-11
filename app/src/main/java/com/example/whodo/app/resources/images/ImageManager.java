package com.example.whodo.app.resources.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.whodo.app.Callback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public  class ImageManager {
    private static final String TAG = "MAIN-ACTIVITY";
    private  ImageManager() {}

    public static List<String> checkMissingMapIcons (List<String> pMapIconList, Context pContext) {

        Set<String> mListA = new HashSet<>(pMapIconList);
        Set<String> mListB = new HashSet<>(checkMapIcons(pContext));

        mListA.removeAll(mListB);
        List<String> mMissingMapIcons = new ArrayList<>(mListA);

        return mMissingMapIcons;
    }

    public static List<String> checkMapIcons (Context pContext){
        File internalDir = pContext.getFilesDir();
        File mMapIconsDirectory = new File(internalDir, "MapIcons");
        if (!mMapIconsDirectory.exists()) {
            mMapIconsDirectory.mkdir();
        }

        // Crear una lista para almacenar los nombres de archivos
        List<String> mMapIconNames = new ArrayList<>();

        // Iterar sobre los archivos en el directorio interno
        for (File file : Objects.requireNonNull(mMapIconsDirectory.listFiles())) {
            if (file.isFile()) {
                mMapIconNames.add(file.getName());
            }
        }

        return mMapIconNames;
    }

    public static void loadMapIcons (List<ImageDTO> pMapIcons, Context pContext,Callback<List<String>> pCallback){
        File internalDir = pContext.getFilesDir();
        File mMapIconsDirectory = new File(internalDir, "MapIcons");

        // Itera sobre los bitmaps y los nombres de archivo
        for (int i = 0; i < pMapIcons.size(); i++) {
            Bitmap bitmap = pMapIcons.get(i).getBitmap();
            String fileName = pMapIcons.get(i).getFileName(); // Nombre del archivo original

            // Crea un archivo en el directorio
            File file = new File(mMapIconsDirectory, fileName);

            // Guarda el bitmap en el archivo
            try (FileOutputStream fos = new FileOutputStream(file)) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                // Puedes ajustar la calidad y el formato según tus necesidades
            } catch (IOException e) {
                Log.e(TAG, "Error saving image: " + e.getMessage());
                // Maneja el error aquí (por ejemplo, reintentar o registrar el problema)
            }
        }
        pCallback.onSuccess(checkMapIcons(pContext));
    }
    
    public static Bitmap getMapIcon (Context pContext,String pIconName){
        File internalDir = pContext.getFilesDir();
        File mMapIconsDirectory = new File(internalDir, "MapIcons");
        String mIconPath = mMapIconsDirectory+"/"+pIconName.replaceAll("\\s+", "_").toLowerCase()+".png"; // Ruta al archivo del icono


        // Cargar el icono como un Bitmap
        Bitmap mIconBitmap = BitmapFactory.decodeFile(mIconPath);
        if(mIconBitmap==null) mIconBitmap = BitmapFactory.decodeFile(mMapIconsDirectory+"/varios_24.png");

        Bitmap mScaledIconBitmap = Bitmap.createScaledBitmap(mIconBitmap, 80, 80, false);
        return mScaledIconBitmap;
    }

    public static Bitmap checkWorkOrderIcons (List<String> pService,Context pContext) {
        File internalDir = pContext.getFilesDir();
        File mWorkOrderIconsDirectory = new File(internalDir, "WorkOrderIcons");
        if (!mWorkOrderIconsDirectory.exists()) {
            mWorkOrderIconsDirectory.mkdir();
        }
        return null;
    }

    public static Bitmap checkSnippetsIcons (List<String> pService,Context pContext) {
        File internalDir = pContext.getFilesDir();
        File mSnippetsIconsDirectory = new File(internalDir, "SnippetsIcons");
        if (!mSnippetsIconsDirectory.exists()) {
            mSnippetsIconsDirectory.mkdir();
        }

        
        return null;
    }
}
