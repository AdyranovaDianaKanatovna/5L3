package com.example.a5l3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;

import com.example.a5l3.adapter.ImageAdapter;
import com.example.a5l3.databinding.ActivityMainBinding;
import com.example.a5l3.network.RetrofitService;
import com.example.a5l3.network.model.Hit;
import com.example.a5l3.network.model.PixabayModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RetrofitService retrofitService;
    ActivityMainBinding binding;
    ImageAdapter adapter;
    public static final String KEY = "27871068-3a1ee228bc2d44923e7674dae";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        retrofitService = new RetrofitService();
        initClickers();
    }

    private void initClickers() {
        binding.applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = binding.wordEd.getText().toString();
                getImageFromApi(word, 1, 10);
            }


            private void getImageFromApi(String word, int page, int perPage) {
                retrofitService.getApi().getImages(KEY, word, page, perPage).enqueue(new Callback<PixabayModel>() {
                    @Override
                    public void onResponse(Call<PixabayModel> call, Response<PixabayModel> response) {
                        if (response.isSuccessful()) {
                            adapter = new ImageAdapter((ArrayList<Hit>) response.body().getHits());
                            binding.recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<PixabayModel> call, Throwable t) {
                        Log.e("ololo", "onFailure:" + t.getMessage());
                    }
                });
                binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        int count = 1;
                        String word = binding.wordEd.getText().toString();
                        getImageFromApi(word, ++count,5);
                        binding.swipeRefresh.setRefreshing(false);
                    }
                });

            }
        });
    }
}