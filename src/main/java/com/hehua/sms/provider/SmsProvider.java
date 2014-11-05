/**
 * 
 */
package com.hehua.sms.provider;

import java.util.Collection;

import com.hehua.sms.provider.exception.SmsException;

/**
 * @author zhihua
 *
 */
public interface SmsProvider {

    public String getName();

    public boolean sendDownstreamMessage(String mobile, String message) throws SmsException;

    public boolean sendUpstreamMessage(String mobile, String message) throws SmsException;

    public boolean sendDownstreamMessages(Collection<String> mobiles, String message)
            throws SmsException;

}
