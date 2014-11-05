/**
 * 
 */
package com.hehua.sms.provider.exception;

/**
 * @author zhihua
 *
 */
public class InsufficientBalanceException extends SmsException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public InsufficientBalanceException() {
        super();
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public InsufficientBalanceException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param message
     * @param cause
     */
    public InsufficientBalanceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public InsufficientBalanceException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public InsufficientBalanceException(Throwable cause) {
        super(cause);
    }

}
