package com.example.whodo.app.resources.images;

import android.graphics.Bitmap;

public class ImageDTO {

    private Bitmap bitmap;
    private String fileName;

    public ImageDTO(Bitmap bitmap, String fileName) {
        this.bitmap = bitmap;
        this.fileName = fileName;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getFileName() {
        return fileName;
    }
}
