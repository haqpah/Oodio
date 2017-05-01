package github.haqpah.oodio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import github.haqpah.oodio.application.controller.FxmlController;
import github.haqpah.oodio.application.controller.SystemController;
import github.haqpah.oodio.musiclibrary.MusicLibrary;
import github.haqpah.oodio.services.SystemPathService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The Oodio media player
 * <p>
 * An open-source desktop audio player capable of library and playlist management.<br>
 * This application is licensed under GNU General Public License v3.
 *
 * @author HaqpaH (haqpah@gmail.com)
 *
 * @version 0.0.0.20170422
 * @since 0.0
 *
 * @see https://github.com/haqpah/oodio
 * @see https://www.gnu.org/licenses/gpl-3.0.en.html
 */
public class Oodio extends Application
{
	/**
	 * Official name for the application
	 */
	private static final String APPLICATION_NAME_ = "Oodio";

	/**
	 * The {@link OodioLogger} object for the application
	 */
	public static Logger systemLogger_;

	/**
	 * The primary {@link Stage} for Oodio.
	 */
	public static Stage primaryStage_;

	/**
	 * The music library loaded in-memory. <strong>This is not a collection of playable media files!</strong>
	 * <p>
	 * This is a collection of artist metadata, which contains album metadata for each album in
	 * the artist's directory. Each album metadata contains a list of metadata objects for each song.
	 */
	public static MusicLibrary musicLibrary_;

	/**
	 * The main method for the Oodio media player
	 *
	 * @version 0.0.1.20170423
	 * @since 0.0
	 *
	 * @param args
	 *            Command line arguments
	 */
	public static void main(String[] args)
	{
		PropertyConfigurator.configure(SystemPathService.getLog4jPropertiesFilePath().toString());
		systemLogger_ = Logger.getLogger("rootLogger");
		systemLogger_.info(APPLICATION_NAME_ + " has begun execution");

		discoverOrCreateMusicLibraryDirectory();

		try
		{
			musicLibrary_ = new MusicLibrary(SystemPathService.getMusicLibraryDirectory(), systemLogger_);
		}
		catch (Exception e)
		{
			systemLogger_.error("Could not load music library", e);
		}

		systemLogger_.info("Launching application");
		launch(args);
	}

	/**
	 * Discovers an existing music library directory. If one is not found, creates it.
	 *
	 * @version 0.0.0.20170430
	 * @since 0.0
	 */
	private static void discoverOrCreateMusicLibraryDirectory()
	{
		Path library = SystemPathService.getMusicLibraryDirectory();

		// Search for an existing library
		if(!Files.exists(library))
		{
			try
			{
				// Create the library
				Files.createDirectory(library);
			}
			catch (IOException e)
			{
				systemLogger_.error("Could not create music library directory at " + library.toUri().toString(), e);
			}
		}
	}

	/**
	 * Main entry point to the JavaFX application
	 *
	 * @version 0.0.1.20170423
	 * @since 0.0
	 */
	@Override
	public void start(Stage primaryStage)
	{
		primaryStage_ = primaryStage;
		primaryStage_.setTitle(APPLICATION_NAME_);

		FxmlController systemController = new SystemController(primaryStage_, systemLogger_, musicLibrary_);

		primaryStage_.setScene(new Scene((AnchorPane) systemController.getRootNode()));
		primaryStage_.setMaximized(true);
		primaryStage_.show();
	}

	/**
	 * Gets the system logger
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *        m
	 * @return the system logger
	 */
	public static Logger getSystemLogger()
	{
		return systemLogger_;
	}
}