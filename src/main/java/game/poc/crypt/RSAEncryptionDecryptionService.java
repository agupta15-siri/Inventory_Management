package game.poc.crypt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * The type Rsa encryption decryption service.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RSAEncryptionDecryptionService {

    //Sensitive logging - For logging in User data
    private final RSADecryptor rsaDecryptor;
    private final RSAEncryptor rsaEncryptor;

    /**
     * Encrypt data string.
     *
     * @param inputData  attribute to be encrypted
     * @return the string
     */
    public String encryptData(String inputData) {
        //log.info("Decrypted Data: {}", inputData);
        log.debug("Encryption started. Encrypting data ");
        //sensitiveLogger.debug("Encryption started. Encrypting data {}", inputData);
        String encryptedData = rsaEncryptor.doEncryption(inputData);
        //log.debug("Encryption finished successfully ");
        //sensitiveLogger.debug("Encryption finished successfully {} ", encryptedData);
        return encryptedData;
    }

    /**
     * Decrypt data string.
     *
     * @param encryptedData attribute to be decrypted
     * @return the decrypted string
     */
    public String decryptData(String encryptedData) {
        log.debug("Decryption started. Encrypted data");
        //sensitiveLogger.debug("Decryption started. Encrypted data {}", encryptedData);
        String decryptedData = rsaDecryptor.doDecryption(encryptedData);
        //log.debug("Decryption finished successfully ");
        //sensitiveLogger.debug("Decryption finished successfully {} ", decryptedData);
        return decryptedData;
    }
}
