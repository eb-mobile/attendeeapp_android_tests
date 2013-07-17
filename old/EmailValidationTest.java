package com.eventbrite.attendee.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

import com.eventbrite.attendee.activities.EmailSentConfirmationActivity;
import com.eventbrite.attendee.activities.MainTabActivity;
import com.jayway.android.robotium.solo.Solo;

/**
 * @author dongjin
 * 
 */
public class EmailValidationTest extends
		ActivityInstrumentationTestCase2<MainTabActivity> {

	private Solo solo;

	public EmailValidationTest() {
		super("com.eventbrite.attendee.activities", MainTabActivity.class);

	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		solo = new Solo(getInstrumentation(), getActivity());
		solo.clickOnButton("MY TICKETS");
		solo.waitForText("Enter the email", 1, 5000);

		// if (!solo.getCurrentActivity().getClass().getName()
		// .contains("EnterPasswordActivity")) {
		//
		// // The current activity isn't the login one
		// assertTrue("Event loading hasn't finished in 5 minutes.",
		// solo.waitForDialogToClose(300000));
		// solo.clickOnMenuItem("Log Out");
		// solo.clickOnButton("Log Out");
		// }
		//
		// solo.clearEditText(0);

	}

	@Override
	protected void tearDown() throws Exception {
		solo.finishOpenedActivities();
	}

	public void testChangeEmailTriggerBack() {
		solo.enterText(1, "dongjin@eventbrite.com");
		solo.clickOnButton("Continue");

		solo.enterText(1, "d");

		assertFalse(
				"Reset screen failed. Changing email address didn't restore the screen back to the initial state.",
				solo.searchText("I forgot my password!"));
		solo.clickOnButton("OK");
	}

	public void testRegisteredEmail() {
		solo.enterText(1, "dongjin@eventbrite.com");
		solo.clickOnButton("Continue");

		if (solo.searchText("I forgot my password")) {
			solo.goBack();
		} else {
			fail("Can't validate email");
		}
	}

	public void testUnusedEmail() {
		solo.enterText(1, "dongjin+test_unused@eventbrite.com");
		solo.clickOnButton("Continue");

		if (solo.searchText("We don't recognize that email address.")) {
			solo.clickOnButton("OK");
		} else {
			fail("No whoops! dialog popping up for unused email.");
		}
	}

	public void testUnregisteredUser() {
		solo.enterText(1, "dongjin+test_unregistered@eventbrite.com");
		solo.clickOnButton("Continue");

		solo.assertCurrentActivity(
				"Unregistered user test failed. Can't go to email sent confirmation activity",
				EmailSentConfirmationActivity.class);
	}

	// public void testChangePassword() {
	// solo.enterText(0, "dongjin@eventbrite.com");
	// solo.clickOnButton("Continue");
	//
	// solo.clickOnText("I forgot my password");
	//
	// solo.assertCurrentActivity(
	// "Change password initiative failed. Can't go to email sent confirmation activity",
	// EmailSentConfirmationActivity.class);
	// }

	public void testInvalidEmail1() {
		solo.enterText(1, "dongjin@eventbrite.");
		Button button = solo.getButton("Continue");
		assertFalse(
				"Email validation failed. Invalid email address \"dongjin@eventbrite.\" enabled the Continue button.",
				button.isEnabled());
	}

	public void testInvalidEmail2() {
		solo.enterText(1, "dongjin@eventbrite.com@");
		Button button = solo.getButton("Continue");
		assertTrue(button.isEnabled());

		solo.clickOnButton("Continue");
		assertTrue(
				"Email validation failed. Invalid email address \"dongjin@eventbrite.com@\" didn't trigger a warning dialog.",
				solo.searchText("Please enter a valid email address"));
	}

	public void testRegisteredAllCapitalEmail() {
		solo.enterText(1, "DONGJIN@EVENTBRITE.COM");
		solo.clickOnButton("Continue");

		assertTrue(
				"Email validation failed. Email address in upper case failed to pass validation.",
				solo.searchText("I forgot my password"));
	}

	public void testEmptyPasswordField() {
		solo.enterText(1, "dongjin@eventbrite.com");
		solo.clickOnButton("Continue");

		Button button = solo.getButton("Log In");

		assertFalse(
				"Password validation failed. Empty password enabled Log In button.",
				button.isEnabled());
	}

	public void testNonEmptyPasswordField() {
		solo.enterText(1, "dongjin@eventbrite.com");
		solo.clickOnButton("Continue");
		solo.enterText(2, "1");

		Button button = solo.getButton("Log In");

		assertTrue(
				"Password validation failed. Non-empty password didn't enable the Log In button.",
				button.isEnabled());
	}

	public void testIncorrectPasswordField() {
		solo.enterText(1, "dongjin@eventbrite.com");
		solo.clickOnButton("Continue");
		solo.enterText(2, "1");

		solo.clickOnButton("Log In");

		assertTrue(
				"Athentication failed. Unmatch user name and password didn't pop up a warning dialog",
				solo.searchText("Check your email address or password for typos."));
	}

	public void testChangeEmailClearPassword() {
		solo.enterText(1, "dongjin@eventbrite.com");
		solo.clickOnButton("Continue");
		solo.waitForDialogToClose(120000);
		solo.enterText(1, "d");
		solo.clearEditText(1);
		solo.enterText(1, "dongjin@eventbrite.com");

		solo.clickOnButton("Continue");
		solo.waitForDialogToClose(120000);

		EditText passwordInputBox = (EditText) solo
				.getView(com.eventbrite.attendee.R.id.edittext_enter_password_password);

		assertTrue(
				"Reset screen failed. Changing email address didn't empty password field.",
				passwordInputBox.getText().toString().length() == 0);
		solo.goBack();
	}
}
