/**
 * 
 */
package com.hehua.sms.provider.guodu;

import java.io.IOException;
import java.util.Collection;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.hehua.sms.provider.AbstractSmsProvider;
import com.hehua.sms.provider.exception.InsufficientBalanceException;
import com.hehua.sms.provider.exception.SmsException;

/**
 * @author zhihua
 *
 */
public class GuoduSmsProvider extends AbstractSmsProvider {

    private static final String ENDPOINT = "http://221.179.180.158:9007/QxtSms/QxtFirewall";

    @Override
    public String getName() {
        return "guodu";
    }

    @Override
    public boolean sendDownstreamMessage(String mobile, String message) throws SmsException {

        message = message + "【荷花亲子】";

        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(ENDPOINT);

        postMethod.setParameter("OperID", "hhqz");
        postMethod.setParameter("OperPass", "123456");
        postMethod.setParameter("DesMobile", mobile);
        postMethod.setParameter("Content", message);
        postMethod.setParameter("ContentType", "15");
        postMethod.getParams().setContentCharset("GBK");

        try {

            logRequest(postMethod);

            int statusCode = httpClient.executeMethod(postMethod);
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException("invalid status code = " + statusCode);
            }

            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(postMethod.getResponseBodyAsStream(), "GBK");

            Element rootElement = document.getRootElement();
            String code = rootElement.elementText("code");

            //            List<Element> messageElements = rootElement.elements("message");
            //            for (Element messageElement : messageElements) {
            //                String desmobile = messageElement.elementText("desmobile");
            //                String msgid = messageElement.elementText("msgid");
            //            }

            if (StringUtils.isEmpty(code)) {
                throw new RuntimeException("invalid response " + document.getText());
            }

            switch (code) {
                case "00":
                case "01":
                case "03":
                    return true;
                case "11":
                    throw new InsufficientBalanceException();
                default:
                    throw new RuntimeException("invalid code " + document.asXML());
            }

        } catch (HttpException e) {
            throw new RuntimeException("error", e);
        } catch (IOException e) {
            throw new RuntimeException("error", e);
        } catch (DocumentException e) {
            throw new RuntimeException("error", e);
        } finally {
            postMethod.releaseConnection();
        }
    }

    @Override
    public boolean sendDownstreamMessages(Collection<String> mobiles, String message)
            throws SmsException {
        return sendDownstreamMessage(StringUtils.join(mobiles, ","), message);
    }

    @Override
    public boolean sendUpstreamMessage(String mobile, String message) throws SmsException {
        // TODO Auto-generated method stub
        return false;
    }

}
