package com.example.mango.a54bluetest;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieModal extends DialogFragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_FRAME, android.R.style.Theme_Black);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.information_modal, container, false);

        String movieString = getArguments().getString("movie");

        try {
            JSONObject movie = new JSONObject(movieString);
            Log.d("Info", movie.getString("Title"));

            TextView title = (TextView)view.findViewById(R.id.title);
            title.setText(movie.getString("Title"));

            TextView year = (TextView)view.findViewById(R.id.year);
            year.setText(movie.getString("Year"));

            ImageView poster = (ImageView)view.findViewById(R.id.poster);

            Context context = poster.getContext();

            Picasso.with(context)
                .load(movie.getString("Poster"))
                .into(poster);

            LinearLayout modal = (LinearLayout) view.findViewById(R.id.modalContainer);
            modal.setOnClickListener(this);
        } catch (JSONException e) {
            Log.d("Warn", e.getMessage());
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}
