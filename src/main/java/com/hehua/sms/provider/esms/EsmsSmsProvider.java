/**
 * 
 */
package com.hehua.sms.provider.esms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.hehua.sms.provider.AbstractSmsProvider;
import com.hehua.sms.provider.exception.SmsException;

/**
 * @author zhihua
 *
 */
public class EsmsSmsProvider extends AbstractSmsProvider {

    private String mtUrl = "http://esms.etonenet.com/sms/mt";

    @Override
    public String getName() {
        return "esms";
    }

    @Override
    public boolean sendDownstreamMessage(String mobile, String message) throws SmsException {

        HttpClient httpClient = new HttpClient();

        GetMethod getMethod = new GetMethod(mtUrl);

        getMethod.getParams().setContentCharset("GBK");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new NameValuePair("command", "MT_REQUEST"));
        params.add(new NameValuePair("spid", "7845"));
        params.add(new NameValuePair("sppassword", "hhqz@0805"));
        params.add(new NameValuePair("spsc", "00"));
        params.add(new NameValuePair("sa", "1065710906655801"));
        params.add(new NameValuePair("da", "86" + mobile));
        int dc = 15;
        params.add(new NameValuePair("dc", String.valueOf(dc)));
        params.add(new NameValuePair("sm", encodeHexStr(dc, message)));
        getMethod.setQueryString(params.toArray(new NameValuePair[params.size()]));

        try {

            logRequest(getMethod);

            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException("invalid status code = " + statusCode);
            }

            String response = IOUtils.toString(getMethod.getResponseBodyAsStream());

            if (StringUtils.isEmpty(response)) {
                throw new RuntimeException("invalid response " + response);
            }

            HashMap<String, String> pp = parseResStr(response);
            String command = pp.get("command");
            String spid = pp.get("spid");
            String mtmsgid = pp.get("mtmsgid");
            String mtstat = pp.get("mtstat");
            String mterrcode = pp.get("mterrcode");

            //            System.out.println(command);
            //            System.out.println(spid);
            //            System.out.println(mtmsgid);
            //            System.out.println(mtstat);
            //            System.out.println(mterrcode);

            switch (mterrcode) {
                case "000":
                    return true;
                case "":
                default:
                    throw new RuntimeException("invalid code " + response);
            }

        } catch (HttpException e) {
            throw new RuntimeException("error", e);
        } catch (IOException e) {
            throw new RuntimeException("error", e);
        } finally {
            getMethod.releaseConnection();
        }
    }

    private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
            'b', 'c', 'd', 'e', 'f' };

    public static String encodeHexStr(int dataCoding, String realStr) {
        String hexStr = null;

        if (realStr != null) {
            byte[] data = null;
            try {
                if (dataCoding == 15) {
                    data = realStr.getBytes("GBK");
                } else if ((dataCoding & 0x0C) == 0x08) {
                    data = realStr.getBytes("UnicodeBigUnmarked");
                } else {
                    data = realStr.getBytes("ISO8859-1");
                }
            } catch (UnsupportedEncodingException e) {
                System.out.println(e.toString());
            }

            if (data != null) {
                int len = data.length;
                char[] out = new char[len << 1];
                // two characters form the hex value.
                for (int i = 0, j = 0; i < len; i++) {
                    out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
                    out[j++] = DIGITS[0x0F & data[i]];
                }
                hexStr = new String(out);
            }
        }
        return hexStr;
    }

    private HashMap<String, String> parseResStr(String resStr) {
        HashMap<String, String> pp = new HashMap<>();
        try {
            String[] ps = resStr.split("&");
            for (int i = 0; i < ps.length; i++) {
                int ix = ps[i].indexOf("=");
                if (ix != -1) {
                    pp.put(ps[i].substring(0, ix), ps[i].substring(ix + 1));
                }
            }
        } catch (Exception e) {
            logger.error("pase querystring fail " + resStr, e);
        }
        return pp;
    }

    @Override
    public boolean sendUpstreamMessage(String mobile, String message) throws SmsException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean sendDownstreamMessages(Collection<String> mobiles, String message)
            throws SmsException {

        HttpClient httpClient = new HttpClient();

        GetMethod getMethod = new GetMethod(mtUrl);

        getMethod.getParams().setContentCharset("GBK");

        StringBuilder das = new StringBuilder();

        int i = 0;
        for (String mobile : mobiles) {
            if (i > 0) {
                das.append(",");
            }
            das.append("86" + mobile);
            i++;
        }

        List<NameValuePair> params = new ArrayList<>();
        params.add(new NameValuePair("command", "MULTI_MT_REQUEST"));
        params.add(new NameValuePair("spid", "7845"));
        params.add(new NameValuePair("sppassword", "hhqz@0805"));
        params.add(new NameValuePair("spsc", "00"));
        params.add(new NameValuePair("sa", "1065710906655801"));
        params.add(new NameValuePair("das", das.toString()));
        int dc = 15;
        params.add(new NameValuePair("dc", String.valueOf(dc)));
        params.add(new NameValuePair("sm", encodeHexStr(dc, message)));
        getMethod.setQueryString(params.toArray(new NameValuePair[params.size()]));

        try {

            logRequest(getMethod);

            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException("invalid status code = " + statusCode);
            }

            String response = IOUtils.toString(getMethod.getResponseBodyAsStream());

            if (StringUtils.isEmpty(response)) {
                throw new RuntimeException("invalid response " + response);
            }

            HashMap<String, String> pp = parseResStr(response);
            String command = pp.get("command");
            String spid = pp.get("spid");
            String mtmsgids = pp.get("mtmsgids");
            String mtstat = pp.get("mtstat");
            String mterrcode = pp.get("mterrcode");

            //            System.out.println(command);
            //            System.out.println(spid);
            //            System.out.println(mtmsgid);
            //            System.out.println(mtstat);
            //            System.out.println(mterrcode);

            switch (mterrcode) {
                case "000":
                    return true;
                case "":
                default:
                    throw new RuntimeException("invalid code " + response);
            }

        } catch (HttpException e) {
            throw new RuntimeException("error", e);
        } catch (IOException e) {
            throw new RuntimeException("error", e);
        } finally {
            getMethod.releaseConnection();
        }
    }

}
