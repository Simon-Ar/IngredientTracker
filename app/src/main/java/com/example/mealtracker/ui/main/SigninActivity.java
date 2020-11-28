package com.example.mealtracker.ui.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mealtracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SigninActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    Button signInButton;
    Button signUpButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_page);
        emailEditText = (EditText) findViewById(R.id.emailEditTextSignIn);
        passwordEditText = (EditText) findViewById(R.id.passwordEditTextSignIn);
        signInButton = (Button) findViewById(R.id.signInButtonSignIn);
        signUpButton = (Button) findViewById(R.id.signUpButtonSignIn);

        mAuth = FirebaseAuth.getInstance();

        signInButton.setOnClickListener(v -> {
            Log.i("INFO", "Clicked sign-in button");
            try {
                new LoginRequest().execute(new URL("http://192.168.1.181:8080/greeting"));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            String email = "test@test.com";
            String password = "test1234";
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Log.d("SIGN-IN", "SUCCESS!");
                            FirebaseUser user = mAuth.getCurrentUser();
                            System.out.println(user.getUid());
                        } else {
                            Log.w("SIGN-IN", "FAILED!");
                            Toast.makeText(SigninActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        });


        signUpButton.setOnClickListener(v -> {
            Log.i("INFO", "Clicked sign-up button");
            String email = "test@test.com";
            String password = "test1234";
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SIGN-UP", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            // Update UI
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SIGN-UP", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SigninActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // Update UI
                        }

                        // ...
                    });
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // Update the UI
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();
    }

    public class LoginRequest extends AsyncTask<URL, Integer, Long> {

        @Override
        protected Long doInBackground(URL... urls) {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create("{\"test\":5}", MediaType.get("application/json; charset=utf-8"));
            Request request = new Request.Builder().url(urls[0]).post(body).build();
            try (Response response = client.newCall(request).execute()) {
                System.out.println(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
