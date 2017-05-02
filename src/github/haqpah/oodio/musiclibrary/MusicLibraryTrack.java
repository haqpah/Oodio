package github.haqpah.oodio.musiclibrary;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;

import org.apache.log4j.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;

/**
 * Contains values to be transformed into a {@link MusicLibraryRow}
 *
 * @version 0.0.0.20170430
 * @since 0.0
 */
public class MusicLibraryTrack
{
	/**
	 * The file path for this track
	 */
	private Path filePath_;

	/**
	 * The title of the track
	 */
	private StringProperty title_;

	/**
	 * The artist of the track
	 */
	private StringProperty artist_;

	/**
	 * The album the track is on
	 */
	private StringProperty album_;

	/**
	 * The genre of the track
	 */
	private StringProperty genre_;

	/**
	 * The year the track was released
	 */
	private StringProperty year_;

	/**
	 * POJO containing values to be transformed into a {@link MusicLibraryRow}
	 *
	 * @version 0.0.0.20170430
	 * @since 0.0
	 *
	 * @param filePath
	 *            the file path for the track to make this object from
	 *
	 * @throws IllegalArgumentException
	 *             if the passed file path is not valid
	 * @throws InterruptedException
	 *             if the thread is interrupted while loading new media
	 */
	public MusicLibraryTrack(Path filePath, Logger systemLogger) throws IllegalArgumentException, InterruptedException
	{
		filePath_ = filePath;

		artist_ = new SimpleStringProperty();
		title_ = new SimpleStringProperty();
		album_ = new SimpleStringProperty();
		genre_ = new SimpleStringProperty();
		year_ = new SimpleStringProperty();

		String filePathString = filePath_.toUri().toString();
		Media media = new Media(filePathString);

		// XXX: workaround because getMetadata() is asynchronous and a little slow
		media.getMetadata().addListener((MapChangeListener<? super String, ? super Object>) c -> {
			if(c.wasAdded())
			{
				if("artist".equals(c.getKey()))
				{
					artist_.set(c.getValueAdded().toString());
				}
				else if("title".equals(c.getKey()))
				{
					title_.set(c.getValueAdded().toString());
				}
				else if("album".equals(c.getKey()))
				{
					album_.set(c.getValueAdded().toString());
				}
				else if("genre".equals(c.getKey()))
				{
					genre_.set(c.getValueAdded().toString());
				}
				else if("year".equals(c.getKey()))
				{
					year_.set(c.getValueAdded().toString());
				}
			}
		});
	}

	/**
	 * POJO containing values to be transformed into a {@link MusicLibraryRow}
	 *
	 * @version 0.0.0.20170430
	 * @since 0.0
	 *
	 * @param file
	 *            the file to make this object from
	 * @return
	 * @throws InterruptedException
	 *             if the thread is interrupted while loading new media
	 * @throws UnsupportedEncodingException
	 *             if the filepath cannot be encoded using UTF-8
	 */
	public MusicLibraryTrack(File file, Logger systemLogger) throws InterruptedException, UnsupportedEncodingException
	{
		this(file.toPath(), systemLogger);
	}

	/**
	 * Accessor for the track's file path
	 *
	 * @version 0.0.0.20170430
	 * @since 0.0
	 *
	 * @return the file path
	 */
	public Path getFilePath()
	{
		return filePath_;
	}

	/**
	 * Accessor for the title
	 *
	 * @version 0.0.0.20170430
	 * @since 0.0
	 *
	 * @return the title
	 */
	public StringProperty titleProperty()
	{
		return title_;
	}

	/**
	 * Accessor for the artist
	 *
	 * @version 0.0.0.20170430
	 * @since 0.0
	 *
	 * @return the artist
	 */
	public StringProperty artistProperty()
	{
		return artist_;
	}

	/**
	 * Accessor for the album
	 *
	 * @version 0.0.0.20170430
	 * @since 0.0
	 *
	 * @return the album
	 */
	public StringProperty albumProperty()
	{
		return album_;
	}

	/**
	 * Accessor for the genre
	 *
	 * @version 0.0.0.20170430
	 * @since 0.0
	 *
	 * @return the genre
	 */
	public StringProperty genreProperty()
	{
		return genre_;
	}

	/**
	 * Accessor for the year
	 *
	 * @version 0.0.0.20170430
	 * @since 0.0
	 *
	 * @return the year
	 */
	public StringProperty yearProperty()
	{
		return year_;
	}

	/**
	 * @version 0.0.0.20170430
	 * @since 0.0
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		// No carriage returns
		sb.append("track[")
				.append(title_)
				.append(", ")
				.append(artist_)
				.append(", ")
				.append(album_)
				.append(", ")
				.append(genre_)
				.append(", ")
				.append(year_)
				.append("]");

		return sb.toString();
	}
}
