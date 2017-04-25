package application.player;

/**
 * A generic exception for an {@link OodioPlayer} object
 *
 * @version 0.0.0.20170423
 * @since 0.0
 */
public class OodioPlayerException extends Exception
{

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = -7344273029930202296L;

	/**
	 * Constructor
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @param message
	 *            a message to add to the exception
	 */
	public OodioPlayerException(String message)
	{
		super(message);
	}

	/**
	 * Constructor
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @param exception
	 *            the underlying {@link Throwable} that cause this exception to occur
	 */
	public OodioPlayerException(Throwable exception)
	{
		super(exception);
	}

	/**
	 * Constructor
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @param message
	 *            a message to add to the exception
	 * @param exception
	 *            the underlying {@link Throwable} that cause this exception to occur
	 */
	public OodioPlayerException(String message, Throwable exception)
	{
		super(message, exception);
	}
}
