package com.pcschool.mygooglemap;


import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

@SuppressWarnings("ALL")
public class OtherActivity extends ListActivity {
    private Sensor mSensor=null;
    private SensorEventListener mSensorEventListener;
    public static String a;
    private Context context;
    private SimpleAdapter adapter;
    private ArrayList<HashMap<String, Object>> itemList;
    private TextToSpeech tts;
    private TextView textView1;
    private SensorManager mSensorManager;
    String urlString = "google.navigation:q=Taronga+Zoo,+Sydney+Australia";
    private Runnable multiThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        // Step 1 :定義商品資料
        HashMap<String, Object> hamburger = new HashMap<>(); // 漢堡
        hamburger.put("name"   , "蔓越莓果汁"); // 商品名稱
        hamburger.put("price"  , 50); // 商品價格
        hamburger.put("image", R.drawable.mulberryconcjuice5); // 商品圖樣Id


        HashMap<String, Object> previous = new HashMap<>(); // 漢堡
        previous.put("name"   , "傳統柳丁"); // 商品名稱
        previous.put("price"  , 50); // 商品價格
        previous.put("image", R.drawable.precious); // 商品圖樣Id


        HashMap<String, Object> french = new HashMap<>(); // 薯條
        french.put("name"   , "雞蛋柳丁");
        french.put("price"  , 220);
        french.put("image", R.drawable.egg);

        HashMap<String, Object> coca = new HashMap<>(); // 可樂
        coca.put("name"   , "吉利柳丁");
        coca.put("price"  , 250);
        coca.put("image", R.drawable.jili);

        HashMap<String, Object> precious = new HashMap<>(); // 可樂
        precious.put("name"   , "珍品柳丁");
        precious.put("price"  , 250);
        precious.put("image", R.drawable.precious);

        // Step 2 :將商品放入到List集合容器中
        itemList = new ArrayList<>();
        itemList.add(hamburger);
        itemList.add(french);
        itemList.add(coca);
        itemList.add(precious);
        // Step 3 :建立SimpleAdapter適配器
        adapter = new SimpleAdapter(
                context,         // 設定接口環境
                itemList,     // 設定接口容器資料
                R.layout.row, // 資料顯示 UI XML
                new String[] {"name"   , "price"   , "image"},   // 商品資料標題
                new int[]    {R.id.name, R.id.price, R.id.image} // 資料 UI
        );

        // Step 4 :設定適配器
        setListAdapter(adapter);

        // Step 5 :註冊 OnItemClickListener
        getListView().setOnItemClickListener(new MyOnItemClickListener());
        mSensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        /*通过SensorManager获取相应的（温度传感器）Sensor类型对象*/
        mSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
    }
    private void createLanguageTTS() {
        // talkBtn = (Button) findViewById(R.id.talkBtn);

        if (tts == null) {
            tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int arg0) {
                    // TTS 初始化成功
                    if (arg0 == TextToSpeech.SUCCESS) {
                        // 指定的語系: 英文(美國)
                        // Locale l = Locale.US;  // 不要用 Locale.ENGLISH, 會預設用英文(印度)
                        //Locale.TAIWAN(locale.Traditional.Han.Taiwan);
                        // 目前指定的【語系+國家】TTS, 已下載離線語音檔, 可以離線發音
                        if (tts.isLanguageAvailable(Locale.CHINESE) == TextToSpeech.LANG_COUNTRY_AVAILABLE) {
                            tts.setLanguage(Locale.CHINESE);
                        }
                    }
                }
            }
            );
        }
    }
    // 列表項目點選之事件處理
    public class MyOnItemClickListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            // 取得被點選之商品資料
            HashMap<String, Object> item =
                    (HashMap<String, Object>)parent.getItemAtPosition(position);
            // 取出商品名稱, 價格
            String msg = "您選的是:" + item.get("name") +
                    ", 價格" + item.get("price");
            // Toast 顯示
         //   tts.speak("您選的是:", TextToSpeech.QUEUE_ADD, null);
            a = msg;
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

            Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=飛牛牧場&origin=國立雲林科技大學&waypoints=千巧股牧場&waypoints=幸福農場&travelmode=driving");

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
            mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
            startActivity(mapIntent);
            Thread thread = new Thread(multiThread);
            thread.start();

            final TextView text_view = (TextView) findViewById(R.id.tv);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 取得 EditText 資料
                    final EditText edit_text = (EditText) findViewById(R.id.edit_text);
                    String stringdata = edit_text.getText().toString();
                    // 清空 EditText
                    edit_text.post(new Runnable() {
                        public void run() {
                            edit_text.setText("");
                        }
                    });
                    // 將資料寫入資料庫
                    MysqlCon con = new MysqlCon();
                    con.insertData(stringdata);
                    // 讀取更新後的資料
                    String updata = con.getData();
                    Log.v("OK", updata);
                    text_view.post(new Runnable() {
                        public void run() {
                            text_view.setText(updata);
                        }
                    });

                }
            }).start();
        }
    }

    private String result1;
    private String result2;
    private Runnable mutiThread = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL("http://192.168.1.105/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.connect();

                int responseCode = connection.getResponseCode();

                if(responseCode == HttpURLConnection.HTTP_OK){
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null){
                        JSONArray dataJson = new JSONArray(line);
                        int i = dataJson.length()-1;
                        JSONObject info = dataJson.getJSONObject(i);
                        String time = info.getString("time");
                        String dB = info.getString("dB");
                        result1 = time.toString();
                        result2 = dB.toString();
                    }
                    inputStream.close();
                }
            } catch (Exception e){
                result1 = e.toString();
                result2 = e.toString();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  //  textView.setText(result1);
                    textView1.setText(result2);
                }
            });
    final SensorEventListener mySensorEventListener=new SensorEventListener() {


        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType()== Sensor.TYPE_TEMPERATURE){
                /*温度传感器返回当前的温度，单位是摄氏度（°C）。*/
                float ambient_temperature = event.values[0];
                textView1.setText("Ambient Temperature:\n " + String.valueOf(ambient_temperature) + getResources().getString(R.string.celsius));
                Toast.makeText(OtherActivity.this,"Ambient Temperature:\n " + String.valueOf(ambient_temperature) + getResources().getString(R.string.celsius) , Toast.LENGTH_SHORT).show();
                Log.i("Ambient Temperature:\n " + String.valueOf(ambient_temperature) + getResources().getString(R.string.celsius), event.values[0]+"");
                textView1.setText(String.valueOf(ambient_temperature)+"°C");

            }

        }



        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub

        }
    };
}};}