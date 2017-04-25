import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import application.player.OodioPlayer;
import application.player.OodioPlayerException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.TilePane;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import log4j.LogBuilder;

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
	 * The {@link OodioLogger} object for the application
	 */
	public static Logger systemLogger_;

	/**
	 * The primary {@link Stage} for Oodio.
	 */
	private static Stage primaryStage_;

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
		PropertyConfigurator.configure("src/log4j/log4j.properties");
		systemLogger_ = Logger.getLogger("rootLogger");
		launch(args);
	}

	/**
	 * @version 0.0.1.20170423
	 * @since 0.0
	 */
	@Override
	public void start(Stage primaryStage)
	{
		systemLogger_.info(APPLICATION_NAME_ + " has begun execution");

		primaryStage_ = primaryStage;

		TilePane root = new TilePane();

		Scene scene = new Scene(root, 300, 250);
		initializePrimaryStage(scene);

		File file = new File("C:/Users/schel/Music/Instrumentals/Jay IDK - Two Hoes.mp3");
		Media media = new Media(file.toURI().toString());

		try
		{
			OodioPlayer player = new OodioPlayer(media);
			player.appendToPane(root);

			LogBuilder logBuilder = new LogBuilder();
			logBuilder.append("Player appended to root pane");
			logBuilder.append(player);

			systemLogger_.info(logBuilder.toString());
		}
		catch (OodioPlayerException e)
		{
			LogBuilder logBuilder = new LogBuilder();
			logBuilder.append("Exception occurred while creating an OodioPlayer with the following media:");
			logBuilder.append(media);

			systemLogger_.fatal(logBuilder.toString());
		}
	}

	/**
	 * Initializes the primary {@link Stage} with the passed {@link Scene}
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @param scene
	 *            the scene to assign to the primary stage
	 */
	private void initializePrimaryStage(Scene scene)
	{
		if(primaryStage_ != null)
		{
			primaryStage_.setTitle(APPLICATION_NAME_);
			primaryStage_.setScene(scene);
			primaryStage_.show();
		}
		else
		{
			throw new NullPointerException("The primary stage's local field is null");
		}
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

	@Override
	public void stop() throws Exception
	{
		// TODO
		systemLogger_.shutdown();

		super.stop();
	}
}