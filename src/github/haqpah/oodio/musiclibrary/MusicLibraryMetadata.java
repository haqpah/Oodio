package github.haqpah.oodio.musiclibrary;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import github.haqpah.oodio.services.ArtistMetadataLoaderService;

public class MusicLibraryMetadata
{
	private Path libraryDirectory_;
	private ArrayList<ArtistMetadata> libraryMetadata_;

	/**
	 *
	 * Constructor
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @param libraryDirectory
	 * @param systemLogger
	 */
	public MusicLibraryMetadata(Path libraryDirectory, final Logger systemLogger)
	{
		libraryDirectory_ = libraryDirectory;

		List<Future<ArtistMetadata>> artistFutures = new ArrayList<Future<ArtistMetadata>>();
		try
		{
			digDownMusicLibraryDirectory(artistFutures);
		}
		catch (IOException e)
		{
			systemLogger.error("Could not create new library stream", e);
			return;
		}

		systemLogger.info(artistFutures.size() + " callable futures were submitted to the executor service");

		try
		{
			populateMusicLibraryMetadata(artistFutures, systemLogger);
		}
		catch (InterruptedException | ExecutionException e)
		{
			systemLogger.error("Artist future failed to finish. Could not load albums", e);
			return;
		}

		systemLogger.info(artistFutures.size() + " callable futures were successfully executed");
	}

	/**
	 * Convenience method to "dig down" through each artist in the library asynchronously and get
	 * metadata on each song in each of their album directories
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @param artistFutures
	 *            a list of {@link Callable} futures to be submitted to the executor service
	 * @throws IOException
	 *             if there is a problem creating the library directory stream
	 */
	private void digDownMusicLibraryDirectory(List<Future<ArtistMetadata>> artistFutures) throws IOException
	{
		ExecutorService executor = Executors.newCachedThreadPool();

		DirectoryStream<Path> libraryStream = Files.newDirectoryStream(libraryDirectory_);

		for(Path artistDirectory : libraryStream)
		{
			// Submit a new callable to the executor for faster directory traversal
			Future<ArtistMetadata> artistFuture = executor.submit(new ArtistMetadataLoaderService(artistDirectory));
			artistFutures.add(artistFuture);
		}
	}

	/**
	 * Convenience method to populate the in memory music library metadata object
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @param artistFutures
	 *            a list of {@link Callable} futures to be retrieved, returning metadata on
	 *            each artist directory in the music library directory
	 * @throws IOException
	 *             if there is a problem creating the library directory stream
	 */
	private void populateMusicLibraryMetadata(List<Future<ArtistMetadata>> artistFutures, final Logger systemLogger)
			throws InterruptedException, ExecutionException
	{
		libraryMetadata_ = new ArrayList<ArtistMetadata>();

		for(Future<ArtistMetadata> future : artistFutures)
		{
			libraryMetadata_.add(future.get());
		}
	}

	/**
	 * Gets the {@link ArtistMetadata} about this library.
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @return the metadata
	 */
	public ArrayList<ArtistMetadata> getLibraryMetadata()
	{
		return libraryMetadata_;
	}

	/**
	 * Gets this music library's {@link Path}
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @return the path
	 */
	public Path getLibraryDirectory()
	{
		return libraryDirectory_;
	}
}
