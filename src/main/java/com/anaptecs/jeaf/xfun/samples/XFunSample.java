/**
 * Copyright 2004 - 2020 anaptecs GmbH, Burgstr. 96, 72764 Reutlingen, Germany
 *
 * All rights reserved.
 */
package com.anaptecs.jeaf.xfun.samples;

import com.anaptecs.jeaf.xfun.annotations.AppInfo;
import com.anaptecs.jeaf.xfun.annotations.TraceConfig;
import com.anaptecs.jeaf.xfun.annotations.XFunConfig;
import com.anaptecs.jeaf.xfun.samples.locale.MyLocaleProviderFactory;

// App info can be used to define some general information about your application. Additional information
@AppInfo(applicationID = "XFunSampleApp", applicationName = "JEAF X-Fun Sample App")

// As we want to use our own implementation of a locale provider we have to configure it via this annotation
@XFunConfig(localeProviderFactory = MyLocaleProviderFactory.class)

// JEAF's tracing can be configured through annotation @TraceConfig. For further details please refer to Javadoc of
// annotation. If nothing is configured here then default values which should be fine in almost all cases will be used.
@TraceConfig
public class XFunSample {
  //
  // For examples see the following classes:
  //
  // - Internationalization / Localization: InternationalizationSample
  // - Tracing: TracingSample
}
