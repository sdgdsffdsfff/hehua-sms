/**
 * 
 */
package com.hehua.sms.provider.composite;

import java.util.List;

import com.hehua.sms.provider.AbstractSmsProvider;
import com.hehua.sms.provider.SmsProvider;
import com.hehua.sms.provider.SmsProvider;
import com.hehua.sms.provider.SmsProvider;

/**
 * @author zhihua
 *
 */
public abstract class CompositeSmsProvider extends AbstractSmsProvider {

    protected final List<SmsProvider> providers;

    /**
     * @param providers
     */
    public CompositeSmsProvider(List<SmsProvider> providers) {
        super();
        this.providers = providers;
    }

}
