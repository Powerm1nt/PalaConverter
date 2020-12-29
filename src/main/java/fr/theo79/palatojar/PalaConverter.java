package fr.theo79.palatojar;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.Key;
import java.util.Base64;


public class PalaConverter
{
    private final String immutableKey = "<,.O5STuL0&$6;*+8,\"OAO|\\";

    private File file;


    protected PalaConverter(File srcFile) {
        file = srcFile;
    }


    public void aesEncoder(ActionType actionType) throws Exception {
        if (actionType == null) throw new NullPointerException();
        if (!file.exists()) throw new FileNotFoundException();
        if (file.getName().endsWith(".jar") || file.getName().endsWith(".pala")) {

            try (OutputStream out = new FileOutputStream( file.getAbsolutePath() +
                    (actionType.equals(ActionType.DECRYPT) ? ".jar" : ".pala"))) {

                String str = new String(Base64.getDecoder().decode(dbitKey(immutableKey)));
                out.write(decrypt(actionType, dbitKey(str), file));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else throw new Exception("Please use a .jar or .pala file.");
    }

    private String dbitKey(final String q) {
        final byte[] a = q.getBytes();
        final byte[] b = { 24, 32, 64, 70 };

        for (int i = 0; i < a.length; ++i) {
            int z = a[i] + b[(i % b.length == 0) ? 0 : (i % b.length - 1)];
            if (z < 32) {
                z = ((32 - z > 0) ? (127 - (32 - z)) : (127 - (32 + z)));
            }
            else if (z > 127) {
                z = z % 127 + 32;
            }
            a[i] = (byte)z;
        }
        return new String(a);
    }


    private byte[] decrypt(ActionType actionType, final String key, final File inputFile) {
        try {
            final Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
            final Cipher cipher = Cipher.getInstance("AES");

            cipher.init(actionType.equals(ActionType.DECRYPT) ? Cipher.DECRYPT_MODE : Cipher.ENCRYPT_MODE, secretKey);

            final FileInputStream inputStream = new FileInputStream(inputFile);
            final byte[] inputBytes = new byte[(int)inputFile.length()];
            inputStream.read(inputBytes);
            final byte[] outputBytes = cipher.doFinal(inputBytes);
            inputStream.close();
            return outputBytes;
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
            return null;
        }
    }

}