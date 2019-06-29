package com.example.bakingapp2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp2.Activity.BakingDetailsActivity;
import com.example.bakingapp2.Model.Ingredient;
import com.example.bakingapp2.Model.ResultsResponse;
import com.example.bakingapp2.Model.Step;
import com.example.bakingapp2.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BakingAdapter extends RecyclerView.Adapter<BakingAdapter.ViewHolder> {

    private final List<ResultsResponse> data;
    Context context;
    private List<Ingredient> ingredient = new ArrayList<>();

    public BakingAdapter(Context context, ArrayList<ResultsResponse> items) {
        this.context = context;
        data = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.baking_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.bakingName.setText(data.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, BakingDetailsActivity.class);
                StringBuffer stringBuffer = new StringBuffer();
                for (Ingredient ingredient : data.get(position).getIngredients()) {
                    stringBuffer.append(ingredient.getQuantity() + " " +
                            ingredient.getIngredient() + " " + ingredient.getMeasure() + "\n");
                }

                List<Step> stepArrayList = data.get(position).getSteps();
                i.putExtra("Steps", (Serializable) stepArrayList);
                i.putExtra("ingredients", stringBuffer.toString());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView bakingName;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            bakingName = view.findViewById(R.id.bakingName);
        }
    }
}
