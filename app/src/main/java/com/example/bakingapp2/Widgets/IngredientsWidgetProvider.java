package com.example.bakingapp2.Widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.example.bakingapp2.Activity.MainBakingActivity;
import com.example.bakingapp2.R;



public class IngredientsWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        String recipeText = context.getString(R.string.ingredient_body_text);
        SharedPreferences sharedPreferences = context.getSharedPreferences(WidgetUtils.WIDGET_INGREDIENTS,
                Context.MODE_PRIVATE);

        recipeText = sharedPreferences.getString(WidgetUtils.RECIPE_STRING, recipeText);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        views.setTextViewText(R.id.appwidget_text, recipeText);

        Intent intent = new Intent(context, MainBakingActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }
}