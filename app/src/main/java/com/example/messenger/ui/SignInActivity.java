package com.example.messenger.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messenger.R;
import com.example.messenger.handler.NumberValidationEvent;
import com.example.messenger.handler.ValidationResponse;
import com.example.messenger.utils.MessageManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class SignInActivity extends AppCompatActivity
{

    private EditText inputNumberEditText;
    private Button enterNumberButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        EventBus.getDefault().register(this);

        inputNumberEditText = findViewById(R.id.enter_number_edit_text);
        enterNumberButton = findViewById(R.id.enter_number_button);

        enterNumberButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!inputNumberEditText.getText().toString().isEmpty())
                    EventBus.getDefault().post(new NumberValidationEvent(inputNumberEditText.getText().toString()));
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onResume()
    {
        if (!EventBus.getDefault().isRegistered(MessageManager.getInstance())) EventBus.getDefault().register(MessageManager.getInstance());
        super.onResume();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onPause()
    {
        EventBus.getDefault().unregister(MessageManager.getInstance());
        super.onPause();
    }

    @Subscribe
    public void setValidation(ValidationResponse event)
    {
        if (event.isAvailable())
        {
            Intent intent = new Intent(SignInActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            inputNumberEditText.getText().clear();
        }
    }
}