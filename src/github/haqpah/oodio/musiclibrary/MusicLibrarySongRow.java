package github.haqpah.oodio.musiclibrary;

import javafx.beans.property.SimpleStringProperty;

/**
 * A row to be submitted to the music library table view, populated with the passed metadata
 *
 * @version 0.0.0.20170429
 * @since 0.0
 */
public class MusicLibrarySongRow
{
	/**
	 * The title column value
	 */
	private SimpleStringProperty title_;

	/**
	 * The artist column value
	 */
	private SimpleStringProperty artist_;

	/**
	 * The album column value
	 */
	private SimpleStringProperty album_;

	/**
	 * The genre colum value
	 */
	private SimpleStringProperty genre_;

	/**
	 * The year column value
	 */
	private SimpleStringProperty year_;

	/**
	 * A row to be submitted to the music library table view, populated with the passed {@link MusicLibrarySong}
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @param song
	 *            the song to populate this row with
	 */
	public MusicLibrarySongRow(MusicLibrarySong song)
	{
		title_ = new SimpleStringProperty(song.getTitle());
		artist_ = new SimpleStringProperty(song.getArtist());
		album_ = new SimpleStringProperty(song.getAlbum());
		genre_ = new SimpleStringProperty(song.getGenre());
		year_ = new SimpleStringProperty(song.getYear());
	}

	/**
	 * Accessor for the name
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @return the name
	 */
	public SimpleStringProperty titleProperty()
	{
		return title_;
	}

	/**
	 * Accessor for the artist
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @return the artist
	 */
	public SimpleStringProperty artistProperty()
	{
		return artist_;
	}

	/**
	 * Accessor for the album
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @return the album
	 */
	public SimpleStringProperty albumProperty()
	{
		return album_;
	}

	/**
	 * Accessor for the genre
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @return the genre
	 */
	public SimpleStringProperty genreProperty()
	{
		return genre_;
	}

	/**
	 * Accessor for the year
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @return the year
	 */
	public SimpleStringProperty yearProperty()
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
		sb.append("row[ ")
				.append(title_.getValue())
				.append(", ")
				.append(artist_.getValue())
				.append(", ")
				.append(album_.getValue())
				.append(", ")
				.append(genre_.getValue())
				.append(", ")
				.append(year_.getValue())
				.append("]");

		return sb.toString();
	}
}
