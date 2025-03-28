package felyv527.tddc77.asciibikes.drawing;

/**
 * To use {@link DrawManager}, a session is required. If there is none available
 * (meaning that {@link DrawManager#startSession()} has not been called), this
 * exception will be thrown.
 *
 */
public class NoActiveDrawSessionException extends RuntimeException {

	private static final long serialVersionUID = 1L;

}