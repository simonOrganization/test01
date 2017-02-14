package com.zfxf.admin.douniu.tools;

import android.Manifest.permission;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ToolsUtils {
	//判断是否有网
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);  
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();  
        if(networkInfo != null && networkInfo.isAvailable()){  
        	return true;//当前有可用网络  
        }  
        else{  
        	return false;//当前无可用网络 
        } 
	}
	//判断是否wife
	public static boolean isWifi(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkINfo = cm.getActiveNetworkInfo();
		if (networkINfo != null&& networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}
	//判断是否4G
	public static boolean isMobile(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkINfo = cm.getActiveNetworkInfo();
		if (networkINfo != null&& networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			return true;
		}
		return false;
	}
	public static String getDeviceID(){
		return "zhaojp";
	}

	
	/**
  	 * 获取设备的唯一标识
  	 */
  	public static String getDeviceID(Context context){
  	//设备的IMEI（除非你有一个没有量产的手机（水货）它可能有无效的IMEI，如：0000000000000）。
			TelephonyManager TelephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE); 
			String imei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE
			//设备的硬件信息
			String DevIDShort = "35" + //we make this look like a valid IMEI 
					Build.BOARD.length()%10 + Build.BRAND.length()%10 + 
					Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10 +
					Build.DISPLAY.length()%10 + Build.HOST.length()%10 + 
					Build.ID.length()%10 + Build.MANUFACTURER.length()%10 + 
					Build.MODEL.length()%10 + Build.PRODUCT.length()%10 + 
					Build.TAGS.length()%10 + Build.TYPE.length()%10 + 
					Build.USER.length()%10 ; //13 digits 
			//The WLAN MAC Address string, 是另一个唯一ID。
			//但是你需要为你的工程加入android.permission.ACCESS_WIFI_STATE 权限，否则这个地址会为null。
			WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE); 
			String WlanMac = wm.getConnectionInfo().getMacAddress();
			//The BT MAC Address string, 只在有蓝牙的设备上运行。
			//并且要加入android.permission.BLUETOOTH 权限.
			BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter 
			m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); 
			String BtMac = m_BluetoothAdapter.getAddress();

			//对获取的四种设备ID进行拼接，并且用拼接后的组合ID计算出的MD5值作为设备的唯一标识。
			String m_szLongID = imei + DevIDShort + WlanMac + BtMac; 
			// compute md5 
			MessageDigest m = null; 
			try {
				m = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace(); 
			} 
			m.update(m_szLongID.getBytes(),0,m_szLongID.length()); 
			// get md5 bytes 
			byte p_md5Data[] = m.digest(); 
			// create a hex string 
			String m_szUniqueID = new String(); 
			for (int i=0;i<p_md5Data.length;i++) { 
				int b = (0xFF & p_md5Data[i]); 
				// if it is a single digit, make sure it have 0 in front (proper padding) 
				if (b <= 0xF) 
					m_szUniqueID+="0"; 
				// add number to string 
				m_szUniqueID+=Integer.toHexString(b); 
			} // hex string to uppercase 
			m_szUniqueID = m_szUniqueID.toUpperCase();

			//  		通过以上算法，可产生32位的16进制数据:
			//  		9DDDF85AFF0A87974CE4541BD94D5F55
			return m_szUniqueID;
  	}  	
  	/*
  	 * 获取versionCode
  	 */
  	public static int getVersionCode(Context context)//获取版本号(内部识别号)  
  	{  
  	    try {  
  	        PackageInfo pi=context.getPackageManager().getPackageInfo(context.getPackageName(), 0);  
  	        return pi.versionCode;  
  	    } catch (NameNotFoundException e) {  
  	        e.printStackTrace();  
  	        return 0;  
  	    }  
  	}  

	public static boolean checkPermission(Context context, String permission) {
	    boolean result = false;
	    if (Build.VERSION.SDK_INT >= 23) {
	        try {
	            Class clazz = Class.forName("android.content.Context");
	            Method method = clazz.getMethod("checkSelfPermission", String.class);
	            int rest = (Integer) method.invoke(context, permission);
	            if (rest == PackageManager.PERMISSION_GRANTED) {
	                result = true;
	            } else {
	                result = false;
	            }
	        } catch (Exception e) {
	            result = false;
	        }
	    } else {
	        PackageManager pm = context.getPackageManager();
	        if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
	            result = true;
	        }
	    }
	    return result;
	}
	public static String getDeviceInfo(Context context) {
	    try {
	        org.json.JSONObject json = new org.json.JSONObject();
	        TelephonyManager tm = (TelephonyManager) context
	                .getSystemService(Context.TELEPHONY_SERVICE);
	        String device_id = null;
	        if (checkPermission(context, permission.READ_PHONE_STATE)) {
	            device_id = tm.getDeviceId();
	        }
	        String mac = null;
	        FileReader fstream = null;
	        try {
	            fstream = new FileReader("/sys/class/net/wlan0/address");
	        } catch (FileNotFoundException e) {
	            fstream = new FileReader("/sys/class/net/eth0/address");
	        }
	        BufferedReader in = null;
	        if (fstream != null) {
	            try {
	                in = new BufferedReader(fstream, 1024);
	                mac = in.readLine();
	            } catch (IOException e) {
	            } finally {
	                if (fstream != null) {
	                    try {
	                        fstream.close();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }
	                if (in != null) {
	                    try {
	                        in.close();
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                }
	            }
	        }
	        json.put("mac", mac);
	        if (TextUtils.isEmpty(device_id)) {
	            device_id = mac;
	        }
	        if (TextUtils.isEmpty(device_id)) {
	            device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
	                    android.provider.Settings.Secure.ANDROID_ID);
	        }
	        json.put("device_id", device_id);
	        return json.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	
	/**
  	 * 判断文件是否存在
  	 * @param strFile 文件存放的路径
  	 * @return
  	 */
	public static boolean isFileExists(String strFile) {
		try {
			File f = new File(strFile);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static int getStatusHeight(Context context) {

		int statusHeight = -1;
		try {
			Class clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusHeight;
	}
//	/*
//	 * 检验自动更新方法
//	 */
//	public static void autoUpdate(Context context, String url, double clientVersion, boolean forceUpdate, String saveFileName)
//	  {
//	    double serverVersion = clientVersion + 1.0D;
//	    UpdateManager manager = new UpdateManager(context, url, clientVersion, serverVersion, forceUpdate, saveFileName);
//	    if (UpdateManager.flag)
//	      manager.showNoticeDialog();
//	  }
	
                  
}
