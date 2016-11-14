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
//must make object when starts with { and array when starts with [ and make fun :)



                JSONObject jsonObject = new JSONObject(json);


                JSONArray jsonArrayFeatures=jsonObject.getJSONArray("features");

                for (int i = 0; i < jsonArrayFeatures.length(); i++) {

                    JSONObject getI = (JSONObject) jsonArrayFeatures.get(i);
                    JSONObject jsonObjectAttributes = (JSONObject) getI.get("attributes");
                    System.out.println(jsonObjectAttributes.get("ALERTTYPE"));
                    System.out.println(jsonObjectAttributes.get("DESCRIPTION"));
                    System.out.println(jsonObjectAttributes.get("LOCATION"));
                    System.out.println("START: "
                            +jsonObjectAttributes.get("START_H")
                            +":"
                            +jsonObjectAttributes.get("START_M")
                            +" END: "
                            +jsonObjectAttributes.get("END_H")
                            +":"
                            +jsonObjectAttributes.get("END_M"));
                    JSONObject jsonObjectGeometry = (JSONObject) getI.get("geometry");
                    JSONArray jsonArrayRings = jsonObjectGeometry.getJSONArray("rings");
                    JSONArray jsonArrayRings0= (JSONArray) jsonArrayRings.get(0);

                    for (int j = 0; j < jsonArrayRings0.length(); j++) {
                        JSONArray jsonArrayRings1 = (JSONArray) jsonArrayRings0.get(j);
                        System.out.println(jsonArrayRings1.toString());
                        double x =  jsonArrayRings1.getDouble(0);
                        double y = jsonArrayRings1.getDouble(1);
                        int earthRadius = 6371*1000;

                        double deviation = 0.777799;


                        double alfa = (((y / earthRadius * Math.cos(x / earthRadius)) * (180 / Math.PI))) - deviation;
                        double beta = ((x / earthRadius) * (180 / Math.PI));
                        System.out.println(alfa+","+beta);
//                        double easting = jsonArrayRings1.getDouble(0);
//                        double northing = jsonArrayRings1.getDouble(1);
//                        OSRef osRef = new OSRef(easting, northing);
//                        LatLng latLng = osRef.toLatLng();
//
//                        double latitude = latLng.getLat();
//                        double longitude = latLng.getLng();
//                        System.out.println(latitude+","+longitude);
                    }
                    //System.out.println(jsonArrayRings0.toString());

                    System.out.println();



                }




                break;


                }

            } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




}