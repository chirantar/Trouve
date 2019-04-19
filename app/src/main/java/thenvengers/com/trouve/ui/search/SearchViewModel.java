package thenvengers.com.trouve.ui.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import thenvengers.com.trouve.model.Photos;
import thenvengers.com.trouve.model.SearchData;
import thenvengers.com.trouve.utils.Http;



public class SearchViewModel extends ViewModel {

    private MutableLiveData<Photos> mImages;
    public final String TAG = getClass().getName();

    public LiveData<Photos> getImages(String url) {
        if(mImages == null) {
            mImages = new MutableLiveData<Photos>();
            loadImages(url);
        }
        return mImages;
    }
    public LiveData<Photos> getNewImages(String url) {
        if(mImages != null) {
            loadImages(url);
        }
        return mImages;
    }

    public void loadImages(String url) {
        new AsyncTask<String, Void ,Photos>(){
            @Override
            protected Photos doInBackground(String... strings) {
                try {
                    String response = Http.getRequest(strings[0]);
                    Gson gson = new Gson();

                    SearchData searchData = gson.fromJson(response, SearchData.class);
                    return searchData.getPhotos();

                } catch (IOException e) {
                    Log.e(TAG, "Exception in LoadImages err:" +e.getStackTrace().toString());
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Photos photos) {
                mImages.setValue(photos);
            }
        }.execute(url);
    }

    public SearchViewModel() {
    }
}
