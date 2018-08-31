package com.example.dagger2_hello_world.greeting;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.dagger2_hello_world.R;
import com.example.model.Greeting;

import javax.inject.Inject;

import dagger.Module;

@Module
public class GreetingActivity extends AppCompatActivity implements Presenter.View {

    @Inject
    Presenter greetingPresenter;
    private TextView mainTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DaggerGreetingMvpComponent.builder()
                .greetingMvpModule(new GreetingMvpModule(this))
                .build()
                .inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);
        mainTextView = (TextView) findViewById(R.id.main_text);
    }

    @Override
    protected void onResume() {
        super.onResume();
        greetingPresenter.updateGreeting();
    }

    @Override
    public void setGreeting(@NonNull Greeting greeting) {
        mainTextView.setText(greeting.getGreetingText());
    }
}
