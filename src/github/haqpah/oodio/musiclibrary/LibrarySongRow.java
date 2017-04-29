package github.haqpah.oodio.musiclibrary;

import java.util.Map;
import java.util.Map.Entry;

import javafx.beans.property.SimpleStringProperty;

/**
 * A row to be submitted to the music library table view, populated with the passed metadata
 *
 * @version 0.0.0.20170429
 * @since 0.0
 */
public class LibrarySongRow
{
	/**
	 * The title column value
	 */
	private SimpleStringProperty title_ = new SimpleStringProperty("");

	/**
	 * The artist column value
	 */
	private SimpleStringProperty artist_ = new SimpleStringProperty("");

	/**
	 * The album column value
	 */
	private SimpleStringProperty album_ = new SimpleStringProperty("");

	/**
	 * The genre colum value
	 */
	private SimpleStringProperty genre_ = new SimpleStringProperty("");

	/**
	 * The year column value
	 */
	private SimpleStringProperty year_ = new SimpleStringProperty("");

	/**
	 * A row to be submitted to the music library table view, populated with the passed metadata
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @param songMetadata
	 *            the metadata to populate the row with
	 */
	public LibrarySongRow(Map<String, Object> songMetadata)
	{
		for(Entry<String, Object> entry : songMetadata.entrySet())
		{
			String key = entry.getKey();
			boolean matchFound = LibrarySongMetadataKey.findMatch(key);
			if(matchFound)
			{
				String valueToSave = String.valueOf(songMetadata.get(key));
				switch (key)
				{
					case "title":
						title_.set(valueToSave);
						break;
					case "artist":
						artist_.set(valueToSave);
						break;
					case "album":
						album_.set(valueToSave);
						break;
					case "genre":
						genre_.set(valueToSave);
						break;
					case "year":
						year_.set(valueToSave);
						break;
					default:
						; // do nothing
				}
			}
		}
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
	 * Modifier for the year
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @param year
	 *            the year to set
	 */
	public void setYear(SimpleStringProperty year)
	{
		this.year_ = year;
	}
}
