package github.haqpah.oodio.application.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import github.haqpah.oodio.services.SystemPathService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

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
	 * Responsible for logging runtime information for a controller
	 */
	private Logger logger_ = LogManager.getLogger(AbstractController.class);

	/**
	 * The root {@link Node} that this controller's UI elements are contained in, defined by the FXML
	 */
	private Pane rootPane_;

	/**
	 * Abstract implementation of a controller for FXML files
	 *
	 * @version 0.0.0.20170426
	 * @since 0.0
	 *
	 * @param fxmlFilename
	 *            The FXML file name for this controller
	 */
	public AbstractController(String fxmlFilename)
	{
		try
		{
			fxmlLoader_ = new FXMLLoader();
			fxmlLoader_.setController(this);

			Path fxmlPath = SystemPathService.getFxmlDirectory().resolve(fxmlFilename);
			FileInputStream stream = new FileInputStream(fxmlPath.toString());
			rootPane_ = getFxmlLoader().load(stream);
		}
		catch (IOException e)
		{
			logger_.error("Exception occurred while setting up controller\n  File: " + fxmlFilename, e);
		}
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
	public Pane getRootPane()
	{
		return rootPane_;
	}
}
