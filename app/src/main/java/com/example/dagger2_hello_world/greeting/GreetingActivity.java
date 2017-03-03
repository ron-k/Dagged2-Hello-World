package com.example.dagger2_hello_world.greeting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.dagger2_hello_world.R;
import com.example.model.Greeting;

public class GreetingActivity extends AppCompatActivity implements Presentor.View {

    private TextView mainTextView;
    private Presentor greetingPresentor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);
        mainTextView = (TextView) findViewById(R.id.main_text);
        greetingPresentor = FeatureModule.getPresentor(this, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        greetingPresentor.onActivityResumed();
    }

    @Override
    public void updateGreeting(@NonNull Greeting greeting) {
        mainTextView.setText(greeting.getGreetingText());
    }
}
