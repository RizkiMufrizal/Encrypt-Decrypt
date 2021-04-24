package org.rizki.mufrizal.generate.helper;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

/**
 *
 * @author Rizki Mufrizal
 */
public class RSAOAEPWithSHA256 {

    public static String encrypt(String plainText, String publicKeyString) {
        try {
            publicKeyString = publicKeyString.replaceAll("\\n", "").replaceAll("-----BEGIN PUBLIC KEY-----", "").replaceAll("-----END PUBLIC KEY-----", "");

            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyString));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey, new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT));
            byte[] cipherText = cipher.doFinal(plainText.getBytes(Charset.defaultCharset()));
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return e.getMessage() + " With Stacktrace " + Arrays.toString(e.getStackTrace());
        }
    }

    public static String decrypt(String cipherText, String privateKeyString) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec;
            if (privateKeyString.contains("BEGIN PRIVATE KEY")) {
                privateKeyString = privateKeyString.replaceAll("\\n", "").replaceAll("-----BEGIN PRIVATE KEY-----", "").replaceAll("-----END PRIVATE KEY-----", "");
                pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString));
            } else {
                privateKeyString = privateKeyString.replaceAll("\\n", "").replaceAll("-----BEGIN RSA PRIVATE KEY-----", "").replaceAll("-----END RSA PRIVATE KEY-----", "");
                byte[] keyBytes = Base64.getDecoder().decode(privateKeyString);
                int pkcs1Length = keyBytes.length;
                int totalLength = pkcs1Length + 22;
                byte[] pkcs8Header = new byte[]{
                    0x30, (byte) 0x82, (byte) ((totalLength >> 8) & 0xff), (byte) (totalLength & 0xff),
                    0x2, 0x1, 0x0,
                    0x30, 0xD, 0x6, 0x9, 0x2A, (byte) 0x86, 0x48, (byte) 0x86, (byte) 0xF7, 0xD, 0x1, 0x1, 0x1, 0x5, 0x0,
                    0x4, (byte) 0x82, (byte) ((pkcs1Length >> 8) & 0xff), (byte) (pkcs1Length & 0xff)
                };
                byte[] bytes = new byte[pkcs8Header.length + keyBytes.length];
                System.arraycopy(pkcs8Header, 0, bytes, 0, pkcs8Header.length);
                System.arraycopy(keyBytes, 0, bytes, pkcs8Header.length, keyBytes.length);
                pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(bytes);
            }

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

            Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey, new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT));
            return new String(cipher.doFinal(Base64.getDecoder().decode(cipherText)), Charset.defaultCharset());
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return e.getMessage() + " With Stacktrace " + Arrays.toString(e.getStackTrace());
        }
    }
}
