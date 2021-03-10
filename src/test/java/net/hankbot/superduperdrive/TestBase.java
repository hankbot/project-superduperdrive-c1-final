package net.hankbot.superduperdrive;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestBase {

  @LocalServerPort
  protected int port;

  protected WebDriver driver;

  @BeforeAll
  static void beforeAll() {
    WebDriverManager.firefoxdriver().setup();
  }

  @BeforeEach
  public void beforeEach() {
    this.driver = new FirefoxDriver();
  }

  @AfterEach
  public void afterEach() {
    if (this.driver != null) {
      driver.quit();
    }
  }

  protected void goToPage(String path) {
    driver.get("http://localhost:" + this.port + path);
  }

}
