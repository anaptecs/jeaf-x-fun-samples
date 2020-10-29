/**
 * Copyright 2004 - 2020 anaptecs GmbH, Burgstr. 96, 72764 Reutlingen, Germany
 *
 * All rights reserved.
 */
package com.anaptecs.jeaf.xfun.samples.trace;

import java.util.Calendar;

import com.anaptecs.jeaf.xfun.annotations.TraceObjectFormatter;
import com.anaptecs.jeaf.xfun.api.trace.ObjectFormatter;
import com.anaptecs.jeaf.xfun.api.trace.TraceLevel;

/**
 * Class implements a {@link ObjectFormatter} for Books.
 * 
 * As we have JEAF Maven Plugin integrated into our build process the only configuration that is required is
 * annotation @TraceObjectFormatter here on this class.
 * 
 * Annotation {@link TraceObjectFormatter} requires that you define all the classes for which the formatter is
 * responsible. In case of inheritance it's sufficient to only define the base class.
 */
@TraceObjectFormatter(supportedClasses = Book.class)
public class BookFormatter implements ObjectFormatter {
  @Override
  public String formatObject( Object pObject, TraceLevel pTraceLevel ) {
    // As we registered this formatter only to "Books" JEAF will ensure that only "Books" will be passed.
    Book lBook = (Book) pObject;

    // As the current trace level is also passed as parameter it is also possible to vary the generated output
    // depending on the current trace level.
    String lString;
    switch (pTraceLevel) {
      // On leveles TRACE and DEBUG we want to trace some more information
      case TRACE:
      case DEBUG:
        lString = "'" + lBook.getTitle() + "' by " + lBook.getAuthor() + " published in "
            + lBook.getPublishingDate().get(Calendar.YEAR);
        break;

      // Default trace representation
      default:
        lString = lBook.getTitle() + " (" + lBook.getAuthor() + ")";
    }

    return lString;
  }
}
