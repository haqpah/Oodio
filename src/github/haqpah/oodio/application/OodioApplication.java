package github.haqpah.oodio.application;

import org.apache.log4j.Logger;

import github.haqpah.oodio.application.controller.FxmlController;
import github.haqpah.oodio.application.controller.SystemMenuController;
import github.haqpah.oodio.application.controller.SystemPlayerController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * Application layer
 *
 * @version 0.0.0.20170426
 * @since 0.0
 */
public class OodioApplication extends Application
{
	/**
	 * Official name for the application
	 */
	private static final String APPLICATION_NAME_ = "Oodio";

	/**
	 * Responsible for logging system runtime information
	 */
	private static Logger systemLogger_;

	/**
	 * The primary {@link Stage} for Oodio.
	 */
	private static Stage primaryStage_;

	/**
	 * The {@link FxmlController} containing application actions for the user
	 */
	private static FxmlController systemMenuController_;

	/**
	 * The {@link FxmlController} for the system player, responsible for song playback
	 */
	private static FxmlController systemPlayerController_;

	public OodioApplication(Logger systemLogger)
	{
		systemLogger_ = systemLogger;
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

	@Override
	public void stop() throws Exception
	{
		// TODO
		systemLogger_.shutdown();

		super.stop();
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
}
