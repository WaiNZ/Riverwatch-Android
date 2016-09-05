package com.vuw.project1.riverwatch.bluetooth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.vuw.project1.riverwatch.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainBluetoothActivity extends BlunoLibrary {
	private Button buttonScan;
	private Button buttonTest;
	private Button buttonRetrieve;
	private Button buttonStatus;

    private TextView serialReceivedText;

    private String allData;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth);
        onCreateProcess();														//onCreate Process by BlunoLibrary

        serialBegin(115200);													//set the Uart Baudrate on BLE chip to 115200

        serialReceivedText=(TextView) findViewById(R.id.serialReveicedText);	//initial the EditText of the received data

        buttonTest = (Button) findViewById(R.id.buttonTest);		//initial the button for sending the data
        buttonTest.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				//serialSend(serialSendText.getText().toString());				//send the data to the BLUNO
                serialReceivedText.setText("");
				findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
				serialSend("Test");

			}
		});

		buttonRetrieve = (Button) findViewById(R.id.buttonRetrieve);		//initial the button for sending the data
		buttonRetrieve.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

                serialReceivedText.setText("");
                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
				serialSend("RetrieveData");
			}
		});

		buttonStatus = (Button) findViewById(R.id.buttonStatus);		//initial the button for sending the data
		buttonStatus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

                serialReceivedText.setText("");
                findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
				serialSend("Status");
			}
		});

        buttonScan = (Button) findViewById(R.id.buttonScan);					//initial the button for scanning the BLE device
        buttonScan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				buttonScanOnClickProcess();										//Alert Dialog for selecting the BLE device
			}
		});
	}

	protected void onResume(){
		super.onResume();
		System.out.println("BlUNOActivity onResume");
		onResumeProcess();														//onResume Process by BlunoLibrary
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		onActivityResultProcess(requestCode, resultCode, data);					//onActivityResult Process by BlunoLibrary
		super.onActivityResult(requestCode, resultCode, data);
	}
	
    @Override
    protected void onPause() {
        super.onPause();
        onPauseProcess();														//onPause Process by BlunoLibrary
    }
	
	protected void onStop() {
		super.onStop();
		onStopProcess();														//onStop Process by BlunoLibrary
	}
    
	@Override
    protected void onDestroy() {
        super.onDestroy();	
        onDestroyProcess();														//onDestroy Process by BlunoLibrary
    }

	@Override
	public void onConnectionStateChange(connectionStateEnum theConnectionState) {//Once connection state changes, this function will be called
		switch (theConnectionState) {											//Four connection state
		case isConnected:
			buttonScan.setText("Connected");
            buttonStatus.setEnabled(true);
            buttonRetrieve.setEnabled(true);
            buttonTest.setEnabled(true);
			break;
		case isConnecting:
			buttonScan.setText("Connecting");
			break;
		case isToScan:
			buttonScan.setText("Scan");
			break;
		case isScanning:
			buttonScan.setText("Scanning");
			break;
		case isDisconnecting:
			buttonScan.setText("isDisconnecting");
			break;
		default:
			break;
		}
	}

	@Override
	public void onSerialReceived(String data) {                            //Once connection data received, this function will be called

        findViewById(R.id.progressBar).setVisibility(View.GONE);
        // TODO Auto-generated method stub
        serialReceivedText.append(data);							//append the text into the EditText
        //The Serial data from the BLUNO may be sub-packaged, so using a buffer to hold the String is a good choice.
        //((ScrollView)serialReceivedText.getParent()).fullScroll(View.FOCUS_DOWN);

        allData += data;

        if (!allData.contains("[dataend]")) {
            //Log.i(TAG, "Current Message:  "+message);
        } else {
            final JSONObject json = WaterQualityCommands.formatRetiredData(allData);

            if (json != null) {
                try {
                    //Log.i(TAG, "Formatted message  " + json.toString());

                    String status = json.getString("status");

                    switch (status) {
                        case "complete":
                            //Handles on receiving data.

                            List<Sample> samples = WaterQualityCommands.makeReportList(json);
                            break;
                        default:
                            break;

                    }
                } catch (JSONException exception) {
                    //Log.e(TAG, exception.toString());
                    System.out.println("The Water Quality device is returning poorly formatted data.");
                }

            }
        }
    }

    private void parseData(){

        if(true){

        }

    }

}