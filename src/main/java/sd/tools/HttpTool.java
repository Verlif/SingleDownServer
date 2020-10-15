package sd.tools;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HttpTool {

    /**
     * 向 url 发送post请求
     *
     * @param url    目标链接
     * @param params 请求所带的参数表
     * @return 本次请求的返回值
     */
    public String doPost(String url, Map<String, String> header, HashMap<String, String> params) {
        List<NameValuePair> pairList = new ArrayList<>();
        if (params != null) {
            for (String key : params.keySet()) {
                pairList.add(new BasicNameValuePair(key, params.get(key)));
            }
        }

        try {
            HttpEntity reqEntity = new UrlEncodedFormEntity(pairList, "utf-8");
            return postSend(url, header, reqEntity);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 向 url 发送post请求
     *
     * @param url         目标链接
     * @param requestBody 请求的内容
     * @return 本次请求的返回值
     */
    public String doPost(String url, Map<String, String> header, String requestBody) {
        if (requestBody == null)
            requestBody = "";

        HttpEntity reqEntity = new StringEntity(requestBody, "utf-8");
        try {
            return postSend(url, header, reqEntity);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String postSend(String uri, Map<String, String> header, HttpEntity reqEntity) throws IOException {
        RequestConfig requestConfig = getRequestConfig();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(reqEntity);
        httpPost.setConfig(requestConfig);
        if (header != null) {
            for (String key: header.keySet()) {
                httpPost.addHeader(key, header.get(key));
            }
        }
        HttpClient client = HttpClients.createDefault();
        HttpResponse response = client.execute(httpPost);
        return responseHandler(response);
    }

    /**
     * 向 url 发送get请求
     *
     * @param url    请求链接
     * @param params 请求所带参数
     * @return 本次请求的返回值
     */
    public String doGet(String url, Map<String, String> header, HashMap<String, String> params) {
        StringBuilder string = new StringBuilder();
        string.append(url);

        int index = 0;
        for (String key : params.keySet()) {
            if (index++ == 0)
                string.append("?");
            else
                string.append("&");
            string.append(key).append("=").append(params.get(key));
        }

        System.out.println(string.toString());

        try {

            RequestConfig requestConfig = getRequestConfig();
            HttpGet httpGet = new HttpGet(string.toString());
            httpGet.setConfig(requestConfig);
            if (header != null) {
                for (String key: header.keySet()) {
                    httpGet.addHeader(key, header.get(key));
                }
            }
            HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(httpGet);
            return responseHandler(response);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 向 url 发送put请求
     *
     * @param uri    请求链接
     * @param params 请求参数
     * @return 响应数据;可能为null
     */
    public String doPut(String uri, Map<String, String> header, Map<String, String> params) {
        List<NameValuePair> pairList = new ArrayList<NameValuePair>();
        if (params != null) {
            for (String key : params.keySet()) {
                pairList.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        try {
            HttpEntity reqEntity = new UrlEncodedFormEntity(pairList, "utf-8");
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPut httpPut = new HttpPut(uri);
            httpPut.setEntity(reqEntity);
            if (header != null) {
                for (String key: header.keySet()) {
                    httpPut.addHeader(key, header.get(key));
                }
            }
            HttpResponse response = httpclient.execute(httpPut);
            return responseHandler(response);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 生成请求配置参数
     *
     * @return 请求配置实体
     */
    private RequestConfig getRequestConfig() {
        return RequestConfig.custom()
                .setConnectTimeout(5000)        // 一、连接超时：connectionTimeout-->指的是连接一个url的连接等待时间
                .setSocketTimeout(5000)         // 二、读取数据超时：SocketTimeout-->指的是连接上一个url，获取response的返回等待时间
                .setConnectionRequestTimeout(5000)
                .build();
    }

    /**
     * 处理请求返回值
     *
     * @param response 请求响应
     * @return 响应的内容, 为null表示请求失败
     * @throws IOException
     */
    private String responseHandler(HttpResponse response) throws IOException {
        int status = response.getStatusLine().getStatusCode();
//        if (status >= 200 && status < 300) {
            HttpEntity resEntity = response.getEntity();
            String entity = EntityUtils.toString(resEntity, "utf-8");
        System.out.println(entity);
            return entity;
//        } else return null;
    }
}
