import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import application.controller.SystemPlayerController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.stage.FileChooser;
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
	private static final String LOG4J_PROPERTIES_FILEPATH_ = "src/log4j/log4j.properties";

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
	private static MenuBar menuBar_;

	/**
	 * The controller for the player tied to the {@link #menuBar_}
	 */
	private static SystemPlayerController systemPlayerController_;

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

		HBox systemMenuRoot = new HBox();
		setupMenuBar(systemMenuRoot);

		systemLogger_.info("Setting up new controller");

		systemPlayerController_ = new SystemPlayerController();
		HBox systemPlayerRoot = (HBox) systemPlayerController_.getRootPane();

		root.setTop(systemMenuRoot);
		root.setCenter(systemPlayerRoot);

		systemLogger_.info("Controller setup complete");

		primaryStage_.setScene(new Scene(root, 300, 250));
		primaryStage_.show();
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
	private static void setupMenuBar(final Node node)
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
				// TODO get controller
				File file = addFileChooser.showOpenDialog(primaryStage_);
				if(file != null)
				{
					Media media = new Media(file.toURI().toString());
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