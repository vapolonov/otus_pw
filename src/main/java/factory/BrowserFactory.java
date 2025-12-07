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
    boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", "true"));
    int slowMo = Integer.parseInt(System.getProperty("slowMo", "100"));
    BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
        .setHeadless(isHeadless);
    if (slowMo > 0) {
      options.setSlowMo(slowMo);
    }
    return switch (browserType) {
      case "chrome" -> playwright.chromium().launch(options);
      case "firefox" -> playwright.firefox().launch(options);
      default -> null;
    };
  }
}
