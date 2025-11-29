package tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.inject.Inject;
import extension.UIExtension;
import modal.TeachersModal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.TeachersPage;

@ExtendWith(UIExtension.class)
public class TeachersTest {

  @Inject
  private TeachersPage teachersPage;

  @Inject
  private TeachersModal teachersModal;

  @Test
  public void userShouldViewInfoAboutTeacherInSlider() {
    teachersPage
        .open()
        .checkTeachersBlockVisible()
        .swipeTeachersSlider();
    String cardContent = teachersPage.selectTeacherCard();
    teachersModal.checkTeachersModalVisible();
    String teacherName = teachersModal.getTeacherName(3);
    assertTrue(cardContent.contains(teacherName));
    teachersModal.clickRightBtn();
    teacherName = teachersModal.getTeacherName(4);
    assertFalse(cardContent.contains(teacherName));
    teachersModal.clickLeftBtn();
    teacherName = teachersModal.getTeacherName(3);
    assertTrue(cardContent.contains(teacherName));
  }
}
