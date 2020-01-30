package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class App {
    public static void main(String[] args) throws Exception {

        init();

    }

    public static void init() {
        
        try {
            // API key by: https://api.nasa.gov/
            final String API_KEY = "b7bciIb4uywoczTjn7JZia5HV0EuQQe9pTuZCYVo";
            
            // "reader" receive the content of the request
            BufferedReader reader = new BufferedReader(new InputStreamReader(getRequest(API_KEY)));

            String inputLine;

            StringBuffer content = new StringBuffer();

            // "reader" will be read line by line and passed to StringBuffer "content"
            while ((inputLine = reader.readLine()) != null) {
                content.append(inputLine);
            }
            
            reader.close();
            
            // "content" converted to a JSON Object
            JSONObject jsonObjContent = new JSONObject(content.toString());

            // Values of "sol_keys" key in a JSONArray
            JSONArray jsonArrayOfSOLs = jsonObjContent.getJSONArray("sol_keys");

            // Print the available SOLs to pick
            System.out.println("Available SOLs to pick: " + jsonArrayOfSOLs.toString());

            String pickedSol = userInteract((jsonArrayOfSOLs));

            // If none has been chosen, the average temperature of the set of SOLs will be printed
            if (pickedSol.isEmpty()) {

                double avgTemperature = 0;
    
                for (int i = 0; i < jsonArrayOfSOLs.length(); i++) {
    
                    JSONObject SOL = jsonObjContent.getJSONObject(jsonArrayOfSOLs.get(i).toString());
                    JSONObject AT = SOL.getJSONObject("AT");
    
                    avgTemperature += AT.getDouble("av");
                }
    
                System.out.println("Average temperature: " + (avgTemperature /= jsonArrayOfSOLs.length()) + " Fahrenheit");
            } 
            // If a SOL has been chosen its average temperature will be printed
            else {
                JSONObject SOL = jsonObjContent.getJSONObject(pickedSol);
                JSONObject AT = SOL.getJSONObject("AT");

                System.out.println("Atmospheric Temperature of the SOL " + pickedSol + ": " + AT.getDouble("av") + " Fahrenheit");
            }

        } catch (Exception e) {
            System.err.println("Exception: " + e);
        }
    }

    /**
     * @param apiKeyParam
     * @return an input stream from an open connection
     * @throws IOException
     */
    public static InputStream getRequest(final String apiKeyParam) throws IOException {
        URL url = new URL("https://api.nasa.gov/insight_weather/?api_key=" + apiKeyParam + "&s&feedtype=json&ver=1.0");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        connection.disconnect();

        return connection.getInputStream();
    }

    /**
     * @param jsonArray
     * @return the user input if it's in the jsonArray or the user input is empty
     */
    @SuppressWarnings("resource")
	public static String userInteract(JSONArray jsonArray) {
        Scanner scannerInput = new Scanner(System.in);

        String userInput = "";
        System.out.println("Pick a VALID SOL of the list. Keep it blank to see the average temperature of the whole set: ");
        userInput = scannerInput.nextLine();

        boolean contains = jsonArray.toString().contains(userInput);

        if (contains || userInput.isEmpty()) {
            scannerInput.close();
            return userInput;
        }

        return userInteract(jsonArray);
    }
}