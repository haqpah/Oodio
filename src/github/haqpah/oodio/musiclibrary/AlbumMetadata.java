package github.haqpah.oodio.musiclibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import github.haqpah.oodio.services.ArtistMetadataLoaderService;

/**
 * An object to contain a list of song metadata objects (as {@link Map}) loaded by the {@link ArtistMetadataLoaderService}
 *
 * @version 0.0.0.20170428
 * @since 0.0
 */
public class AlbumMetadata
{
	/**
	 * The album name
	 */
	private String albumName_;

	/**
	 * A list of metadata objects
	 */
	private List<Map<String, Object>> songMetadataList_;

	/**
	 * An object to contain a list of metadata objects loaded by the {@link ArtistMetadataLoaderService}
	 *
	 * @version 0.0.0.20170428
	 * @since 0.0
	 */
	public AlbumMetadata(String albumName)
	{
		albumName_ = albumName;
		songMetadataList_ = new ArrayList<Map<String, Object>>();
	}

	/**
	 * Gets the name of the album
	 *
	 * @version 0.0.0.20170428
	 * @since 0.0
	 *
	 * @return the album name
	 */
	public String getAlbumName()
	{
		return albumName_;
	}

	/**
	 * Gets the list of song metadata objects
	 *
	 * @version 0.0.0.20170428
	 * @since 0.0
	 *
	 * @return the list of song metadata objects
	 */
	public List<Map<String, Object>> getSongMetadata()
	{
		return songMetadataList_;
	}
}
