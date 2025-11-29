package pages;

import annotations.Path;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

@Path("/uslugi-kompaniyam")
public class CompanyServicesPage extends AbsBasePage<CompanyServicesPage> {

  private final Locator detailedBtn = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Подробнее"));

  public CustomCoursesPage selectDetailed() {
    return clickLinkByNewTab(detailedBtn, CustomCoursesPage.class);
  }
}
