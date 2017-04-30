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
	private List<ArtistMetadata> libraryMetadata_;

	/**
	 *
	 * Constructor
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @param libraryDirectory
	 * @param systemLogger
	 * @throws Exception
	 */
	public MusicLibraryMetadata(Path libraryDirectory, final Logger systemLogger) throws Exception
	{
		libraryDirectory_ = libraryDirectory;
		libraryMetadata_ = new ArrayList<ArtistMetadata>();

		// run synchronous directory parse
		runSynchDirectoryParse(systemLogger);

		// XXX: asynch is too fast for small library?
		// run asynchronous directory parse
		// runAsynchDirectoryParse(systemLogger);

	}

	/**
	 * Method to synchronously parse through a music library directory for artist metadata.
	 * <p>
	 * With a small library, it appears to be more reliable that {@link #runAsynchDirectoryParse(Logger)}.
	 *
	 * @version 0.0.0.20170430
	 * @since 0.0
	 *
	 * @param systemLogger
	 *            logs this synch process with debug logs.
	 */
	private void runSynchDirectoryParse(final Logger systemLogger) throws Exception
	{
		DirectoryStream<Path> libraryStream = Files.newDirectoryStream(libraryDirectory_);
		for(Path artistDirectory : libraryStream)
		{
			ArtistMetadataLoaderService artistMetadataLoader = new ArtistMetadataLoaderService(artistDirectory, systemLogger);
			ArtistMetadata artistMetadata = artistMetadataLoader.traverseArtistDirectory();

			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Synchronous result is being added to in-memory music library")
					.append(System.lineSeparator())
					.append(artistMetadata.toString());

			systemLogger.debug(logMessage.toString());

			libraryMetadata_.add(artistMetadata);
		}
	}

	/**
	 * Method to asynchronously parse through a music library directory for artist metadata.
	 * <p>
	 * This method is not used but the logic should be saved. With a small library, it appears
	 * to execute to fast and miss data.
	 * <p>
	 * Make sure debug logging is on in log4j.properties
	 *
	 * @version 0.0.0.20170430
	 * @since 0.0
	 *
	 * @param systemLogger
	 *            logs this asynch process with debug logs.
	 */
	private void runAsynchDirectoryParse(final Logger systemLogger)
	{
		List<Future<ArtistMetadata>> artistFutures = new ArrayList<Future<ArtistMetadata>>();
		try
		{
			digDownMusicLibraryDirectory(artistFutures, systemLogger);
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
	private void digDownMusicLibraryDirectory(List<Future<ArtistMetadata>> artistFutures, final Logger systemLogger) throws IOException
	{
		ExecutorService executor = Executors.newCachedThreadPool();

		DirectoryStream<Path> libraryStream = Files.newDirectoryStream(libraryDirectory_);

		// TODO race condition. maybe futures are too fast for this
		for(Path artistDirectory : libraryStream)
		{
			// Submit a new callable to the executor for faster directory traversal
			Callable<ArtistMetadata> callable = new Callable<ArtistMetadata>()
			{
				@Override
				public ArtistMetadata call() throws Exception
				{
					return new ArtistMetadataLoaderService(artistDirectory, systemLogger).traverseArtistDirectory();
				}
			};
			Future<ArtistMetadata> artistFuture = executor.submit(callable);
			artistFutures.add(artistFuture);
		}
	}

	/**
	 * Convenience method to populate the in-memory music library metadata object
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
		for(Future<ArtistMetadata> future : artistFutures)
		{
			ArtistMetadata artistMetadata = future.get();

			StringBuilder logMessage = new StringBuilder();
			logMessage.append("Future result is being added to in-memory music library")
					.append(System.lineSeparator())
					.append(artistMetadata.toString());

			systemLogger.debug(logMessage.toString());

			libraryMetadata_.add(artistMetadata);
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
	public List<ArtistMetadata> getLibraryMetadata()
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
