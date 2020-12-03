package com.rejointech.tu_du.Login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.rejointech.tu_du.MainActivity;
import com.rejointech.tu_du.R;

public class Splashfrag extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Inflate the layout for this fragment

        View root=inflater.inflate(R.layout.fragment_splashfrag, container, false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Navigation.findNavController(root).navigate(R.id.action_splashfrag_to_signInfrag);
            }
        },3000);



        return root;
    }
}