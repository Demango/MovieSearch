package com.example.mango.a54bluetest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private ArrayList<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void searchMovie() {
        RequestQueue queue = Volley.newRequestQueue(this);
        EditText search = (EditText)findViewById(R.id.searchText);

        String query = search.getText().toString();

        if (query.isEmpty()) {
            return;
        }

        String url = "http://www.omdbapi.com/?s=" + query;

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();


        for (Fragment fragment : fragmentList){
            ft.remove(fragment);
        }

        fragmentList = new ArrayList<>();

        ft.commit();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("Search");

                            for (int i = 0; i < results.length(); i++) {
                                JSONObject row = results.getJSONObject(i);

                                insertMovie(row);
                            }
                        } catch (JSONException e) {
                            Log.d("Warn", e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError e) {
                        Log.d("Warn", e.getMessage());
                    }
                });

        queue.add(jsObjRequest);
    }

    private void insertMovie(JSONObject movie) {
        Bundle bundle = new Bundle();
        bundle.putString("movie", movie.toString());

        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(bundle);

        fragmentList.add(fragment);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, fragment).commit();
    }
}
