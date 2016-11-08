package com.company;

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

                String strRingsStart = "\"rings\":[[[";
                String strRingsEnd = "]]]";

                String startTime = "START_H\":\"";
                String endTime = "\",\"CREATEDBY\"";

                String startLocation = "LOCATION\":";
                String endLocation=",\"ACTIVESTATUS\"";

               int indexRings =0;
                int indexStartTime=0;

                int indexLocation = 0;


                while (true){
                    indexLocation=json.indexOf(startLocation);
                    indexStartTime=json.indexOf(startTime);

                    indexRings=json.indexOf(strRingsStart);
                    int lastIndex = json.indexOf(strRingsEnd);
                    int indexLocationEnd =json.indexOf(endLocation);
                    int indexTime=json.indexOf(endTime);
                    if (indexRings==-1 ||indexLocation==-1||indexTime==-1){

                        break;
                    }
                    //location-It's ok
                    String location = json.substring(indexLocation+startLocation.length(),indexLocationEnd);
                    System.out.println(location);
                    //time must delete some strings
                    String time = json.substring(indexStartTime+startTime.length(), indexTime)
                            .replace("\",\"START_M\":\"",":")
                            .replace("\",\"END_H\":\""," End:")
                            .replace("\",\"END_M\":\"",":");
                    //time.replace("\",\"START_M\":\"",":");

                    System.out.println("Start: "+time);
                    //rings
                        String substring = json.substring(indexRings + strRingsStart.length(), lastIndex).replace("],["," ");

                    String[] twoDimentionalMeters = substring.split(" ");

                        json = json.substring(lastIndex + strRingsEnd.length());
                        indexRings = lastIndex + strRingsEnd.length();
                        System.out.println(twoDimentionalMeters.toString());

                }
//TODO: Replace all '[',']' and split by ','
                //TODO: Parse to Double then convert by formula (ASK google) and then print



            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void  getSubstring(int indexRings, String strRingsStart, String strRingsEnd, String json) {
        int index = json.indexOf(strRingsStart);
        int lastIndex = json.indexOf(strRingsEnd);
       String substring =json.substring(indexRings + strRingsStart.length(), lastIndex);
        System.out.println(substring);
    }



}