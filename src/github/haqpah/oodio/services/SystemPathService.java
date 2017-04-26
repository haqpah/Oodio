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
}
