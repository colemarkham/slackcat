package net.ccmcomputing.slackcat;

import com.google.gson.Gson;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class SlackcatTest extends TestCase{
   /**
    * @return the suite of tests being tested
    */
   public static Test suite(){
      return new TestSuite(SlackcatTest.class);
   }

   /**
    * Create the test case
    *
    * @param testName
    *           name of the test case
    */
   public SlackcatTest(String testName){
      super(testName);
   }

   // public void testAllCodes() throws IOException, KeyManagementException,
   // NoSuchAlgorithmException, KeyStoreException{
   // SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, new
   // TrustStrategy(){
   //
   // @Override
   // public boolean isTrusted(final X509Certificate[] chain, final String
   // authType) throws CertificateException{
   // return true;
   // }
   // }).build();
   // try (CloseableHttpClient httpClient =
   // HttpClientBuilder.create().setSSLContext(sslContext).setSSLHostnameVerifier(new
   // NoopHostnameVerifier()).build()){
   // for(int code = 100; code <= 599; code++){
   // // URL url = new URL("https://http.cat/" + code);
   // System.out.println("Testing " + code);
   // HttpHead request = new HttpHead("https://http.cat/" + code);
   // try (CloseableHttpResponse response = httpClient.execute(request)){
   // int statusCode = response.getStatusLine().getStatusCode();
   // if(Arrays.binarySearch(Slackcat.HTTP_CAT_CODES, code) >= 0) {
   // assertTrue("Status code has no cat: " + code, statusCode == 200);
   // }else{
   // assertFalse("Missing status code that has a cat: " + code, statusCode ==
   // 200);
   // }
   // }
   // }
   // }
   //
   // }

   public void testBegining(){
      assertEquals("{\"text\":\"https://http.cat/404\"}", new Gson().toJson(Slackcat.handleMessage("404 me!")));
   }

   public void testBigNumber(){
      assertEquals("{}", new Gson().toJson(Slackcat.handleMessage("Give me 1000000 dollars!")));
   }

   public void testCommas(){
      assertEquals("{}", new Gson().toJson(Slackcat.handleMessage("Give me 100,000 dollars!")));
   }

   public void testDecimal(){
      assertEquals("{}", new Gson().toJson(Slackcat.handleMessage("Give me 100.00 dollars!")));
   }

   public void testEmptyInput(){
      assertEquals("{}", new Gson().toJson(Slackcat.handleMessage("")));
   }

   public void testEnd(){
      assertEquals("{\"text\":\"https://http.cat/404\"}", new Gson().toJson(Slackcat.handleMessage("Hey that's a 404")));
   }

   public void testNormalInput(){
      assertEquals("{\"text\":\"https://http.cat/404\"}", new Gson().toJson(Slackcat.handleMessage("Hey that's a 404!")));
   }

   public void testStandalone(){
      assertEquals("{\"text\":\"https://http.cat/404\"}", new Gson().toJson(Slackcat.handleMessage("You got a 404 request!")));
   }
}
