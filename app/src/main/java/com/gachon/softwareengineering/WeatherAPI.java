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
        String time = getTime();

        precipitation = Integer.parseInt(apiRequest("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst",
                time, "obsrValue", "PTY"));
        humidity = Integer.parseInt(apiRequest("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst",
                time, "obsrValue", "REH"));
        rainPerHour = Float.parseFloat(apiRequest("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst",
                time, "obsrValue", "RN1"));
        temperature = Float.parseFloat(apiRequest("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst",
                time, "obsrValue", "T1H"));
        wind = Float.parseFloat(apiRequest("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst",
                time, "obsrValue", "WSD"));

        maxTemp = Float.parseFloat(apiRequest("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst",
                "0200", "fcstValue", "TMX"));

        sky = Integer.parseInt(apiRequest("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst",
                time, "fcstValue", "SKY"));
    }

    private String apiRequest(String apiUrl, String time, String category, String itemCode) throws IOException{
        //API 요청
        StringBuilder urlBuilder = new StringBuilder(apiUrl); /*URL*/
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
                + URLEncoder.encode(time, "UTF-8")); /*정시단위*/
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "="
                + URLEncoder.encode("60", "UTF-8")); /*예보지점의 X 좌표값*/
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

        //데이터 파싱
        String result = "0";
        try {
            result = parsing(sb.toString(), category, itemCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    //JSON 파싱
    private String parsing(String strData, String category, String itemCode) throws JSONException{
        JSONObject jsonData = new JSONObject(strData);
        JSONObject response = jsonData.getJSONObject("response");
        JSONObject body = response.getJSONObject("body");
        JSONObject items = body.getJSONObject("items");
        JSONArray itemArray = items.getJSONArray("item");

        JSONObject item;

        String result = "0";
        for(int i = 0; i < itemArray.length(); i++) {
            item = itemArray.getJSONObject(i);
            if(item.get("category").equals(itemCode)){
                result = item.get(category).toString();
                break;
            }
        }

        return result;
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
