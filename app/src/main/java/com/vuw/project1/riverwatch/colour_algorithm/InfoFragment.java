package com.vuw.project1.riverwatch.colour_algorithm;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.vuw.project1.riverwatch.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by George on 17/09/2016.
 */
public class InfoFragment extends Fragment {

    private EditText name;
    private EditText info;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public InfoFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static InfoFragment newInstance(int sectionNumber) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_nitrate_info, container, false);

        TextView nitrateView = (TextView) rootView.findViewById(R.id.nitrateTextView);
        TextView nitriteView = (TextView) rootView.findViewById(R.id.nitriteTextView);
        ImageView leftView = (ImageView) rootView.findViewById(R.id.nitrateImageView);
        ImageView rightView = (ImageView) rootView.findViewById(R.id.nitriteImageView);

        final Double nitrate = getArguments().getDouble("nitrate");
        final Double nitrite = getArguments().getDouble("nitrite");

        // set image views to left and right square of strip
        Bitmap left = (Bitmap) getArguments().getParcelable("left");
        Bitmap right = (Bitmap) getArguments().getParcelable("right");
        leftView.setImageBitmap(left);
        rightView.setImageBitmap(right);

        // set text views to nitrite and nitrate levels
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        nitrateView.setText("Nitrate: " + df.format(nitrate));
        nitriteView.setText("Nitrite: " + df.format(nitrite));

        // set text view for information
        info = (EditText) rootView.findViewById(R.id.infoEditText);
        name = (EditText) rootView.findViewById(R.id.nameEditText);

        return rootView;
    }

    public String getName(){
        return name.getText().toString();
    }

    public String getInfo(){
        return info.getText().toString();
    }

}