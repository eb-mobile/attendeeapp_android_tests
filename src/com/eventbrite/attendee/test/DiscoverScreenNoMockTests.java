package com.eventbrite.attendee.test;

import android.test.ActivityInstrumentationTestCase2;

import com.eventbrite.attendee.R;
import com.eventbrite.attendee.activities.MainTabActivity;
import com.eventbrite.attendee.activities.SearchResultListActivity;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.jayway.android.robotium.solo.Solo;

public class DiscoverScreenNoMockTests extends ActivityInstrumentationTestCase2<MainTabActivity> {

	// member variables
	private Solo s;
	private WireMockServer w;

	// constructor
	public DiscoverScreenNoMockTests() {
		super(MainTabActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		s = new Solo(getInstrumentation(), getActivity());
		s.clickOnButton(s.getString(R.string.main_tab_find_events_new));

	}

	@Override
	protected void tearDown() throws Exception {
		s.finishOpenedActivities();
	}

	//*****Requires release build, location service enabled and not logged in with facebook

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

	// Test section headers like "TOMORROW", "THIS WEEK", ... are display
	public void testDiscoverScreenSectionHeader() {
		assertTrue(s.waitForText(s.getString(R.string.search_result_list_section_header_tomorrow).toUpperCase()));
		assertTrue(s.searchText(s.getString(R.string.search_result_list_section_header_this_week).toUpperCase()));
		assertTrue(s.searchText(s.getString(R.string.search_result_list_section_header_this_weekend).toUpperCase()));
		assertTrue(s.searchText(s.getString(R.string.search_result_list_section_header_next_week).toUpperCase()));
		assertTrue(s.searchText(s.getString(R.string.search_result_list_section_header_this_month).toUpperCase()));
		assertTrue(s.searchText(s.getString(R.string.search_result_list_section_header_upcoming).toUpperCase()));
	}

	// XX Test search bar hints
	public void testSearchBarHints() {
		s.clickOnView(s.getView(R.id.abs__search_button));
		s.clickOnView(s.getView(R.id.abs__search_button));
		assertTrue(s.searchText(s.getString(R.string.event_search_hint)));
		assertTrue(s.searchText(s.getString(R.string.event_search_default_location_new)));
		s.hideSoftKeyboard();
		s.goBack();
	}

	public void testConnectWithFacebookButton() {
		s.waitForText(s.getString(R.string.facebook_connect_button_connect_to_facebook));
		s.clickOnText(s.getString(R.string.facebook_connect_button_connect_to_facebook));
		assertTrue(s.waitForActivity(com.facebook.LoginActivity.class));
	}
}