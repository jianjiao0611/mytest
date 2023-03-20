package com.jjtest.tool.util.wx;

import com.alibaba.fastjson.JSONObject;
import com.google.common.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.cloud.openfeign.encoding.HttpEncoding.CONTENT_TYPE;

public class WxUtils {
    /**
     *
     * @param corpId 企业id
     * @param corpSecret 企业应用私钥
     * @return
     * @throws IOException
     */
    public String getToken(String corpId, String corpSecret) throws IOException {
        UrlData uData = new UrlData();
        uData.setGetTokenUrl(corpId, corpSecret);
        String resp = this.toAuth(uData.getGetTokenUrl());
        //就是按照GET方式拼接了字符串得到url
        Map<String, Object> map = JSONObject.parseObject(resp,
                new TypeToken<Map<String, Object>>() {
                }.getType());
        System.out.println(map);
        return map.get("access_token").toString();
    }

    protected String toAuth(String Get_Token_Url) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(Get_Token_Url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        System.out.println(response.toString());
        String resp;
        try {
            HttpEntity entity = response.getEntity();
            System.out.println(response.getAllHeaders());
            resp = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }

        return resp;
    }

    /**
     * 创建POST BODY
     */
    private String createPostData(String touser, String msgtype, int agent_id, String contentKey, String contentValue) {
        WeChatData weChatData = new WeChatData();
        weChatData.setTouser(touser);
        weChatData.setAgentid(agent_id);
        weChatData.setMsgtype(msgtype);
        Map<Object, Object> content = new HashMap<Object, Object>();
        content.put(contentKey, contentValue + "\n--------\n" );
        weChatData.setText(content);
        System.out.println(JSONObject.toJSONString(weChatData));
        return JSONObject.toJSONString(weChatData);
    }

    /**
     * POST请求
     */
    private String post(String charset, String contentType, String url, String data, String token) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url + token);
        httpPost.setHeader(CONTENT_TYPE, contentType);
        httpPost.setEntity(new StringEntity(data, charset));
        CloseableHttpResponse response = httpclient.execute(httpPost);
        String resp;
        try {
            HttpEntity entity = response.getEntity();
            resp = EntityUtils.toString(entity, charset);
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        return resp;
    }

    public void sendTextMesg(String toUser, String contentValue) throws IOException {
        //q
        String token = getToken("wwdcd4807a8349dca5", "f8ZE03421a97oZP_IwLm_Bs509zTqjcs7ARe_Om7T_M");
        String postData = createPostData(toUser, "text", 1000002, "content", contentValue);
        String response = post("utf-8", "application/json", (new UrlData()).getSendMessageUrl(), postData, token);
        System.out.println("获取到的token======>" + token);
        System.out.println("请求数据======>" + postData);
        System.out.println("发送微信的响应数据======>" + response);
    }

    public static void main(String[] args) {
        try {
            WxUtils wxUtils = new WxUtils();
            wxUtils.sendTextMesg("JianJiao", "ces");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
