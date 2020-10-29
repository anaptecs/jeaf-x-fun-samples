package com.anaptecs.jeaf.accounting;

import com.anaptecs.jeaf.xfun.annotations.MessageResource;
import com.anaptecs.jeaf.xfun.api.XFun;
import com.anaptecs.jeaf.xfun.api.errorhandling.ErrorCode;
import com.anaptecs.jeaf.xfun.api.messages.LocalizedString;
import com.anaptecs.jeaf.xfun.api.messages.MessageID;
import com.anaptecs.jeaf.xfun.api.messages.MessageRepository;

/**
 * Demo class for generation of message constants.
 *
 * @author JEAF Development Team
 * @version 1.5.x
 */
@MessageResource(path = "accounting-messages.xml")
public final class AccountingMessages {
  /**
   * Constant for XML file that contains all messages that are defined within this class.
   */
  private static final String MESSAGE_RESOURCE = "accounting-messages.xml";

  /**
   * Trace message when executing SWIFT booking.
   */
  public static final MessageID SWIFT_BOOKING;

  /**
   * Trace message of transaction amount.
   */
  public static final MessageID TX_AMOUNT;

  /**
   * Error message in case that a money transfer is not possible due to insufficient bank balance.
   */
  public static final ErrorCode BANK_BALANCE_NOT_SUFFICIENT;

  /**
   * Localized text for user interface label showing transaction amount.
   */
  public static final LocalizedString TX_AMOUNT_LABEL;
  /**
   * Static initializer contains initialization for all generated constants.
   */
  static {
    MessageRepository lRepository = XFun.getMessageRepository();
    lRepository.loadResource(MESSAGE_RESOURCE);
    // Handle all info messages.
    SWIFT_BOOKING = lRepository.getMessageID(20000);
    TX_AMOUNT = lRepository.getMessageID(20001);
    // Handle all messages for errors.
    BANK_BALANCE_NOT_SUFFICIENT = lRepository.getErrorCode(20002);
    // Handle all localized strings.
    TX_AMOUNT_LABEL = lRepository.getLocalizedString(20003);
  }

  /**
   * Constructor is private to ensure that no instances of this class will be created.
   */
  private AccountingMessages( ) {
    // Nothing to do.
  }
}