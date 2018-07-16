package com.nttdata.weather.service.util.temperatureUtil;

import com.nttdata.weather.service.TemperatureService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class HttpClient {
    private final Logger log = LoggerFactory.getLogger(TemperatureService.class);

    public String resolveRequest(String apiKey, String data){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://dataservice.accuweather.com/" + data + "/v1/topcities/150.json?apikey=" + apiKey + "&details=true");
        CloseableHttpResponse response;
        try {
            response = httpClient.execute(httpget);
            //System.out.println(response.getProtocolVersion());
            //System.out.println(response.getStatusLine().getStatusCode());
            //System.out.println(response.getStatusLine().getReasonPhrase());
            log.debug(response.getStatusLine().toString());
            HttpEntity entity = response.getEntity();
            String string = EntityUtils.toString(entity);
            //System.out.println(string);
            return string;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
