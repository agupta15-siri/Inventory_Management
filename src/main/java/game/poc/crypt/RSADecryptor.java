package game.poc.crypt;

import game.poc.configuration.LoadKeyConfiguration;
import game.poc.exception.RSADecryptionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * The type Rsa decryptor.
 */
@Slf4j
@Component
public class RSADecryptor {

    /**
     * Do decryption string.
     *
     * @param inputData the input data
     * @return the string
     */
    public String doDecryption(String inputData) {

        try {
            // Initialise Decryption Cipher instance.
            Cipher decryptionCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            decryptionCipher.init(Cipher.DECRYPT_MODE, LoadKeyConfiguration.privateKeyInst);

            byte[] encryptedMessageBytes = decryptionCipher.doFinal(Base64Utils.decodeFromUrlSafeString(inputData));
            String decryption = new String(encryptedMessageBytes);
            //sensitiveLogger.debug("Decryption string is {} ", decryption);

            return decryption;
        } catch (NoSuchAlgorithmException ex){
            log.error("Incorrect Algorithm provided to get Cipher instance. {}", ex.getMessage());
            throw new RSADecryptionException(ex);
        } catch (NoSuchPaddingException  ex){
            log.error("Cipher initialised with incorrect/un-supported Padding! {}", ex.getMessage());
            throw new RSADecryptionException(ex);
        }  catch (InvalidKeyException ex){
            log.error("Invalid Key Instance provided to initialise decryption cipher. {}", ex.getMessage());
            throw new RSADecryptionException(ex);
        } catch (Exception ex) {
            log.error("RSA decryption failed! ");
            throw new RSADecryptionException(ex);
        }
    }
}
