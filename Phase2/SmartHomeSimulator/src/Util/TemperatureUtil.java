package Util;

import Backend.Model.DateTime;
import Backend.SimulatorMenu.SimulatorHome;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class TemperatureUtil {
    public static String getTemperatureForCurrentTime (){
        DateTime dateTime = DateTime.getInstance();
        Calendar currentTime = dateTime.getDate();
        String hour = dateTime.getTimeAsString().split(":")[0];

        String temperature = "N/A";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String curentDateTimeString = dateFormat.format(currentTime.getTime());

        try (InputStream is = TemperatureUtil.class.getResourceAsStream("/Resources/temp.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))){
            String line;
            while ((line = reader.readLine()) != null){
                
                String[] data = line.split(",");
                if (data[0].equals(curentDateTimeString)){
                    if(data[1].split(":")[0].equals(hour)){
                        temperature = data[2];
                        break;
                    }

                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
                return temperature;


    }

}
