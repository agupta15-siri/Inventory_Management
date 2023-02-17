package game.poc.crypt;


import game.poc.configuration.LoadKeyConfiguration;
import game.poc.exception.RSAEncryptionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * The type Rsa encryptor.
 */
@Slf4j
@Component
public class RSAEncryptor {

    /**
     * Do encryption string.
     *
     * @param inputData the input data
     * @return the string
     */
    public String doEncryption(String inputData) {

        try {
            // Initialise Encryption cipher instance.
            Cipher encryptionCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            encryptionCipher.init(Cipher.ENCRYPT_MODE, LoadKeyConfiguration.publicKeyInst);

            byte[] encryptedMessage = encryptionCipher.doFinal(inputData.getBytes());
            //sensitiveLogger.debug("Encrypted Message is {} " + Base64Utils.encodeToUrlSafeString(encryptedMessage));
            return Base64Utils.encodeToUrlSafeString(encryptedMessage);

        } catch (NoSuchAlgorithmException ex){
            log.error("Incorrect Algorithm provided to get Cipher instance. {}", ex.getMessage());
            throw new RSAEncryptionException(ex);
        } catch (NoSuchPaddingException  ex){
            log.error("Cipher initialised with incorrect/un-supported Padding! {}", ex.getMessage());
            throw new RSAEncryptionException(ex);
        }  catch (InvalidKeyException ex){
            log.error("Invalid Key Instance provided to initialise decryption cipher. {}", ex.getMessage());
            throw new RSAEncryptionException(ex);
        } catch (Exception ex) {
            log.error("RSA encryption failed! ");
            throw new RSAEncryptionException(ex);
        }
    }
}
