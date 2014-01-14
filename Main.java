package de.skyrising.test;

import java.net.*;
import java.util.*;
import java.io.*;

import org.json.*;

import android.os.*;
import android.app.*;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.*;
import android.widget.TextView;

public class Main extends Activity {

	public static Activity theActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		theActivity = this;
		try {
			new AsyncTask<URL, Void, JSONArray>() {
				@Override
				protected JSONArray doInBackground(URL... params) {
					JSONArray arr = getJSONfromURL(params[0]);
					return arr;
				}
				protected void onPostExecute(JSONArray result) 
				{
					TextView v = (TextView) theActivity .findViewById(R.id.view1);
					v.setText("");
					try {
						List<JSONObject> list = new ArrayList<JSONObject>();
						
						for (int i = 0; i < result.length(); i++) 
							list.add(result.getJSONObject(i));
						
						Collections.sort(list, new Comparator<JSONObject>() {
							public int compare(JSONObject o1, JSONObject o2) 
							{
								try {
									long l1 = o1.getLong("timestamp");
									long l2 = o2.getLong("timestamp");
									int i1 = o1.getInt("lesson");
									int i2 = o2.getInt("lesson");
									return l1 > l2 ? 1 : l1 < l2 ? -1
										 : i1 > i2 ? 1 : i1 < i2 ? -1 : o1.getInt("id") - o2.getInt("id");
								} catch (JSONException e) {
									return 0;
								}
							}
						});
						long lastTimestamp = 0;
						for(JSONObject o : list)
						{
							if(o.getLong("timestamp")*1000 != lastTimestamp)
							{
								lastTimestamp = o.getLong("timestamp")*1000;
								v.append("\n" + DateFormat.format("E. d.LL.yy", lastTimestamp) + "\n");
							}
							int lesson = o.getInt("lesson");
							String teacher = Html.fromHtml(o.getString("teacher")).toString().replace(" ", " ");
							String comment = Html.fromHtml(o.getString("comment")).toString().replace(" ", " ");
							String replacement = Html.fromHtml(o.getString("replacement")).toString().replace(" ", " ");
							String room = Html.fromHtml(o.getString("room")).toString().replace(" ", " ");
							v.append(lesson + " Std. " + teacher + " -> " +
									comment + (replacement.trim().length() > 0 ? " (" + replacement.trim() + ")" : "") + 
									(room.length() > 0 ? " in " + room : "") + "\n");
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}.execute(new URL("http://lemuecke.no-ip.org/vplan/phpapp/app.php"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	private static JSONArray getJSONfromURL(URL url) {
		try {
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null)
				sb.append(line).append('\n');
			reader.close();
			conn.disconnect();
			return new JSONArray(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new JSONArray();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
