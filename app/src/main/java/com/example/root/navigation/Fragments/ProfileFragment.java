package com.example.root.navigation.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.root.navigation.Models.CircleTransform;
import com.example.root.navigation.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    View view;
    ImageView image_Perfil;
    SharedPreferences sharedPreferences;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences("marcaideas", Context.MODE_PRIVATE);
        binding();
        return view;
    }

    private void binding() {
        image_Perfil = view.findViewById(R.id.ivProfile_perfil);
        String ruta_imagen = sharedPreferences.getString("url_image", "");
        if (ruta_imagen.equals("")) {
            Picasso.with(getContext()).load(R.drawable.photocercle2).into(image_Perfil);
        } else {
            Picasso.with(getContext()).load(ruta_imagen).transform(new CircleTransform()).placeholder(R.drawable.photocercle).into(image_Perfil);
        }
    }

}
