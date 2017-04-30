package github.haqpah.oodio.application.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.log4j.Logger;

import github.haqpah.oodio.services.SystemPathService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * Abstract implementation of a controller for FXML files
 *
 * @version 0.0.0.20170426
 * @since 0.0
 */
abstract class AbstractController implements FxmlController
{
	/**
	 * The loader has access to the underlying FXML document the defines
	 * the {@link javafx.scene.Node}s being controlled by this controller
	 */
	private FXMLLoader fxmlLoader_;

	/**
	 * Responsbile for logging runtime information for a controller
	 */
	private Logger systemLogger_;

	/**
	 * The stage this controllers parent is attached to
	 */
	// TODO do better
	private Stage primaryStage_;
	/**
	 * The root {@link Node} that this controller's UI elements are contained in, defined by the FXML
	 */
	private Node rootNode_;

	/**
	 * Abstract implementation of a controller for FXML files
	 *
	 * @version 0.0.0.20170426
	 * @since 0.0
	 */
	public AbstractController(final Stage primaryStage, final Logger systemLogger, String fxmlFilename)
	{
		primaryStage_ = primaryStage;
		systemLogger_ = systemLogger;

		try
		{
			fxmlLoader_ = new FXMLLoader();
			fxmlLoader_.setController(this);

			Path fxmlPath = SystemPathService.getFxmlDirectory().resolve(fxmlFilename);
			FileInputStream stream = new FileInputStream(fxmlPath.toString());
			rootNode_ = getFxmlLoader().load(stream);

			// ((Region) rootNode_).prefWidthProperty().bind(primaryStage.widthProperty());
		}
		catch (IOException e)
		{
			addLog("Exception occurred while setting up controller\n  File: " + fxmlFilename, e);
		}
	}

	/**
	 * Logs a message to the system log file
	 *
	 * @version 0.0.0.20170427
	 * @since 0.0
	 *
	 * @param logMessage
	 *            the message to log
	 */
	protected void addLog(String logMessage)
	{
		systemLogger_.info(logMessage);
	}

	/**
	 * Logs a message with an exception to the system log file
	 *
	 * @version 0.0.0.20170427
	 * @since 0.0
	 *
	 * @param logMessage
	 *            the message to log
	 * @param throwable
	 *            the exception that occurred
	 */
	protected void addLog(String logMessage, Throwable throwable)
	{
		systemLogger_.error(logMessage, throwable);
	}

	/**
	 * Gets the system logger passed into this controller
	 *
	 * @version 0.0.0.20170427
	 * @since 0.0
	 */
	protected Logger getSystemLogger()
	{
		return systemLogger_;
	}

	/**
	 * @version 0.0.0.20170425
	 * @since 0.0
	 */
	@Override
	public FXMLLoader getFxmlLoader()
	{
		return fxmlLoader_;
	}

	/**
	 * @version 0.0.0.20170426
	 * @since 0.0
	 */
	@Override
	public Stage getPrimaryStage()
	{
		return primaryStage_;
	}

	/**
	 * @version 0.0.0.20170426
	 * @since 0.0
	 */
	@Override
	public Node getRootNode()
	{
		return rootNode_;
	}
}
