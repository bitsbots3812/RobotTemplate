package frc.robot.subsystems;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.state.RobotState;

public class BlackBox extends Thread {

    private static String rootPath = "/media/sda1/logs/";
    private static PrintWriter writer;
    private static boolean canLog = false;
    private static ObjectWriter ow = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, Visibility.ANY).writer().withDefaultPrettyPrinter().without(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    private static ArrayList<String> globalMessageBuffer = new ArrayList<String>();
    private static boolean firstRun = true;

    public static void init() {
        BlackBox blackBox = new BlackBox();
        blackBox.start();
    }

    private static void startLog() {

        String filePath = "";

        while(!DriverStation.getInstance().isDSAttached()) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                System.out.println("Failed to get driver station connection - black box disabled");
                return;
            }
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            System.out.println("An unknown error occured - black box disabled");
            return;
        }

        Date date = new Date();
        DateFormat dateFolderFormat = new SimpleDateFormat("MMMddYYYY");
		dateFolderFormat.setTimeZone(TimeZone.getTimeZone("PST"));
        filePath += dateFolderFormat.format(date) + "/";

        boolean isMatch = DriverStation.getInstance().isFMSAttached();

        if(isMatch) {
            filePath += "Match" + DriverStation.getInstance().getMatchNumber();
        }
        else {
            DateFormat militaryTime = new SimpleDateFormat("HHmm");
            militaryTime.setTimeZone(TimeZone.getTimeZone("PST"));
            filePath += militaryTime.format(date);
        }

        filePath += ".json";

        File file = new File(rootPath + filePath);
        file.getParentFile().mkdirs();

        try {
            writer = new PrintWriter(file, "UTF-8");
        } catch(FileNotFoundException | UnsupportedEncodingException e) {

            try {

                File file2 = new File("media/sdb1/logs" + filePath);
                writer = new PrintWriter(file2, "UTF-8");

            } catch(FileNotFoundException | UnsupportedEncodingException e2) {

                File f = new File("/media/sda1/logs");
                if(f.exists() == false) {

                    System.out.println("Error - No USB detected - Plug one in");
                    return;

                }

                else {

                    System.out.println("File Error - BlackBox is disabled - Restart the robot");
                    Thread.currentThread().interrupt();
                    return;

                }

            }

        }

        canLog = true;

        if(firstRun) {

            DateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss:SSS");
            dateFormat.setTimeZone(TimeZone.getTimeZone("PST"));
            timeFormat.setTimeZone(TimeZone.getTimeZone("PST"));

            writer.println("{\"date\": \"" + dateFormat.format(date) + "\", ");
            writer.println("\"time\": \"" + timeFormat.format(date) + "\", ");
            writer.println("\"states\": [");

            firstRun = false;

        }

        writer.flush();

    }

    public static void log(String value) {

        Date date = new Date();
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss:SSS");
        timeFormat.setTimeZone(TimeZone.getTimeZone("PST"));

        globalMessageBuffer.add("{\"time\": \""+ timeFormat.format(date) + "\", \"output\": \""+ value + "\"}, ");

    }

    public static void logState(RobotState state) {
            
        try {
            String json = ow.writeValueAsString(state);
            Date date = new Date();
            DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss:SSS");
            timeFormat.setTimeZone(TimeZone.getTimeZone("PST"));
            globalMessageBuffer.add("{\"time\": \""+ timeFormat.format(date) + "\", \"state\": " + json + "}, ");
        } catch (JsonProcessingException e) {
            log("Invalid object JSON - " + e);
        }

    }

    public void run() {

        ArrayList<String> messageBuffer = new ArrayList<String>();

        while(!Thread.currentThread().isInterrupted()) {

            if(globalMessageBuffer.size() > 0) {
                messageBuffer.addAll(globalMessageBuffer);
                globalMessageBuffer.clear();
            }

            if(!canLog) {
                startLog();
            }
            else if(messageBuffer.size() > 0) {
                writer.println(messageBuffer.get(0));
                writer.flush();
                if(!writer.checkError() && messageBuffer.size() > 0) {
                    messageBuffer.remove(0);
                }
                else {
                    writer = null;
                    canLog = false;
                }
            }

        }

    }

}
