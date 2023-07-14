package tk.jandev.superscraper.scraper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;

public class DataPointTracker {
    public static LinkedList<TickDataPoint> dataPoints = new LinkedList<>();

    public static void add(TickDataPoint e) {
        dataPoints.add(e);
    }

    public static void safe(String filePath) {
        System.out.println("possibly invalid path: "+(filePath));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            System.out.println("size of tracker: "+dataPoints.size());
            for (TickDataPoint dataPoint : dataPoints) {
                if (dataPoint == null) {
                    writer.write(" ");
                    writer.newLine();
                    continue;
                }
                writer.write(dataPoint.toString());
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }
}
