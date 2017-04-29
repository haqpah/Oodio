package github.haqpah.oodio.services;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A service for getting common locations in the system
 *
 * @version 0.0.0.20170425
 * @since 0.0
 */
public class SystemPathService
{
	/**
	 * Constructor prevents instantiation
	 *
	 * @version 0.0.0.20170425
	 * @since 0.0
	 *
	 */
	private SystemPathService()
	{
		;
	}

	/**
	 * Gets the log4j.properties file as a {@link Path}
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @return the log4j.properties file path
	 */
	public static Path getLog4jPropertiesFilePath()
	{
		return Paths.get("src/github/haqpah/oodio/log/log4j/log4j.properties");
	}

	/**
	 * Gets the {@link Path} for the FXML directory
	 *
	 * @version 0.0.0.20170425
	 * @since 0.0
	 *
	 * @return
	 */
	public static Path getFxmlDirectory()
	{
		return Paths.get("src/github/haqpah/oodio/application/fxml");
	}

	/**
	 * Gets the {@link Path} for this Oodio's music library. The application:musicLibrary relationship is always 1:1
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @return
	 */
	public static Path getMusicLibraryDirectory()
	{
		return Paths.get("Music Library");
	}
}
