package com.example.ingredienttracker.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ingredienttracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    EditText passwordConfirmEditText;
    Button signUpButton;
    Button signInButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);
        emailEditText = findViewById(R.id.emailEditTextSignUp);
        passwordEditText = findViewById(R.id.passwordEditTextSignUp);
        passwordConfirmEditText = findViewById(R.id.passwordConfirmEditTextSignUp);
        signUpButton = findViewById(R.id.signUpButtonSignUp);
        signInButton = findViewById(R.id.signInButtonSignUp);

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
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
            handleSignIn();
    }

    public void handleSignUp() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = passwordConfirmEditText.getText().toString();
        if (!password.equals(confirmPassword)) {
            Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                handleSignIn();
            } else {
                Toast.makeText(SignUpActivity.this, "Sign-up failed", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void handleSignIn() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

}
