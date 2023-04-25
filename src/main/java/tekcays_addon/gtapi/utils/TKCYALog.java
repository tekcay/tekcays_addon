package tekcays_addon.gtapi.utils;

import org.apache.logging.log4j.Logger;

public class TKCYALog {
    public static Logger logger;

    public TKCYALog() {
    }

    public static void init(Logger modLogger) {
        logger = modLogger;
    }

}
