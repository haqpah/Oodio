package github.haqpah.oodio.musiclibrary;

import javafx.scene.media.Media;

/**
 * Holds the string representation of the key that {@link Media#getMetadata()} returns/expects
 *
 * @version 0.0.0.20170429
 * @since 0.0
 */
public enum JavaFxMediaMetadataKey
{
	ALBUM_ARTIST("album artist"),
	TRACK_NUMBER("track number"),
	IMAGE("image"),
	ARTIST("artist"),
	YEAR("year"),
	ALBUM("album"),
	COMPOSER("composer"),
	TITLE("title"),
	DISC_NUMBER("disc number"),
	GENRE("genre"),
	;

	/**
	 * The string representation of the key
	 */
	private String key_;

	/**
	 * Holds the string representation of the key that {@link Media#getMetadata()} returns/expects
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @param key
	 *            the key of the map returned from {@link Media#getMetadata()}
	 */
	JavaFxMediaMetadataKey(String key)
	{
		key_ = key;
	}

	/**
	 * Return the metadata key
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @return
	 */
	public String getKey()
	{
		return key_;
	}
}
