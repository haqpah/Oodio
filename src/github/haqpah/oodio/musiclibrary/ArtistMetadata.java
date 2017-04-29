package github.haqpah.oodio.musiclibrary;

import java.util.ArrayList;
import java.util.List;

import github.haqpah.oodio.services.ArtistMetadataLoaderService;

/**
 * An object to contain a list of {@link AlbumMetadata} objects loaded by the {@link ArtistMetadataLoaderService}
 *
 * @version 0.0.0.20170428
 * @since 0.0
 */
public class ArtistMetadata
{
	/**
	 * The artist's name
	 */
	private String artistName_;

	/**
	 * A list of {@link AlbumMetadata}, which contains a list of song metadata objects
	 */
	private List<AlbumMetadata> albumMetadata_;

	/**
	 *
	 * Constructor
	 *
	 * @version 0.0.0.20170428
	 * @since 0.0
	 */
	public ArtistMetadata(String artistName)
	{
		artistName_ = artistName;
		albumMetadata_ = new ArrayList<AlbumMetadata>();
	}

	/**
	 * Gets the artist's name
	 *
	 * @version 0.0.0.20170428
	 * @since 0.0
	 */
	public String getArtistName()
	{
		return artistName_;
	}

	/**
	 * Gets the list of {@link AlbumMetadata} objects for this artist
	 *
	 * @version 0.0.0.20170428
	 * @since 0.0
	 *
	 * @return
	 */
	public List<AlbumMetadata> getAlbumMetadata()
	{
		return albumMetadata_;
	}
}
