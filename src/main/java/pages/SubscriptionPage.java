package pages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import annotations.Path;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

@Path("/subscription")
public class SubscriptionPage extends AbsBasePage<SubscriptionPage> {

  private final Locator
      tariffCards = page.locator("//*[@id='packages']/div[2]/div"),  // 3 шт
      pageTitle = page.getByTitle("Варианты подписки"),
      detailedBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Подробнее")),
      rollUpBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Свернуть")),
      buyBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Купить")),
      notVisibleListItem = tariffCards.first().getByText("Не предусмотрены"),
      tariffList = page.locator("//*[@id=\"packages\"]/div[2]/div[1]/div[2]/div/div[2]/div");

  public SubscriptionPage checkSectionTitle() {
    pageTitle.isVisible();
    return this;
  }

  public SubscriptionPage selectBasic() {
    click(detailedBtn.first());
    waitForVisible(notVisibleListItem);
    assertThat(tariffList).hasCSS("height", "145px");
    click(rollUpBtn);
    assertThat(tariffList).hasCSS("height", "0px");
    return this;
  }

  public SubscriptionPage clickBuyBtn() {
    click(buyBtn.nth(0));
    return this;
  }
}

