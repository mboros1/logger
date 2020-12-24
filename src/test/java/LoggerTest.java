import org.junit.jupiter.api.Test;

class LoggerTest {

    @Test
    public void test_getInstance(){
        System.out.println(System.getProperty("user.dir"));
        LM log = LM.getInstance();
    }

    @Test
    public void test_log_levels(){
        LM.LOG_LEVEL trace = LM.LOG_LEVEL.TRACE;
        LM.LOG_LEVEL info = LM.LOG_LEVEL.INFO;
        LM.LOG_LEVEL debug = LM.LOG_LEVEL.DEBUG;
        LM.LOG_LEVEL warn = LM.LOG_LEVEL.WARNING;
        LM.LOG_LEVEL error = LM.LOG_LEVEL.ERROR;

        System.out.println(trace.compareTo(info));
    }

    @Test
    public void test_write(){
        LM log = LM.getInstance();
        log.writeTrace("this is a trace");
        log.writeDebug("some extra debug info");
        log.writeInfo("general info here");
        log.writeWarning("warning time");
        log.writeError("this is bad");
    }
}