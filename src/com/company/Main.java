package com.company;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.sun.org.apache.xml.internal.serialize.OutputFormat.Defaults.Encoding;

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
            //userInput = stdIn.readLine();
            while ((userInput=stdIn.readLine())!=null) {
                out.println(userInput);
                for (int i = 0; i < 9; i++) {
                    System.out.println(in.readLine());
                }

                String json = in.readLine();

                String strRings = "\"rings\":[[[";
                List<Double> dataMeters = new ArrayList<>();

               int index =0;
                while (true){
                    index=json.indexOf(strRings);
                    int lastIndex = json.indexOf("]]]");
                    if (index==-1 ){

                        break;
                    }
                        String substring = json.substring(index + 11, lastIndex);
                        json = json.substring(lastIndex + 3);
                        index = lastIndex + 3;
                        System.out.println(substring);

                }
//TODO: Replace all '[',']' and split by ','
                //TODO: Parse to Double then convert by formula (ASK google) and then print
                System.out.println(json);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}