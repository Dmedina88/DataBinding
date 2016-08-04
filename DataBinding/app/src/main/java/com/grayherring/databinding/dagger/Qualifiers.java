package com.grayherring.databinding.dagger;

import java.lang.annotation.Retention;
import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

public final class Qualifiers {

  private Qualifiers() {
    throw new AssertionError("No instances.");
  }

  @Qualifier
  @Retention(RUNTIME)
  public @interface ApiAms {
  }

  @Qualifier
  @Retention(RUNTIME)
  public @interface ApiProlific {
  }

  @Qualifier
  @Retention(RUNTIME)
  public @interface Title {
  }

  @Qualifier
  @Retention(RUNTIME)
  public @interface IsMockMode {
  }

  @Qualifier
  @Retention(RUNTIME)
  public @interface MockNetworkDelay {
  }


  @Qualifier
  @Retention(RUNTIME)
  public @interface SimpleDividerItemDecoration {
  }
}
