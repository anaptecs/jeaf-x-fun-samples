/**
 * Copyright 2004 - 2020 anaptecs GmbH, Burgstr. 96, 72764 Reutlingen, Germany
 *
 * All rights reserved.
 */
package com.anaptecs.jeaf.xfun.samples.trace;

import java.util.Calendar;

public class Book {
  private String author;

  private String title;

  private Calendar publishingDate;

  public String getAuthor( ) {
    return author;
  }

  public void setAuthor( String pAuthor ) {
    author = pAuthor;
  }

  public String getTitle( ) {
    return title;
  }

  public void setTitel( String pTitel ) {
    title = pTitel;
  }

  public Calendar getPublishingDate( ) {
    return publishingDate;
  }

  public void setPublishingDate( Calendar pPublishingDate ) {
    publishingDate = pPublishingDate;
  }
}
