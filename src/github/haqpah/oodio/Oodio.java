package github.haqpah.oodio;

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
	 * The music library loaded in-memory. This is <strong>not</strong> a collection of playable media files!
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

		try
		{
			musicLibrary_ = new MusicLibrary(SystemPathService.getMusicLibraryDirectory(), systemLogger_);
		}
		catch (Exception e)
		{
			systemLogger_.error("Could not load music library", e);
		}

		systemLogger_.info("Launching application");

		launch();
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
	 * @version 0.0.0.20170501
	 * @since 0.0
	 */
	@Override
	public void stop() throws Exception
	{
		systemLogger_.info("Exiting application via X button");
		systemLogger_.shutdown(); // TODO

		super.stop();
		System.exit(0);
	}
}