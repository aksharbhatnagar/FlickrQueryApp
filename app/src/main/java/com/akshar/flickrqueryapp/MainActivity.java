package com.akshar.flickrqueryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private ArrayList<String> UrlArray = new ArrayList<>();
    private EditText editText;

    private int pageNumber = 1;
    private int perPage = 10;
    LinearLayoutManager linearLayoutManager;
    private boolean loading = true; // to check if we are currently loading from the Flickr API

    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.recyclerView = findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapter(UrlArray, this) {
            @Override
            public void loadMore() {
                pageNumber++;
                addUrls();
            }
        };
        recyclerView.setAdapter(adapter);
        final int[] pastVisibleItems = new int[1];
        final int[] visibleItemCount = new int[1];
        final int[] totalItemCount = new int[1];
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() { // for endless scrolling
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount[0] = linearLayoutManager.getChildCount();
                    totalItemCount[0] = linearLayoutManager.getItemCount();
                    pastVisibleItems[0] = linearLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if (visibleItemCount[0] + pastVisibleItems[0] >= totalItemCount[0]) {
                            loading = false;
                            pageNumber++; // fetch next page
                            addUrls();
                        }
                    }
                }
            }
        });

        textViewResult = findViewById(R.id.text_view_result);
        editText = findViewById(R.id.edit_query);

    }

    public void executeSearch(View view) {

        pageNumber = 1; // start from page 1
        UrlArray.clear(); // clear list for new search
        // adapter.notifyDataSetChanged();

        addUrls();

        try { // hide keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


    public void addUrls() {

         retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.flickrApiBaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FlickrApi flickrApi = retrofit.create(FlickrApi.class);

        Call<FlickrApiResult> call = flickrApi.getImages(
                getString(R.string.flickrApiQueryMethod),
                getString(R.string.flickrApiKey),
                editText.getText().toString(),
                perPage,
                pageNumber,
                "json",
                1
        );

        call.enqueue(new Callback<FlickrApiResult>() {
            @Override
            public void onResponse(Call<FlickrApiResult> call, Response<FlickrApiResult> response) {
                if (response.isSuccessful())
                    Log.e("Success", new Gson().toJson(response.body()));
                else
                    Log.e("unSuccess", new Gson().toJson(response.errorBody()));

                FlickrApiResult imgs = response.body();
                List<PhotoResult> photosArray = imgs.getPhotos().getPhotoArray();

                for (PhotoResult photo : photosArray) {
                    UrlArray.add(Utils.createUrl(photo));
                }

                adapter.notifyDataSetChanged();
                loading = true; // loading done
            }

            @Override
            public void onFailure(Call<FlickrApiResult> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

}