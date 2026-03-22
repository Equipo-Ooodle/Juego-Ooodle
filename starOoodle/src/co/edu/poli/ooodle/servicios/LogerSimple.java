package co.edu.poli.ooodle.servicios;

import java.io.IOException;
import java.util.logging.*;

public class LogerSimple {

    private static final Logger logger = Logger.getLogger(LogerSimple.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("errores.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}
