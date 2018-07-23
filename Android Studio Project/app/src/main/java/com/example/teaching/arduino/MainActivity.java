package com.example.teaching.arduino;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static android.widget.Toast.makeText;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private LeDeviceListAdapter mLeDeviceListAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private static final long SCAN_PERIOD = 10000;
    private boolean mScanning;
    private Handler mHandler;
    private BluetoothGatt mGatt = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<BluetoothDevice> availableBT = new ArrayList<>();
        mLeDeviceListAdapter = new LeDeviceListAdapter(this, availableBT);
        final ListView listView_available = (ListView) findViewById(R.id.listview2);


        mHandler = new Handler();
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            makeText(this, "BLE Not Supported", Toast.LENGTH_SHORT).show();
            finish();
        }

        listView_available.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mGatt = null;
                connectToDevice(mLeDeviceListAdapter.getItem(position));
            }
        });

        Button scan = (Button) findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = makeText(getApplicationContext(), "Scanning...", Toast.LENGTH_SHORT);
                toast.show();

                mLeDeviceListAdapter.clear();
                scanLeDevice(true);
                listView_available.setAdapter(mLeDeviceListAdapter);
                
            }
        });

        final Button dc = (Button) findViewById(R.id.dc);
        dc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mGatt!=null){
                    mGatt.disconnect();
                    mGatt = null;
                    Toast tdc = makeText(getApplicationContext(),"Disconnected From Device", Toast.LENGTH_SHORT);
                    tdc.show();
                }else {
                    Toast tndc = makeText(getApplicationContext(),"No Device Is Connected", Toast.LENGTH_SHORT);
                    tndc.show();
                }
                mGatt = null;
            }
        });

        Button forward = (Button) findViewById(R.id.forward);
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGatt != null) {

                    BluetoothGattService service = mGatt.getService(UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb"));
                    BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb"));

                    characteristic.setValue("f".getBytes());
                    mGatt.writeCharacteristic(characteristic);
                }
            }
        });

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGatt != null) {

                    BluetoothGattService service = mGatt.getService(UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb"));
                    BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb"));

                    characteristic.setValue("b".getBytes());
                    mGatt.writeCharacteristic(characteristic);
                }
            }
        });

        Button left = (Button) findViewById(R.id.left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGatt != null) {

                    BluetoothGattService service = mGatt.getService(UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb"));
                    BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb"));

                    characteristic.setValue("l".getBytes());
                    mGatt.writeCharacteristic(characteristic);
                }
            }
        });

        Button right = (Button) findViewById(R.id.right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGatt != null) {

                    BluetoothGattService service = mGatt.getService(UUID.fromString("0000ffe0-1000-8000-00805f9b34fb"));
                    BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb"));

                    characteristic.setValue("r".getBytes());
                    mGatt.writeCharacteristic(characteristic);
                }
            }
        });

        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }


        private void scanLeDevice(final boolean enable) {
            if (enable) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mScanning=false;
                        mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    }
                 },SCAN_PERIOD);

                mScanning = true;
                mBluetoothAdapter.startLeScan(mLeScanCallback);

            }else {
                mScanning = false;
                mBluetoothAdapter.stopLeScan(mLeScanCallback);

            }
        }

        private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLeDeviceListAdapter.addDevice(device);
                        mLeDeviceListAdapter.notifyDataSetChanged();

                    }
                });
            }
        };

        public void connectToDevice(BluetoothDevice device) {
            if (mGatt == null) {
                scanLeDevice(false);
                mGatt = device.connectGatt(this, false, gattCallback);
            }
        }

        private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                Log.i("onConnectionStateChange", "Status: " + status);
                switch (newState) {
                    case BluetoothProfile.STATE_CONNECTED:
                        Log.i("gattCallback", "STATE_CONNECTED");
                        gatt.discoverServices();
                        break;
                    case BluetoothProfile.STATE_DISCONNECTED:
                        Log.e("gattCallback", "STATE_DISCONNECTED");
                        break;
                    default:
                        Log.e("gattCallback", "STATE_OTHER");
                }
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {

                List<BluetoothGattService> services = mGatt.getServices();
                Log.i("onServicesDiscovered", services.toString());
                gatt.readCharacteristic(services.get(0).getCharacteristics().get(0));
            }

            @Override
            public void onCharacteristicRead(final BluetoothGatt gatt, final BluetoothGattCharacteristic characteristic, int status) {
                Log.i("onCharacteristicRead", characteristic.toString());
            }
        };


}


