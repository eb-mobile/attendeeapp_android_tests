package com.eventbrite.attendee.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.eventbrite.attendee.activities.MainTabActivity;
import com.jayway.android.robotium.solo.Solo;

public class LoginAndLogOutTest extends
		ActivityInstrumentationTestCase2<MainTabActivity> {

	// member variables
	private Solo solo;

	// constructor
	public LoginAndLogOutTest() {
		super("com.eventbrite.attendee.activities", MainTabActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		solo.clickOnButton("MY TICKETS");	// get to MY TICKETS tab
		//solo.waitForText("Enter the email");
	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}
	
	// user enters malformed email address e.g. sdfjkhadsjkf
	public void testMalformedEmail() {
		solo.enterText(0, "sdfjkhadsjkf\n");
		
	}

	public void testLogInAndLogOut() {

		final EditText loginEditText = (EditText) solo
				.getView(com.eventbrite.attendee.R.id.edittext_enter_password_email);

		loginEditText.post(new Runnable() {
			@Override
			public void run() {
				loginEditText.setText("dongjin@eventbrite.com");
			}
		});

		solo.clickOnButton("Continue");

		solo.waitForDialogToClose(120000);

		if (solo.searchText("I forgot my password")) {
			solo.enterText(2, "");
			solo.clickOnButton("Log In");
		}

		solo.waitForDialogToClose(120000);

		solo.assertCurrentActivity(
				"Login failed. Can't go to my tickets fragment",
				MainTabActivity.class);

		assertTrue("Event loading hasn't finished in 2 minutes.",
				solo.waitForDialogToClose(120000));

		// solo.clickOnActionBarItem(com.eventbrite.attendee.R.id.menu_item_);

		solo.waitForText("Don't Go");
		//
		solo.clickOnButton("Log Out");
	}
}
