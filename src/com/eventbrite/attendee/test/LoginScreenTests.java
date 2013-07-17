package com.eventbrite.attendee.test;

import android.test.ActivityInstrumentationTestCase2;
import android.view.inputmethod.EditorInfo;

import com.eventbrite.attendee.R;
import com.eventbrite.attendee.activities.MainTabActivity;
import com.jayway.android.robotium.solo.Solo;

public class LoginScreenTests extends
ActivityInstrumentationTestCase2<MainTabActivity> {

	// member variables
	private Solo s;

	// constructor
	public LoginScreenTests() {
		super(MainTabActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		s = new Solo(getInstrumentation(), getActivity());
		// click on MY TICKETS tab
		s.clickOnButton(s.getString(R.string.main_tab_my_tickets));
	}

	@Override
	protected void tearDown() throws Exception {
		s.finishOpenedActivities();
	}

	// Submit "Log in" without any text entered for email
	public void testLoginNoEmail() {
		// enter next/enter key
		s.getCurrentActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				s.getEditText(0).onEditorAction(EditorInfo.IME_ACTION_NEXT);
			}
		});
		assertTrue(s.searchText(s.getString(R.string.enter_password_not_valid_email_address)));
	}

	// Submit "Log in" with invalid email address which is not a valid email format
	public void testLoginInvalidEmailFormat() {
		// enter garbage text for email
		s.enterText(0, "oyrtu90nhjc");
		// enter next/enter key
		s.getCurrentActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				s.getEditText(0).onEditorAction(EditorInfo.IME_ACTION_NEXT);
			}
		});
		assertTrue(s.searchText(s.getString(R.string.enter_password_not_valid_email_address)));
	}

	// Submit "Log in" with unrecognized email address which is a valid email format
	public void testLoginUnrecognizedEmail() {
		s.enterText(0, "oyrtu90nhjc@gmail.com");
		s.clickOnButton(s.getString(R.string.enter_password_continue_button_text));
		s.waitForDialogToClose(10000);
		assertTrue(s.searchText(s.getString(R.string.enter_password_wrong_email)));
	}

	// Submit "Log in" valid in email field but with wrong password
	public void testLoginValidEmailWrongPassword() {
		// enter a valid email, continue using continue button
		s.enterText(0, "raymond+0@evbqa.com");
		s.clickOnButton(s.getString(R.string.enter_password_continue_button_text));
		s.waitForDialogToClose(10000);
		assertTrue(s.searchText(s.getString(R.string.enter_password_password_hint)));

		// enter wrong password, continue using "Log in" button
		s.enterText(1, "wrongPassword");
		s.clickOnButton(s.getString(R.string.enter_password_login_button_text));
		assertTrue(s.searchText(s.getString(R.string.enter_password_wrong_email_password_dialog_text)));

		s.clickOnButton(s.getString(R.string.ok));

		// start over from top
		s.clearEditText(0);

		// enter a valid email, continue using next button on keyboard
		s.enterText(0, "raymond+0@evbqa.com");
		s.getCurrentActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				s.getEditText(0).onEditorAction(EditorInfo.IME_ACTION_NEXT);
			}
		});
		s.waitForDialogToClose(10000);
		assertTrue(s.searchText(s.getString(R.string.enter_password_password_hint)));

		// enter wrong password, continue using next button on keyboard
		s.enterText(1, "wrongPassword");
		s.getCurrentActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				s.getEditText(1).onEditorAction(EditorInfo.IME_ACTION_DONE);
			}
		});
		assertTrue(s.searchText(s.getString(R.string.enter_password_wrong_email_password_dialog_text)));
	}

	// Log in with valid email and valid password and then log out
	public void testLoginAndLogout() {
		// enter a valid email
		s.enterText(0, "raymond+0@evbqa.com");
		s.clickOnButton(s.getString(R.string.enter_password_continue_button_text));
		s.waitForDialogToClose(10000);
		// enter valid password
		s.enterText(1, "qwert");
		s.clickOnButton(s.getString(R.string.enter_password_login_button_text));
		s.waitForDialogToClose(10000);

		// logged in
		// look for upcoming and past events buttons
		assertTrue(s.searchButton(s.getString(R.string.list_event_upcoming_events_tab_name_new))
				&& s.searchButton(s.getString(R.string.list_event_past_events_tab_name)));

		// logout
		s.clickOnActionBarItem(R.id.menu_item_settings);
		s.clickOnMenuItem(s.getString(R.string.settings_log_out_title_new));
		s.clickOnButton(s.getString(R.string.logout_dialog_logout_button));
		assertTrue(s.searchText(s.getString(R.string.enter_password_description)));
	}
}