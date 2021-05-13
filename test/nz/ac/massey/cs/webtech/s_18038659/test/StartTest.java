/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nz.ac.massey.cs.webtech.s_18038659.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 18038659
 */
public class StartTest {

    private static final String path = "assignment2_server_18038659/"; //TODO: Change to your setup
    private static int port = 8080;
    String sessionID;

    public StartTest() {
  
    }

    @Before
    public void setUpClass() {
        
    }

    @After
    public void tearDownClass() {
    }




    @Test
    public void StartTest() throws Exception {

        assertEquals(statusRequest("jack/start"), HttpStatus.SC_OK);// check if session is creaded when jack/start is called
        assertNotEquals(makeServiceRequest("jack/start"), makeServiceRequest("jack/start")); // check if a new session is created ech time jack/start is called
    }

    private Object statusRequest(String expression) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();

        URI url = constructUri(expression);
        HttpGet get = new HttpGet(url);

        //HttpGet get = new HttpGet("http://localhost:8080/assignment2_server_18038659/jack/start");
        CloseableHttpResponse response = client.execute(get);
        return response.getStatusLine().getStatusCode();
    }

    private String makeServiceRequest(String expression) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
   
        URI url = constructUri(expression);
        HttpGet get = new HttpGet(url);

        //HttpGet get = new HttpGet("http://localhost:8080/assignment2_server_18038659/jack/start");
        CloseableHttpResponse response = client.execute(get);
      
        //if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK);
        
        Header[] header = response.getHeaders("Set-Cookie");
        String arr[] = header[0].getValue().split(";");
        sessionID = arr[0];
        return sessionID;

    }

    private static URI constructUri(String expression) throws Exception {

        URI url = null;
        try {
            url = new URIBuilder()
                    .setScheme("http")
                    .setHost("localhost")
                    .setPort(port)
                    .setPath(path+expression)
                    //.setParameter("expression", expression)
                    .build();
        } catch (URISyntaxException ex) {
            throw new Exception("Error creating service URI", ex);
        }
        return url;
    }

}
