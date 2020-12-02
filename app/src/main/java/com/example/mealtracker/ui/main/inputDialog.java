package com.example.mealtracker.ui.main;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.mealtracker.R;

public class inputDialog extends Dialog implements View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button create, cancel;

    public inputDialog(@NonNull Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.input_dialog);
        create = (Button) findViewById(R.id.btnCreate);
        cancel = (Button) findViewById(R.id.btnCancel);
        create.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreate:
                c.finish();
                break;
            case R.id.btnCancel:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}

//TODO Finish making the input dialog!
