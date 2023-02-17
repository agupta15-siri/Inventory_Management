package game.poc.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.xml.bind.DatatypeConverter;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * The type Load key configuration.
 */
@Configuration
@Slf4j
public class LoadKeyConfiguration {

    public static PrivateKey privateKeyInst = null;
    public static PublicKey publicKeyInst  = null;

    @Value("${oneapp.encryption.publicKey}")
    private String public_Key;

    @Value("${oneapp.decryption.privateKey}")
    private String private_Key;


    /**
     * Import keys from the configuration file and store them in memory.
     */
    @PostConstruct
    public void loadKeys(){

        // Initialise Encryption & Decryption Private and Public Key Instance
        initPrivateKeyInst(private_Key);
        initPublicKeyInst(public_Key);
    }

    /***
     * Initialises Decryption Private key instance.
     */
    private void initPrivateKeyInst(String privateKey) {

        // Generates Private Key based on property file Private Key.
        try {
            KeyFactory rsaKeyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(DatatypeConverter.parseBase64Binary(privateKey));
            privateKeyInst = rsaKeyFactory.generatePrivate(privateKeySpec);
        } catch (NoSuchAlgorithmException ex) {
            log.error("No Such Algorithm found exception occurred. {}", ex.getMessage());
        } catch(InvalidKeySpecException ex) {
            log.error("Invalid Private Ky Specification. {}", ex.getMessage());
        }
    }

    /***
     * Initialises Encryption public Key instance.
     */
    private void initPublicKeyInst(String publicKey) {

        // Generates Public Key based on property file Public Key.
        try {
            KeyFactory rsaKeyFactory = KeyFactory.getInstance("RSA");
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(DatatypeConverter.parseBase64Binary(publicKey));
            publicKeyInst = rsaKeyFactory.generatePublic(publicKeySpec);

        } catch (NoSuchAlgorithmException ex) {
            log.error("No Such Algorithm found exception occurred. {}", ex.getMessage());
        } catch(InvalidKeySpecException ex) {
            log.error("Invalid Private Ky Specification. {}", ex.getMessage());
        }
    }
}
