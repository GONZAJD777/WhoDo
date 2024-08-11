package com.example.whodo.app.resources.images;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.whodo.app.Callback;

import java.util.List;

public class ImagesViewModel extends ViewModel {
    private ImagesDao<ImageDTO> mImagesDao ;
    private final MutableLiveData<List<ImageDTO>> mMapIcons = new MutableLiveData<>();
    private final MutableLiveData<List<String>> mMapIconsList = new MutableLiveData<>();
    private final MutableLiveData<List<String>> mLoadedMapIcons = new MutableLiveData<>();

    public ImagesViewModel(){
        mImagesDao=new FirebaseStorageImageDAO();

        this.setMapIconsList();

    }

    public void setMapIconsList () {
        mImagesDao.getMapIconsList(new Callback<List<String>>() {
            @Override
            public void onSuccess(List<String> mMapIconList) {
                mMapIconsList.setValue(mMapIconList);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    public LiveData<List<String>> getMapIconList (){
        return mMapIconsList;
    }
    public void setMapIcons (List<String> pMapIconNameList) {
        mImagesDao.getMapIcons(pMapIconNameList, new Callback<List<ImageDTO>>() {
            @Override
            public void onSuccess(List<ImageDTO> pMapIconsList) {
                mMapIcons.setValue(pMapIconsList);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    public LiveData<List<ImageDTO>> getMapIcons(){ return this.mMapIcons; }
    public void setStorageLoadedMapIcons(List<String> pLoadedMapIcons){ mLoadedMapIcons.setValue(pLoadedMapIcons); }
    public LiveData<List<String>> getStorageLoadedMapIcons(){return mLoadedMapIcons;}

}
