package org.rizki.mufrizal;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class AES256GCM {

    public static String encrypt(String plainText, String secretKey) {
        try {
            byte[] _keyBytes = secretKey.getBytes(Charset.defaultCharset());
            byte[] keyBytes = new byte[32];
            System.arraycopy(_keyBytes, 0, keyBytes, 0, _keyBytes.length);

            byte[] IV = new byte[12];
            SecureRandom random = new SecureRandom();
            random.nextBytes(IV);

            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, IV);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);

            byte[] cipherText = cipher.doFinal(plainText.getBytes(Charset.defaultCharset()));

            ByteArrayOutputStream concatCipherTextIV = new ByteArrayOutputStream();
            concatCipherTextIV.write(cipherText);
            concatCipherTextIV.write(IV);

            return Base64.getEncoder().encodeToString(concatCipherTextIV.toByteArray());
        } catch (IOException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decrypt(String cipherText, String secretKey) {
        try {
            byte[] _keyBytes = secretKey.getBytes(Charset.defaultCharset());
            byte[] keyBytes = new byte[32];
            System.arraycopy(_keyBytes, 0, keyBytes, 0, _keyBytes.length);

            byte[] cipherTextBase64 = Base64.getDecoder().decode(cipherText);

            byte[] IV = Arrays.copyOfRange(cipherTextBase64, cipherTextBase64.length - 12, cipherTextBase64.length);
            byte[] cipherByte = Arrays.copyOfRange(cipherTextBase64, 0, cipherTextBase64.length - IV.length);

            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, IV);

            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);

            return new String(cipher.doFinal(cipherByte), Charset.defaultCharset());
        } catch (BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
