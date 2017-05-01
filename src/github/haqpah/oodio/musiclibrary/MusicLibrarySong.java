package github.haqpah.oodio.musiclibrary;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;

import org.apache.log4j.Logger;

import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;

/**
 * POJO containing values to be transformed into a {@link MusicLibraryRow}
 *
 * @version 0.0.0.20170430
 * @since 0.0
 */
public class MusicLibrarySong
{
	/**
	 * The file path for this song
	 */
	private Path filePath_;

	/**
	 * The title of the song
	 */
	private String title_;

	/**
	 * The artist of the song
	 */
	private String artist_;

	/**
	 * The album the song is on
	 */
	private String album_;

	/**
	 * The genre of the song
	 */
	private String genre_;

	/**
	 * The year the song was released
	 */
	private String year_;

	/**
	 * POJO containing values to be transformed into a {@link MusicLibraryRow}
	 *
	 * @version 0.0.0.20170430
	 * @since 0.0
	 *
	 * @param filePath
	 *            the file path for the song to make this object from
	 *
	 * @throws IllegalArgumentException
	 *             if the passed filepah is not valid
	 * @throws InterruptedException
	 *             if the thread is interrupted while loading new media
	 * @throws UnsupportedEncodingException
	 *             if the filepath cannot be encoded using UTF-8
	 */
	public MusicLibrarySong(Path filePath, Logger systemLogger) throws IllegalArgumentException, InterruptedException, UnsupportedEncodingException
	{
		filePath_ = filePath;

		String filePathString = filePath_.toUri().toString();
		Media media = new Media(filePathString);

		// TODO research asynch on #getMetadata
		media.getMetadata().addListener((MapChangeListener<? super String, ? super Object>) c -> {
			if(c.wasAdded())
			{
				if("artist".equals(c.getKey()))
				{
					artist_ = c.getValueAdded().toString();
				}
				else if("title".equals(c.getKey()))
				{
					title_ = c.getValueAdded().toString();
				}
				else if("album".equals(c.getKey()))
				{
					album_ = c.getValueAdded().toString();
				}
				else if("genre".equals(c.getKey()))
				{
					genre_ = c.getValueAdded().toString();
				}
				else if("year".equals(c.getKey()))
				{
					year_ = c.getValueAdded().toString();
				}
			}
		});

		Thread.sleep(250); // TODO remove once asynch is figured out
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
	public MusicLibrarySong(File file, Logger systemLogger) throws InterruptedException, UnsupportedEncodingException
	{
		// TODO validate string
		this(file.toPath(), systemLogger);
	}

	/**
	 * Accessor for the song's file path
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
	public String getTitle()
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
	public String getArtist()
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
	public String getAlbum()
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
	public String getGenre()
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
	public String getYear()
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
		sb.append("song[")
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
