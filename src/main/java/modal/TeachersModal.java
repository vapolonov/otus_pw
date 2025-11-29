package modal;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.AriaRole;
import pages.AbsBasePage;


public class TeachersModal extends AbsBasePage<TeachersModal> {
  private final Locator teacherCard = page.locator("#__PORTAL__").locator("div.full-phone"),
      toRightBtn = teacherCard.locator(page.getByRole(AriaRole.BUTTON)).nth(2),
      toLeftBtn = teacherCard.locator(page.getByRole(AriaRole.BUTTON)).nth(1),
      closeBtn = teacherCard.locator(page.getByRole(AriaRole.BUTTON)).first(),
      teacherName = teacherCard.locator("h3");

  public TeachersModal checkTeachersModalVisible() {
    waitForVisible(teacherCard);
    return this;
  }

  public String checkTeacherName() {
    return getTeacherName(3);
  }

  public TeachersModal clickLeftBtn() {
    click(toLeftBtn);
    return this;
  }

  public TeachersModal clickRightBtn() {
    click(toRightBtn);
    return this;
  }

  public String getTeacherName(int index) {
    waitForVisible(teacherName.nth(index));
    return teacherName.nth(index).textContent();
  }
}
