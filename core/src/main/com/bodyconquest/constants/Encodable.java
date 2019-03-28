package main.com.bodyconquest.constants;

/**
 * The interface Encodable.
 *
 * @param <T> the type parameter
 */
public interface Encodable<T extends Enum> {

    /**
     * Gets the encoded String.
     *
     * @return the encoded String
     */
    String getEncoded();

}
