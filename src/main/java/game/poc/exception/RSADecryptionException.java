package game.poc.exception;

/**
 * The type Rsa decryption exception.
 */
public class RSADecryptionException extends GenericException {

    public RSADecryptionException(String message) {
        super(message);
    }

    public RSADecryptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public RSADecryptionException(Throwable cause) {
        super(cause);
    }

    public RSADecryptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
