package fr.theo79.palatojar;

import java.io.File;

public class PalaMain {
    public static void main(String[] args) throws Exception {

        final File curApp = new File(PalaMain.class.getProtectionDomain().getCodeSource().getLocation().getFile());

        if (args.length <= 1) {
            System.out.println("----[ HELP ] ----\n");
            System.out.println("just type java -jar " + curApp.getName() + " file.jar <ActionType:encrypt|decrypt>\n");
            System.out.println("---- [ by Theo79 ] ----\n");
            System.exit(1);
        } else {

            if (!args[0].isEmpty()) {
                PalaConverter palaConverter = new PalaConverter(new File(args[0]));
                if (!args[1].isEmpty()) palaConverter.aesEncoder(ActionType.valueOf(args[1].toUpperCase()));
            }
        }
    }
}
