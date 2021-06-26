package com.example.ex7_postpc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;
import android.widget.Button;
import android.widget.EditText;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class NewOrderActivityTest {

    Button saveButton;
    EditText picklesEditText;
    EditText nameEditText;

    @Before
    public void setup(){

        ActivityController<NewOrderActivity> activityController = Robolectric.buildActivity(NewOrderActivity.class);
        NewOrderActivity activityUnderTest = activityController.get();
        activityController.create().start().resume();
        nameEditText = activityUnderTest.findViewById(R.id.CustomerName);
        picklesEditText = activityUnderTest.findViewById(R.id.PickelsEditText);
        saveButton = activityUnderTest.findViewById(R.id.sendOrderButton);

    }

    @Test
    public void when_order_created_ThenTheTheOrderInWaitingStatus() {
        String name = "Dosha";
        int numOfPickles = 3;
        boolean isHummus = true;
        boolean isTahini = false;
        String comment = "without Ketchup";

        Order newOrder = new Order(name, numOfPickles, isHummus, isTahini, comment);

        Assert.assertEquals("waiting", newOrder.getStatus());
    }

    @Test
    public void when_creating_new_order_with_no_name_ThenToastShouldPop(){

        saveButton.performClick();

        Assert.assertEquals(ShadowToast.getTextOfLatestToast(), "Enter name");
    }

    @Test
    public void when_creating_new_order_with_illegal_pickles_ThenToastShouldPop(){
        String name = "Dosha";
        int numOfPickles = 11;

        nameEditText.setText(name);
        picklesEditText.setText(Integer.toString(numOfPickles));

        saveButton.performClick();

        Assert.assertEquals(ShadowToast.getTextOfLatestToast(), "Choose pickles from 0 to 10");
    }
}
