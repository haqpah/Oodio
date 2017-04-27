package github.haqpah.oodio;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import github.haqpah.oodio.application.controller.FxmlController;
import github.haqpah.oodio.application.controller.SystemMenuController;
import github.haqpah.oodio.application.controller.SystemPlayerController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
 * @see https://www.gnu.org/licenses/gpl-3.0.en.html
 * @see https://github.com/haqpah/oodio
 */
public class Oodio extends Application
{
	/**
	 * Official name for the application
	 */
	private static final String APPLICATION_NAME_ = "Oodio";

	/**
	 * The file path for the log4j properties file, responsible for LOG4J configuration
	 */
	private static final String LOG4J_PROPERTIES_FILEPATH_ = "src/github/haqpah/oodio/log/log4j/log4j.properties";

	/**
	 * The {@link OodioLogger} object for the application
	 */
	public static Logger systemLogger_;

	/**
	 * The primary {@link Stage} for Oodio.
	 */
	public static Stage primaryStage_;

	/**
	 * The {@link FxmlController} containing application actions for the user
	 */
	private static FxmlController systemMenuController_;

	/**
	 * The {@link FxmlController} for the system player, responsible for song playback
	 */
	private static FxmlController systemPlayerController_;

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
		PropertyConfigurator.configure(LOG4J_PROPERTIES_FILEPATH_);

		systemLogger_ = Logger.getLogger("rootLogger");
		systemLogger_.info(APPLICATION_NAME_ + " has begun execution");

		launch(args);
	}

	/**
	 * @version 0.0.1.20170423
	 * @since 0.0
	 */
	@Override
	public void start(Stage primaryStage)
	{
		primaryStage_ = primaryStage;
		primaryStage_.setTitle(APPLICATION_NAME_);

		BorderPane root = new BorderPane();

		systemMenuController_ = new SystemMenuController(primaryStage_);
		HBox systemMenuRoot = (HBox) systemMenuController_.getRootPane();

		systemLogger_.info("Setting up new controller");

		systemPlayerController_ = new SystemPlayerController(primaryStage_);
		HBox systemPlayerRoot = (HBox) systemPlayerController_.getRootPane();

		root.setTop(systemMenuRoot);
		root.setCenter(systemPlayerRoot);

		systemLogger_.info("Controller setup complete");

		primaryStage_.setScene(new Scene(root, 300, 250));
		primaryStage_.show();
	}

	/**
	 * Accessor for the {@link #primaryStage_}
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @return the primary stage
	 */
	public Stage getPrimaryStage()
	{
		return primaryStage_;
	}

	/**
	 * Gets the system logger
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @return the system logger
	 */
	public static Logger getSystemLogger()
	{
		return systemLogger_;
	}
}