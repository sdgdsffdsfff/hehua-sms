/**
 * 
 */
package com.hehua.sms.provider.exception;

/**
 * @author zhihua
 *
 */
public class SmsException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public SmsException() {
        super();
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public SmsException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param message
     * @param cause
     */
    public SmsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public SmsException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public SmsException(Throwable cause) {
        super(cause);
    }

}
