package apppackage;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

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
		JLabel jLabel_location = new JLabel(""+this.name+", "+this.country, SwingConstants.CENTER);
		JLabel jLabel_temp = new JLabel(""+this.temp+"\u00B0F", SwingConstants.CENTER);
		JLabel jLabel_description = new JLabel(""+this.description, SwingConstants.CENTER);
		JLabel jLabel_temp_min = new JLabel("<html>Temp Min<br/>"+this.temp_min+"\u00B0F</html>", SwingConstants.CENTER);
		JLabel jLabel_temp_max = new JLabel("<html>Temp Max<br/>"+this.temp_max+"\u00B0F</html>", SwingConstants.CENTER);
		JLabel jLabel_humidity = new JLabel(" Humidity: "+this.humidity, SwingConstants.CENTER);
		
		
		jLabel_location.setBounds(10, 0, 250, 50);
		jLabel_location.setFont(new Font("Serif", Font.BOLD, 20));
		jLabel_temp.setBounds(10, 40, 250, 50);
		jLabel_temp.setFont(new Font("Serif", Font.BOLD, 40));
		jLabel_description.setBounds(10, 70, 250, 50);
		jLabel_description.setFont(new Font("Serif", Font.BOLD, 18));
		jLabel_temp_min.setBounds(10, 120, 150, 50);
		jLabel_temp_min.setFont(new Font("Serif", Font.BOLD, 18));
		jLabel_temp_max.setBounds(120, 120, 150, 50);
		jLabel_temp_max.setFont(new Font("Serif", Font.BOLD, 18));
		jLabel_humidity.setBounds(10, 170, 250, 50);
		jLabel_humidity.setFont(new Font("Serif", Font.BOLD, 20));
		
		
		this.add(jLabel_location);
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
		String location = "5391959"; //San Francisco,us
		String urlString = "https://api.openweathermap.org/data/2.5/weather?id="+location+"&appid="+API_KEY+"&units=imperial";
		
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
