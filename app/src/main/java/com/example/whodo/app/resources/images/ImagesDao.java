package com.example.whodo.app.resources.images;

import com.example.whodo.app.Callback;

import java.util.List;

public interface ImagesDao<T> {

    void getServIconNames(Callback<List<String>> callback);
    void getServIconImages(List<String> fileName,Callback<List<T>> callback);
}
