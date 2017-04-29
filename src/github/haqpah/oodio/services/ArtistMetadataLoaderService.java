package github.haqpah.oodio.services;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;

import github.haqpah.oodio.musiclibrary.AlbumMetadata;
import github.haqpah.oodio.musiclibrary.ArtistMetadata;
import javafx.scene.media.Media;

public class ArtistMetadataLoaderService implements Callable<ArtistMetadata>
{
	/**
	 * The artist directory to load
	 */
	private Path artistDirectory_;

	/**
	 * A service to traverse an artist directory to get metadata on every media file buried in the directory.
	 * <p>
	 * The Oodio music library is organized as "../Music Library/artistName/albumName/songFile.extension"
	 *
	 * @version 0.0.0.20170428
	 * @since 0.0
	 */
	public ArtistMetadataLoaderService(Path artistDirectory)
	{
		artistDirectory_ = artistDirectory;
	}

	/**
	 * @version 0.0.0.20170428
	 * @since 0.0
	 */
	@Override
	public ArtistMetadata call() throws Exception
	{
		return traverseArtistDirectory();
	}

	/**
	 * Traverse the {@link #artistDirectory_} to get the metadata
	 *
	 * @version 0.0.0.20170428
	 * @since 0.0
	 *
	 * @return a list of metadata objects in a map
	 */
	private ArtistMetadata traverseArtistDirectory() throws IOException
	{
		DirectoryStream<Path> artistDirectoryStream = Files.newDirectoryStream(artistDirectory_);

		String artistName = artistDirectory_.getFileName().toString();
		ArtistMetadata artistMetadata = new ArtistMetadata(artistName);

		// Traverse the artist directory for each album
		for(Path albumDirectory : artistDirectoryStream)
		{
			String albumName = albumDirectory.getFileName().toString();
			AlbumMetadata albumMetadata = new AlbumMetadata(albumName);

			// Traverse the album for each song
			DirectoryStream<Path> albumDirectoryStream = Files.newDirectoryStream(albumDirectory);
			for(Path song : albumDirectoryStream)
			{
				// Add the song's metadata (just a Map<String, Object>) to the album metadata object (just a List<Map<String, Object>)
				Media media = new Media(song.toUri().toString());
				albumMetadata.getSongMetadata().add(media.getMetadata());
			}

			// Add this album's metadata to the artist metadata object (ultimately making a List<List<Map<String, Object>>>)
			artistMetadata.getAlbumMetadata().add(albumMetadata);
		}

		return artistMetadata;
	}
}
