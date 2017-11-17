package com.demo.hope.config;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static java.lang.System.out;

public class ConfigJavaHttpClient {


    public static void main(String[] args) throws ExecutionException, InterruptedException {

         HttpClient httpClient = HttpClient.newHttpClient();
         CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(createConfigJavaHttpRequest(), HttpResponse.BodyHandler.asString());

         out.println(response.get().statusCode());
         out.println(response.get().body());

    }

   static HttpRequest createConfigJavaHttpRequest() {
        return HttpRequest.newBuilder().
                uri(URI.create("http://api.geonames.org/findNearByWeatherJSON?lat=59.91&lng=10.74&username=d4widk")).
                GET().
                build();
    }


    /** JAVA 8
     URL url = new URL("https://www.google.com/"); //Specify the URL
     URLConnection urlConnection = url.openConnection(); //Create the connection
     BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); //Create input stream and load into reader
     String inputLine;
     while ((inputLine = reader.readLine()) != null) { //Loop through and output each line in stream.
     System.out.println(inputLine);
     }
     reader.close(); //Close Reader
     */

}
