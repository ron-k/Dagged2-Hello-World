package com.example.dagger2_hello_world.greeting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.dagger2_hello_world.R;
import com.example.model.Greeting;

public class GreetingActivity extends AppCompatActivity {

    private TextView mainTextView;
    private GreetingModel greetingModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);
        mainTextView = (TextView) findViewById(R.id.main_text);
        greetingModel = new GreetingModel(this, UserAgeModel.create(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        bindModelToView();
    }

    private void bindModelToView() {
        Greeting greeting = greetingModel.getGreeting();
        mainTextView.setText(greeting.getGreetingText());
    }
}
