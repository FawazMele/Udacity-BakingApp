package com.example.bakingapp2.Fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bakingapp2.Adapter.BakingAdapter;
import com.example.bakingapp2.Data.RetrofitSingleton;
import com.example.bakingapp2.Data.Services;
import com.example.bakingapp2.Model.ResultsResponse;
import com.example.bakingapp2.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BakingFragment extends Fragment {


    RecyclerView recyclerView;

    public BakingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_baking, container, false);
        recyclerView = view.findViewById(R.id.list);

        if (isTablet(getActivity())) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        getBakings();
        return view;
    }


    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    private ArrayList<ResultsResponse> getBakings() {

        ArrayList<ResultsResponse> resultsResponse = new ArrayList<ResultsResponse>();
        Services services = RetrofitSingleton.newInstance().create(Services.class);
        Call<ArrayList<ResultsResponse>> call = services.getResult();
        call.enqueue(new Callback<ArrayList<ResultsResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<ResultsResponse>> call, Response<ArrayList<ResultsResponse>> response) {
                ArrayList<ResultsResponse> resultsResponse = response.body();
                BakingAdapter adapter = new BakingAdapter(getContext(), resultsResponse);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<ResultsResponse>> call, Throwable t) {
                Toast.makeText(getContext(), R.string.network_issue, Toast.LENGTH_LONG).show();
            }
        });
        return resultsResponse;
    }
}
