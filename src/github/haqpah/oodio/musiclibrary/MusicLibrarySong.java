package github.haqpah.oodio.musiclibrary;

import java.util.Map;

/**
 * POJO containing values to be transformed into a {@link MusicLibraryRow}
 *
 * @version 0.0.0.20170430
 * @since 0.0
 */
public class MusicLibrarySong
{
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
	 */
	public MusicLibrarySong(Map<String, Object> rawMetadata)
	{
		for(String key : rawMetadata.keySet())
		{
			switch (key)
			{
				case "title":
					title_ = (String) rawMetadata.get(key);
					break;
				case "artist":
					artist_ = (String) rawMetadata.get(key);
					break;
				case "album":
					album_ = (String) rawMetadata.get(key);
					break;
				case "genre":
					genre_ = (String) rawMetadata.get(key);
					break;
				case "year":
					year_ = String.valueOf(rawMetadata.get(key));
					break;
				default:
					; // do nothing
			}
		}
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
