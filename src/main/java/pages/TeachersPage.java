package pages;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static com.microsoft.playwright.options.AriaRole.BUTTON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import annotations.Path;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import java.awt.*;

@Path("/lessons/clickhouse/")
public class TeachersPage extends AbsBasePage<TeachersPage> {

  private final Locator
      teachersBlockContainer = page.locator("section").nth(2),
      slider = page.locator(".swiper-wrapper").first(),
      teacherCards = teachersBlockContainer.locator(".swiper-slide"),
      teacherCard1 = teachersBlockContainer.locator(".swiper-slide").first(),
      teacherCard3 = teachersBlockContainer.locator(".swiper-slide").nth(2),
      toRightBtn = teachersBlockContainer.locator(page.getByRole(BUTTON)).nth(1),
      toLeftBtn = teachersBlockContainer.locator(page.getByRole(BUTTON)).first();

  @Step("Проверить, что блок с преподавателями виден")
  public TeachersPage checkTeachersBlockVisible() {
    teachersBlockContainer.scrollIntoViewIfNeeded();
    teachersBlockContainer.isVisible();
    assertThat(teacherCards).hasCount(5);
    return this;
  }

  @Step("Свайпнуть слайдер")
  public TeachersPage swipeTeachersSlider() {
    String teacher1 = teacherCard1.textContent();
    String teacher3 = teacherCard3.textContent();
    moveSlider(slider, 2);
    assertNotEquals(teacher1, teacherCard1.textContent());
    assertEquals(teacher3, teacherCard1.textContent());
    return this;
  }

  @Step("Выбрать карточку преподавателя")
  public String selectTeacherCard() {
    String cardContent = teacherCard3.textContent();
    click(teacherCard3);
    return cardContent;
  }
}
