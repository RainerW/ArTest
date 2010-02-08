package de.bitnoise.sample;

import junit.framework.Assert;

public class ASampleBase {

	protected void isLoggedIn() {
		assertTextPresent("logged in");
		assertTextPresent("success");
	}

	private void assertTextPresent(String string) {
		Assert.assertEquals("success", string);
	}
}
