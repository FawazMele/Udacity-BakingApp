package com.example.bakingapp2.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp2.Adapter.StepsAdapter;
import com.example.bakingapp2.Model.Step;
import com.example.bakingapp2.R;

import java.util.ArrayList;

public class DetailsFragment extends Fragment {


    public DetailsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        TextView stepName = (TextView) view.findViewById(R.id.ingredient_text);

        final Bundle bundle = this.getArguments();
        String stepTextBundle = bundle.getString("ingredientsFragment");
        ArrayList<Step> stepList = (ArrayList<Step>) bundle.getSerializable("StepsFragment");

        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        StepsAdapter adapter = new StepsAdapter(getActivity(), stepList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnClickListener(new StepsAdapter.onItemClickListner() {
            @Override
            public void onClick(String str) {
                VideoFragment videoFragment = new VideoFragment();
                Bundle bundle = new Bundle();
                bundle.putString("videoURLFragment", str);
                videoFragment.setArguments(bundle);
            }
        });


        stepName.setText(stepTextBundle);

        return view;
    }
}
