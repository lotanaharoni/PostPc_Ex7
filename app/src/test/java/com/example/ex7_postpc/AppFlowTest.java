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

import java.io.StringReader;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)

public class AppFlowTest {

    MyLocalDb myLocalDb;

    @Before
    public void setup(){
        ActivityController<NewOrderActivity> activityController = Robolectric.buildActivity(NewOrderActivity.class);
        NewOrderActivity activityUnderTest = activityController.get();
        activityController.create().start().resume();
        myLocalDb = MyAppActivity.getLocalDb();
        myLocalDb.deleteSp();
    }

    @Test
    public void when_creating_new_order_ThenTheIdSavedInSp() {
        myLocalDb = MyAppActivity.getLocalDb();

        String name = "Dosha";
        int numOfPickles = 9;
        boolean isHummus = true;
        boolean isTahini = false;
        String comment = "without Ketchup";

        Assert.assertEquals("", myLocalDb.loadFromLocal());

        Order newOrder = new Order(name, numOfPickles, isHummus, isTahini, comment);

        myLocalDb.savedOrderLocally(newOrder);

        Assert.assertNotEquals("", myLocalDb.loadFromLocal());
    }
}
