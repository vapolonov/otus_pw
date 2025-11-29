package tests;

import com.google.inject.Inject;
import extension.UIExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.PayBasePage;
import pages.SubscriptionPage;

@ExtendWith(UIExtension.class)
public class SubscriptionTest {

  @Inject
  private SubscriptionPage subscriptionPage;

  @Inject
  private PayBasePage payBasePage;

  @Test
  public void userShouldSubscribeToCourse() {
    subscriptionPage
        .open()
        .checkSectionTitle()
        .selectBasic()
        .clickBuyBtn();
    payBasePage
        .checkPageLoaded();

  }
}
