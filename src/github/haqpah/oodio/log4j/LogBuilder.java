package github.haqpah.oodio.log4j;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.naming.SizeLimitExceededException;

import github.haqpah.oodio.Oodio;
import javafx.scene.media.Media;

/**
 * Builder class for log messages in Oodio. Log messages should have a specific
 * uniformity regardless of the contributor.
 * <p>
 * <strong>Recommended Usage:</strong><br>
 * <blockquote>This builder can contain one message object, one media object, and one player object. Append each object to this builder in any
 * order. Call {@link #toString()} to get a {@link String} representation of this builder.</blockquote>
 *
 * @version 0.0.0.20170423
 * @since 0.0
 */
public class LogBuilder
{
	/**
	 * Underlying {@link StringBuilder} building the log message section containing the message. This field should be directly accessed when building
	 * sections within this class in order to avoid a {@link SizeLimitExceededException}
	 */
	private StringBuilder messageBuilder_;

	/**
	 * Underlying {@link StringBuilder} building the log message section containing the {@link Media} object. This section will contain the file
	 * source of the {@link Media} object.
	 * <p>
	 * Note: This field should be directly accessed when building sections within this class in order to avoid a {@link SizeLimitExceededException}
	 */
	private StringBuilder mediaBuilder_;

	/**
	 * Underlying {@link StringBuilder} building the log message section containing the {@link OodioPlayer} object. This section will contain the file
	 * source of the {@link Media} object and each {@link Button}'s {@link ButtonActionType}.
	 * <p>
	 * Note: This field should be directly accessed when building sections within this class in order to avoid a {@link SizeLimitExceededException}
	 */
	private StringBuilder playerBuilder_;

	/**
	 * Underlying {@link StringBuilder} building the log message section containing the {@link Exception} object. This section will contain the
	 * exception's stack trace.
	 * <p>
	 * Note: This field should be directly accessed when building sections within this class in order to avoid a {@link SizeLimitExceededException}
	 */
	private StringBuilder exceptionBuilder_;

	/**
	 * Builder class for log messages in Oodio. Log messages should have a specific
	 * uniformity regardless of the contributor.
	 * <p>
	 * <strong>Recommended Usage:</strong><br>
	 * <blockquote>This builder can contain one message object, one media object, and one player object. Append each object to this builder in any
	 * order. Call {@link #toString()} to get a {@link String} representation of this builder.</blockquote>
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 */
	public LogBuilder()
	{
		messageBuilder_ = new StringBuilder();
		mediaBuilder_ = new StringBuilder();
		playerBuilder_ = new StringBuilder();
		exceptionBuilder_ = new StringBuilder();
	}

	/**
	 * Instantiates a new instance of {@link LogBuilder}.
	 * <p>
	 * <strong>Important:</strong>
	 * <blockquote>The previous log message will be lost if {@link #toString()} was not called prior to calling this method!</blockquote>
	 *
	 * @version 0.0.0.20170424
	 * @since 0.0
	 *
	 */
	public void newInstance()
	{
		new LogBuilder();
	}

	/**
	 * Appends a {@link String} to this builder
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @param param0
	 *            the string to be appended
	 */
	public void append(String param0)
	{
		try
		{
			if(messageBuilder_.length() == 0)
			{
				messageBuilder_.append(param0);
			}
			else
			{
				throw new SizeLimitExceededException("The builder cannot contain more than one message");
			}
		}
		catch (SizeLimitExceededException e)
		{
			logSizeLimitExceededException(e);
		}
	}

	/**
	 * Appends {@link Media} to this builder
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @param media
	 *            the media to be appended
	 */
	public void append(Media media)
	{
		try
		{
			if(mediaBuilder_.length() == 0)
			{
				mediaBuilder_.append("  Media: " + media.getSource());
			}
			else
			{
				throw new SizeLimitExceededException("The builder cannot contain more than one media object");
			}
		}
		catch (SizeLimitExceededException e)
		{
			logSizeLimitExceededException(e);
		}
	}

	/**
	 * Appends an {@link Exception} to this builder
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @param exception
	 *            the exception to be appended
	 */
	public void append(Exception exception)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);

		exceptionBuilder_.append(sw.toString());
	}

	/**
	 * Directly accesses the {@link Oodio#getSystemLogger()} to log an exception within this builder
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @param e
	 */
	private void logSizeLimitExceededException(SizeLimitExceededException e)
	{
		// TODO
		// getSystemLogger().error(e.getMessage(), e);
	}

	/**
	 * Returns a string representation of the object.
	 * <p>
	 * Log messages should have a specific uniformity regardless of the contributor.
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 */
	@Override
	public String toString()
	{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(messageBuilder_.toString());

		if(playerBuilder_.length() == 0) // There is no media in this builder
		{
			stringBuilder.append(System.lineSeparator()).append(mediaBuilder_.toString());
		}

		if(playerBuilder_.length() > 0) // There is media in this builder
		{
			stringBuilder.append(System.lineSeparator()).append(mediaBuilder_.toString()).append(System.lineSeparator()).append(playerBuilder_.toString());
		}

		if(exceptionBuilder_.length() > 0) // There is an exception in this builder
		{
			stringBuilder.append(System.lineSeparator()).append(exceptionBuilder_.toString());
		}

		return stringBuilder.toString();
	}
}
