package factory;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public class BrowserFactory {
  private Playwright playwright;
  private String browserType = System.getProperty("browser.type", "chrome");

  public BrowserFactory(Playwright playwright) {
    this.playwright = playwright;
  }

  public Browser create() {
    return switch (browserType) {
      case "chrome" -> playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
      case "firefox" -> playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
      default -> null;
    };
  }
}
