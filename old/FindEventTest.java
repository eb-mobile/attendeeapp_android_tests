package com.eventbrite.attendee.test;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;

import com.eventbrite.attendee.R;
import com.eventbrite.attendee.activities.MainTabActivity;
import com.eventbrite.attendee.activities.SearchFilterActivity;
import com.jayway.android.robotium.solo.Solo;

public class FindEventTest extends
		ActivityInstrumentationTestCase2<MainTabActivity> {

	private Solo solo;

	public FindEventTest() {
		super("com.eventbrite.attendee.activities", MainTabActivity.class);
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

	public void testTapOnSearchFieldLocationFieldVisible() {
		solo.clickOnEditText(0);

		View locationEditText = solo
				.getView(R.id.edittext_search_custom_component_location);
		assertTrue(locationEditText.getVisibility() == View.VISIBLE);
	}

	public void testTapOnSearchFieldSearchButtonVisible() {
		solo.clickOnEditText(0);
		View searchButton = solo
				.getView(R.id.button_search_custom_component_search);
		assertTrue(searchButton.getVisibility() == View.VISIBLE);
	}

	public void testBackButtonCollapseSearchComponent() {
		solo.clickOnEditText(0);
		View searchButton = solo
				.getView(R.id.button_search_custom_component_search);
		assertTrue(searchButton.getVisibility() == View.VISIBLE);
		// This is to dismiss the keyboard
		solo.goBack();
		// This is to collapse the component
		solo.goBack();
		// solo.sleep(2000);
		assertTrue(searchButton.getVisibility() != View.VISIBLE);
	}

	public void testTapOnQueryEditTextDisableTonightButton() {
		solo.clickOnEditText(0);
		View searchTonightButton = solo
				.getView(R.id.button_search_event_fragment_search_tonight);
		assertFalse(searchTonightButton.isEnabled());
	}

	public void testTapOnQueryEditTextDisableNearMeButton() {
		solo.clickOnEditText(0);

		View searchNearMeButton = solo
				.getView(R.id.button_search_event_fragment_search_near_me);
		assertFalse(searchNearMeButton.isEnabled());
	}

	public void testPerformNearMeSearch() {
		Button searchNearMeButton = (Button) solo
				.getView(R.id.button_search_event_fragment_search_near_me);
		solo.clickOnView(searchNearMeButton);

		View shortcutButtonLayout = solo
				.getView(R.id.scrollview_search_event_fragment_quick_search_buttons);
		assertFalse(shortcutButtonLayout.getVisibility() == View.VISIBLE);
	}

	public void testPerformTonightSearch() {
		Button searchTonightButton = (Button) solo
				.getView(R.id.button_search_event_fragment_search_tonight);
		solo.clickOnView(searchTonightButton);

		View shortcutButtonLayout = solo
				.getView(R.id.scrollview_search_event_fragment_quick_search_buttons);
		assertFalse(shortcutButtonLayout.getVisibility() == View.VISIBLE);
	}

	public void testPerformKeywordQuerySearch() {
		solo.clickOnEditText(0);
		solo.enterText(0, "food");
		View searchButton = solo
				.getView(R.id.button_search_custom_component_search);
		solo.clickOnView(searchButton);
		View shortcutButtonLayout = solo
				.getView(R.id.scrollview_search_event_fragment_quick_search_buttons);
		solo.sleep(1000);
		assertFalse(shortcutButtonLayout.getVisibility() == View.VISIBLE);
	}

	public void testTabBarGoneInResultView() {
		Button searchTonightButton = (Button) solo
				.getView(R.id.button_search_event_fragment_search_tonight);
		solo.clickOnView(searchTonightButton);

		View tabbar = solo.getView(R.id.linearlayout_main_tab_activity_tab_bar);
		assertFalse(tabbar.getVisibility() == View.VISIBLE);
	}

	public void testLaunchSearchFilterActivity() {
		solo.clickOnActionBarItem(R.id.menu_item_search_events_settings);

		solo.assertCurrentActivity("Search filter activity hasn't be launched",
				SearchFilterActivity.class);
	}

}
