package com.mtm.party.mobile.ctrl;

import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 移动推送OpenAPI调用示例
 * 以PushNoticeToAndroid接口为例，其他接口请替换相应接口名称及私有参数
 */
public class Main {
    //账号AK信息请填写(必选)
    private static String access_key_id = "LTAIf4ofZ3JPU77b";
    //账号AK信息请填写(必选)
    private static String access_key_secret = "a43dad34c1f689130b59b20f225f50b8";
    //账号AppKey信息请填写(必选)
    private static String appkey = "24932081";
    //以下参数不需要修改
    //STS临时授权方式访问时该参数为必选，使用主账号AK和RAM子账号AK不需要填写
    private static String security_token = "";
    private final static String VOD_DOMAIN = "http://cloudpush.aliyuncs.com";
    private final static String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private final static String HTTP_METHOD_GET = "GET";
    private final static String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private final static String UTF_8 = "utf-8";
    private final static Logger LOG = Logger.getLogger(Main.class.getName());

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
//        //生成私有参数，不同API需要修改
//        Map<String, String> privateParams = generatePrivateParamters();
//        //生成公共参数，不需要修改
//        Map<String, String> publicParams = generatePublicParamters();
//        //生成OpenAPI地址，不需要修改
//        String URL = generateOpenAPIURL(publicParams, privateParams);
//        //发送HTTP GET 请求
//        httpGet(URL);
    }

    /**
     * 移动推送OpenAPI私有参数
     * 不同API需要修改此方法中的参数
     * 推送高级接口 https://help.aliyun.com/knowledge_detail/48089.html
     *
     * @return
     */
    private static Map<String, String> generatePrivateParamters() {
        // 接口私有参数列表, 不同API请替换相应参数
        Map<String, String> privateParams = new HashMap<String, String>();
        // API名称
        privateParams.put("Action", "Push");
        // AppKey
        privateParams.put("AppKey", appkey);
        //推送目标: DEVICE:按设备推送 ALIAS : 按别名推送 ACCOUNT:按帐号推送  TAG:按标签推送; ALL: 广播推送
        privateParams.put("Target", "ALL");
        //根据Target来设定
        privateParams.put("TargetValue", "ALL");
        // 消息类型 MESSAGE NOTICE
        privateParams.put("PushType", "NOTICE");
        // 设备类型 ANDROID iOS ALL
        privateParams.put("DeviceType", "ANDROID");
        // 推送的标题
        privateParams.put("Title", "title");
        // 推送的内容
        privateParams.put("Body", "body");
        //通知的提醒方式 "VIBRATE" : 震动 "SOUND" : 声音 "BOTH" : 声音和震动 NONE : 静音
        privateParams.put("AndroidNotifyType", "BOTH");
        privateParams.put("AndroidNotificationBarType", "1");
        privateParams.put("AndroidNotificationBarPriority", "1");
        privateParams.put("AndroidOpenType", "URL");
        privateParams.put("AndroidOpenUrl", "http://www.aliyun.com");
        privateParams.put("AndroidMusic", "default");
        privateParams.put("AndroidPopupActivity", "com.alibaba.cloudpushdemo.bizactivity.ThirdPushPopupActivity");
        privateParams.put("AndroidPopupTitle", "PopupTitle");
        privateParams.put("AndroidPopupBody", "PopupBody");
        privateParams.put("PushTime", generateTimestamp(System.currentTimeMillis()));
        privateParams.put("ExpireTime", generateTimestamp(System.currentTimeMillis() + 12 * 3600 * 1000));
        // 离线消息是否保存,若保存, 在推送时候，用户即使不在线，下一次上线则会收到
        privateParams.put("StoreOffline", "true");
        //设定通知的扩展属性
        privateParams.put("ExtParameters", "{'key1':'value1','api_name':'PushRequest'}");
        return privateParams;
    }

    /**
     * 移动推送OpenAPI公共参数
     * 不需要修改
     *
     * @return
     */
    private static Map<String, String> generatePublicParamters() {
        Map<String, String> publicParams = new HashMap<String, String>();
        publicParams.put("Format", "JSON");
        publicParams.put("Version", "2016-08-01");
        publicParams.put("AccessKeyId", access_key_id);
        publicParams.put("SignatureMethod", "HMAC-SHA1");
        publicParams.put("Timestamp", generateTimestamp(System.currentTimeMillis()));
        publicParams.put("SignatureVersion", "1.0");
        publicParams.put("SignatureNonce", generateRandom());
        if (security_token != null && security_token.length() > 0) {
            publicParams.put("SecurityToken", security_token);
        }
        return publicParams;
    }

    /**
     * 生成OpenAPI地址
     *
     * @param privateParams
     * @return
     * @throws Exception
     */
    private static String generateOpenAPIURL(Map<String, String> publicParams, Map<String, String> privateParams) {
        return generateURL(VOD_DOMAIN, HTTP_METHOD_GET, publicParams, privateParams);
    }

    /**
     * @param domain        请求地址
     * @param httpMethod    HTTP请求方式GET，POST等
     * @param publicParams  公共参数
     * @param privateParams 接口的私有参数
     * @return 最后的url
     */
    private static String generateURL(String domain, String httpMethod, Map<String, String> publicParams, Map<String, String> privateParams) {
        List<String> allEncodeParams = getAllParams(publicParams, privateParams);
        String cqsString = getCQS(allEncodeParams);
        out("CanonicalizedQueryString = " + cqsString);
        String stringToSign = httpMethod + "&" + percentEncode("/") + "&" + percentEncode(cqsString);
        out("StringtoSign = " + stringToSign);
        String signature = hmacSHA1Signature(access_key_secret, stringToSign);
        out("Signature = " + signature);
        return domain + "?" + cqsString + "&" + percentEncode("Signature") + "=" + percentEncode(signature);
    }

    private static List<String> getAllParams(Map<String, String> publicParams, Map<String, String> privateParams) {
        List<String> encodeParams = new ArrayList<String>();
        List<String> publicParamsList = paramsUrlEncode(publicParams);
        List<String> privateParamsList = paramsUrlEncode(privateParams);
        encodeParams.addAll(publicParamsList);
        encodeParams.addAll(privateParamsList);
        return encodeParams;
    }

    /**
     * 将参数和值都urlEncode一下
     */
    private static List<String> paramsUrlEncode(Map<String, String> params) {
        List<String> encodeParams = new ArrayList<String>();
        if (params != null) {
            for (String key : params.keySet()) {
                String value = params.get(key);
                //将参数和值都urlEncode一下。
                String encodeKey = percentEncode(key);
                String encodeVal = percentEncode(value);
                encodeParams.add(encodeKey + "=" + encodeVal);
            }
        }
        return encodeParams;
    }

    /**
     * 参数urlEncode
     *
     * @param value
     * @return
     */
    private static String percentEncode(String value) {
        try {
            String urlEncodeOrignStr = URLEncoder.encode(value, "UTF-8");
            String plusReplaced = urlEncodeOrignStr.replace("+", "%20");
            String starReplaced = plusReplaced.replace("*", "%2A");
            String waveReplaced = starReplaced.replace("%7E", "~");
            return waveReplaced;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取CQS 的字符串
     *
     * @param allParams
     * @return
     */
    private static String getCQS(List<String> allParams) {
        ParamsComparator paramsComparator = new ParamsComparator();
        Collections.sort(allParams, paramsComparator);
        String cqString = "";
        for (int i = 0; i < allParams.size(); i++) {
            cqString += allParams.get(i);
            if (i != allParams.size() - 1) {
                cqString += "&";
            }
        }

        return cqString;
    }

    private static class ParamsComparator implements Comparator<String> {
        public int compare(String lhs, String rhs) {
            return lhs.compareTo(rhs);
        }
    }

    private static String hmacSHA1Signature(String accessKeySecret, String stringtoSign) {
        try {
            String key = accessKeySecret + "&";
            try {
                SecretKeySpec signKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
                Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
                mac.init(signKey);
                byte[] rawHmac = mac.doFinal(stringtoSign.getBytes());
                //按照Base64 编码规则把上面的 HMAC 值编码成字符串，即得到签名值（Signature）
                return new String(new BASE64Encoder().encode(rawHmac));
            } catch (Exception e) {
                throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
            }
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 生成随机数
     *
     * @return
     */
    private static String generateRandom() {
        String signatureNonce = UUID.randomUUID().toString();
        return signatureNonce;
    }

    /**
     * 生成当前UTC时间戳
     *
     * @return
     */
    public static String generateTimestamp(long time) {
        Date date = new Date(time);
        SimpleDateFormat df = new SimpleDateFormat(ISO8601_DATE_FORMAT);
        df.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return df.format(date);
    }

    private static String httpGet(String url) throws IOException {
        /*
         * Read and covert a inputStream to a String.
         * Referred this:
         * http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
         */
        out("URL = " + url);
        @SuppressWarnings("resource")
        Scanner s = new Scanner(new URL(url).openStream(), UTF_8).useDelimiter("\\A");
        try {
            String resposne = s.hasNext() ? s.next() : "true";
            out("Response = " + resposne);
            return resposne;
        } finally {
            s.close();
        }
    }

    private static void out(String newLine) {
        LOG.log(Level.INFO, newLine);
    }
}