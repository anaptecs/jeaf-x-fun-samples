/**
 * Copyright 2004 - 2020 anaptecs GmbH, Burgstr. 96, 72764 Reutlingen, Germany
 *
 * All rights reserved.
 */
package com.anaptecs.jeaf.xfun.samples.locale;

import com.anaptecs.jeaf.xfun.api.locale.LocaleProvider;
import com.anaptecs.jeaf.xfun.api.locale.LocaleProviderFactory;

public class MyLocaleProviderFactory implements LocaleProviderFactory {
  /**
   * As our implementation supports multi-thread environments without any restrictions we can use one instance for all
   * requests (less garbage less trouble ;-) )
   */
  private final LocaleProvider localeProvider = new MyLocaleProvider();

  @Override
  public LocaleProvider getLocaleProvider( ) {
    return localeProvider;
  }
}
