package tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.inject.Inject;
import extension.UIExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.CoursesPage;

@ExtendWith(UIExtension.class)
public class CoursesTest {

  @Inject
  private CoursesPage coursesPage;

  @Test
  public void userShouldSelectCourseUsingFilter() {
    coursesPage
        .open()
        .checkSelectedDirection()
        .checkSelectedLevel()
        .selectDuration(3, 10);
    assertTrue(coursesPage.checkMonthRange());
    coursesPage.clearFilter();
    coursesPage.selectDuration(2, 12);
    assertFalse(coursesPage.checkMonthRange());
  }
}
