/**
 * Copyright 2004 - 2020 anaptecs GmbH, Burgstr. 96, 72764 Reutlingen, Germany
 *
 * All rights reserved.
 */
package com.anaptecs.jeaf.xfun.samples.trace;

import java.util.GregorianCalendar;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.anaptecs.jeaf.accounting.AccountingMessages;
import com.anaptecs.jeaf.xfun.api.XFun;
import com.anaptecs.jeaf.xfun.api.trace.Trace;

/**
 * Class demonstrates how to use JEAF X-Fun's tracing mechanism
 * 
 * Class is implemented as unit test to ensure that sample code is really working properly ;-)
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TracingSample {
  @Test
  @Order(10)
  public void howToUseTracing( ) {
    // JEAF X-Fun's tracing is like an abstraction for many other tracing solutions that are available. When writing
    // your application code you do not have to care about the tracing solution that should be used in the current
    // environment. Therefore JEAF defines a Tracing API that is independent from any implementation. In addition to
    // regular tracing it also adds some feature that are very helpful in enterprise environments.

    // First let's start with simple tracing.
    // JEAF knows the following trace levels trace (ordered from low to high):
    // - TRACE
    // - DEBUG
    // - INFO
    // - WARN
    // - ERROR
    // - FATAL
    //
    // One big difference to other tracing solutions is that JEAF's API does not require something like a logger.
    // Instead of that the current service / component from which the tracing is called is used as "Logger". Idea
    // behind that is that enterprise applications are usually separated into multiple services. When it comes to
    // tracing then it's sufficient to configure tracing on the level of services. Through this approach usage of the
    // API is simpler and less error-prone. If tracing is called not from within a service then the logger of the
    // application will be used.
    Trace lTrace = XFun.getTrace();
    lTrace.trace("Message with level TRACE");
    lTrace.debug("Message with level DEBUG");
    lTrace.info("Message with level INFO");
    lTrace.warn("Message with level WARN");
    lTrace.error("Message with level ERROR");
    lTrace.fatal("Message with level FATAL");

    // The trace statements from above cause the following output
    //
    // TRACE XFunSampleApp - Message with level TRACE
    // DEBUG XFunSampleApp - Message with level DEBUG
    // INFO XFunSampleApp - Message with level INFO
    // WARN XFunSampleApp - Message with level WARN
    // ERROR XFunSampleApp - Message with level ERROR
    // FATAL XFunSampleApp - Message with level FATAL
    //
    // As you can see on the generated output the so called Application-ID is used as logger as we called the tracing
    // not from within a service call.

    // In addition to standard tracing JEAF X-Fun also offers additional features that help in enterprise environments.
    // One example therefore is classes from JEAF's Internationalization features such as MessageID and ErrorCode's can
    // be directly used in Tracing-API-
    // There you have the option to programmatically define the trace level or you can use the trace level that was
    // defined in the message resource. In general it's recommend to not overwrite the trace level from the message
    // definition.
    lTrace.info(AccountingMessages.BANK_BALANCE_NOT_SUFFICIENT);
    lTrace.write(AccountingMessages.BANK_BALANCE_NOT_SUFFICIENT);

    // Of course same as for messages also traces can be parameterized. In order to avoid garbage JEAF only converts the
    // error code and it's parameters into a message string if the trace level is enabled.
    lTrace.info(AccountingMessages.BANK_BALANCE_NOT_SUFFICIENT, "100.00 EUR");
    lTrace.write(AccountingMessages.BANK_BALANCE_NOT_SUFFICIENT, "100.00 EUR");

    // What you also can see on the logs is the message ID / error code of the message that was written. In out case
    // code 20002. If a message expected a parameter but it is not provided then the placeholder {0} remains in the
    // message.
    //
    // INFO XFunSampleApp - [20002] Money transfer failed. Transaction amount: {0}
    // INFO XFunSampleApp - [20002] Money transfer failed. Transaction amount: {0}
    // INFO XFunSampleApp - [20002] Money transfer failed. Transaction amount: 100.00 EUR
    // INFO XFunSampleApp - [20002] Money transfer failed. Transaction amount: 100.00 EUR
  }

  @Test
  @Order(20)
  public void demonstrateTraceObjectFormatter( ) {
    // Sometimes there are cases where you want to have a special trace output about an object. In this case it's
    // possible to hand over the object to JEAF Tracing. In this case JEAF will make use of a so called
    // TraceObjectFormatter
    Book lBook = new Book();
    lBook.setAuthor("Stephen Hawking");
    lBook.setTitel("A Brief History of Time");
    lBook.setPublishingDate(new GregorianCalendar(1988 + 1, 0, 0));

    // This will produce the following log:
    // A Brief History of Time (Stephen Hawking)
    XFun.getTrace().infoObject(lBook);

    // While on level DEBUG we will see some additional information:
    // 'A Brief History of Time' by Stephen Hawking published in 1988
    XFun.getTrace().debugObject(lBook);

    // In case that there is no formatter defined for a class then the objects toString() will be called by JEAF. In the
    // example below this will result in "... I'm just a closet"
    //
    // However this variant of tracing objects is not recommended as it pollutes our business object with technical
    // information about tracing :-(
    Closet lCloset = new Closet();
    XFun.getTrace().infoObject(lCloset);
  }
}
