package com.rejointech.tu_du.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rejointech.tu_du.R;
import com.rejointech.tu_du.dashboard.Home;
import com.rejointech.tu_du.model.Login_Cred;

import java.util.Objects;


public class SignUpfrag extends Fragment {
    View root;
    TextInputLayout account, passwordtt, name;
    Button useGoogle, registerBot, alreadyUserBot;
    private SharedPreferences pref, pref1;
    private SharedPreferences.Editor editor, editor1;
    private FirebaseAuth auth;
    private DatabaseReference database;
    Login_Cred login_cred, login_credGoogle;
    private GoogleSignInClient mGoogleSignInClient;
    static String namet, tokenUID, accountt, id;
    private int gui = 2;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(getContext(), Home.class));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_sign_upfrag, container, false);




//TODO: all muticals::::::::::::::::::::::::::::::::::::::::::::::::::::::::
        muticals();
//TODO all variables:::::::::::::::::::::::::::::::::::::::::::::::::::::

//TODO Buttons :::::::::::::::::::::::::::::::::::::::::::::::::::::::

        alreadyUserBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {{
                Navigation.findNavController(root).navigate(R.id.action_signUpfrag_to_signInfrag);} }
        });

        registerBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    if (name.getEditText().getText().toString().isEmpty()) {
                        name.setError("Username is required");
                        name.requestFocus();
                    } else if (account.getEditText().getText().toString().isEmpty()) {
                        account.setError("Account Required");
                        account.requestFocus();
                    } else if (!account.getEditText().getText().toString().contains("@") || !account.getEditText().getText().toString().contains(".com")) {
                        account.setError("Emter a valid Email.id");
                        account.requestFocus();
                    } else if ((passwordtt.getEditText().getText().toString().length() < 6)) {
                        passwordtt.setError("Atleast 6 digit long");
                        passwordtt.requestFocus();
                    } else if (
                            !(passwordtt.getEditText().getText().toString().contains("@")
                                    || passwordtt.getEditText().getText().toString().contains("!")
                                    || passwordtt.getEditText().getText().toString().contains("#")
                                    || passwordtt.getEditText().getText().toString().contains("$")
                                    || passwordtt.getEditText().getText().toString().contains("%")
                                    || passwordtt.getEditText().getText().toString().contains("^")
                                    || passwordtt.getEditText().getText().toString().contains("&")
                                    || passwordtt.getEditText().getText().toString().contains("*")
                                    || passwordtt.getEditText().getText().toString().contains("_")
                                    || passwordtt.getEditText().getText().toString().contains("+")
                                    || passwordtt.getEditText().getText().toString().contains("=")
                                    || passwordtt.getEditText().getText().toString().contains("/")
                                    || passwordtt.getEditText().getText().toString().contains("?")
                                    || passwordtt.getEditText().getText().toString().contains(">")
                                    || passwordtt.getEditText().getText().toString().contains("<")
                                    || passwordtt.getEditText().getText().toString().contains(";")
                                    || passwordtt.getEditText().getText().toString().contains(":"))
                    ) {
                        passwordtt.setError("Use a special character");
                        passwordtt.requestFocus();
                    } else if (
                            !(passwordtt.getEditText().getText().toString().contains("1")
                                    || passwordtt.getEditText().getText().toString().contains("2")
                                    || passwordtt.getEditText().getText().toString().contains("3")
                                    || passwordtt.getEditText().getText().toString().contains("4")
                                    || passwordtt.getEditText().getText().toString().contains("5")
                                    || passwordtt.getEditText().getText().toString().contains("6")
                                    || passwordtt.getEditText().getText().toString().contains("7")
                                    || passwordtt.getEditText().getText().toString().contains("8")
                                    || passwordtt.getEditText().getText().toString().contains("9")
                                    || passwordtt.getEditText().getText().toString().contains("0"))
                    ) {
                        passwordtt.setError("Use a digit in your password!");
                        passwordtt.requestFocus();
                    } else {
                        login(account.getEditText().getText().toString(), passwordtt.getEditText().getText().toString());
                    }
                }
            }});
        useGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleChooser();
                googleSignUp(gui);
            }
        });


        return root;
    }


    //TODO all functions::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private void muticals() {
        useGoogle=root.findViewById(R.id.useGoogle);
        account=root.findViewById(R.id.account);
        passwordtt=root.findViewById(R.id.passwordtt);
        alreadyUserBot=root.findViewById(R.id.alreadyUserBot);
        name=root.findViewById(R.id.userName);
        registerBot=root.findViewById(R.id.registerBot);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("Profile");
        login_cred = new Login_Cred();
        login_credGoogle=new Login_Cred();
    }

    private void login(String username, final String password) {
        auth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isComplete()) {
                            addLoginDataToDatabaseandSharedPref();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addLoginDataToDatabaseandSharedPref() {
        login_cred.setEmail_id(account.getEditText().getText().toString());
        login_cred.setUid(auth.getUid());
        login_cred.setUsername(name.getEditText().getText().toString());
        database
                .child("User_cred")
                .child(Objects.requireNonNull(auth.getUid()))
                .setValue(login_cred)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        addtoSharedPref();
                        startActivity(new Intent(getContext(), Home.class));
                        getActivity().finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addtoSharedPref() {
        pref = getActivity().getSharedPreferences("LoginInfo", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putString("namePref", name.getEditText().getText().toString());
        editor.putString("uidPref", auth.getUid());
        editor.putString("emailPref", account.getEditText().getText().toString());
        editor.apply();
    }


    //GOOGLE

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
        if (requestCode == gui) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                tokenUID = account.getIdToken();
                accountt = account.getEmail();
                namet = account.getDisplayName();
                id = account.getId();
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
                    addDatabaseinfoGoogle( tokenUID, accountt);
                } else {
                    // If sign in fails, display a message to the user.


                    // ...
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addDatabaseinfoGoogle(String tokenUID, String accountt) {
        login_credGoogle.setEmail_id(accountt);
        login_credGoogle.setUid(tokenUID);
        login_credGoogle.setUsername(namet);
        database
                .child("User_cred")
                .child(id)
                .setValue(login_credGoogle)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        addDataToPrefGOOGLE(login_credGoogle.getUsername(), login_credGoogle.getUid(), login_credGoogle.getEmail_id());
                        startActivity(new Intent(getContext(), Home.class));
                        getActivity().finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addDataToPrefGOOGLE(String name, String tokenUID, String accountt) {
        pref1 = getActivity().getSharedPreferences("GoogleLoginInfo", Context.MODE_PRIVATE);
        editor1 = pref1.edit();
        editor1.putString("GOOGLEnamePref", name);
        editor1.putString("GOOGLEuidPref", tokenUID);
        editor1.putString("GOOGLEemailPref", accountt);
        editor1.apply();
    }

}