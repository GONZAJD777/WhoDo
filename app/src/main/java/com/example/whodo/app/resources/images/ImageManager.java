package com.example.whodo.app.resources.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.appcompat.content.res.AppCompatResources;

import com.example.whodo.R;
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
    private static final String TAG = "IMAGE-MANAGER";
    private static final String mServIconDirectory="servIconImages";
    private  ImageManager() {}

    public static List<String> checkMissingIcons (List<String> pMapIconList, Context pContext) {

        Set<String> mListA = new HashSet<>(pMapIconList);
        Set<String> mListB = new HashSet<>(checkStoredIcons(pContext));

        mListA.removeAll(mListB);
        List<String> mMissingIconNames = new ArrayList<>(mListA);

        return mMissingIconNames;
    }

    public static List<String> checkStoredIcons (Context pContext){
        File internalDir = pContext.getFilesDir();
        File mMapIconsDirectory = new File(internalDir, mServIconDirectory);
        if (!mMapIconsDirectory.exists()) {
            mMapIconsDirectory.mkdir();
        }

        // Crear una lista para almacenar los nombres de archivos
        List<String> mStoredIconNames = new ArrayList<>();

        // Iterar sobre los archivos en el directorio interno
        for (File file : Objects.requireNonNull(mMapIconsDirectory.listFiles())) {
            if (file.isFile()) {
                mStoredIconNames.add(file.getName());
            }
        }

        return mStoredIconNames;
    }

    public static void storeIcons (List<ImageDTO> pMapIcons, Context pContext,Callback<List<String>> pCallback){
        File internalDir = pContext.getFilesDir();
        File mMapIconsDirectory = new File(internalDir, mServIconDirectory);

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
        pCallback.onSuccess(checkStoredIcons(pContext));
    }

    public static Bitmap getStoredIcon (Context pContext,String pIconName){
        File internalDir = pContext.getFilesDir();
        File mFullPathServIconDirectory = new File(internalDir, mServIconDirectory);
        String mRelIconPath = pIconName.replaceAll("\\s+", "_").toLowerCase()+"_.*\\.png"; // Ruta al archivo del icono
        Bitmap mIconBitmap = null;

        File directory = new File(String.valueOf(mFullPathServIconDirectory));
        File[] files = directory.listFiles();
        List<File> patternFiles = new ArrayList<>();

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
               String FileName= files[i].getName();
                if (FileName.matches(mRelIconPath)) {
                    patternFiles.add(files[i]);
                }
            }
        }

        if (!patternFiles.isEmpty()) {
            long mDate;
            long mAuxDate=0L;
            for (File auxFile : patternFiles) {
                mDate = Long.parseLong(auxFile.getName().substring(auxFile.getName().length()-12,auxFile.getName().length()-4));
                if (mDate > mAuxDate) {
                    mAuxDate = Long.parseLong(auxFile.getName().substring(auxFile.getName().length()-12,auxFile.getName().length()-4));
                    mIconBitmap = BitmapFactory.decodeFile(mFullPathServIconDirectory+"/"+auxFile.getName());
                }
            }
        }

        if(mIconBitmap==null){
            mIconBitmap = BitmapFactory.decodeResource(pContext.getResources(), R.drawable.loading_512);
        }
        Bitmap mScaledIconBitmap = Bitmap.createScaledBitmap(mIconBitmap, 80, 80, false);
        return mScaledIconBitmap;
    }

}
