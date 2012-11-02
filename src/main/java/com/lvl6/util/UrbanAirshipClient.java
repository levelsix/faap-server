package com.lvl6.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * Format for tags:
 * 
 * udid:<UDID> - tag for this specific udid
 * <language>:<segment> - tag for this specific language and segment
 * all:<segment> - tag added for this specific segment, all languages
 * <language>:all - tag added for this specific language, all segments
 * all:all - tag added to all, as another form of broadcast
 * 
 */
// http://urbanairship.com/docs/push.html
public class UrbanAirshipClient {
    public static Logger logger = Logger.getLogger(UrbanAirshipClient.class);

    private static String baseURL = "https://go.urbanairship.com/api/";
    
    private static String apiKey = "r-flBhXcSJenV99plArdAg";
    private static String secret = "GGlJ8kQTQ-2HPgDeTfinGQ";
    private static String masterSecret = "47FUKd76Q32CaQ491oDwbA";
    
    private HttpClient registrationClient;
    private HttpClient pushClient;
    
    private static UrbanAirshipClient _theClient;

    public UrbanAirshipClient()
    {
        initHttpClient();
    }
    
    static public UrbanAirshipClient getClient() {
        if (_theClient == null)
            _theClient = new UrbanAirshipClient();
        
        return _theClient;
    }
    
    private void initHttpClient()
    {
        HttpConnectionManagerParams connectionManagerParams = new HttpConnectionManagerParams();
        connectionManagerParams.setDefaultMaxConnectionsPerHost(30);

        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.setParams(connectionManagerParams);
        
        pushClient = new HttpClient(connectionManager);
        pushClient.getParams().setParameter("http.socket.timeout", new Integer(2 * 1000)); // milliseconds
        pushClient.getParams().setParameter("http.connection-manager.timeout", new Long(2 * 1000)); // milliseconds
        
        // sends auth first as an optimization
        pushClient.getParams().setAuthenticationPreemptive(true);

        Credentials pushCreds = new UsernamePasswordCredentials(apiKey, masterSecret);
        
        // always send the credentials regardless of host..
        // safe to do, since this client will only ever talk to urban airship
        pushClient.getState().setCredentials(AuthScope.ANY, pushCreds);
        
        registrationClient = new HttpClient(connectionManager);
        registrationClient.getParams().setParameter("http.socket.timeout", new Integer(2 * 1000)); // milliseconds
        registrationClient.getParams().setParameter("http.connection-manager.timeout", new Long(2 * 1000)); // milliseconds
        
        // sends auth first as an optimization
        registrationClient.getParams().setAuthenticationPreemptive(true);

        Credentials registrationCreds = new UsernamePasswordCredentials(apiKey, secret);
        
        // always send the credentials regardless of host..
        // safe to do, since this client will only ever talk to urban airship
        registrationClient.getState().setCredentials(AuthScope.ANY, registrationCreds);
        
        // http://hc.apache.org/httpclient-3.x/exception-handling.html#Custom%20exception%20handler
        HttpMethodRetryHandler myretryhandler = new HttpMethodRetryHandler()
        {
            public boolean retryMethod(
                    final HttpMethod method,
                    final IOException exception,
                    int executionCount)
            {
                if (executionCount >= 5)
                {
                    // Do not retry if over max retry count
                    return false;
                }
                /*
                if (exception instanceof NoHttpResponseException) {
                    // Retry if the server dropped connection on us
                    return true;
                }
                */
                if (!method.isRequestSent())
                {
                    // Retry if the request has not been sent fully or
                    // if it's OK to retry methods that have been sent
                    return true;
                }
                // otherwise do not retry
                return false;
            }
        };

        registrationClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, myretryhandler);
        pushClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, myretryhandler);
    }
    
    public void registerDevice(String udid, String deviceToken, List<String> tags) {
        PutMethod method = new PutMethod(baseURL + "device_tokens/" + deviceToken.toUpperCase());
        
        StringRequestEntity body = null;
        
        /*
            {
            "aliases": [
                "user1",
                "user2"
            ],
            "tags": [
                "tag1",
                "tag2"
            ],
            "aps": {
                 "badge": "+1",
                 "alert": message,
            }
        }
         */
        
        ArrayList<String> aliases = new ArrayList<String>();
        aliases.add(udid);
        
        // only set tags for the token
        HashMap<String, Object> bodyMap = new HashMap<String, Object>();
        bodyMap.put("tags", tags);
        bodyMap.put("aliases", aliases);
        
        method.addRequestHeader("content-type", "application/json");
        
        try {
            body = new StringRequestEntity(new Gson().toJson(bodyMap), "text/plain", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        method.setRequestEntity(body);
        
        try {
            int status = registrationClient.executeMethod(method);
            
            String response = method.getResponseBodyAsString();
            System.out.println(status + ":" +response);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public void registerDevice(String udid, String deviceToken,
            String segmentId, String language) {
        
        ArrayList<String> tags = new ArrayList<String>();
        
        // add a tag for just this UDID
        tags.add("udid:"+udid);
        // tag for all
        tags.add("all:all");
        
        this.registerDevice(udid, deviceToken, tags);
    }
    
    public void pushToTag(String tag, String message) {
        PostMethod method = new PostMethod(baseURL + "push/");
        
        StringRequestEntity body = null;
        
        /*
        {
            "tags": [
                "tag1",
                "tag2"
            ],
            "aps": {
                 "badge": "+1",
                 "alert": message,
            }
        }
         */
        
        // only set tags for the token
        HashMap<String, Object> bodyMap = new HashMap<String, Object>();
        ArrayList<String> tags = new ArrayList<String>();
        tags.add(tag);
        
        HashMap<String, String> apsMap = new HashMap<String, String>();
        // +1 is auto-increment badge count
        apsMap.put("badge", "+1");
        apsMap.put("alert", message);
        
        bodyMap.put("tags", tags);
        bodyMap.put("aps", apsMap);
        
        method.addRequestHeader("content-type", "application/json");

        try {
            body = new StringRequestEntity(new Gson().toJson(bodyMap), "text/plain", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        method.setRequestEntity(body);
        
        try {
            int status = pushClient.executeMethod(method);
            
            String response = method.getResponseBodyAsString();
            System.out.println(status + ":" +response);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public static void main(String[] args) {
        String udid = args[0];
        String token = args[1];
        
        ArrayList<String> tags = new ArrayList<String>();
        tags.add("test:tag1");
        tags.add("test:tag2");
        
        getClient().registerDevice(udid, token, tags);
        getClient().pushToTag("test:tag1", "test push notification");
        
    }
    
}
