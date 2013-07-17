package com.eventbrite.attendee.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import com.eventbrite.attendee.activities.CreatePasswordActivity;
import com.jayway.android.robotium.solo.Solo;

public class CreatePasswordTest extends
		ActivityInstrumentationTestCase2<CreatePasswordActivity> {

	private Solo solo;

	public CreatePasswordTest() {
		super("com.eventbrite.attendee.activities",
				CreatePasswordActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		solo = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void testUnmatchedPassword() {
		solo.enterText(1, "eb1234");
		solo.enterText(2, "eb1235");

		Button button = solo.getButton("Set Password");
		assertFalse("Set Password button is incorrectly enabled",
				button.isEnabled());
	}

	public void testMatchedPassword() {
		solo.enterText(1, "eb1234");
		solo.enterText(2, "eb1234");

		Button button = solo.getButton("Set Password");
		assertTrue("Set Password button is incorrectly disabled",
				button.isEnabled());
	}

	public void testPasswordRestFailure() {
		solo.enterText(1, "eb1234");
		solo.enterText(2, "eb1234");

		solo.clickOnButton("Set Password");
		assertTrue(
				"Password Reset Failed dialog hasn't shown when no email address is provided.",
				solo.searchText("Password Reset Failed"));

	}
}
