package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import modal.TeachersModal;
import pages.*;

public class PageModule extends AbstractModule {

  @Provides
  @Singleton
  public TeachersPage getTeachersPage(){
    return new TeachersPage();
  }

  @Provides
  @Singleton
  public TeachersModal getTeachersModal(){
    return new TeachersModal();
  }

  @Provides
  @Singleton
  public CoursesPage getCoursesPage(){
    return new CoursesPage();
  }

  @Provides
  @Singleton
  public CompanyServicesPage getCompanyServicePage(){
    return new CompanyServicesPage();
  }

  @Provides
  @Singleton
  public CustomCoursesPage getCustomCoursePage(){
    return new CustomCoursesPage();
  }

  @Provides
  @Singleton
  public SubscriptionPage getSubscriptionPage(){
    return new SubscriptionPage();
  }

  @Provides
  @Singleton
  public PayBasePage getPayBasePage() {
    return new PayBasePage();
  }
}
