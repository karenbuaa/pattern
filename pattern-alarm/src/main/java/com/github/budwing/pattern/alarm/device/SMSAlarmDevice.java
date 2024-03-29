package com.github.budwing.pattern.alarm.device;

import com.github.budwing.pattern.alarm.Employee;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class SMSAlarmDevice extends AlarmDevice {
	private String smsURL;

	public boolean sendMessage(String msg, List<Employee> contacts) {
		int index = 0;
		try {
			StringBuilder sb = new StringBuilder(smsURL);
			sb.append("?msg=").append(URLEncoder.encode(msg, "utf-8"))
					.append("&contacts=");
			StringBuilder contactsStr = new StringBuilder();

			for (Employee e : contacts) {
				if (e.getMobile() != null && !e.getMobile().isEmpty()) {
					if (index > 0)
						contactsStr.append(",");
					contactsStr.append(e.getMobile());
					index++;
				}
			}
			sb.append(contactsStr);

			String smsResult = null;
			URL url = new URL(sb.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			smsResult = reader.readLine();
			if (smsResult.equals("done")) {
				return true;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

}
