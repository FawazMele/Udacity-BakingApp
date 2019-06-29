package com.example.bakingapp2.Data;

import com.example.bakingapp2.Model.ResultsResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Services {
    @GET("baking.json")
    Call<ArrayList<ResultsResponse>> getResult();
}
