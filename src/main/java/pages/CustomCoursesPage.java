package pages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import annotations.Path;
import com.microsoft.playwright.Locator;

@Path("/custom_courses")
public class CustomCoursesPage extends AbsBasePage<CustomCoursesPage> {

  private final Locator
      learningDirectionBlock = page.locator(".t396").nth(3),
      learningDirections = learningDirectionBlock.locator(".tn-atom__sbs-anim-wrapper.js-sbs-anim-trigger_hover");

  public CustomCoursesPage checkPageLoaded() {
    page.waitForLoadState();
    assertTrue(page.url().contains("custom_courses"));
    assertEquals("Разработка индивидуальных программ обучения для бизнеса", page.title());
    return this;
  }

  public CustomCoursesPage checkLearningDirectionBlock() {
    assertEquals(6, learningDirections.count());
    return this;
  }

  public CoursesPage selectTestingDirection() {
    click(learningDirections.nth(1));
    return new CoursesPage();
  }
  
}
