package com.eventbrite.attendee.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		// $JUnit-BEGIN$
		// suite.addTestSuite(EmailValidationTest.class);
		suite.addTestSuite(LoginAndLogOutTest.class);
		// suite.addTestSuite(CreatePasswordTest.class);
		// suite.addTestSuite(ListEventTest.class);

		// $JUnit-END$
		return suite;
	}
}
