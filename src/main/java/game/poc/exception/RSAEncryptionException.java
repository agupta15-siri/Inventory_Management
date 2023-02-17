package game.poc.exception;

/**
 * The type Rsa encryption exception.
 */
public class RSAEncryptionException extends GenericException {

    public RSAEncryptionException(String message) {
        super(message);
    }

    public RSAEncryptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public RSAEncryptionException(Throwable cause) {
        super(cause);
    }

    public RSAEncryptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
