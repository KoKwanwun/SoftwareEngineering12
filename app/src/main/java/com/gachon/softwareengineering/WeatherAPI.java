package com.gachon.softwareengineering;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class WeatherAPI {
    //API 서비스 키
    private final String SERVICE_KEY = "hXscbGdYsGzEpYjEAL7kZP28p7SfJsLey6PtLZPpAdxZu26rQLSTsAwMkiM3HJh%2BRDEvFSx3P7xgMhJoBwGGzw%3D%3D";

    public float getTemperature() {
        return temperature;
    }

    public float getRainPerHour() {
        return rainPerHour;
    }

    public float getWind() {
        return wind;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getPrecipitation() {
        return precipitation;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public int getSky() { return sky; }


    //불러온 날씨 데이터
    private float temperature = 0.0F;
    private float maxTemp = 0.0F;
    private float rainPerHour = 0.0F;
    private float wind = 0.0F;
    private int humidity = 0;
    private int precipitation = 0;
    private int sky = 0;

    public WeatherAPI(){ }

    public void apiLoad() throws IOException {
        //API 요청
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="
                + SERVICE_KEY); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "="
                + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "="
                + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "="
                + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식 JSON*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "="
                + URLEncoder.encode(getDate(), "UTF-8")); /*발표 날짜*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "="
                + URLEncoder.encode(getTime(), "UTF-8")); /*정시단위*/
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "="
                + URLEncoder.encode("55", "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "="
                + URLEncoder.encode("127", "UTF-8")); /*예보지점의 Y 좌표값*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        //데이터 불러오기
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        //데이터 확인
        Log.d("data",sb.toString());

        //데이터 파싱
        try {
            parsing(sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="
                + SERVICE_KEY); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "="
                + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "="
                + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "="
                + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식 JSON*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "="
                + URLEncoder.encode(getDate(), "UTF-8")); /*발표 날짜*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "="
                + URLEncoder.encode("0200", "UTF-8")); /*정시단위*/
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "="
                + URLEncoder.encode("55", "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "="
                + URLEncoder.encode("127", "UTF-8")); /*예보지점의 Y 좌표값*/

        url = new URL(urlBuilder.toString());
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        //데이터 불러오기
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        sb = new StringBuilder();
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        //데이터 확인
        Log.d("data",sb.toString());

        //데이터 파싱
        try {
            parsing2(sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="
                + SERVICE_KEY); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "="
                + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "="
                + URLEncoder.encode("1000", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "="
                + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식 JSON*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "="
                + URLEncoder.encode(getDate(), "UTF-8")); /*발표 날짜*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "="
                + URLEncoder.encode(getTime(), "UTF-8")); /*정시단위*/
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "="
                + URLEncoder.encode("55", "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "="
                + URLEncoder.encode("127", "UTF-8")); /*예보지점의 Y 좌표값*/

        url = new URL(urlBuilder.toString());
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        //데이터 불러오기
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        sb = new StringBuilder();
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        //데이터 확인
        Log.d("data",sb.toString());

        //데이터 파싱
        try {
            parsing3(sb.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //JSON 파싱
    private void parsing(String strData) throws JSONException{
        JSONObject jsonData = new JSONObject(strData);
        JSONObject response = jsonData.getJSONObject("response");
        JSONObject body = response.getJSONObject("body");
        JSONObject items = body.getJSONObject("items");
        JSONArray itemArray = items.getJSONArray("item");

        JSONObject item;
        item = itemArray.getJSONObject(0);
        precipitation = Integer.parseInt(item.get("obsrValue").toString());

        item = itemArray.getJSONObject(1);
        humidity = Integer.parseInt(item.get("obsrValue").toString());

        item = itemArray.getJSONObject(2);
        rainPerHour = Float.parseFloat(item.get("obsrValue").toString());

        item = itemArray.getJSONObject(3);
        temperature = Float.parseFloat(item.get("obsrValue").toString());

        item = itemArray.getJSONObject(7);
        wind = Float.parseFloat(item.get("obsrValue").toString());
    }

    //JSON 파싱2
    private void parsing2(String strData) throws JSONException{
        JSONObject jsonData = new JSONObject(strData);
        JSONObject response = jsonData.getJSONObject("response");
        JSONObject body = response.getJSONObject("body");
        JSONObject items = body.getJSONObject("items");
        JSONArray itemArray = items.getJSONArray("item");

        JSONObject item;
        for(int i = 0; i < itemArray.length(); i++) {
            item = itemArray.getJSONObject(i);
            if(item.get("category").equals("TMX")){
                maxTemp = Float.parseFloat(item.get("fcstValue").toString());
                break;
            }
        }
    }

    //JSON 파싱2
    private void parsing3(String strData) throws JSONException{
        JSONObject jsonData = new JSONObject(strData);
        JSONObject response = jsonData.getJSONObject("response");
        JSONObject body = response.getJSONObject("body");
        JSONObject items = body.getJSONObject("items");
        JSONArray itemArray = items.getJSONArray("item");

        JSONObject item;
        for(int i = 0; i < itemArray.length(); i++) {
            item = itemArray.getJSONObject(i);
            if(item.get("category").equals("SKY")){
                sky = Integer.parseInt(item.get("fcstValue").toString());
                break;
            }
        }
    }

    //날짜 가져오기
    private String getDate(){
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");

        return date.format(today);
    }

    //시간 가져오기
    private String getTime(){
        Date today = new Date();
        TimeZone tz;

        SimpleDateFormat time = new SimpleDateFormat("HH", Locale.KOREAN);
        tz = TimeZone.getTimeZone("Asia/Singapore");  // TimeZone에 표준시 설정
        time.setTimeZone(tz);                    //time에 TimeZone 설정

        return time.format(today) + "00";
    }
}
