/**
 * Copyright 2004 - 2020 anaptecs GmbH, Burgstr. 96, 72764 Reutlingen, Germany
 *
 * All rights reserved.
 */
package com.anaptecs.jeaf.xfun.samples.messages;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.Test;

import com.anaptecs.jeaf.accounting.AccountingMessages;
import com.anaptecs.jeaf.xfun.api.XFun;
import com.anaptecs.jeaf.xfun.api.errorhandling.ErrorCode;
import com.anaptecs.jeaf.xfun.api.messages.LocalizedString;
import com.anaptecs.jeaf.xfun.api.messages.MessageID;
import com.anaptecs.jeaf.xfun.samples.locale.MyLocaleProvider;

/**
 * Class demonstrates how to use JEAF X-Fun's mechanism for internationalization.
 * 
 * Class is implemented as unit test to ensure that sample code is really working properly ;-)
 */
public class InternationalizationSample {
  /**
   * Test demonstrates how to use JEAF's mechanism for internationalization.
   */
  @Test
  public void howToUseInternationalization( ) {
    // Internationalization features of JEAF can be used really easy. JEAF Generator generates for every file which
    // contains messages a matching Java class that contains constants for every message. In our case class
    // "AccountingMessages" is the class that was generated.
    //
    // There are 2 way to provide message content to JEAF X-Fun, either by maintaining a XML resource file that contains
    // the message data or by using Excel workbooks. Both mechanism provide the same capabilities so it's your choice
    // what you prefer.
    //
    // In both cases JEAF Generator will generate a class containing constants for secure use of messages.
    //
    // Depending on what type of message is defined constants of different types will be generated:
    // - LocalizedString can used used for any kind of text that should be localized such as labels for UI etc.
    // - MessageID Any kind of message that should be also be traceable.
    // - ErrorCodes should be used in case of business or technical errors and can be used to provide error message e.g.
    // as part of exceptions.
    LocalizedString lLocalizedString = AccountingMessages.TX_AMOUNT_LABEL;

    // If no specific locale is passed to toString() then the current locale as returned from the configured locale
    // provider will be used.
    Locale lCurrentLocale = XFun.getLocaleProvider().getCurrentLocale();
    String lStringWithCurrentLocale = lLocalizedString.toString();
    assertEquals(lStringWithCurrentLocale, lLocalizedString.toString(lCurrentLocale));
    assertEquals("Transaction amount (de)", lLocalizedString.toString(Locale.GERMAN));

    // Please be aware that you have to be consistent in the country codes (ISO-3166 Alpha-2 vs. ISO-3166 Alpha-3 codes,
    // e.g. "CH" or "CHE" for Switzerland).
    assertEquals("Transaction amount (de_CHE)", lLocalizedString.toString(new Locale("de", "CHE")));
    assertEquals("Transaction amount (de)", lLocalizedString.toString(new Locale("de", "CH")));

    // If a locale is requested where no localization is available for then a fallback to the closest available
    // localization will be used. In our case here this will fall back to the default message.
    assertEquals("Transaction amount", lLocalizedString.toString(Locale.FRENCH));

    // As we do not have a special localization for Austria German will be used.
    assertEquals("Transaction amount (de)", lLocalizedString.toString(new Locale("de", "AT")));

    // Messages also can be parameterized using notation as defined by class @link java.text.MessageFormat
    // e.g. "Executing SWIFT booking from account ''{0}'' to ''{1}''."
    MessageID lMessage = AccountingMessages.SWIFT_BOOKING;
    assertEquals(
        "Executing SWIFT booking from account '1234 0000 0000 9876 12' to '9999 0000 0000 1111 11' (Language: de)",
        lMessage.toString(Locale.GERMAN, "1234 0000 0000 9876 12", "9999 0000 0000 1111 11"));

    // In addition to the already used message types (LocalizedString, MessageID) also a type for error codes
    // (ErrorCode) is available. Whenever JEAF Generator detects a message of type error then it will generate an
    // constant of type error code. Usage of errors code is just the same as for messages and localized text, but in
    // addition error codes are part of JEAF mechanism of exception handling.
    ErrorCode lErrorCode = AccountingMessages.BANK_BALANCE_NOT_SUFFICIENT;
    assertEquals("Money transfer failed. Transaction amount: 100.00 EUR", lErrorCode.toString("100.00 EUR"));

    // All types of message constants can also be passed to JEAF's tracing mechanism
    XFun.getTrace().info(lErrorCode, "100.00 EUR");
  }

  /**
   * Test demonstrates how to define and use locale providers.
   */
  @Test
  public void howToUseLocaleProviders( ) {
    // Usually in standard application code you do not get in contact with locale providers. In very most cases you only
    // make use of the indirectly. Every time you work with localized objects and you do not explicitly define the
    // locale / language you want to use then a locale provider will be used.
    //
    // In order to provide your own locale provider you have to do the 3 following steps
    // - Implement your version interface of LocaleProvider
    // - Implement your own version of interface LocaleProviderFactory
    // - Register your LocaleProviderFactory in @XFunConfig (see class XFunSample for usage)

    // Let's check if our locale provider is really used.
    assertEquals(MyLocaleProvider.class, XFun.getLocaleProvider().getClass());

    // As we ensured that our implementation of a locale provider is used we can now do the downcast. Which of course in
    // real application code should not be done and is not required in any case.
    MyLocaleProvider lLocaleProvider = (MyLocaleProvider) XFun.getLocaleProvider();

    // Let's track if the locale provider is really used.
    int lAmountOfCalls = lLocaleProvider.getAmountOfCalls();
    AccountingMessages.BANK_BALANCE_NOT_SUFFICIENT.toString("100.00 EUR");
    assertEquals(lAmountOfCalls + 1, lLocaleProvider.getAmountOfCalls());
  }
}
