package apppackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.json.*;


public class WeatherGUI extends JFrame{
	
	private static final long serialVersionUID = 5856199499721403355L;
	String name;
	String country;
	String description;
	String temp;
	String temp_min;
	String temp_max;
	String humidity;

	/**
	 * constructor
	 */
	public WeatherGUI() {
		initComponents();
		getWeather();
		display();
	}
	
	/**
	 * draws the displays
	 */
	private void display() {
		JLabel jLabel_name = new JLabel(" City: " + this.name);
		JLabel jLabel_country = new JLabel(" Country: " + this.country);
		JLabel jLabel_description = new JLabel(" Description: " + this.description);
		JLabel jLabel_temp = new JLabel(" Temperature: " + this.temp);
		JLabel jLabel_temp_min = new JLabel(" Temperature (Min): " + this.temp_min);
		JLabel jLabel_temp_max = new JLabel(" Temperature (Max): " + this.temp_max);
		JLabel jLabel_humidity = new JLabel(" Humidity: " + this.humidity);
		
		
		jLabel_name.setBounds(10, 0, 250, 50);
		jLabel_country.setBounds(10, 20, 250, 50);
		jLabel_description.setBounds(10, 50, 250, 50);
		jLabel_temp.setBounds(10, 70, 250, 50);
		jLabel_temp_min.setBounds(10, 90, 250, 50);
		jLabel_temp_max.setBounds(10, 110, 250, 50);
		jLabel_humidity.setBounds(10, 130, 250, 50);
		
		
		this.add(jLabel_name);
		this.add(jLabel_country);
		this.add(jLabel_description);
		this.add(jLabel_temp);
		this.add(jLabel_temp_min);
		this.add(jLabel_temp_max);
		this.add(jLabel_humidity);
		
		
		this.setLayout(null);
		this.setVisible(true);
	}

	/**
	 * gets the weather info from https://api.openweathermap.org/
	 */
	private void getWeather() {
		
		//credentials
		final String API_KEY = "9eeeb730de15d01bea2b068ccf0fde37";
		String location = "San Francisco,us";
		String urlString = "https://api.openweathermap.org/data/2.5/weather?q="+location+"&appid=" + API_KEY;
		
		//try to retrieve weather data
		try {
			StringBuilder sb = new StringBuilder();
			URL url = new URL(urlString);
			URLConnection conn = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader (conn.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			
			//closes BufferedReader
			br.close();
			
			String jsonString = sb.toString();
			JSONObject jsonObject = new JSONObject(jsonString);
			
			//assign variables
			this.name = jsonObject.getString("name");
			this.country = jsonObject.getJSONObject("sys").getString("country");
			this.description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
			this.temp = "" + jsonObject.getJSONObject("main").getDouble("temp");
			this.temp_min = "" + jsonObject.getJSONObject("main").getDouble("temp_min");
			this.temp_max = "" + jsonObject.getJSONObject("main").getDouble("temp_max");
			this.humidity = "" + jsonObject.getJSONObject("main").getDouble("humidity");
			
			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * initializes the JFrame
	 */
	private void initComponents() {
		this.setTitle("Weather App");
		this.setSize(300, 300);
		this.setLocation(300, 300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
	}
	
	public static void main(String[]args) {
		WeatherGUI weathergui = new WeatherGUI();
		
		
	}
}
