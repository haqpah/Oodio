package github.haqpah.oodio.musiclibrary;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * A row to be submitted to the music library table view, populated with the passed metadata
 *
 * @version 0.0.0.20170429
 * @since 0.0
 */
public class MusicLibraryTrackRow
{
	/**
	 * The track that this row is built from
	 */
	private MusicLibraryTrack track_;

	/**
	 * The title column value
	 */
	private StringProperty title_;

	/**
	 * The artist column value
	 */
	private StringProperty artist_;

	/**
	 * The album column value
	 */
	private StringProperty album_;

	/**
	 * The genre colum value
	 */
	private StringProperty genre_;

	/**
	 * The year column value
	 */
	private StringProperty year_;

	/**
	 * A row to be submitted to the music library table view, populated with the passed {@link MusicLibraryTrack}
	 * <p>
	 * Due to the asynchronous nature of {@link Media} and its hindrance to {@link Media.getMetadata()}, the properties
	 * should be bound to observable values
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @param track
	 *            the track to populate this row with
	 */
	public MusicLibraryTrackRow(MusicLibraryTrack track)
	{
		track_ = track;

		title_ = new SimpleStringProperty();
		title_.bind(track.titleProperty());

		artist_ = new SimpleStringProperty();
		artist_.bind(track.artistProperty());

		album_ = new SimpleStringProperty();
		album_.bind(track.albumProperty());

		genre_ = new SimpleStringProperty();
		genre_.bind(track.genreProperty());

		year_ = new SimpleStringProperty();
		year_.bind(track.yearProperty());
	}

	/**
	 * Accessor for the track this row is built from
	 *
	 * @version 0.0.0.20170504
	 * @since 0.0
	 *
	 * @return the track
	 */
	public MusicLibraryTrack getTrack()
	{
		return track_;
	}

	/**
	 * Accessor for the name
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @return the name
	 */
	public StringProperty titleProperty()
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
	public StringProperty artistProperty()
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
	public StringProperty albumProperty()
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
	public StringProperty genreProperty()
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
