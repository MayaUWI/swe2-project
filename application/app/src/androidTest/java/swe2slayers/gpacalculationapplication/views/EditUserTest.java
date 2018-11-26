package swe2slayers.gpacalculationapplication.views;


import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import androidx.test.rule.ActivityTestRule;
import androidx.test.filters.LargeTest;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class EditUserTest {
    @Rule
    public ActivityTestRule<EditUser> editU= new ActivityTestRule<>(EditUser.class);

        private String userName_Typed="test@test.com";
    @Test
    public void Test1(){

    }
}