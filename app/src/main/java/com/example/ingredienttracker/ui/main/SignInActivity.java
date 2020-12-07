package com.example.ingredienttracker.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ingredienttracker.R;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    Button signInButton;
    Button signUpButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_page);
        emailEditText = findViewById(R.id.emailEditTextSignIn);
        passwordEditText = findViewById(R.id.passwordEditTextSignIn);
        signInButton = findViewById(R.id.signInButtonSignIn);
        signUpButton = findViewById(R.id.signUpButtonSignIn);

        mAuth = FirebaseAuth.getInstance();

        signInButton.setOnClickListener(v -> {
            handleSignIn();
        });

        signUpButton.setOnClickListener(v -> {
            handleSignUp();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            Log.i("LOGIN", mAuth.getCurrentUser().getUid());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void handleSignIn() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(SignInActivity.this, "Login failed please try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void handleSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

}
