package com.example.bakingapp2.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.bakingapp2.Fragment.DetailsFragment;
import com.example.bakingapp2.Fragment.VideoFragment;
import com.example.bakingapp2.R;
import com.example.bakingapp2.Widgets.WidgetUtils;

import java.io.Serializable;

public class BakingDetailsActivity extends AppCompatActivity {

    Serializable stepList;
    private Parcelable mLayoutManagerState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        stepList = getIntent().getSerializableExtra("Steps");


        setContentView(R.layout.activity_baking_details);

        String ingredients = getIntent().getStringExtra("ingredients");

        SharedPreferences sharedPreferences = getSharedPreferences(WidgetUtils.WIDGET_INGREDIENTS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(WidgetUtils.RECIPE_STRING, ingredients);
        editor.apply();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DetailsFragment detailsFragment = new DetailsFragment();

        VideoFragment videoFragment = new VideoFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ingredientsFragment", ingredients);
        bundle.putSerializable("StepsFragment", stepList);
        detailsFragment.setArguments(bundle);

        if (isTablet(this)) {
            fragmentTransaction.add(R.id.container_details_fragment, detailsFragment);
            fragmentTransaction.add(R.id.container_video_fragment, videoFragment);
            fragmentTransaction.commit();
        } else {
            fragmentTransaction.add(R.id.details_container, detailsFragment);
            fragmentTransaction.commit();
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }

    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

}
