package com.almundo.browser.automation.utils;

import com.almundo.browser.automation.base.TestBaseSetup;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;
import org.openqa.selenium.Proxy;

import static com.almundo.browser.automation.utils.PageUtils.printSeparator;

/**
 * Created by gabrielcespedes on 04/08/17.
 */
public class SeleniumProxy extends TestBaseSetup{

    private static BrowserMobProxy proxy = null;

    /***************** Proxy Setup Methods *****************/

    public void setBrowserMobProxy(){
        logger.info("Setting Up Selenium Proxy Capture Types: [REQUEST_CONTENT] - [RESPONSE_CONTENT]");
        proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
        proxy.newHar("almundo.com");
    }

    public Proxy initSeleniumProxy(){
        logger.info("Setting Up Selenium Proxy.");
        proxy = new BrowserMobProxyServer();
        proxy.setTrustAllServers(true);
        proxy.start(9001);
        Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
        return seleniumProxy;
    }

    /***************** Proxy .Har Methods *****************/

    private String getRequestUrl(Har har, int i){
        String requestUrl = har.getLog().getEntries().get(i).getRequest().getUrl().toString();
        return requestUrl;
    }

    private void displayRequestBody(Har har, int i){
        printSeparator();
        try {
            logger.info("Body:" + har.getLog().getEntries().get(i).getRequest().getPostData().getText().toString());
        }catch(NullPointerException ouch){
            logger.info("NullPointerException while trying to get Request Body");
        }
    }

    private void displayRequestMethod(String requestMethod){
        printSeparator();
        logger.info("Method:" + requestMethod);
    }

    private void displayRequestUrl(String requestUrl){
        logger.info("URL: " + requestUrl);
    }

    private void displayStatusCode(Har har, int i){
        logger.info("Status Code:" + har.getLog().getEntries().get(i).getResponse().getStatusText().toString());
    }

    private void displayPostData(Har har, int i){
        try {
            logger.info("Post Data: " + har.getLog().getEntries().get(i).getRequest().getPostData().toString());
        }catch(NullPointerException ouch){
            logger.info("NullPointerException while trying to get Post Data");
        }
    }

    private void displayResponseData(Har har, int i){
        try {
            logger.info("Response Data: " + har.getLog().getEntries().get(i).getResponse().getContent().getText().toString());
        }catch(NullPointerException ouch){
            logger.info("NullPointerException while trying to get Response Data");
        }
    }

    private void iterateHar(Har har){
        int harSize = (har.getLog().getEntries().size()-1);
        String requestMethod, requestUrl;
        for(int i = harSize; i >= 0 ; i--){
            requestMethod = har.getLog().getEntries().get(i).getRequest().getMethod().toString();
            if(requestMethod.toString().equals("POST")) {
                requestUrl = getRequestUrl(har, i);
                if(requestUrl.contains("/checkout") && !requestUrl.contains("bam.nr-data.net")) {
                    displayRequestMethod(requestMethod);
                    displayRequestUrl(requestUrl);
                    displayRequestBody(har, i);
                    displayStatusCode(har, i);
                    displayPostData(har, i);
                    displayResponseData(har, i);
                }
            }
        }
        printSeparator();
    }

    public void displayHarInfo(){
        Har har = proxy.getHar();
        logger.info("Total Number of Requests: " + har.getLog().getEntries().size() + 1);
        logger.info("Displaying .har file info for POST methods");
        iterateHar(har);
    }

}
