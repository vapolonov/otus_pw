package pages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import annotations.Path;

@Path("/subscription")
public class PayBasePage extends AbsBasePage<PayBasePage> {

  public PayBasePage checkPageLoaded() {
    page.waitForLoadState();
    assertTrue(page.url().contains("subscription"));
    assertEquals("Доступ по подписке к 3-м онлайн-курсам в месяц по цене одного", page.title());
    return this;
  }
}
