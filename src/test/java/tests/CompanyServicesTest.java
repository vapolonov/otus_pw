package tests;

import com.google.inject.Inject;
import extension.UIExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.CompanyServicesPage;
import pages.CustomCoursesPage;

@ExtendWith(UIExtension.class)
public class CompanyServicesTest {

  @Inject
  private CompanyServicesPage servicesPage;

  @Test
  public void userShouldOrderCourseDevelopment() {
    CustomCoursesPage customPage = servicesPage.open()
        .selectDetailed();
    customPage
        .checkPageLoaded()
        .checkLearningDirectionBlock()
        .selectTestingDirection()
        .checkSelectedTestDirection();
  }
}
