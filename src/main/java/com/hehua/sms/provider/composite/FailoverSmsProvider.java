/**
 * 
 */
package com.hehua.sms.provider.composite;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hehua.sms.provider.SmsProvider;
import com.hehua.sms.provider.exception.SmsException;

/**
 * @author zhihua
 *
 */
public class FailoverSmsProvider extends CompositeSmsProvider {

    private static final Log logger = LogFactory.getLog(FailoverSmsProvider.class);

    /**
     * @param providers
     */
    public FailoverSmsProvider(List<SmsProvider> providers) {
        super(providers);
    }

    @Override
    public String getName() {
        return "failover";
    }

    @Override
    public boolean sendDownstreamMessage(String mobile, String message) throws SmsException {
        for (SmsProvider provider : this.providers) {
            try {
                if (provider.sendDownstreamMessage(mobile, message)) {
                    return true;
                }
            } catch (Exception e) {
                logger.error("error", e);
            }
        }
        return false;
    }

    @Override
    public boolean sendUpstreamMessage(String mobile, String message) throws SmsException {
        for (SmsProvider provider : this.providers) {
            try {
                if (provider.sendUpstreamMessage(mobile, message)) {
                    return true;
                }
            } catch (Exception e) {
                logger.error("error", e);
            }
        }
        return false;
    }

    @Override
    public boolean sendDownstreamMessages(Collection<String> mobiles, String message)
            throws SmsException {
        for (SmsProvider provider : this.providers) {
            try {
                if (provider.sendDownstreamMessages(mobiles, message)) {
                    return true;
                }
            } catch (Exception e) {
                logger.error("error", e);
            }
        }
        return false;
    }

}
