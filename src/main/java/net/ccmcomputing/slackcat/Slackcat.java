package net.ccmcomputing.slackcat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

class SlackMessage{
   private String text;
   private String username;

   public SlackMessage(String text){
      this.setText(text);
   }

   public String getText(){
      return text;
   }

   public String getUsername(){
      return username;
   }

   public void setText(String text){
      this.text = text;
   }

   public void setUsername(String username){
      this.username = username;
   }

}

class SlackRequest{
   private String text;
   private String user_name;

   public String getText(){
      return text;
   }

   public String getUser_name(){
      return user_name;
   }

   public void setText(String text){
      this.text = text;
   }

   public void setUser_name(String user_name){
      this.user_name = user_name;
   }

}

/**
 * Slackcat! A Slack webhook that listens for HTTP status codes and responds
 * with an appropriate image from http.cat.
 *
 */
public class Slackcat implements RequestHandler<SlackRequest, SlackMessage>{
   public static final int[] HTTP_CAT_CODES = {100, 101, 200, 201, 202, 204, 206, 207, 300, 301, 302, 303, 304, 305, 307, 400, 401, 402, 403, 404, 405, 406, 408, 409, 410, 411, 412, 413, 414, 415, 416, 417, 418, 422, 423, 424, 425, 426, 429, 431, 444,
                  450, 451, 500, 502, 503, 506, 507, 508, 509, 599};
   private static final Pattern PATTERN = Pattern.compile("(?<=\\s|^)[1-5]\\d{2}(?=[.!;:?]?(?:\\s|$))");

   private static boolean isWhitelistedUser(String user){
      List<String> patterns = getWhitelistPatterns();
      for(String pattern: patterns){
         if(Pattern.matches(pattern, user)) return true;
      }
      return false;
   }

   static List<String> getWhitelistPatterns(){
      ProcessBuilder processBuilder = new ProcessBuilder();
      String string = processBuilder.environment().get("WHITELIST_PATTERNS");
      if(string == null) return Collections.emptyList();
      List<String> patterns = Arrays.asList(string.split(","));
      return patterns;
   }

   public static SlackMessage handleMessage(String user, String incomingText){
      if(isWhitelistedUser(user)) return new SlackMessage(null);
      Matcher matcher = PATTERN.matcher(incomingText);
      if(matcher.find()){
         String match = matcher.group();
         try{
            int code = Integer.parseInt(match);
            if(Arrays.binarySearch(HTTP_CAT_CODES, code) >= 0) return new SlackMessage("https://http.cat/" + match);
         }catch(NumberFormatException e){
            e.printStackTrace();
         }
      }
      return new SlackMessage(null);
   }

   public SlackMessage handleRequest(SlackRequest request, Context context){
      return handleMessage(request.getUser_name(), request.getText());
   }

}
