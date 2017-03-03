package com.example.dagger2_hello_world.greeting;


import android.app.Activity;
import android.app.Instrumentation;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.dagger2_hello_world.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static java.util.concurrent.TimeUnit.MINUTES;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class GreetingActivityTest {

    @Rule
    public ActivityTestRule<GreetingActivity> mActivityTestRule = new ActivityTestRule<>(GreetingActivity.class);
    private UserAgeModel userAgeModel;
    private Instrumentation instrumentation;

    @BeforeClass
    public static void staticSetUp() throws Exception {
        UserAgeModel userAgeModel = UserAgeModel.create(getTargetContext());
        userAgeModel.setFirstTimeRun(userAgeModel.now());
    }

    private static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @Before
    public void setUp() throws Exception {
        userAgeModel = UserAgeModel.create(getTargetContext());
        instrumentation = getInstrumentation();
    }

    @Test
    public void greetingActivityTest() {
        ViewInteraction mainTextViewInteraction = getViewInteraction();
        mainTextViewInteraction.check(matches(withText(R.string.greeting_text_newbie)));
        userAgeModel.setFirstTimeRun(userAgeModel.now() - MINUTES.toMillis(UserAgeModel.NEWBIE_AGE_THRESHOLD_MINUTES));
        restartActivity();
        ViewInteraction textView2 = getViewInteraction();
        textView2.check(matches(withText(Matchers.startsWith(getDynamicStringPrefix()))));

    }

    private void restartActivity() {
        final Activity currentActivity = getCurrentActivity();
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                instrumentation.callActivityOnResume(currentActivity);
            }
        });
    }

    private ViewInteraction getViewInteraction() {
        return onView(withId(R.id.main_text));
    }

    @NonNull
    protected String getDynamicStringPrefix() {
        String s = InstrumentationRegistry.getTargetContext().getString(R.string.greeting_text_returning, "__REMOVE");
        s = s.replaceAll("__REMOVE.*", "");
        return s;
    }

    private Activity getCurrentActivity() {
        final Activity[] activity = new Activity[1];
        onView(isRoot()).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException noViewFoundException) {
                activity[0] = (Activity) view.getContext();
            }
        });
        return activity[0];
    }
}
