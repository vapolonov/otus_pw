package pages;

import static org.junit.jupiter.api.Assertions.assertTrue;

import annotations.Path;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Mouse;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.BoundingBox;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path("/catalog/courses")
public class CoursesPage extends AbsBasePage<CoursesPage> {

  private final Locator
      leftSidebar = page.locator("section").nth(1),
      allAreasCheckbox = leftSidebar.getByRole(AriaRole.CHECKBOX,
          new Locator.GetByRoleOptions().setName("Все направления")),
      testingCheckbox = leftSidebar.getByRole(AriaRole.CHECKBOX,
          new Locator.GetByRoleOptions().setName("Тестирование")),
      anyLevelCheckbox = leftSidebar.getByRole(AriaRole.CHECKBOX,
          new Locator.GetByRoleOptions().setName("Любой уровень")),
      durationBlock = leftSidebar.locator(".ReactCollapse--content").nth(3),
      sliderLeftBtn = leftSidebar.locator("[role='slider']").nth(0),
      sliderRightBtn = leftSidebar.locator("[role='slider']").nth(1),
      sliderTrack = durationBlock.locator("div").nth(6),
      coursesSection = page.locator("section").nth(2),
      courseCards = coursesSection.locator("a"),
      clearFiltersBtn = leftSidebar.getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Очистить фильтры"));

  public CoursesPage checkSelectedDirection() {
    assertTrue(allAreasCheckbox.isChecked());
    return this;
  }

  public CoursesPage checkSelectedTestDirection() {
    assertTrue(testingCheckbox.isChecked());
    return this;
  }

  public CoursesPage checkSelectedLevel() {
    assertTrue(anyLevelCheckbox.isChecked());
    return this;
  }

  public CoursesPage selectDuration(int from, int to) {
    moveSliderToValue(sliderLeftBtn, sliderTrack, from);
    moveSliderToValue(sliderRightBtn, sliderTrack, to);
    return this;
  }

  public void clearFilter() {
    click(clearFiltersBtn);
  }

  private void moveSliderToValue(Locator handle, Locator track, int targetValue) {
    waitForVisible(handle);
    handle.scrollIntoViewIfNeeded();

    int min = Integer.parseInt(handle.getAttribute("aria-valuemin"));
    int max = Integer.parseInt(handle.getAttribute("aria-valuemax"));

    if (targetValue < min || targetValue > max)
      throw new IllegalArgumentException("Значение должно быть в диапазоне от 0 до 15");

    BoundingBox trackBox = track.boundingBox();
    BoundingBox handleBox = handle.boundingBox();
    // сколько px приходится на 1 шаг
    double stepPx = trackBox.width / (max - min);
    // абсолютная X-координата, куда тянуть ползунок
    double targetX =
        trackBox.x + (targetValue - min) * stepPx + handleBox.width / 2;

    double centerY = handleBox.y + handleBox.height / 2;

    page.mouse().move(handleBox.x + handleBox.width / 2, centerY);
    page.mouse().down();
    page.mouse().move(targetX, centerY, new Mouse.MoveOptions().setSteps(10));
    page.mouse().up();
  }

  private List<Integer> getCoursesDuration() {
    List<Integer> monthNumbers = new ArrayList<>();
    Pattern pattern = Pattern.compile("(\\d+)\\s*месяц");

    for (int i = 0; i < courseCards.count(); i++) {
      Locator card = courseCards.nth(i);
      Locator durationElement = card.locator("//div[2]/div/div[contains(., '2025')]");

      if (durationElement.count() > 0) {
        String durationText = durationElement.textContent();
        if (durationText != null) {
          Matcher matcher = pattern.matcher(durationText);
          if (matcher.find()) {
            int months = Integer.parseInt(matcher.group(1));
            monthNumbers.add(months);
          }
        }
      }
    }
    return monthNumbers;
  }

  public Boolean checkMonthRange() {
    implicitWait();
    List<Integer> months = getCoursesDuration();
    boolean isValid = true;

    for (int month : months) {
      if (month < 3 || month > 10) {
        isValid = false;
        break;
      }
    }
    return isValid;
  }
}
