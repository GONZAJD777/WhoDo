package com.example.whodo.app.resources.images;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.whodo.app.Callback;

import java.util.List;

public class ImagesViewModel extends ViewModel {
    private ImagesDao<ImageDTO> mImagesDao ;
    private final MutableLiveData<List<ImageDTO>> mServIconImages = new MutableLiveData<>();
    private final MutableLiveData<List<String>> mServIconNames = new MutableLiveData<>();
    private final MutableLiveData<List<String>> mStoredServIconNames = new MutableLiveData<>();

    public ImagesViewModel(){
        mImagesDao=new FirebaseStorageImageDAO();

        this.setServIconNames();

    }

    public void setServIconNames () {
        mImagesDao.getServIconNames(new Callback<List<String>>() {
            @Override
            public void onSuccess(List<String> pServIconNames) {
                mServIconNames.setValue(pServIconNames);
            }
            @Override
            public void onError(Exception e) {

            }
        });
    }
    public LiveData<List<String>> getServIconNames (){
        return mServIconNames;
    }

    public void setServIconImages (List<String> pServIconNames) {
        mImagesDao.getServIconImages(pServIconNames, new Callback<List<ImageDTO>>() {
            @Override
            public void onSuccess(List<ImageDTO> pServIconImages) {
                mServIconImages.setValue(pServIconImages);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    public LiveData<List<ImageDTO>> getServIconImages(){ return this.mServIconImages; }

    public void setStoredServIconNames(List<String> pStoredServIconNames){ mStoredServIconNames.setValue(pStoredServIconNames); }
    public LiveData<List<String>> getStoredServIconNames(){return mStoredServIconNames;}

}
