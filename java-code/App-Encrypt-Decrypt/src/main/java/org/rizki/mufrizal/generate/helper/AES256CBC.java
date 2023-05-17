package org.rizki.mufrizal.generate.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES256CBC {

    public static String encrypt(String value, String secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, IOException {
        byte[] _keyBytes = secretKey.getBytes(Charset.defaultCharset());

        IvParameterSpec ivspec = new IvParameterSpec(_keyBytes);
        String keyMd5 = HexaHelper.toHexString(MessageDigest.getInstance("MD5").digest(_keyBytes));

        SecretKeySpec keySpec = new SecretKeySpec(keyMd5.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivspec);

        byte[] cipherText = cipher.doFinal(value.getBytes(Charset.defaultCharset()));
        ByteArrayOutputStream concatCipherTextIV = new ByteArrayOutputStream();

        concatCipherTextIV.write(cipherText);

        return HexaHelper.toHexString(concatCipherTextIV.toByteArray());
    }

    public static String decrypt(String cipherText, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] _keyBytes = secretKey.getBytes(Charset.defaultCharset());

        IvParameterSpec ivspec = new IvParameterSpec(_keyBytes);
        String keyMd5 = HexaHelper.toHexString(MessageDigest.getInstance("MD5").digest(_keyBytes));

        SecretKeySpec keySpec = new SecretKeySpec(keyMd5.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivspec);

        return new String(cipher.doFinal(HexaHelper.hexStringToByteArray(cipherText)));
    }
}
