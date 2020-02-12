package com.example.erreietocomecandodnv;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {

            init();
        }
        catch (Exception e){

        }
    }

    private OutputStream outputStream;
    private InputStream inStream;
    Object[] devices;
    List<String> names = new ArrayList<>();
    private void init() throws IOException {
        BluetoothAdapter blueAdapter = BluetoothAdapter.getDefaultAdapter();
        if (blueAdapter != null) {
            if (blueAdapter.isEnabled()) {

                Set<BluetoothDevice> bondedDevices = blueAdapter.getBondedDevices();
                for (BluetoothDevice device: bondedDevices) {
                    names.add(device.getName());
                }

                if (bondedDevices.size() > 0) {
                 devices = (Object[]) bondedDevices.toArray();
                                     ListView lv = (ListView) findViewById(R.id.list);
                    lv.setAdapter(new ArrayAdapter<Object>(this, R.layout.oi, names.toArray()));

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            BluetoothDevice device = (BluetoothDevice) devices[position];
                            ParcelUuid[] uuids = device.getUuids();
                            try{
                                BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
                                socket.connect();
                            }
                            catch (Exception e){
                            }

                            Log.e("device", device.toString());
                        }
                    });

                  /*  BluetoothDevice device = (BluetoothDevice) devices[position];
                    ParcelUuid[] uuids = device.getUuids();
                    BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuids[0].getUuid());
                    socket.connect();
                    outputStream = socket.getOutputStream();
                    inStream = socket.getInputStream();*/
                }

                Log.e("error", "No appropriate paired devices.");
            } else {
                Log.e("error", "Bluetooth is disabled.");
            }
        }
    }

    public void write(String s) throws IOException {
        outputStream.write(s.getBytes());
    }

    public void run() {
        final int BUFFER_SIZE = 1024;
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytes = 0;
        int b = BUFFER_SIZE;
    }
}