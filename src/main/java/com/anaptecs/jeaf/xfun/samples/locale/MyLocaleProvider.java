/**
 * Copyright 2004 - 2020 anaptecs GmbH, Burgstr. 96, 72764 Reutlingen, Germany
 *
 * All rights reserved.
 */
package com.anaptecs.jeaf.xfun.samples.locale;

import java.util.Locale;

import com.anaptecs.jeaf.xfun.api.locale.LocaleProvider;

/**
 * Class implements a locale provider in order to demonstrate its usage.
 */
public class MyLocaleProvider implements LocaleProvider {
  private int callCounter = 0;

  @Override
  public Locale getCurrentLocale( ) {
    // Having synchronized blocks inside a locale provider is bad style. In our case it's required to track if our
    // implementation was really called.
    synchronized (this) {
      callCounter++;
    }
    return Locale.getDefault(Locale.Category.DISPLAY);
  }

  public int getAmountOfCalls( ) {
    return callCounter;
  }
}
