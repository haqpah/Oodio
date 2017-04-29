package github.haqpah.oodio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import github.haqpah.oodio.application.controller.FxmlController;
import github.haqpah.oodio.application.controller.MusicLibraryController;
import github.haqpah.oodio.application.controller.SystemMenuController;
import github.haqpah.oodio.application.controller.SystemPlayerController;
import github.haqpah.oodio.musiclibrary.MusicLibraryMetadata;
import github.haqpah.oodio.services.SystemPathService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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
	 * The music library loaded in memory. <strong>This is not a collection of playable media files!</strong>
	 * <p>
	 * This is a collection of artist metadata, which contains album metadata for each album in
	 * the artist's directory. Each album metadata contains a list of metadata objects for each song.
	 */
	public static MusicLibraryMetadata musicLibraryMetadata_;

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

		loadMusicLibrary();

		try
		{
			Thread.sleep(5000);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		systemLogger_.info("Launching application");
		launch(args);
	}

	/**
	 * Convenience method for housing logic for setting up the application's music library view. This
	 * entails searching for an existing music folder and traversing all directories, or creating the
	 * folder if one is not found.
	 * <p>
	 * The music library loaded in memory. <strong>This is not a collection of playable media files!</strong>
	 * <p>
	 * This is a collection of artist metadata, which contains album metadata for each album in
	 * the artist's directory. Each album metadata contains a list of metadata objects for each song.
	 *
	 * @version 0.0.0.20170427
	 * @since 0.0
	 */
	private static void loadMusicLibrary()
	{
		try
		{
			Path library = SystemPathService.getMusicLibraryDirectory();

			// Search for an existing library
			if(!Files.exists(library))
			{
				// Create the library
				Files.createDirectory(library);
			}

			musicLibraryMetadata_ = new MusicLibraryMetadata(library, systemLogger_);

			systemLogger_.info("Music library has successfully be loaded");

		}
		catch (IOException e)
		{
			systemLogger_.fatal("An exception occurred while loading the music library", e);
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

		BorderPane root = new BorderPane();

		FxmlController systemMenuController = new SystemMenuController(primaryStage_, systemLogger_, musicLibraryMetadata_);
		root.setTop(systemMenuController.getRootNode());

		FxmlController musicLibraryController = new MusicLibraryController(primaryStage_, systemLogger_, musicLibraryMetadata_);
		root.setCenter(musicLibraryController.getRootNode());

		FxmlController systemPlayerController = new SystemPlayerController(primaryStage_, systemLogger_);
		root.setBottom(systemPlayerController.getRootNode());

		primaryStage_.setScene(new Scene(root));
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