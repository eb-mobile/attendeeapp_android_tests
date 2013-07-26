package com.eventbrite.attendee.test;

import android.test.ActivityInstrumentationTestCase2;

import com.eventbrite.attendee.activities.MainTabActivity;
import com.eventbrite.ngapi.EventbriteConstants.BuildType;
import com.eventbrite.ngapi.ReleaseBuildConfig;
import com.jayway.android.robotium.solo.Solo;

public class AttendeeMockTests extends ActivityInstrumentationTestCase2<MainTabActivity> {
	// member variables
	private Solo s;

	public AttendeeMockTests() {
		super(MainTabActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		// Requires running mock server
		if (ReleaseBuildConfig.BUILD_TYPE == BuildType.MOCK_BUILD) {
			super.setUp();
			s = new Solo(getInstrumentation(), getActivity());
		} else {
			fail("Test Aborted! Requires com.eventbrite.ngapi.ReleaseBuildConfig.BUILD_TYPE == BuildType.MOCK_BUILD");
			tearDown();
		}
	}

	@Override
	protected void tearDown() throws Exception {
		s.finishOpenedActivities();
	}

}
