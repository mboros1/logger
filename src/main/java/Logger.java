import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    // Singleton class
    private static Logger logger = null;
    private Logger(){
        this.config = new Config();
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        try {
            Logger.Config config = om.readValue(new File("config.yaml"), Logger.Config.class);
            this.config = config;
        } catch (IOException e) {
            System.out.println("Could not find config.yaml file, using defaults");
            this.config.setLog_level(LOG_LEVEL.INFO);
            this.config.setWrite_to_console(true);
            this.config.setWrite_to_log(true);
            this.config.setLog_file("logger.log");
        }

        this.log_file = new File(this.config.log_file);
        try {
            if (this.log_file.createNewFile()){
                System.out.println("Log file created");
            } else {
                System.out.println("File already exists; appending output");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Logger getInstance(){
        if (logger == null)
            logger = new Logger();
        return logger;
    }

    private Config config;
    private File log_file;

    private static String Timestamp(){
        LocalDateTime ts = LocalDateTime.now();
        DateTimeFormatter f = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        return f.format(ts);
    }

    private void write(LOG_LEVEL level, String str){
        if (level.compareTo(config.log_level) < 0){
            return;
        }

        String timestamp = Timestamp();

        if (config.isWrite_to_console()){
            System.out.println(timestamp + ": " + level + ": " + str);
        }
        if (config.isWrite_to_log()){
            try {
                FileWriter fw = new FileWriter(log_file, true);
                fw.write(timestamp + ": " + level + ": " + str+ "\n");
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void writeTrace(String str){
        System.out.print(Color.WHITE_BOLD_BRIGHT);
        write(LOG_LEVEL.TRACE, str);
        System.out.print(Color.RESET);
    }
    public void writeDebug(String str){
        System.out.print(Color.CYAN);
        write(LOG_LEVEL.DEBUG, str);
        System.out.print(Color.RESET);
    }
    public void writeInfo(String str){
        System.out.print(Color.GREEN);
        write(LOG_LEVEL.INFO, str);
        System.out.print(Color.RESET);
    }
    public void writeWarning(String str){
        System.out.print(Color.YELLOW);
        write(LOG_LEVEL.WARNING, str);
        System.out.print(Color.RESET);
    }
    public void writeError(String str){
        System.out.print(Color.RED);
        write(LOG_LEVEL.ERROR, str);
        System.out.print(Color.RESET);
    }


    enum LOG_LEVEL {
        TRACE, DEBUG, INFO, WARNING, ERROR
    }
    static class Config {
        private LOG_LEVEL log_level;
        private boolean write_to_log;
        private boolean write_to_console;
        private String log_file;

        public LOG_LEVEL getLog_level() {
            return log_level;
        }

        public void setLog_level(LOG_LEVEL log_level) {
            System.out.println("Log Level set to: " + log_level);
            this.log_level = log_level;
        }

        public boolean isWrite_to_log() {
            return write_to_log;
        }

        public void setWrite_to_log(boolean write_to_log) {
            System.out.println("Write to log file set to: " + write_to_log);
            this.write_to_log = write_to_log;
        }

        public boolean isWrite_to_console() {
            return write_to_console;
        }

        public void setWrite_to_console(boolean write_to_console) {
            System.out.println("Write to console set to: " + write_to_console);
            this.write_to_console = write_to_console;
        }

        public String getLog_file() {
            return log_file;
        }

        public void setLog_file(String log_file) {
            System.out.println("Log file path set to: "+ log_file);
            this.log_file = log_file;
        }
    }
    enum Color {
        //Color end string, color reset
        RESET("\033[0m"),

        // Regular Colors. Normal color, no bold, background color etc.
        BLACK("\033[0;30m"),    // BLACK
        RED("\033[0;31m"),      // RED
        GREEN("\033[0;32m"),    // GREEN
        YELLOW("\033[0;33m"),   // YELLOW
        BLUE("\033[0;34m"),     // BLUE
        MAGENTA("\033[0;35m"),  // MAGENTA
        CYAN("\033[0;36m"),     // CYAN
        WHITE("\033[0;37m"),    // WHITE

        // Bold
        BLACK_BOLD("\033[1;30m"),   // BLACK
        RED_BOLD("\033[1;31m"),     // RED
        GREEN_BOLD("\033[1;32m"),   // GREEN
        YELLOW_BOLD("\033[1;33m"),  // YELLOW
        BLUE_BOLD("\033[1;34m"),    // BLUE
        MAGENTA_BOLD("\033[1;35m"), // MAGENTA
        CYAN_BOLD("\033[1;36m"),    // CYAN
        WHITE_BOLD("\033[1;37m"),   // WHITE

        // Underline
        BLACK_UNDERLINED("\033[4;30m"),     // BLACK
        RED_UNDERLINED("\033[4;31m"),       // RED
        GREEN_UNDERLINED("\033[4;32m"),     // GREEN
        YELLOW_UNDERLINED("\033[4;33m"),    // YELLOW
        BLUE_UNDERLINED("\033[4;34m"),      // BLUE
        MAGENTA_UNDERLINED("\033[4;35m"),   // MAGENTA
        CYAN_UNDERLINED("\033[4;36m"),      // CYAN
        WHITE_UNDERLINED("\033[4;37m"),     // WHITE

        // Background
        BLACK_BACKGROUND("\033[40m"),   // BLACK
        RED_BACKGROUND("\033[41m"),     // RED
        GREEN_BACKGROUND("\033[42m"),   // GREEN
        YELLOW_BACKGROUND("\033[43m"),  // YELLOW
        BLUE_BACKGROUND("\033[44m"),    // BLUE
        MAGENTA_BACKGROUND("\033[45m"), // MAGENTA
        CYAN_BACKGROUND("\033[46m"),    // CYAN
        WHITE_BACKGROUND("\033[47m"),   // WHITE

        // High Intensity
        BLACK_BRIGHT("\033[0;90m"),     // BLACK
        RED_BRIGHT("\033[0;91m"),       // RED
        GREEN_BRIGHT("\033[0;92m"),     // GREEN
        YELLOW_BRIGHT("\033[0;93m"),    // YELLOW
        BLUE_BRIGHT("\033[0;94m"),      // BLUE
        MAGENTA_BRIGHT("\033[0;95m"),   // MAGENTA
        CYAN_BRIGHT("\033[0;96m"),      // CYAN
        WHITE_BRIGHT("\033[0;97m"),     // WHITE

        // Bold High Intensity
        BLACK_BOLD_BRIGHT("\033[1;90m"),    // BLACK
        RED_BOLD_BRIGHT("\033[1;91m"),      // RED
        GREEN_BOLD_BRIGHT("\033[1;92m"),    // GREEN
        YELLOW_BOLD_BRIGHT("\033[1;93m"),   // YELLOW
        BLUE_BOLD_BRIGHT("\033[1;94m"),     // BLUE
        MAGENTA_BOLD_BRIGHT("\033[1;95m"),  // MAGENTA
        CYAN_BOLD_BRIGHT("\033[1;96m"),     // CYAN
        WHITE_BOLD_BRIGHT("\033[1;97m"),    // WHITE

        // High Intensity backgrounds
        BLACK_BACKGROUND_BRIGHT("\033[0;100m"),     // BLACK
        RED_BACKGROUND_BRIGHT("\033[0;101m"),       // RED
        GREEN_BACKGROUND_BRIGHT("\033[0;102m"),     // GREEN
        YELLOW_BACKGROUND_BRIGHT("\033[0;103m"),    // YELLOW
        BLUE_BACKGROUND_BRIGHT("\033[0;104m"),      // BLUE
        MAGENTA_BACKGROUND_BRIGHT("\033[0;105m"),   // MAGENTA
        CYAN_BACKGROUND_BRIGHT("\033[0;106m"),      // CYAN
        WHITE_BACKGROUND_BRIGHT("\033[0;107m");     // WHITE

        private final String code;

        Color(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }
}

