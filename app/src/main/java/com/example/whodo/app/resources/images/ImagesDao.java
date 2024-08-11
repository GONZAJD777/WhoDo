package com.example.whodo.app.resources.images;

import com.example.whodo.app.Callback;

import java.util.List;

public interface ImagesDao<T> {

    void getMapIconsList(Callback<List<String>> callback);
    void getMapIcons(List<String> fileName,Callback<List<T>> callback);

    void getSnippetsIconsList(Callback<String> callback);
    void getSnippetsIcons(List<String> fileName,Callback<List<T>> callback);

    void getWorkOrderIconsList(Callback<String> callback);
    void getWorkOrderIcons(List<String> fileName,Callback<List<T>> callback);
}
