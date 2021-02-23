package org.rizki.mufrizal

import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import java.nio.charset.Charset
import java.security.InvalidAlgorithmParameterException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom

class AES256GCMGroovy {

    def encrypt(String plainText, String secretKey) {
        try {
            def _keyBytes = secretKey.getBytes(Charset.defaultCharset())
            def keyBytes = new byte[32]
            System.arraycopy(_keyBytes, 0, keyBytes, 0, _keyBytes.length)

            def IV = new byte[12]
            def random = new SecureRandom()
            random.nextBytes(IV)

            def keySpec = new SecretKeySpec(keyBytes, "AES")
            def cipher = Cipher.getInstance("AES/GCM/NoPadding")
            def gcmParameterSpec = new GCMParameterSpec(128, IV)
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec)

            def cipherText = cipher.doFinal(plainText.getBytes(Charset.defaultCharset()))

            def concatCipherTextIV = new ByteArrayOutputStream()
            concatCipherTextIV.write(cipherText)
            concatCipherTextIV.write(IV)

            return Base64.getEncoder().encodeToString(concatCipherTextIV.toByteArray())
        } catch (IOException e) {
            e.printStackTrace()
            return null
        } catch (NoSuchPaddingException e) {
            e.printStackTrace()
            return null
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace()
            return null
        } catch (InvalidKeyException e) {
            e.printStackTrace()
            return null
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace()
            return null
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace()
            return null
        } catch (BadPaddingException e) {
            e.printStackTrace()
            return null
        }
    }

    def decrypt(String cipherText, String secretKey) {
        try {
            def _keyBytes = secretKey.getBytes(Charset.defaultCharset())
            def keyBytes = new byte[32]
            System.arraycopy(_keyBytes, 0, keyBytes, 0, _keyBytes.length)

            def cipherTextBase64 = Base64.getDecoder().decode(cipherText)

            def IV = Arrays.copyOfRange(cipherTextBase64, cipherTextBase64.length - 12, cipherTextBase64.length)
            def cipherByte = Arrays.copyOfRange(cipherTextBase64, 0, cipherTextBase64.length - IV.length)

            def keySpec = new SecretKeySpec(keyBytes, "AES")
            def cipher = Cipher.getInstance("AES/GCM/NoPadding")
            def gcmParameterSpec = new GCMParameterSpec(128, IV)

            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec)

            return new String(cipher.doFinal(cipherByte), Charset.defaultCharset())
        } catch (BadPaddingException e) {
            e.printStackTrace()
            return null
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace()
            return null
        } catch (NoSuchPaddingException e) {
            e.printStackTrace()
            return null
        } catch (InvalidKeyException e) {
            e.printStackTrace()
            return null
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace()
            return null
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace()
            return null
        }
    }

}
