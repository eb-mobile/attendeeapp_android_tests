package com.eventbrite.attendee.test;

import android.test.ActivityInstrumentationTestCase2;

import com.eventbrite.attendee.R;
import com.eventbrite.attendee.activities.MainTabActivity;
import com.jayway.android.robotium.solo.Solo;

public class MyTicketsScreenTests extends ActivityInstrumentationTestCase2<MainTabActivity> {

	// member variables
	private Solo s;

	// constructor
	public MyTicketsScreenTests() {
		super(MainTabActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// set up member variables
		s = new Solo(getInstrumentation(), getActivity());
	}

	@Override
	protected void tearDown() throws Exception {
		s.finishOpenedActivities();
	}

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
		s.sleep(5000);
	}
	
	private void logout() {
		s.clickOnButton(s.getString(R.string.main_tab_my_tickets));
		s.clickOnActionBarItem(R.id.menu_item_settings);
		s.clickOnMenuItem(s.getString(R.string.settings_log_out_title_new));
		s.clickOnButton(s.getString(R.string.logout_dialog_logout_button));
		assertTrue(s.searchText(s.getString(R.string.enter_password_description)));
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
	
	// Testing an account with upcoming events
	public void testUpcomingHasEvents() {

	}
	
	// Testing the scroll down refresh 
	public void testUpcomingScrollDownRefresh() {

	}
}
