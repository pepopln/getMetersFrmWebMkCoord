package com.company;




import org.json.*;

import java.io.*;
import java.net.Socket;


public class Main {

    public static void main(String[] args) {


        try (
                Socket socket = new Socket("gis.sofiyskavoda.bg", 6080);
                PrintWriter out =
                        new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {
            out.write("GET /arcgis/rest/services/InfrastructureAlerts/MapServer/3/query?d=1478477069780&f=json&where=ACTIVESTATUS%20%3D%20%27Confirmed%27&returnGeometry=true&spatialRel=esriSpatialRelIntersects&outFields=*&outSR=102100 HTTP/1.0\n" +
                    "Host: gis.sofiyskavoda.bg:6080\n" +
                   // "Connection: keep-alive\n" +
                    "Origin: http://infocenter.sofiyskavoda.bg\n" +
                    "User-Agent: Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) " +
                    "Chrome/54.0.2840.71 Safari/537.36\n" +
                    "Content-Type: application/x-www-form-urlencoded\n" +
                    "Accept: */*\n" +
                    "Referer: http://infocenter.sofiyskavoda.bg/default.aspx?url=http://www.sofiyskavoda" +
                    ".bg/water_stops.aspx&e=&d=371\n");
                    //"Accept-Encoding: gzip, deflate, sdch\n" +
                    //"Accept-Language: bg-BG,bg;q=0.8");

            out.flush();
            String userInput = "";

            while ((userInput=stdIn.readLine())!=null) {
                out.println(userInput);
                for (int i = 0; i < 9; i++) {
                    System.out.println(in.readLine());
                }

                String json = in.readLine();


//                JSONArray jsonArrayFeatures=new JSONArray(json);
                JSONObject jsonObject = new JSONObject(json);


                JSONArray jsonArrayFeatures=jsonObject.getJSONArray("features");

                for (int i = 0; i < jsonArrayFeatures.length(); i++) {
//                    System.out.println(jsonArrayFeatures.get(i));
                    JSONObject getI = (JSONObject) jsonArrayFeatures.get(i);
                    JSONObject jsonObjectAttributes = (JSONObject) getI.get("attributes");
                    //JSONObject object = jsonArrayLocation.getJSONObject("LOCATION");
                    System.out.println(jsonObjectAttributes.get("ALERTTYPE"));
                    System.out.println(jsonObjectAttributes.get("DESCRIPTION"));
                    System.out.println(jsonObjectAttributes.get("LOCATION"));
                    System.out.println("START: "+jsonObjectAttributes.get("START_H")+":"+jsonObjectAttributes.get("START_M")+" END: "+jsonObjectAttributes.get("END_H")+":"+jsonObjectAttributes.get("END_M"));
                    JSONObject jsonObjectGeometry = (JSONObject) getI.get("geometry");
                    JSONArray jsonArrayRings = jsonObjectGeometry.getJSONArray("rings");
                    JSONArray jsonArrayRings0= (JSONArray) jsonArrayRings.get(0);

                    for (int j = 0; j < jsonArrayRings0.length()-1; j++) {
                        JSONArray jsonArrayRings1 = (JSONArray) jsonArrayRings0.get(j);
                        System.out.print(jsonArrayRings1.toString().toString());
                    }
                    System.out.println(jsonArrayRings0.toString());

                    System.out.println();



                }
                JSONObject get0 = (JSONObject) jsonArrayFeatures.get(0);

                //JSONArray jsonArray0 = get0.getJSONArray("attributes");
                System.out.println();

                System.out.println(jsonObject.toString());
                System.out.println(get0);


//must make object when starts with { and array when starts with [ and make fun :)


                }
//TODO: Replace all '[',']' and split by ','
                //TODO: Parse to Double then convert by formula (ASK google) and then print


            } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static double MetersToDecimalDegrees(Double degrees) {
        return 0;
    }

    private static void  getSubstring(int indexRings, String strRingsStart, String strRingsEnd, String json) {
        int index = json.indexOf(strRingsStart);
        int lastIndex = json.indexOf(strRingsEnd);
       String substring =json.substring(indexRings + strRingsStart.length(), lastIndex);
        System.out.println(substring);
    }


}