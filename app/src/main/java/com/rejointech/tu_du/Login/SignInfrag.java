package com.rejointech.tu_du.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rejointech.tu_du.R;
import com.rejointech.tu_du.dashboard.Home;
import com.rejointech.tu_du.model.Login_Cred;

public class SignInfrag extends Fragment {

    View root;
    TextInputLayout account,passwordtt;
    Button useGoogle,forgotPassBot,loginBot,newUser;
    private SharedPreferences pref, pref1;
    private SharedPreferences.Editor editor, editor1;
    private FirebaseAuth auth;
    private DatabaseReference database;
    Login_Cred login_cred, login_cred1;
    private GoogleSignInClient mGoogleSignInClient;
    private String tokenUID;
    private int guh=2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root=inflater.inflate(R.layout.fragment_sign_infrag, container, false);

//TODO: all muticals::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        muticals();
//TODO all variables:::::::::::::::::::::::::::::::::::::::::::::::::::::

//TODO Buttons :::::::::::::::::::::::::::::::::::::::::::::::::::::::


        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(root).navigate(R.id.action_signInfrag_to_signUpfrag);
            }
        });

        useGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleChooser();
                googleSignUp(guh);
            }
        });



        return root;
    }

//TODO all functions::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private void muticals() {
        useGoogle=root.findViewById(R.id.useGoogle);
        forgotPassBot=root.findViewById(R.id.forgotPassBot);
        loginBot=root.findViewById(R.id.newUser);
        account=root.findViewById(R.id.account);
        newUser=root.findViewById(R.id.newUser);
        passwordtt=root.findViewById(R.id.passwordtt);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("Profile");
        login_cred = new Login_Cred();
    }

    private void signin(String username, final String password) {
        auth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isComplete()) {
                            logindatatofirebase();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void logindatatofirebase() {
        database
                .child("User_cred")
                .child(auth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            Toast.makeText(getContext(), "pottyyyykrlon frandzzzzzz", Toast.LENGTH_SHORT).show();
                            login_cred = snapshot.getValue(Login_Cred.class);
                            addDataTosharedPref();
                            startActivity(new Intent(getContext(), Home.class));
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addDataTosharedPref() {
        pref = getActivity().getSharedPreferences("loginSaves", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString("accountLogin", account.getEditText().getText().toString());
        editor.putString("passwordLogin", passwordtt.getEditText().getText().toString());
        editor.putString("usernameLogin", login_cred.getUsername());
        editor.putString("uidLogin", login_cred.getUid());
        editor.apply();
    }

    private void googleChooser() {
        /* Configure sign-in to request the user's ID, email address, and basic
            profile. ID and basic profile are included in DEFAULT_SIGN_IN.*/
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
    }

    private void googleSignUp(int code) {
        Intent signInIntent = this.mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, code);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == guh) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                tokenUID=account.getIdToken();
                firebaseAuthWithGoogle(tokenUID);
                // Signed in successfully, show authenticated UI.
            } catch (ApiException e) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = auth.getCurrentUser();
                    startActivity(new Intent(getContext(), Home.class));
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(getContext(), "YOU ARE FUCKED!!!!!", Toast.LENGTH_SHORT).show();
                    // ...
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}