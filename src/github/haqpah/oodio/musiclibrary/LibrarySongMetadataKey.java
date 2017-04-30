package github.haqpah.oodio.musiclibrary;

import javafx.scene.media.Media;

/**
 * The metadata that should be shown in a {@link MusicLibrarySongRow}
 *
 * @version 0.0.0.20170429
 * @since 0.0
 */
public enum LibrarySongMetadataKey
{
	ARTIST("artist"),
	YEAR("year"),
	ALBUM("album"),
	TITLE("title"),
	GENRE("genre"),
	;

	/**
	 * The string representation of the key
	 */
	private String key_;

	/**
	 * Holds the {@link JavaFxMediaMetadataKey}s that should be shown for a {@link MusicLibrarySongRow}
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @param key
	 *            the key of the map returned from {@link Media#getMetadata()}
	 */
	LibrarySongMetadataKey(String key)
	{
		key_ = key;
	}

	/**
	 * Checks if the passed key matches any of the {@link #key_} for the values in this enum
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @param keyToCheck
	 *            the key to check against this enums values
	 * @return {@code true} if the passed key can be matched to one of the values
	 */
	public static boolean findMatch(String keyToCheck)
	{
		for(LibrarySongMetadataKey value : values())
		{
			if(keyToCheck.equals(value.getKey()))
			{
				return true;
			}
		}

		return false;
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
