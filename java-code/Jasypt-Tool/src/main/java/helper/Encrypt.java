package helper;

import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricConfig;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricStringEncryptor;
import org.jasypt.encryption.StringEncryptor;

import static com.ulisesbocchio.jasyptspringboot.util.AsymmetricCryptography.KeyFormat.PEM;

public class Encrypt {

    public static String cipher(String publicKey, String data) {
        SimpleAsymmetricConfig simpleAsymmetricConfig = new SimpleAsymmetricConfig();
        simpleAsymmetricConfig.setKeyFormat(PEM);
        simpleAsymmetricConfig.setPublicKey(publicKey);
        StringEncryptor stringEncryptor = new SimpleAsymmetricStringEncryptor(simpleAsymmetricConfig);
        return stringEncryptor.encrypt(data);
    }

    public static String data(String privateKey, String cipher) {
        SimpleAsymmetricConfig simpleAsymmetricConfig = new SimpleAsymmetricConfig();
        simpleAsymmetricConfig.setKeyFormat(PEM);
        simpleAsymmetricConfig.setPrivateKey(privateKey);
        StringEncryptor stringEncryptor = new SimpleAsymmetricStringEncryptor(simpleAsymmetricConfig);
        return stringEncryptor.decrypt(cipher);
    }
}
