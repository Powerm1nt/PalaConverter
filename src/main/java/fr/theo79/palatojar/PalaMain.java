package fr.theo79.palatojar;

import javafx.application.Application;

import java.awt.*;
import java.io.Console;
import java.io.File;
import java.util.Base64;

public class PalaMain {

    private static final String appName = "PalaConverter";
    private static final String appVersion = "2.1.1";

    public static void main(String[] args) throws Exception {

        final File curApp = new File(PalaMain.class.getProtectionDomain().getCodeSource().getLocation().getFile());
        final Console console = System.console();

        if (console != null) {
            if (args.length > 0 && args[0].equals("gui")) Application.launch(PalaConverterUi.class);
            else if (args.length <= 1) {
                System.out.println("You need to type java -jar " + curApp.getName() + " file.jar <ActionType:encrypt|decrypt>\n");
                System.out.println("gui : launch the gui.");
                System.out.println("encrypt : convert the given jar file to pala file.");
                System.out.println("decrypt : convert the given pala file to jar file.");
                System.out.println("\nMade with love, by Theo79 - " + appName + "@" + appVersion);
                System.exit(1);
            } else {
                PalaConverter palaConverter = new PalaConverter(new File(args[0]));
                palaConverter.aesEncoder(ActionType.valueOf(args[1].toUpperCase()));
            }

        } else if (!GraphicsEnvironment.isHeadless()) {
            Application.launch(PalaConverterUi.class);
        }
    }

    protected static String getAppName() {
        return appName;
    }

    protected static String getAppVersion() {
        return appVersion;
    }
}
