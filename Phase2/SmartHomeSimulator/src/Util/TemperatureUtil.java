package Util;

import Backend.Model.DateTime;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TemperatureUtil {
    public static String getTemperatureForCurrentTime (){
        DateTime dateTime = DateTime.getInstance();
        Calendar currentTime = dateTime.getDate();
        String temperature = "N/A";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd,HH:mm");
        String curentDateTimeString = dateFormat.format(currentTime.getTime());

        try (InputStream is = TemperatureUtil.class.getResourceAsStream("/Resources/temp.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))){
            String line;
            while ((line = reader.readLine()) != null){
                String[] data = line.split(",");
                if (data[0].equals(curentDateTimeString)){
                    temperature = data[2];
                    break;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
                return temperature;


    }

}
