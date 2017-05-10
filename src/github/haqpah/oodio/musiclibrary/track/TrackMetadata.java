package github.haqpah.oodio.musiclibrary.track;

import javafx.scene.media.Media;

/**
 * Holds the string representation of the key that {@link Media#getMetadata()} returns/expects
 *
 * @version 0.0.0.20170510
 * @since 0.0
 */
public enum TrackMetadata
{
	ALBUM_ARTIST("album artist", "Album Artist"),
	TRACK_NUMBER("track number", "Track Number"),
	IMAGE("image", "Album Art"),
	ARTIST("artist", "Artist"),
	YEAR("year", "Year"),
	ALBUM("album", "Album"),
	COMPOSER("composer", "Composer"),
	TITLE("title", "Title"),
	DISC_NUMBER("disc number", "Disc Number"),
	GENRE("genre", "Genre"),
	;

	/**
	 * The string representation of the key
	 */
	private String key_;

	/**
	 * A user friendly display version of the enum value
	 */
	private String displayValue_;

	/**
	 * Holds the string representation of the key that {@link Media#getMetadata()} returns/expects
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @param key
	 *            the key of the map returned from {@link Media#getMetadata()}
	 */
	TrackMetadata(String key, String displayValue)
	{
		key_ = key;
		displayValue_ = displayValue;
	}

	/**
	 * Return the metadata key
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @return the key
	 */
	public String getKey()
	{
		return key_;
	}

	/**
	 * Return the display value
	 *
	 * @version 0.0.0.20170510
	 * @since 0.0
	 *
	 * @return the user friendly version
	 */
	public String getDisplayValue()
	{
		return displayValue_;
	}
}
