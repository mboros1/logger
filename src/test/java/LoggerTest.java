import org.junit.jupiter.api.Test;

class LoggerTest {

    @Test
    public void test_getInstance(){
        System.out.println(System.getProperty("user.dir"));
        Logger log = Logger.getInstance();
    }

    @Test
    public void test_log_levels(){
        Logger.LOG_LEVEL trace = Logger.LOG_LEVEL.TRACE;
        Logger.LOG_LEVEL info = Logger.LOG_LEVEL.INFO;
        Logger.LOG_LEVEL debug = Logger.LOG_LEVEL.DEBUG;
        Logger.LOG_LEVEL warn = Logger.LOG_LEVEL.WARNING;
        Logger.LOG_LEVEL error = Logger.LOG_LEVEL.ERROR;

        System.out.println(trace.compareTo(info));
    }

    @Test
    public void test_write(){
        Logger log = Logger.getInstance();
        log.writeTrace("this is a trace");
        log.writeDebug("some extra debug info");
        log.writeInfo("general info here");
        log.writeWarning("warning time");
        log.writeError("this is bad");
    }
}