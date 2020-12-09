package com.example.ingredienttracker.ui.main.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ingredienttracker.R;
import com.example.ingredienttracker.ui.main.MainActivity;
import com.example.ingredienttracker.ui.main.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;


public class AccountFragment extends Fragment {

    Button mButton;


    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButton = view.findViewById(R.id.btnLogOut);
        mButton.setOnClickListener(v -> {
            getFragmentManager().popBackStackImmediate();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this.getActivity(), SignInActivity.class);
            startActivity(intent);
        });
    }
}