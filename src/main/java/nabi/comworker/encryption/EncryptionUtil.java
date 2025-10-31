package nabi.comworker.encryption;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtil {

	 private static final String ALGORITHM = "AES";
	    private static final String KEY = "empmeetnabikey"; // 16-character key

	    // Encrypt
	    public static String encrypt(String data) {
	        try {
	            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
	            Cipher cipher = Cipher.getInstance(ALGORITHM);
	            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
	            byte[] encrypted = cipher.doFinal(data.getBytes());
	            return Base64.getEncoder().encodeToString(encrypted);
	        } catch (Exception e) {
	            throw new RuntimeException("Error encrypting data", e);
	        }
	    }
	    
	    // Decrypt
	    public static String decrypt(String encryptedData) {
	        try {
	            SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
	            Cipher cipher = Cipher.getInstance(ALGORITHM);
	            cipher.init(Cipher.DECRYPT_MODE, keySpec);
	            byte[] decoded = Base64.getDecoder().decode(encryptedData);
	            return new String(cipher.doFinal(decoded));
	        } catch (Exception e) {
	            throw new RuntimeException("Error decrypting data", e);
	        }
	    }
}
