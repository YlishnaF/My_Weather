package com.example.myweather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SecondFragment extends Fragment {
    Button btnOk;
    EditText etLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View v = inflater.inflate(R.layout.fragment_second, container, false);
      btnOk = (Button) v.findViewById(R.id.oklocationbtn);
      etLocation = (EditText) v.findViewById(R.id.location_et);

      btnOk.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Bundle result = new Bundle();
              result.putString("location", etLocation.getText().toString());
              MainFragment main = new MainFragment();
              main.setArguments(result);
                getFragmentManager()
                      .beginTransaction()
                      .replace(R.id.fragment_container, main)
                      .commit();
          }
      });


      return v;
    }
}