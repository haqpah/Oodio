import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import application.player.OodioPlayer;
import application.player.OodioPlayerException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
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
	 * The {@link MenuBar} containing application actions for the user
	 */
	private MenuBar menuBar_;

	/**
	 * The player tied to the {@link #menuBar_}
	 */
	private OodioPlayer systemPlayer_;

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

		TilePane rootNode = new TilePane();

		try
		{
			setupInitialPlayer(rootNode);
		}
		catch (OodioPlayerException e)
		{
			LogBuilder logBuilder = new LogBuilder();
			logBuilder.append("Exception occurred while creating initial player");

			systemLogger_.fatal(logBuilder.toString());
		}

		setupMenuBar(rootNode);

		Scene scene = new Scene(rootNode, 300, 250);
		initializePrimaryStage(scene);
	}

	/**
	 * Sets up the {@link #menuBar_}
	 *
	 * @version 0.0.0.20170425
	 * @since 0.0
	 *
	 * @param player
	 *            the initial player
	 * @return
	 */
	private void setupMenuBar(final Node node)
	{
		menuBar_ = new MenuBar();

		Menu fileMenu = new Menu("File");

		final FileChooser addFileChooser = new FileChooser();
		MenuItem add = new MenuItem("Add track");

		add.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent t)
			{
				File file = addFileChooser.showOpenDialog(primaryStage_);
				if(file != null)
				{
					Media media = new Media(file.toURI().toString());
					try
					{
						systemPlayer_.newMedia(media);

						LogBuilder logBuilder = new LogBuilder();
						logBuilder.append("New media added to system player");
						logBuilder.append(systemPlayer_);

						systemLogger_.info(logBuilder.toString());
					}
					catch (OodioPlayerException e)
					{
						LogBuilder logBuilder = new LogBuilder();
						logBuilder.append("Could not setup menu bar");
						logBuilder.append(systemPlayer_);

						systemLogger_.fatal(logBuilder.toString(), e);
					}
				}
			}
		});

		fileMenu.getItems().addAll(add);

		Menu editMenu = new Menu("Edit");
		Menu helpMenu = new Menu("Help");

		menuBar_.getMenus().addAll(fileMenu, editMenu, helpMenu);

		// TODO remove bad cast
		((Pane) node).getChildren().addAll(menuBar_);
	}

	/**
	 * Sets up the first player available on application startup
	 *
	 * @version 0.0.0.20170425
	 * @since 0.0
	 *
	 * @return the player that was setup
	 */
	private void setupInitialPlayer(Node node) throws OodioPlayerException
	{
		File file = new File("C:/Users/schel/Music/Instrumentals/Jay IDK - Two Hoes.mp3");
		Media media = new Media(file.toURI().toString());

		systemPlayer_ = new OodioPlayer(media);

		LogBuilder logBuilder = new LogBuilder();
		logBuilder.append("Player appended to root pane");
		logBuilder.append(systemPlayer_);

		systemLogger_.info(logBuilder.toString());

		systemPlayer_.appendToNode(node);
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