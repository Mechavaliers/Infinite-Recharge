package ca.team4519.lib;

import edu.wpi.first.wpilibj.DriverStation;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MechaLogger {

    private String logDirectory = "/home/lvuser/log/";

    private static MechaLogger instance = new MechaLogger();

    public static MechaLogger grabInstance()
    {
        return instance;
    }

    private final List<LogInputs> logInput = new ArrayList<>();
    private Path file;

    private MechaLogger()
    {
        File usb1 = new File("/media/sda/");
        if(usb1 .exists()){
            logDirectory = "/media/sda1/logs/";
        }

    }

    private void initLogDirectory() throws IOException
    {
        File logDir = new File(logDirectory);
        if(!logDir.exists())
        {
            Files.createDirectory(Paths.get(logDirectory));
        }
    }

    private void newFile()
    {
        Writer out = null;
        try
        {
            initLogDirectory();
            if(DriverStation.getInstance().isFMSAttached())
            {
                file = Paths.get(logDirectory+
                DriverStation.getInstance().getEventName()+"_"+DriverStation.getInstance().getMatchType()+DriverStation.getInstance().getMatchNumber()+".csv");

            }
            else
            {
                file = Paths.get(logDirectory+"testingLog.csv");
            }
            if(Files.exists(file)){
                Files.delete(file);
            }
            Files.createFile(file);
            saveTitles();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(out != null)
            {
                try
                {
                    out.close();
                }
                catch(IOException ignored) {}
            }
        }
    }

    public void logThis(String name, Supplier<Object> supplier) {
        logInput.add(new LogInputs(name, supplier.get()::toString));
    }

    public void logThis_Double(String name, Supplier<Double> supplier) {
        logInput.add(new LogInputs(name, supplier.get()::toString));
    }

    public void logThis_Int(String name, Supplier<Integer> supplier) {
        logInput.add(new LogInputs(name, supplier.get()::toString));
    }

    public void logThis_Bool(String name, Supplier<Boolean> supplier) {
        logInput.add(new LogInputs(name, supplier.get()::toString));
    }

    public void logThis_String(String name, Supplier<String> supplier) {
        logInput.add(new LogInputs(name, supplier.get()::toString));
    }

    public void saveLogs()
    {
        try
        {
            if (file == null)
            {
                newFile();
            }
            String string = Instant.now().toString() + "," +
                            DriverStation.getInstance().getMatchTime() + "," +
                            getInputs();
            Files.write(file, Collections.singletonList(string), StandardOpenOption.APPEND);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void saveTitles() throws IOException
    {
        String titles = "Timestamp," +
                        "match_time," +
                        logInput.stream().map(t -> t.name).collect(Collectors.joining(",")) + ",";
        Files.write(file, Collections.singletonList(titles), StandardOpenOption.APPEND);
    }

    private String getInputs()
    {
        return logInput.stream().map(s -> s.supplier.get()).collect(Collectors.joining(","));
    }

    private static class LogInputs {
        private final String name;
        private final Supplier<String> supplier;

        public LogInputs(String name, Supplier<String> supplier)
        {
            this.name = name;
            this.supplier = supplier;
        }
    }
}
