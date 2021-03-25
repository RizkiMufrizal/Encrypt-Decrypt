package org.rizki.mufrizal;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;

public class JWSSignature {

    public static String sign(String body, String privateKeyString) {
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

            JWSSigner signer = new RSASSASigner(privateKey);

            JWSObject jwsObject = new JWSObject(
                    new JWSHeader.Builder(JWSAlgorithm.RS256).build(),
                    new Payload(body));

            jwsObject.sign(signer);
            return jwsObject.serialize();
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | JOSEException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Boolean verify(String body, String publicKeyString) {
        try {
            publicKeyString = publicKeyString.replaceAll("\\n", "").replaceAll("-----BEGIN PUBLIC KEY-----", "").replaceAll("-----END PUBLIC KEY-----", "");

            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyString));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            JWSObject jwsObject = JWSObject.parse(body);
            JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);

            return jwsObject.verify(verifier);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | ParseException | JOSEException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String signOther(String body, String privateKeyString) {
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

            Signature rsa = Signature.getInstance("SHA256withRSA");
            rsa.initSign(privateKey);

            String headerEncoded = Base64.getUrlEncoder().withoutPadding().encodeToString("{\"alg\":\"RS256\"}".getBytes(Charset.defaultCharset()));
            String bodyEncoded = Base64.getUrlEncoder().withoutPadding().encodeToString(body.getBytes(Charset.defaultCharset()));

            rsa.update(headerEncoded.concat(".").concat(bodyEncoded).getBytes(Charset.defaultCharset()));

            return headerEncoded.concat(".").concat(bodyEncoded).concat(".")
                    .concat(Base64.getUrlEncoder().withoutPadding().encodeToString(rsa.sign()));
        } catch (InvalidKeySpecException | NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
            return null;
        }
    }

}
