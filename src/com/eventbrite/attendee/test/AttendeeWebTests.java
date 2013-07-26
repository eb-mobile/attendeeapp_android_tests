package com.eventbrite.attendee.test;

import android.test.ActivityInstrumentationTestCase2;
import android.view.inputmethod.EditorInfo;

import com.eventbrite.attendee.R;
import com.eventbrite.attendee.activities.MainTabActivity;
import com.eventbrite.attendee.activities.SearchResultListActivity;
import com.eventbrite.ngapi.EventbriteConstants.BuildType;
import com.eventbrite.ngapi.ReleaseBuildConfig;
import com.jayway.android.robotium.solo.Solo;

public class AttendeeWebTests extends ActivityInstrumentationTestCase2<MainTabActivity> {

	// member variables
	private Solo s;

	public AttendeeWebTests() {
		super(MainTabActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		if (ReleaseBuildConfig.BUILD_TYPE != BuildType.MOCK_BUILD) {
			super.setUp();
			s = new Solo(getInstrumentation(), getActivity());
		} else {
			fail("Test Aborted! Requires com.eventbrite.ngapi.ReleaseBuildConfig.BUILD_TYPE != BuildType.MOCK_BUILD");
			tearDown();
		}
	}

	@Override
	protected void tearDown() throws Exception {
		s.finishOpenedActivities();
	}

	// private utility methods
	private void login(String username, String password) {
		// click on MY TICKETS tab
		s.clickOnButton(s.getString(R.string.main_tab_my_tickets));

		// login with username
		s.enterText(0, username);
		s.clickOnButton(s.getString(R.string.enter_password_continue_button_text));
		s.waitForDialogToClose(10000);
		// enter valid password
		s.enterText(1, password);
		s.clickOnButton(s.getString(R.string.enter_password_login_button_text));
		s.waitForDialogToClose(10000);
		s.waitForText(s.getString(R.string.list_event_upcoming_events_tab_name_new));
	}

	private void logout() {
		s.clickOnButton(s.getString(R.string.main_tab_my_tickets));
		s.clickOnActionBarItem(R.id.menu_item_settings);
		s.clickOnMenuItem(s.getString(R.string.settings_log_out_title_new));
		s.clickOnButton(s.getString(R.string.logout_dialog_logout_button));
		assertTrue(s.searchText(s.getString(R.string.enter_password_description)));
	}

	// Test browse event category menu
	public void testBrowseCategoryMenu() {
		s.clickOnText(s.getString(R.string.menu_item_browse));
		assertTrue(s.searchText(s.getString(R.string.search_filter_category_array_music)));
		assertTrue(s.searchText(s.getString(R.string.search_filter_category_array_conferences)));
		assertTrue(s.searchText(s.getString(R.string.search_filter_category_array_food)));
		assertTrue(s.searchText(s.getString(R.string.search_filter_category_array_festivals)));
		assertTrue(s.searchText(s.getString(R.string.search_filter_category_array_classs)));
		assertTrue(s.searchText(s.getString(R.string.search_filter_category_array_business)));
		assertTrue(s.searchText(s.getString(R.string.search_filter_category_array_performance_arts)));
		assertTrue(s.searchText(s.getString(R.string.search_filter_category_array_fundraisers)));
		assertTrue(s.searchText(s.getString(R.string.search_filter_category_array_outdoors)));
		assertTrue(s.searchText(s.getString(R.string.search_filter_category_array_networking)));
	}

	// Test browse event category header
	public void testCategoryHeaderHasEvents() {
		s.clickOnText(s.getString(R.string.menu_item_browse));
		s.clickOnText(s.getString(R.string.search_filter_category_array_music));
		s.assertCurrentActivity("Not in SearchResultListActivity", SearchResultListActivity.class);
		assertTrue(s.searchText(s.getString(R.string.search_filter_category_array_music)));
		String expectedText1 = "\\d+ near you, " + s.getString(R.string.search_filter_category_array_music) + ", by Date";
		assertTrue(s.waitForText(expectedText1));
		s.goBack();
	}

	// Test discover around me
	public void testDiscoverAroundMe() {
		s.waitForText(s.getString(R.string.list_event_date_at_time));
		s.clickOnButton(s.getString(R.string.search_filter_category_array_around_you));
		assertTrue(s.searchText(s.getString(R.string.search_filter_category_array_around_you)));
		assertTrue(s.searchText("\\d+ near you, 5 mi, by Date"));
		s.goBack();
	}

	public void testDiscoverThisWeekend() {
		s.waitForText(s.getString(R.string.list_event_date_at_time));
		s.clickOnButton(s.getString(R.string.search_filter_category_array_this_weekend));
		assertTrue(s.searchText(s.getString(R.string.search_filter_category_array_around_you)));
		assertTrue(s.searchText("\\d+ near you, 5 mi, by Date"));
	}

	// Test section headers like "TOMORROW", "THIS WEEK", ... are display
	// Not reliable
	public void testDiscoverScreenSectionHeader() {
		s.waitForText(s.getString(R.string.list_event_date_at_time));
		assertTrue(s.waitForText(s.getString(R.string.search_result_list_section_header_tomorrow).toUpperCase()));
		assertTrue(s.searchText(s.getString(R.string.search_result_list_section_header_this_week).toUpperCase()));
		assertTrue(s.searchText(s.getString(R.string.search_result_list_section_header_this_weekend).toUpperCase()));
		assertTrue(s.searchText(s.getString(R.string.search_result_list_section_header_next_week).toUpperCase()));
		assertTrue(s.searchText(s.getString(R.string.search_result_list_section_header_this_month).toUpperCase()));
		assertTrue(s.searchText(s.getString(R.string.search_result_list_section_header_upcoming).toUpperCase()));
	}

	// Test connect with facebook button
	public void testConnectWithFacebookButton() {
		s.waitForText(s.getString(R.string.list_event_date_at_time));
		s.clickOnText(s.getString(R.string.facebook_connect_button_connect_to_facebook));
		assertTrue(s.waitForActivity(com.facebook.LoginActivity.class));
		s.waitForDialogToClose(10000);
		s.enterText(0, "raymond@evbqa.com");
		s.enterText(1, "qwert12345");
		s.clickOnButton(s.getString(com.facebook.android.R.string.com_facebook_loginview_log_in_button));
		s.clickOnButton(s.getString(com.facebook.android.R.string.com_facebook_dialogloginactivity_ok_button));
		s.waitForDialogToClose(10000);
		assertTrue(s.searchText(s.getString(R.string.facebook_connect_button_invite_friends_new)));
		assertTrue(s.searchText("going"));

		// turn off facebook
		s.clickOnButton(s.getString(R.string.main_tab_my_tickets));
		s.clickOnMenuItem(s.getString(R.string.menu_item_settings));
		s.clickOnText(s.getString(R.string.settings_social_facebook_name));
		s.goBack();
	}

	// Submit "Log in" without any text entered for email
	public void testLoginNoEmail() {
		// enter next/enter key
		s.clickOnButton(s.getString(R.string.main_tab_my_tickets));
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
		s.clickOnButton(s.getString(R.string.main_tab_my_tickets));
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
		s.clickOnButton(s.getString(R.string.main_tab_my_tickets));
		s.enterText(0, "oyrtu90nhjc@gmail.com");
		s.clickOnButton(s.getString(R.string.enter_password_continue_button_text));
		s.waitForDialogToClose(10000);
		assertTrue(s.searchText(s.getString(R.string.enter_password_wrong_email)));
	}

	// Submit "Log in" valid in email field but with wrong password
	public void testLoginValidEmailWrongPassword() {
		s.clickOnButton(s.getString(R.string.main_tab_my_tickets));
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
		s.clickOnButton(s.getString(R.string.main_tab_my_tickets));
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
		logout();
	}

	// Login with an account with no events
	public void testAccountWithNoEvents() {
		login("raymond+0@evbqa.com", "qwert");

		// look for text you have no events...
		assertTrue(s.searchText(s.getString(R.string.empty_order_title)));

		logout();
	}

	// Test press cancel during logout dialog
	public void testLogoutCancel() {
		login("raymond+0@evbqa.com", "qwert");

		s.clickOnButton(s.getString(R.string.main_tab_my_tickets));
		s.clickOnActionBarItem(R.id.menu_item_settings);
		s.clickOnMenuItem(s.getString(R.string.settings_log_out_title_new));

		// press cancel in logout dialog
		s.clickOnButton(s.getString(R.string.logout_dialog_cancel_button));
		assertTrue(s.searchText(s.getString(R.string.settings_log_out_title_new)));

		// logout
		s.clickOnMenuItem(s.getString(R.string.settings_log_out_title_new));
		s.clickOnButton(s.getString(R.string.logout_dialog_logout_button));
		assertTrue(s.searchText(s.getString(R.string.enter_password_description)));
	}
}
