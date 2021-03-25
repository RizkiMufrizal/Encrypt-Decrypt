package org.rizki.mufrizal

import com.nimbusds.jose.JOSEException
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.JWSObject
import com.nimbusds.jose.Payload
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jose.crypto.RSASSAVerifier

import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.interfaces.RSAPublicKey
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.text.ParseException

class JWSSignatureGroovy {
    def sign(String body, String privateKeyString) {
        try {
            def pkcs8EncodedKeySpec
            if (privateKeyString.contains("BEGIN PRIVATE KEY")) {
                privateKeyString = privateKeyString.replaceAll("\\n", "").replaceAll("-----BEGIN PRIVATE KEY-----", "").replaceAll("-----END PRIVATE KEY-----", "")
                pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyString))
            } else {
                privateKeyString = privateKeyString.replaceAll("\\n", "").replaceAll("-----BEGIN RSA PRIVATE KEY-----", "").replaceAll("-----END RSA PRIVATE KEY-----", "")
                def keyBytes = Base64.getDecoder().decode(privateKeyString)
                def pkcs1Length = keyBytes.length
                def totalLength = pkcs1Length + 22
                def pkcs8Header = [
                        0x30, (byte) 0x82, (byte) ((totalLength >> 8) & 0xff), (byte) (totalLength & 0xff),
                        0x2, 0x1, 0x0,
                        0x30, 0xD, 0x6, 0x9, 0x2A, (byte) 0x86, 0x48, (byte) 0x86, (byte) 0xF7, 0xD, 0x1, 0x1, 0x1, 0x5, 0x0,
                        0x4, (byte) 0x82, (byte) ((pkcs1Length >> 8) & 0xff), (byte) (pkcs1Length & 0xff)
                ] as byte[]
                def bytes = new byte[pkcs8Header.length + keyBytes.length]
                System.arraycopy(pkcs8Header, 0, bytes, 0, pkcs8Header.length)
                System.arraycopy(keyBytes, 0, bytes, pkcs8Header.length, keyBytes.length)
                pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(bytes)
            }

            def keyFactory = KeyFactory.getInstance("RSA")
            def privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec)

            def signer = new RSASSASigner(privateKey)

            def jwsObject = new JWSObject(new JWSHeader.Builder(JWSAlgorithm.RS256).build(), new Payload(body))

            jwsObject.sign(signer)
            return jwsObject.serialize()
        } catch (InvalidKeySpecException e) {
            e.printStackTrace()
            return null
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace()
            return null
        } catch (JOSEException e) {
            e.printStackTrace()
            return null
        }
    }

    def verify(String body, String publicKeyString) {
        try {
            publicKeyString = publicKeyString.replaceAll("\\n", "").replaceAll("-----BEGIN PUBLIC KEY-----", "").replaceAll("-----END PUBLIC KEY-----", "")

            def keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyString))
            def keyFactory = KeyFactory.getInstance("RSA")
            def publicKey = keyFactory.generatePublic(keySpec)

            def jwsObject = JWSObject.parse(body)
            def verifier = new RSASSAVerifier((RSAPublicKey) publicKey)

            return jwsObject.verify(verifier)
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace()
            return null
        } catch (InvalidKeySpecException e) {
            e.printStackTrace()
            return null
        } catch (ParseException e) {
            e.printStackTrace()
            return null
        } catch (JOSEException e) {
            e.printStackTrace()
            return null
        }
    }
}
