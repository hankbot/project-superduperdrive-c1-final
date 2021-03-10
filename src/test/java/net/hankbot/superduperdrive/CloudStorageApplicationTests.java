package net.hankbot.superduperdrive;

import org.junit.jupiter.api.*;

class CloudStorageApplicationTests extends TestBase {

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

}
