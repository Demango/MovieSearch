package com.example.mango.a54bluetest;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieFragment extends Fragment implements View.OnClickListener {

    private JSONObject movie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String movieString = getArguments().getString("movie");

        View view = inflater.inflate(R.layout.movie_fragment, container, false);

        try {
            movie = new JSONObject(movieString);
            Log.d("Info", movie.getString("Title"));

            TextView title = (TextView)view.findViewById(R.id.title);
            title.setText(movie.getString("Title"));

            LinearLayout opener = (LinearLayout) view.findViewById(R.id.container);
            opener.setOnClickListener(this);
        } catch (JSONException e) {
            Log.d("Warn", e.getMessage());
        }
        return view;

    }

    @Override
    public void onClick(View view) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("modal");
        if (prev != null) {
            ft.remove(prev);
        }

        ft.addToBackStack(null).commit();

        Bundle bundle = new Bundle();
        bundle.putString("movie", movie.toString());

        FragmentManager fm = getActivity().getFragmentManager();

        DialogFragment modal = new MovieModal();
        modal.setArguments(bundle);
        modal.show(fm, "modal");
    }
}