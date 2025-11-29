package common;

import com.google.inject.Guice;
import com.microsoft.playwright.Page;
import modules.PageModule;

public abstract class AbsCommon {

  public AbsCommon() {
    Guice.createInjector(new PageModule()).injectMembers(this);
  }
}
