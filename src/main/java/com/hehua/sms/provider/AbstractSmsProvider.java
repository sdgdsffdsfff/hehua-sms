/**
 * 
 */
package com.hehua.sms.provider;

import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author zhihua
 *
 */
public abstract class AbstractSmsProvider implements SmsProvider {

    protected final Log logger = LogFactory.getLog(getClass());

    protected void logRequest(HttpMethod method) {
        if (logger.isDebugEnabled()) {
            String url = "";
            try {
                url = method.getURI().toString();
            } catch (URIException e) {
                logger.error("error", e);
            }

            if (method instanceof PostMethod) {
                logger.debug(method.getName() + " " + url + "?" + method.getQueryString() + " Body"
                        + Arrays.toString(((PostMethod) method).getParameters()));
            } else {
                logger.debug(method.getName() + " " + url + "?" + method.getQueryString() + " ");
            }
        }
    }

    protected void logResponse(HttpMethod method) {
        if (logger.isDebugEnabled()) {
            try {
                logger.debug(method.getStatusCode() + " " + method.getResponseBodyAsString());
            } catch (IOException e) {
                logger.error("error", e);
            }
        }
    }
}
