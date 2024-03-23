package com.pcschool.mygooglemap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.collection.ArraySet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings("ALL")
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, SensorEventListener {

    private static GoogleMap mMap;
    private Context context;
    private TextView textView1;
    private BroadcastReceiver receiver2 = null;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    public static final String MY_BROADCAST_TAG = "com.pcschool.mygooglemap2";
    private static final LatLng DEFAULT_TTS = new LatLng(23.836, 120.64);//松柏街
    private static final LatLng DEFAULT_TTS1 = new LatLng(24.925711, 121.176023);//埔心牧場
    private static final LatLng DEFAULT_TTS2 = new LatLng(23.802127, 121.462939);//新光兆豐休閒農場
    private static final LatLng DEFAULT_TTS3 = new LatLng(23.760679, 120.365768);//千巧股牧場
    private static final LatLng DEFAULT_TTS6 = new LatLng(23.69200, 120.5320);//斗六
    private static final String PROX_ALERT_INTENT = "";
    public static final String TAG = "wifidirectdemo";
    private static final long MARKER_UPDATE_INTERVAL = 3;
    private static final String SERVICE_URL = "";
    private static final Object LOG_TAG = 12131211;
    private static final LatLng DEFAULT_TTS4 = new LatLng(23.4792093, 121.3459565);//瑞穗牧場
    private static final LatLng DEFAULT_TTS5 = new LatLng(24.441272, 120.741292);//飛牛牧場
    private static final int MESSAGE_READ = 999;
    private static final String MY_BLOADCAST_TAG = "com.pcschool.mygooglemap2";
    private static final String ENDPOINT = "http://www.locahost.com/api";
    private static final LatLng DEFAULT_TTS7 = new LatLng(23.685,120.37);//土庫雲鄉生機農場
    private static final double POINT_RADIUS2 =500;
    private static final int REQUEST_CAMERA_PERMISSIONS = 1;
    public static Object photosAdp;
    private WFSearchProcess m_wtSearchProcess;
    private static final String Access_URL = "127.0.0.1";
    // private CreateAPProcess m_createAPProcess;
    public MapsActivity m_wiFiAdmin;
    private Sensor mTemperature;
    private WifiP2pManager manager;
    private String USB_PERMISSION = "xxxxxxxxxx.usb.permission";
    private PendingIntent mPrtPermissionIntent; //获取外设权限的意图
    Button btnWifiSearch;
    Button unBindButton;
    Button end;
    private boolean retryChannel = false;
    private final IntentFilter intentFilter = new IntentFilter();
    private TextView devicesInfo_3;
    private HashSet<String> msgList;
    private Button button,b,c;
    private TextView text;
    private Calendar LoadBroadcastManager;
    private Service mChatService;
    private ContentResolver mStreamConnection;
    private BroadcastReceiver mBroadcastReceiver;
    public static final int m_nWifiSearchTimeOut = 0;// 搜索超時
    public static final int m_nWTScanResult = 1;// 搜索到wifi返回結果
    public static final int m_nWTConnectResult = 2;// 連接上wifi熱點
    public static final int m_nCreateAPResult = 3;// 創建熱點結果
    public static final int m_nUserResult = 4;// 用戶上線人數更新命令(待定)
    public static final int m_nWTConnected = 5;// 點擊連接後斷開wifi，3.5秒後刷新adapter
    private LinearLayout m_linearLCreateAP; //創建熱點View
    private ProgressBar m_progBarCreatingAP; //創建熱點進度條
    private TextView m_textVPromptAP; //創建熱點進度條文字
    private boolean m_isFirstOpen = true;  //標記是否是第一次打開
    ArrayList m_listWifi = new ArrayList();//檢測到熱點信息列表
    private final UUID MyUUID=UUID.fromString("00002a02-0000-1000-8000-00805f9b34fb");
    private WifiP2pDevice wifiP2pDevice;
    private WifiP2pManager mWifiP2pManager;
    private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();
    private Object mCommandService;
    Button talkBtn;
    private static final int ENABLE_BLUETOOTH = 2;
    private BluetoothAdapter bluetoothAdapter;
    private TextToSpeech tts;
    private SQLiteDatabase db;
    private SimpleCursorAdapter adapter;
    private ArrayList<HashMap<String,String>> arrayList;
    private TextView tv;
    private URLConnection con;
    private String action;
    private Activity activity;
    private String orignalName;
    String topic;
    int qos;
    private Button button2;
    double lat;
    double lng;
    private double longitude;
    private double latitude;
    private final long MINIMUM_DISTANCECHANGE_FOR_UPDATE = 1;
    private final long MINIMUM_TIME_BETWEEN_UPDATE = 1000;
    private final long POINT_RADIUS = 400;
    private Location location;
    private final long PROX_ALERT_EXPIRATION = -1;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    private SensorManager mSensorManager;
    private static final String ACTION_USB_PERMISSION = "ACTION.USB_PERMISSION";
    UsbDevice device = null;
    private final UUID MY_UUID = UUID
            .fromString("00002a02-0000-1000-8000-00805f9b34fb");//和客戶端相同的UUID
    private final String NAME = "Bluetooth_Socket";
    private BluetoothServerSocket serverSocket;
    private BluetoothSocket socket;
    private InputStream is;//輸入流
    private static String address = "7C:2A:31:C7:4D:03";//要連接的藍芽設備MAC位址
    private final String TARGET_DEVICE_NAME = "PHIL-PC";
    //序列化user object
    //  String custom = gson.toJson(new User());
    private ServiceConnection connection;

    GoogleApiClient mGoogleApiClient;

    private ProximityIntentReceiver receiver;
    protected Handler mHandler;
    private ServiceConnection serviceConnection;
    private Cursor maincursor; // 記錄目前資料庫查詢指標

    MarkerOptions marker;
    private Button On,Off,Visible,list;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    private ListView lv;
    private static final int REQUEST_BLUETOOTH_PERMISSION=10;
    private TextView tv_search_result;
    private WifiManager wifi_mng;

    int[] values = {1, 2, 3};
    private TextView brokerSubPort;
    private TextView brokerSubHost;
    //請求打開藍芽
    private static final int REQUEST_ENABLE = 0x1;
    //請求能夠被搜索
    private static final int REQUEST_DISCOVERABLE = 0x2;

    private OutputStream outStream = null;
    private TextView subId;
    private String clientId;
    private LatLng latLng;
    private Intent intent;
    private UsbManager mUsbManager;
    private SensorEvent event;
    private UsbDevice mDevice;
    private PendingIntent permissionIntent;
    private UsbInterface inft;
    private int endpointCount;
    byte[] bytes = new byte[0];
    private UsbEndpoint mEndpointIn,mEndpointOUT;
    UsbDeviceConnection mUsbConnection;
    private BluetoothReceiver btreceiver;
    private ListView listmenu;

    private static int TIMEOUT = 0;
    private boolean forceClaim = true;
    private void requestBluetoothPermission(){
//判斷系統版本
        if (Build.VERSION.SDK_INT >= 23) {
//檢測當前app是否擁有某個許可權
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION);
//判斷這個許可權是否已經授權過
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
//判斷是否需要 向使用者解釋，為什麼要申請該許可權
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION))
                    Toast.makeText(this,"Need bluetooth permission.",
                            Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this ,new String[]
                        {Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_BLUETOOTH_PERMISSION);
                return;
            }else{

            }
        } else {
        }
    }
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    Runnable updateMarker = new Runnable() {
        @Override
        public void run() {

            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.c));

            handler2.postDelayed(this, MARKER_UPDATE_INTERVAL);
        }
    };
    private Handler handler2;
    private LatLng hereLocation;
    private ListView listView;

    //Bluetooth test
    private static final int REQUEST_ENABLE_BT = 2;
    private TextView devicesInfo_1, devicesInfo_2,temperaturelabel;
    private Button scanBoundBtn, scanBtn,disBtn;
    private BluetoothAdapter btAdapter;
    private Set<BluetoothDevice> devices;
    private WebView webView;
    @SuppressLint("MissingPermission")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        context = this;
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new MyLocationListener();
        textView1 = (TextView) findViewById(R.id.textView1);
        try {
            openGPS();
        } catch (Exception e) {
            e.printStackTrace();
        }
        m_wtSearchProcess = new WFSearchProcess(this);
        //創建Wifi熱點
        // m_createAPProcess = new CreateAPProcess(this);
        //wifi管理類

        unBindButton = (Button) findViewById(R.id.unbindBtn);
        TextView tv = (TextView) findViewById(R.id.tv);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //已配對藍芽設備
        devicesInfo_1 = (TextView)findViewById(R.id.devicesInfo_1);
        scanBoundBtn = (Button)findViewById(R.id.scanBoundBtn);
        scanBoundBtn.setOnClickListener(new BtnOnClickListener());

        //尋找藍芽設備
        devicesInfo_2 = (TextView)findViewById(R.id.devicesInfo_2);
        scanBtn = (Button)findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(new BtnOnClickListener());

        //開啟藍芽許可要求
        disBtn = (Button)findViewById(R.id.disBtn);
        disBtn.setOnClickListener(new BtnOnClickListener());

        btAdapter = BluetoothAdapter.getDefaultAdapter();

        if(btAdapter==null){
            Toast.makeText(this,"請開啟Bluetooth",Toast.LENGTH_SHORT).show();
            finish();
        }else if(!btAdapter.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,REQUEST_ENABLE_BT);
        }

        intent = new Intent(MapsActivity.this,ChronometerBindService.class);

        context = this;
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new MyLocationListener();
        textView1 = (TextView) findViewById(R.id.textView1);
        unBindButton = (Button) findViewById(R.id.unbindBtn);
        // openGPS();
        createLanguageTTS();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);

        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_TIME_BETWEEN_UPDATE, MINIMUM_DISTANCECHANGE_FOR_UPDATE, new MyLocationListener());

        addProximityAlert(DEFAULT_TTS);
        addProximityAlert(DEFAULT_TTS4);
        addProximityAlert4(DEFAULT_TTS6);
        addProximityAlert7(DEFAULT_TTS7);

        Button d = findViewById(R.id.ItemInfo);

        d.setText("商品資訊~");


        findId();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter==null){
            finish();
        }else if(!bluetoothAdapter.isEnabled()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent,ENABLE_BLUETOOTH);
        }
        arrayList = new ArrayList<HashMap<String,String>>();


        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //【英文】發音
                // tts.speak( et.getText().toString(), TextToSpeech.QUEUE_FLUSH, null );
                tts.speak("請登入商品頁面", TextToSpeech.QUEUE_ADD, null);

                //   Toast.makeText(context,  "此為商品資訊"+sb, Toast.LENGTH_LONG).show();
                Intent mainActivityIntent = new Intent(context, LoginActivity.class);  // 要啟動的Activity
                mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(mainActivityIntent);

            }
        });

