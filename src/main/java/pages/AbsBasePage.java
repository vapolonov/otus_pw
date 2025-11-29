package pages;

import annotations.Path;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Mouse;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.BoundingBox;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;
import common.AbsCommon;
import exceptions.PageCreateException;
import extension.UIExtension;

public abstract class AbsBasePage<T> extends AbsCommon {
  protected Page page;
  private String baseUrl = System.getProperty("base.url", "https://otus.ru");

  public AbsBasePage() {
    this.page = UIExtension.PAGE.get();
  }

  private String getPath() {
    Class<? extends AbsBasePage> clazz = this.getClass();
    if (clazz.isAnnotationPresent(Path.class)) {
      Path path = clazz.getDeclaredAnnotation(Path.class);
      return path.value();
    }
    return "";
  }

  public T open() {
    page.navigate(this.baseUrl + this.getPath());
    page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    return (T) this;
  }

  public T waitForVisible(Locator locator) {
    locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    return (T) this;
  }

  public T waitForHidden(Locator locator) {
    locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
    return (T) this;
  }

  public T waitForDetach(Locator locator) {
    locator.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.DETACHED));
    return (T) this;
  }

  public void implicitWait() {
    page.waitForTimeout(3000);
  }

  public void click(Locator locator) {
    locator.scrollIntoViewIfNeeded();
    waitForVisible(locator);
    locator.click();
  }

  public <P extends AbsBasePage<P>> P clickLinkByNewTab(Locator locator, Class<P> pageClass) {
    Page newTab = page.waitForPopup(() -> click(locator));
    newTab.waitForLoadState();
    newTab.bringToFront();
    UIExtension.PAGE.set(newTab);

    try {
      return pageClass.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new PageCreateException("Не удалось создать экземпляр страницы: " + pageClass.getName(), e);
    }
  }

  public void moveSlider(Locator locator, int slidesCount) {
    int slideWidth = 320;
    int slideMargin = 40;
    int step = slideWidth + slideMargin;
    int moveByX = -step * slidesCount;

    waitForVisible(locator);
    locator.scrollIntoViewIfNeeded();

    BoundingBox box = locator.boundingBox();

    double centerX = box.x + box.width / 2;
    double centerY = box.y + box.height / 2;

    page.mouse().move(centerX, centerY);
    page.mouse().down();
    page.mouse().move(centerX + moveByX, centerY, new Mouse.MoveOptions().setSteps(10));
    page.mouse().up();
  }
}
