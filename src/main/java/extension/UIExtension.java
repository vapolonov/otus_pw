package extension;

import com.google.inject.Guice;
import com.microsoft.playwright.*;
import factory.BrowserFactory;
import modules.PageModule;
import org.junit.jupiter.api.extension.*;
import java.io.File;

public class UIExtension implements AfterEachCallback, AfterAllCallback, BeforeAllCallback, BeforeEachCallback {
  public static final ThreadLocal<Page> PAGE = new ThreadLocal<>();
  private Playwright playwright = null;
  private Browser browser = null;
  private BrowserContext browserContext = null;

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {
    this.playwright = Playwright.create();
    // Устанавливаем slowMo по умолчанию 100, если не указано иначе
    if (System.getProperty("slowMo") == null) {
      System.setProperty("slowMo", "100");
    }
    BrowserFactory browserFactory = new BrowserFactory(playwright);
    this.browser = browserFactory.create();
  }

  @Override
  public void beforeEach(ExtensionContext context) throws Exception {
    browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080));
    browserContext.tracing().start(new Tracing.StartOptions()
        .setScreenshots(true)
        .setSources(true)
        .setSnapshots(true));
    Page page = browserContext.newPage();
    PAGE.set(page);

    Guice.createInjector(new PageModule()).injectMembers(context.getTestInstance().get());
  }

  @Override
  public void afterEach(ExtensionContext context) throws Exception {
    if (PAGE.get() != null) {
      PAGE.get().close();
    }
    this.browserContext.tracing().stop(new Tracing.StopOptions()
        .setPath(new File(System.getProperty("trace.directory")).toPath()));
    this.browserContext.close();
    PAGE.remove();
  }

  @Override
  public void afterAll(ExtensionContext context) throws Exception {
    if (this.browser != null) {
      this.browser.close();
    }
    if (this.playwright != null) {
      this.playwright.close();
    }
  }
}