//       mUsbConnection = mUsbManager.openDevice(mDevice);
        unBindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                wifi.setWifiEnabled(false);
                // unbindService(serviceConnection);
                Toast.makeText(context, "bind Service 已關閉", Toast.LENGTH_SHORT);
                unBindButton.setEnabled(false);
                stopService(intent);
            }
        });
        Button button1 = findViewById(R.id.button1);
        button1.setText("OPEN GPS");
        button1.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                wifi.setWifiEnabled(true);
                unBindButton.setEnabled(true);
                startService(intent);
                Uri uri = Uri.parse("https://examples.sencha.com/extjs/7.4.0/examples/admin-dashboard/?fbclid=IwAR0M2vHKcl5a7Fl5VR1oqlg8tZI9vyszHcgP88UNyuAa-GpqllSZ0NEmRBI#dashboard");
                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent2);

            }
        });
        button1 = findViewById(R.id.button1);
        button1.setText("OPEN GPS");
        button1.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                wifi.setWifiEnabled(true);
                unBindButton.setEnabled(true);
                startService(intent);
                Uri uri = Uri.parse("https://examples.sencha.com/extjs/7.4.0/examples/admin-dashboard/?fbclid=IwAR0M2vHKcl5a7Fl5VR1oqlg8tZI9vyszHcgP88UNyuAa-GpqllSZ0NEmRBI#dashboard");
                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent2);

            }
        });
        button2 = findViewById(R.id.saveData);
        button2.setText("Save Data");
        button2.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {

                webView.loadUrl(Access_URL);

            }
        });
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        if (!mBluetoothAdapter.isEnabled()) {

            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            Toast.makeText(this, "啟動Bluetooth中", Toast.LENGTH_LONG);
            finish();
        } else if(mBluetoothAdapter == null) {
            Toast.makeText(this,"不支援bluetooth",Toast.LENGTH_LONG);
            finish();
        }
        BluetoothDevice device = getPairedDevices();
        if (device == null) {
            // 注册广播接收器。
            // 接收系统发送的蓝牙发现通知事件。
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver, filter);

            if (mBluetoothAdapter.startDiscovery()) {
                Log.d(TAG, "搜索蓝牙设备...");
                mBluetoothAdapter.startDiscovery();

                btreceiver = new BluetoothReceiver();
                IntentFilter inFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                registerReceiver(btreceiver,inFilter);
            }
        } else {
            new ClientThread(device).start();
        }

    }

    private void findId() {

    }

    @SuppressLint("MissingPermission")
    private void addProximityAlert7(LatLng latLng) {
        Intent intent = new Intent(PROX_ALERT_INTENT);
        PendingIntent proximityIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        mLocationManager.addProximityAlert(latLng.latitude,
                latLng.longitude,
                POINT_RADIUS,
                PROX_ALERT_EXPIRATION,
                proximityIntent
        );

        IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT);
        receiver = new ProximityIntentReceiver();
        registerReceiver(receiver, filter);

    }

    @SuppressLint("MissingPermission")
    private void addProximityAlert4(LatLng latLng) {
        Intent intent = new Intent(PROX_ALERT_INTENT);
        PendingIntent proximityIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        mLocationManager.addProximityAlert(latLng.latitude,
                latLng.longitude,
                POINT_RADIUS,
                PROX_ALERT_EXPIRATION,
                proximityIntent
        );

        IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT);
        receiver = new ProximityIntentReceiver();
        registerReceiver(receiver, filter);

    }

    @SuppressLint("MissingPermission")
    private void addProximityAlert(LatLng latLng) {
        Intent intent = new Intent(PROX_ALERT_INTENT);
        PendingIntent proximityIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        mLocationManager.addProximityAlert(latLng.latitude,
                latLng.longitude,
                POINT_RADIUS,
                PROX_ALERT_EXPIRATION,
                proximityIntent
        );

        IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT);
        receiver = new ProximityIntentReceiver();
        registerReceiver(receiver, filter);
    }

    public void onClick(View view) throws Exception {
        openGPS();
        Intent bindIntent = new Intent(context, ChronometerBindService.class);
        bindService(bindIntent, serviceConnection, Context.BIND_ADJUST_WITH_ACTIVITY);
        Toast.makeText(context, "Bind Service已啟動!", Toast.LENGTH_SHORT);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://localhost:1841/#personnelview"));
        startActivity(browserIntent);
        unBindButton.setEnabled(true);
        button.setEnabled(false);
        tts.speak("開起WIFI", TextToSpeech.QUEUE_ADD, null);

    }

    public void onUnClick(View view,SensorEvent event) throws Exception {
        openGPS();
        textView1.setText("");
        unbindService(serviceConnection);
        Toast.makeText(context, "Bind Service已關閉!", Toast.LENGTH_SHORT);
        tts.speak("關閉WIFI", TextToSpeech.QUEUE_ADD, null);
        Uri uri = Uri.parse("https://examples.sencha.com/extjs/7.4.0/examples/kitchensink/?classic#big-data-grid");
        Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent2);
        unBindButton.setEnabled(false);
        button.setEnabled(true);
        startService(intent);
        this.event = event;

        float ambient_temperature = event.values[0];
        temperaturelabel.setText("Ambient Temperature:\n " + String.valueOf(ambient_temperature) + getResources().getString(R.string.celsius));
        Toast.makeText(MapsActivity.this,"Ambient Temperature:\n " + String.valueOf(ambient_temperature) + getResources().getString(R.string.celsius) , Toast.LENGTH_SHORT).show();
        Log.i("Ambient Temperature:\n " + String.valueOf(ambient_temperature) + getResources().getString(R.string.celsius), event.values[0]+"");

    }
    private void createLanguageTTS() {
        // talkBtn = (Button) findViewById(R.id.talkBtn);

        if (tts == null) {
            tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int arg0) {
                    // TTS 初始化成功
                    if (arg0 == TextToSpeech.SUCCESS) {
                        // 指定的語系:中文
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        this.event = event;

        float ambient_temperature = event.values[0];
        temperaturelabel.setText("Ambient Temperature:\n " + String.valueOf(ambient_temperature) + getResources().getString(R.string.celsius));
        Toast.makeText(MapsActivity.this,"Ambient Temperature:\n " + String.valueOf(ambient_temperature) + getResources().getString(R.string.celsius) , Toast.LENGTH_SHORT).show();
        Log.i("Ambient Temperature:\n " + String.valueOf(ambient_temperature) + getResources().getString(R.string.celsius), event.values[0]+"");
        temperaturelabel.setTextColor(Color.BLUE);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    private class BtnOnClickListener implements View.OnClickListener {

        @SuppressLint("MissingPermission")
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.scanBoundBtn:
                    devicesInfo_1.setText("");

                    devices = btAdapter.getBondedDevices();
                    if(devices.size() > 0) {
                        for(BluetoothDevice device : devices) {
                            devicesInfo_1.append(device.getName() + ":" +
                                    device.getAddress() + "==>" + device.getBondState() + "\n");
                        }
                    }

                    break;
                case R.id.scanBtn:
                    //devicesInfo_2.setText("");
                    btAdapter.startDiscovery();
                    receiver2 = new BTReceiver();
                    IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(receiver, filter);
                    Toast.makeText(MapsActivity.this, "Begin to scan", Toast.LENGTH_SHORT).show();
                    tts.speak("尋找藍芽設備", TextToSpeech.QUEUE_ADD, null);
                    break;
                case R.id.disBtn:
                    Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                    //  startActivity(discoverableIntent);
                    //startActivityForResult(discoverableIntent, REQUEST_ENABLE_BT);
                    Uri uri = Uri.parse("https://examples.sencha.com/extjs/7.4.0/examples/kitchensink/#view-options-grid");
                    Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent2);

                    Toast.makeText(MapsActivity.this, "開啟價目表", Toast.LENGTH_SHORT).show();
                    tts.speak("股市漲跌圖", TextToSpeech.QUEUE_ADD, null);
                    break;

            }
        }
    }
    public String creatingJsonString() throws JSONException, IOException {
        JSONArray pets = new JSONArray();
        pets.put("Pen");
        pets.put("pencilbox");
        JSONObject person = new JSONObject();

        person.put("name", "John Brown");
        person.put("age", 35);
        person.put("items", pets);
        OutputStreamWriter wr= new OutputStreamWriter(con.getOutputStream());
        wr.write(person.toString());
        return person.toString(2);
    }
    private class BTReceiver extends BroadcastReceiver {
        private WifiP2pManager.Channel channel;

        @SuppressLint("MissingPermission")
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();


            UsbInterface intf = device.getInterface(0);
            UsbEndpoint endpoint = intf.getEndpoint(0);
            UsbDeviceConnection connection = mUsbManager.openDevice(device);
            connection.claimInterface(intf, forceClaim);
            connection.bulkTransfer(endpoint, bytes, bytes.length, TIMEOUT); //do in another thread


            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                devicesInfo_2.append(device.getName() + ":" +
                        device.getAddress() + "==>" + device.getBondState() + "\n");
                String name = device.getName();
                if (name != null)
                    Log.d(TAG, "发现蓝牙设备:" + name);

                if (name != null && name.equals("PHIL-PC")) {
                    Log.d(TAG, "发现目标蓝牙设备，开始线程连接");
                    new Thread(new ClientThread(device)).start();

                    // 蓝牙搜索是非常消耗系统资源开销的过程，一旦发现了目标感兴趣的设备，可以关闭扫描。
                    mBluetoothAdapter.cancelDiscovery();
                }

            }
            //如果當前在搜尋，就先取消搜尋
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
            //開啟搜尋
            mBluetoothAdapter.startDiscovery();
            if(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals( action )){

            }else if(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals( action )){
                Log.d(Utils.LOGTAG,"WIFI_P2P_Connection_CHANGE_ACTION");

                if(manager==null){
                    return;
                }

                NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra( WifiP2pManager.EXTRA_NETWORK_INFO );

                if(networkInfo.isConnected()){
                    Log.d(Utils.LOGTAG,"We are connected");

                    manager.requestConnectionInfo( channel, new WifiP2pManager.ConnectionInfoListener() {
                        class ClientAsyncTask {
                            public ClientAsyncTask(Activity activity, String hostAddress) {
                            }

                            public void excute() {
                            }
                        }

                        @Override
                        public void onConnectionInfoAvailable(WifiP2pInfo info) {
                            //取得遠端裝置的IP位址
                            InetAddress address = info.groupOwnerAddress;

                            if(info.groupOwnerAddress!=null){
                                Log.d(Utils.LOGTAG,address.getHostAddress());
                            }
                            else{
                                Log.d(Utils.LOGTAG,"沒有發現Group owner ,無法連線");
                            }
                        }
                    } );

                }
                else{
                    Log.d(Utils.LOGTAG,"We are not connected");

                    manager.discoverPeers( channel,new WifiP2pManager.ActionListener(){

                        /**
                         * The operation succeeded
                         */
                        @Override
                        public void onSuccess() {
                            Log.d(Utils.LOGTAG,"Wifi 對點裝置掃描成功");
                        }
                        /**
                         * The operation failed
                         *
                         * @param reasonCode The reason for failure could be one of {@link },
                         *                or
                         */
                        @Override
                        public void onFailure(int reasonCode) {
                            Log.d(Utils.LOGTAG,"掃描失敗,錯誤碼:"+reasonCode);
                        }
                    } );
                }
            }

        }
    }
    private void openGPS() throws Exception {
        boolean gps = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean network = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Toast.makeText(context, "GPS:"+gps+",Network:"+network, Toast.LENGTH_SHORT).show();

        if(gps||network){
            return;
        }else{
            Intent gpsOptionsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(gpsOptionsIntent);

        }
        sendPost("http://localhost:1841/#personnelview","1",true);

    }
    private void register(){
        //注册在此service下的receiver的监听的action
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        intentFilter.addAction(UsbManager.ACTION_USB_ACCESSORY_ATTACHED);
        intentFilter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        intentFilter.addAction(USB_PERMISSION);
        registerReceiver(usbReceiver, intentFilter);//注册receiver


        //通知监听外设权限注册状态
        //PendingIntent：连接外设的intent
        //ask permission
        mPrtPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(USB_PERMISSION), 0);
    }

    private boolean configUsb(int paramInt){
        byte[] arrayOfByte = new byte[8];
        mUsbConnection.controlTransfer(192,95,0,0,arrayOfByte,8,1000);
        mUsbConnection.controlTransfer(64,161,0,0,null,0,1000);
        long l1 = 1532620800/paramInt;
        for(int i = 3;;i++){
            if((l1<65520L)||(i<0)){
                long l2 = 65536L -l1;
                int j = (short)(int)(0xFF00&l2|i);
                int k =  (short)(int)(0xFF&l2);
                mUsbConnection.controlTransfer(64,154,4882,j,null,0,1000);
                mUsbConnection.controlTransfer(64,154,3884,k,null,0,1000);
                mUsbConnection.controlTransfer(64,154,4882,j,null,0,1000);
                mUsbConnection.controlTransfer(192,161,9496,0,arrayOfByte,8,1000);

                mUsbConnection.controlTransfer(64,154,1304,80,null,0,1000);
                mUsbConnection.controlTransfer(64,161,20511,55562,null,0,1000);

                mUsbConnection.controlTransfer(64,154,4882,j,null,0,1000);
                mUsbConnection.controlTransfer(64,154,3884,k,null,0,1000);
                mUsbConnection.controlTransfer(64,154,0,0,null,0,1000);
                return true;
            }
            l1>>=3;

        }



    }
    private BroadcastReceiver usbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            mUsbConnection = mUsbManager.openDevice(mDevice);
            String action = intent.getAction();

            // USB注册动作
            if (USB_PERMISSION.equals(action)) {

                synchronized (this) {
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        mDevice = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                        if(mDevice!=null){
                            mUsbConnection = mUsbManager.openDevice(mDevice);
                            mUsbConnection.claimInterface(inft,true);
                            if(mUsbConnection!=null){
                                Log.d(TAG,"連接設備成功");
                                Toast.makeText(MapsActivity.this,"連接設備成功",Toast.LENGTH_SHORT);

                            }
                            configUsb(9600);
                            MyThread myThread = new MyThread();
                            myThread.start();
                        }
                    } else {
                        Toast.makeText(MapsActivity.this,"訪問被拒絕",Toast.LENGTH_SHORT);
                    }
                }
            }
            // USB拔插动作
            else if(UsbManager.ACTION_USB_ACCESSORY_ATTACHED.equals(action)
                    || UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)){

                search();
            }

        }
    };

    private void sendMessage(String msg){
        byte[] bytes = msg.getBytes();
        if(mUsbConnection!=null){
            int result = mUsbConnection.bulkTransfer(mEndpointOUT,bytes,bytes.length,3000);
            if(result<0){
                Log.d(TAG,"發送失敗");
            }else{
                Log.d(TAG,"發送成功");
            }

        }else{
            Log.d(TAG,"mUsbConnection --> nil");
        }
    }
    private void search(){
        mUsbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        HashMap<String,UsbDevice> deviceList = mUsbManager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        while(deviceIterator.hasNext()){
            mDevice = deviceIterator.next();
            getEndPoint();
            mUsbManager.requestPermission(mDevice,permissionIntent);
        }
    }

    private void getEndPoint() {
        inft = mDevice.getInterface(0);
        Log.d(TAG,"Interface Count:"+mDevice.getInterfaceCount());
        endpointCount = inft.getEndpointCount();
        for(int i = 0;i<endpointCount;i++){
            if(inft.getEndpoint(i).getType()== UsbConstants.USB_ENDPOINT_XFER_BULK){
                if(inft.getEndpoint(i).getDirection()==UsbConstants.USB_DIR_IN){
                    mEndpointIn = inft.getEndpoint(i);
                    Log.d(TAG,"獲取到mEndPoint");

                }else if(inft.getEndpoint(i).getDirection()==UsbConstants.USB_DIR_OUT){
                    mEndpointOUT = inft.getEndpoint(i);
                    Log.d(TAG,"獲取到EndPointOut");
                }
            }
        }
    }
    private final BroadcastReceiver usbPerMissionReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);

                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if(device != null){
                            //call method to set up device communication
                        }
                    }
                    else {
                        Log.d(TAG, "permission denied for device " + device);
                    }
                }
            }
        }
    };
    public static String sendPost(String url, String param, boolean isGBK) throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("Charset", "UTF-8");
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            //
            String charSetName = "UTF-8";
            if (isGBK) {
                charSetName = "GBK";
            } else {
                charSetName = "UTF-8";
            }
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), charSetName));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
            throw e;
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
    private class ClientThread extends Thread implements Runnable{
        private BluetoothDevice device;
        private FileInputStream bis;
        ServerSocket ss;
        Socket cs;

        public ClientThread(BluetoothDevice device) {
            this.device = device;
            this.ss = ss;
        }


        @Override
        public void run() {
            BluetoothSocket socket;
            try {
                socket = device.createRfcommSocketToServiceRecord(UUID.fromString(String.valueOf(MY_UUID)));
                cs = ss.accept();
                Thread thread = new Thread(new HTTPSession(cs));
                Log.d(TAG, "連接蓝牙服務端...");
                socket.connect();
                Log.d(TAG, "连接建立.");

                // 开始往服务器端发送数据。
                Log.d(TAG, "開始往藍芽服务器发送数据...");
                FileOutputStream fos = new FileOutputStream("image.jpg");

                int c = 0;
                byte[] buffer = new byte[1024];

                System.out.println("開始读数据...");
                while (true) {
                    c = bis.read(buffer);
                    if (c == -1) {
                        System.out.println("讀取數據结束");
                        break;
                    } else {
                        fos.write(buffer, 0, c);
                    }

                    sendDataToServer(socket);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        private class HTTPSession implements Runnable {
            public HTTPSession(Socket cs) {

            }
            /**
             * When an object implementing interface <code>Runnable</code> is used
             * to create a thread, starting the thread causes the object's
             * <code>run</code> method to be called in that separately executing
             * thread.
             * <p>
             * The general contract of the method <code>run</code> is that it may
             * take any action whatsoever.
             *
             * @see Thread#run()
             */
            @Override
            public void run() {}
        }
        private void sendDataToServer(BluetoothSocket socket) {
            try {
                FileInputStream fis = new FileInputStream(getFile());
                BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());

                byte[] buffer = new byte[1024];
                int c;
                while (true) {
                    c = fis.read(buffer);
                    if (c == -1) {
                        Log.d(TAG, "读取结束");
                        break;
                    } else {
                        bos.write(buffer, 0, c);
                    }
                }

                bos.flush();

                fis.close();
                bos.close();

                Log.d(TAG, "发送文件成功");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private File getFile() {
            File root = Environment.getExternalStorageDirectory();
            File file = new File(root, "b.jpg");
            return file;
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setMyLocationEnabled(true);
        drawCircle();
        drawCircle2();
        drawCircle3();
        drawCircle4();
        drawCircle5();
        drawCircle6();
        drawCircle7();
        drawCircle8();
        addMarker4();
        addMarker2();
        addMarker();

        // drawMarker();
        // addMarker();
        LatLng sydney2 = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions()
                .position(sydney2)
                .title("Marker in Sydney"));
        LatLng sydney3 = new LatLng(22.631392, 120.301803);
        mMap.addMarker(new MarkerOptions().position(sydney).title("捷運美麗島站"));

        //  mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        mMap.setMyLocationEnabled(true); // 右上角的定位功能；這行會出現紅色底線，不過仍可正常編譯執行
        mMap.getUiSettings().setZoomControlsEnabled(true);  // 右下角的放大縮小功能
        mMap.getUiSettings().setCompassEnabled(true);       // 左上角的指南針，要兩指旋轉才會出現
        mMap.getUiSettings().setMapToolbarEnabled(true);    // 右下角的導覽及開啟 Google Map功能

        Log.d(TAG, "最高放大層級：" + mMap.getMaxZoomLevel());
        Log.d(TAG, "最低放大層級：" + mMap.getMinZoomLevel());

        //       mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));     // 放大地圖到 16 倍大

        Runnable runnable3 = new Runnable() {
            @Override
            public void run() {
                addMarker4();

            }
        };
        runnable3.run();

        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));     // 放大地圖到 16 倍大
    }
    private BluetoothDevice getPairedDevices() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices != null && pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                // 把已经取得配对的蓝牙设备名字和地址打印出来。
                Log.d(TAG, device.getName() + " : " + device.getAddress());

                //如果已经发现目标蓝牙设备和Android蓝牙已经配对，则直接返回。
                if (TextUtils.equals(TARGET_DEVICE_NAME, device.getName())) {
                    Log.d(TAG, "已配對目標設備 -> " + TARGET_DEVICE_NAME);
                    return device;
                }
            }
        }

        return null;
    }
    public static void addMarker4() {
        LatLng latLng = DEFAULT_TTS2;
        MarkerOptions options = new MarkerOptions();
        options.position(latLng);
        options.title("新光兆豐休閒農場");
        options.snippet("經緯度" + latLng);
        options.anchor(0.5f, 1.0f);
        options.draggable(true);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.b));

        mMap.addMarker(options);
        //     mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

    }
    private void drawMarker(LatLng latLng) {
        MarkerOptions mo = new MarkerOptions();

        mo.position(latLng);
        mo.title("新地點");
        mo.snippet("經緯度"+latLng);
        mo.anchor(0.5f, 1.0f);
        mo.icon(BitmapDescriptorFactory.fromResource(R.drawable.c));
        mo.draggable(true);

        mMap.addMarker(mo);

    }
    public class MyThread extends Thread{
        private boolean isReceive = true;
        String message = null;
        byte[] bytes = new byte[5];

        @Override
        public void run(){
            super.run();
            while(isReceive){

                int i = mUsbConnection.bulkTransfer(mEndpointIn,bytes,0,bytes.length,3000);
                if(i<0){
                    Log.d(TAG,"沒收到數據");
                }
                else{
                    try{
                        message = new String(bytes,"UTF-8");
                        Log.d(TAG,"接收到數據"+message);
                        runOnUiThread(new Runnable(){

                            @Override
                            public void run() {
                                text.setText(message);
                            }
                        });
                        isReceive = false;
                    }catch(UnsupportedEncodingException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void buildGoogleApiClient() {
    }

    private void drawCircle() {
        CircleOptions options = new CircleOptions();
        options.center(DEFAULT_TTS7);
        options.radius(POINT_RADIUS);
        options.strokeWidth(5);
        options.strokeColor(Color.TRANSPARENT);
        options.fillColor(Color.argb(100, 0, 0, 255));
        options.zIndex(3);

        mMap.addCircle(options);
    }

    private void drawCircle2() {
        CircleOptions options = new CircleOptions();
        options.center(DEFAULT_TTS1);
        options.radius(POINT_RADIUS);
        options.strokeWidth(5);
        options.strokeColor(Color.TRANSPARENT);
        options.fillColor(Color.argb(100, 0, 0, 255));
        options.zIndex(3);

        mMap.addCircle(options);
    }

    public void drawCircle3() {
        CircleOptions options = new CircleOptions();
        options.center(DEFAULT_TTS2);
        options.radius(POINT_RADIUS);
        options.strokeWidth(5);
        options.strokeColor(Color.TRANSPARENT);
        options.fillColor(Color.argb(100, 0, 0, 255));
        options.zIndex(3);

        mMap.addCircle(options);
    }

    public void drawCircle4() {
        CircleOptions options = new CircleOptions();
        options.center(DEFAULT_TTS3);
        options.radius(POINT_RADIUS);
        options.strokeWidth(5);
        options.strokeColor(Color.TRANSPARENT);
        options.fillColor(Color.argb(100, 0, 0, 255));
        options.zIndex(3);

        mMap.addCircle(options);
    }

    public void drawCircle5() {
        CircleOptions options = new CircleOptions();
        options.center(DEFAULT_TTS4);
        options.radius(POINT_RADIUS);
        options.strokeWidth(5);
        options.strokeColor(Color.TRANSPARENT);
        options.fillColor(Color.argb(100, 0, 0, 255));
        options.zIndex(3);

        mMap.addCircle(options);
    }

    public void drawCircle6() {
        CircleOptions options = new CircleOptions();
        options.center(DEFAULT_TTS5);
        options.radius(POINT_RADIUS);
        options.strokeWidth(5);
        options.strokeColor(Color.TRANSPARENT);
        options.fillColor(Color.argb(100, 0, 0, 255));
        options.zIndex(3);

        mMap.addCircle(options);
    }
    public void drawCircle7() {
        CircleOptions options = new CircleOptions();
        options.center(DEFAULT_TTS6);
        options.radius(POINT_RADIUS);
        options.strokeWidth(5);
        options.strokeColor(Color.TRANSPARENT);
        options.fillColor(Color.argb(100, 0, 0, 255));
        options.zIndex(3);

        mMap.addCircle(options);
    }
    public void drawCircle8() {
        CircleOptions options = new CircleOptions();
        options.center(DEFAULT_TTS);
        options.radius(POINT_RADIUS2);
        options.strokeWidth(5);
        options.strokeColor(Color.TRANSPARENT);
        options.fillColor(Color.argb(100, 0, 0, 255));
        options.zIndex(3);

        mMap.addCircle(options);
    }
    public void addMarker() {
        LatLng latLng = DEFAULT_TTS1;
        MarkerOptions options = new MarkerOptions();
        options.position(latLng);
        options.title("埔心牧場");
        options.snippet("經緯度" + latLng);
        options.anchor(0.5f, 1.0f);
        options.draggable(true);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.b));
        mMap.addMarker(options);
        //     mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

    }

    private void addMarker2() {
        LatLng latLng = DEFAULT_TTS3;
        MarkerOptions options = new MarkerOptions();
        options.position(latLng);
        options.title("千巧古牧場");
        options.snippet("經緯度" + latLng);
        options.anchor(0.5f, 1.0f);
        options.draggable(true);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.b));

        mMap.addMarker(options);
        //     mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

    }



    private class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            LatLng latLng = new LatLng(location.getLatitude(),location.getLatitude());
            //   drawMarker(latLng);

            drawCircle();
            drawCircle2();
            drawCircle3();
            drawCircle4();
            drawCircle5();
            drawCircle8();
            addMarker4();
            drawMarker(latLng);
            // drawMarker();
            //  float distance = getDistanceBetween(latLng);
         /*   textView1.setText("Location-GPS"+"\n"+
                    "緯度-Latitude:"+location.getLatitude()+"\n"+
                    "經度-Longitude:"+location.getLongitude()+"\n"+
                    "精確度-Accuracy"+location.getAccuracy()+"\n"+
                    "標高-Altitude"+location.getAltitude()+"\n"+
                    "時間-Time"+new Date(location.getTime())+"\n"+
                    "速度-Speed"+location.getSpeedAccuracyMetersPerSecond()+"\n"+
                    "方位-Bearing"+location.getBearing()
            );*/
            setTitle("GPS位置資訊已更新");

            addMarker();
            addMarker2();

            MarkerOptions mp = new MarkerOptions();

            mp.position(new LatLng(location.getLatitude(), location.getLongitude()));

            mp.title("經度:"+location.getLatitude()+"緯度:"+location.getLongitude() );
            mp.icon(BitmapDescriptorFactory.fromResource(R.drawable.c));

            mMap.addMarker(mp);
            mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title("MyMarker"));
            //     mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
            //           new LatLng(location.getLatitude(), location.getLongitude()), 16));
        }
    }

    private class WFSearchProcess {
        public WFSearchProcess(MapsActivity mapsActivity) {
        }
    }

    private class BrocastReceiver {
    }

    private class BluetoothReceiver extends BroadcastReceiver {

        private ArraySet<HashMap<String, String>> arrayList;

        @Override
        public void onReceive(Context context, Intent intent) {
            tv.setText("");
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)){
                BluetoothDevice bluetoothdevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(bluetoothdevice.getBondState()!= BluetoothDevice.BOND_BONDED){
                    short rssi = intent.getExtras().getShort(BluetoothDevice.EXTRA_DEVICE);
                    tv.append("名字"+bluetoothdevice.getName()+"遠端藍芽信號強弱"+rssi+"位址:"+bluetoothdevice.getAddress()+"\n");
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    HashMap<String,String> map = new HashMap<String,String>();
                    map.put("arrayName",device.getName());
                    map.put("arrayAddress", device.getAddress());
                    arrayList.add(map);
                }else if(BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)){
                    BluetoothDevice device1 = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    switch(device1.getBondState()){
                        case BluetoothDevice.BOND_BONDING:
                            tv_search_result.setText("配對中");
                        case BluetoothDevice.BOND_BONDED:
                            tv_search_result.setText("配對完成");
                        case BluetoothDevice.BOND_NONE:
                            tv_search_result.setText("配對取消");
                        default:
                            break;


                    }

                }

            }
        }
        private void findId(){
            unBindButton=(Button)findViewById(R.id.button1);
        }
    }
}
