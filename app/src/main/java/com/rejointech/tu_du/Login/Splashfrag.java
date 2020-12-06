package com.rejointech.tu_du.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rejointech.tu_du.MainActivity;
import com.rejointech.tu_du.R;
import com.rejointech.tu_du.dashboard.Home;

public class Splashfrag extends Fragment {
    FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Inflate the layout for this fragment

        View root=inflater.inflate(R.layout.fragment_splashfrag, container, false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                auth = FirebaseAuth.getInstance();
                FirebaseUser user = auth.getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(getContext(), Home.class));
                    getActivity().finish();
                } else {
                    Navigation.findNavController(root).navigate(R.id.action_splashfrag_to_signInfrag);
                }
            }
        },3000);



        return root;
    }
}