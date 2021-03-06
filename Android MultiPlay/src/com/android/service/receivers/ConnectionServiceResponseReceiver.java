package com.android.service.receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.dialogs.AlertDialogs;
import com.android.dialogs.mainactivity.MainActivityDialogList;
import com.android.extendedWidgets.ImageToggleButton;
import com.android.multiplay.R;
import com.android.services.ConnectionService;

public class ConnectionServiceResponseReceiver extends BroadcastReceiver {
	
	private static final String CLASS = "com.android.service.receivers.ConnectionServiceResponseReceiver";

	public static final String ACTION_RESP = CLASS + "ACTION_RESP";
	
	Activity activity = null;
	
	private ImageToggleButton b_wireless_network_switch = null;
	private ImageToggleButton b_bluetooth_switch = null;
	
	private int called_reason_id = 0;
	private boolean is_bluetooth_service_enabled = false;
	private boolean is_wireless_network_service_enabled = false;
	private boolean is_service_enabling = false;
	private boolean is_bluetooth_default_conf_available = false;
	private boolean is_wireless_network_default_conf_available = false;
	
	public ConnectionServiceResponseReceiver( Activity activity ) {
		this.activity = activity;
	}
	   
	@Override
	public void onReceive(Context context, Intent intent) {
		b_wireless_network_switch = (ImageToggleButton) activity.findViewById(R.id.b_wireless_network_switch);
		b_bluetooth_switch = (ImageToggleButton) activity.findViewById(R.id.b_bluetooth_switch);
		   
		 Log.i("ConnectionDevice", "recive");
		called_reason_id = intent.getIntExtra(ConnectionService.INPUT_DATA_CALLED_REASON,0);
	    is_service_enabling = intent.getBooleanExtra(ConnectionService.INPUT_DATA_CONNECTION_SWITH,false);
	    is_bluetooth_service_enabled = intent.getBooleanExtra(ConnectionService.OUTPUT_DATA_IS_BT_ENABLE,false);
	    is_bluetooth_default_conf_available = intent.getBooleanExtra(ConnectionService.OUTPUT_DATA_BT_DEFAULT_CONF_AVAILABLE,false);
	    is_wireless_network_service_enabled = intent.getBooleanExtra(ConnectionService.OUTPUT_DATA_IS_WIFI_ENABLE,false);
	    is_wireless_network_default_conf_available= intent.getBooleanExtra(ConnectionService.OUTPUT_DATA_WIFI_DEFAULT_CONF_AVAILABLE,false);

	      if ( called_reason_id == ConnectionService.INIT ) {
	    	  updateButtonStatus();
	    	  if ( b_bluetooth_switch.isToggle() ) {
	    		  if ( is_bluetooth_default_conf_available ) {
	    			  AlertDialogs.showDialog(
	    					  activity,
	    					  MainActivityDialogList.TAG_BLUETOOTH_CONNECTED,
	    					  null,
	    					  MainActivityDialogList.ID_TITLE_BLUETOOTH_CONNECTED,
	    					  MainActivityDialogList.ID_MESSAGE_BLUETOOTH_CONNECTED,
	    					  MainActivityDialogList.ID_BUTTON_OK,
	    					  null,
	    					  null);
	    		  } else {
	    			  AlertDialogs.showDialog(
	    					  activity,
	    					  MainActivityDialogList.TAG_BLUETOOTH_ENABLED,
	    					  null,
	    					  MainActivityDialogList.ID_TITLE_BLUETOOTH_ENABLED,
	    					  MainActivityDialogList.ID_MESSAGE_BLUETOOTH_ENABLED,
	    					  MainActivityDialogList.ID_BUTTON_OPTIONS,
	    					  null,
	    					  MainActivityDialogList.ID_BUTTON_CANCEL);
	    		  }
	    	  }
	      } else if ( called_reason_id == ConnectionService.BLUETOOTH ) {

	      } else if ( called_reason_id == ConnectionService.WIFI ) {

	      }
	    	 
	       
	      // Toast.makeText(activity, text, Toast.LENGTH_LONG).show();
	    }
	   
	   
	   


	private void updateButtonStatus() {
		b_bluetooth_switch.setBackgroundResource(
				(is_bluetooth_service_enabled) ? R.drawable.main_activity_button_on : R.drawable.main_activity_button_off);
		b_bluetooth_switch.setToggle(
				(is_bluetooth_service_enabled) ? true : false);
		b_wireless_network_switch.setBackgroundResource(
				(is_wireless_network_service_enabled) ? R.drawable.main_activity_button_on : R.drawable.main_activity_button_off);
		b_wireless_network_switch.setToggle(
				(is_wireless_network_service_enabled) ? true : false);
	}

	private static boolean isConnected(Context context) {
			ConnectivityManager connectivityManager = (ConnectivityManager)
			        context.getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo networkInfo = null;
		    if (connectivityManager != null) {
		        networkInfo =
		            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		    }
		    return networkInfo == null ? false : networkInfo.isConnected();
		}
		
		private static boolean isConnectedBT(Context context) {
		    ConnectivityManager connectivityManager = (ConnectivityManager)
		        context.getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo networkInfo = null;
		    if (connectivityManager != null) {
		        networkInfo =
		            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_BLUETOOTH);
		    }
		    return networkInfo == null ? false : networkInfo.isConnected();
		}
		
	}