package github.haqpah.oodio;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import github.haqpah.oodio.application.controller.FxmlController;
import github.haqpah.oodio.application.controller.SystemController;
import github.haqpah.oodio.musiclibrary.MusicLibrary;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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
	 * The logger for this class
	 */
	public static Logger logger_ = LogManager.getLogger(Oodio.class);

	/**
	 * Official name for the application
	 */
	private static final String APPLICATION_NAME_ = "Oodio";

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
		logger_.error(APPLICATION_NAME_ + " has begun execution");

		launch();
	}

	/**
	 * Main entry point to the JavaFX application
	 *
	 * @version 0.2.0.20170510
	 * @since 0.0
	 */
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			MusicLibrary musicLibrary = new MusicLibrary();

			FxmlController systemController = new SystemController(musicLibrary);
			Pane root = systemController.getRootPane();
			primaryStage.setScene(new Scene(root));
		}
		catch (Exception e)
		{
			logger_.error("Could not load music library", e);
		}

		primaryStage.setTitle(APPLICATION_NAME_);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

	/**
	 * @version 0.0.0.20170501
	 * @since 0.0
	 */
	@Override
	public void stop() throws Exception
	{
		logger_.info("Exiting application via X button");
		LogManager.shutdown();

		super.stop();
		System.exit(0);
	}
}