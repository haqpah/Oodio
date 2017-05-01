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
	 * Gets the {@link String} for this Oodio's music library path
	 *
	 * @version 0.0.0.20170429
	 * @since 0.0
	 *
	 * @return the file path as a string
	 */
	public static Path getMusicLibraryDirectory()
	{
		return Paths.get(System.getProperty("user.home"), "Music", "Oodio Music Library");
	}

	/**
	 * Gets the {@link Path} for the default song to be loaded into the system player. This mp3 is shipped with the installation
	 *
	 * @version 0.0.0.20170430
	 * @since 0.0
	 *
	 * @return the hello world mp3
	 */
	public static Path getHelloWorldMediaPath()
	{
		return Paths.get("helloworld.mp3");
	}
}
