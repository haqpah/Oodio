package github.haqpah.oodio.application.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Interface for controllers
 *
 * @version 0.0.0.20170425
 * @since 0.0
 */
public interface FxmlController
{
	/**
	 * Gets the {@link FXMLLoader} within this controller. The loader has access to the underlying
	 * FXML document that defines the UI elements being controlled by this controller
	 *
	 * @version 0.0.0.20170425
	 * @since 0.0
	 *
	 * @return the FXMLLoader
	 */
	public FXMLLoader getFxmlLoader();

	/**
	 * Gets the filename of the FXML that is loaded into the controller
	 *
	 * @version 0.0.0.20170426
	 * @since 0.0
	 *
	 * @return
	 */
	public String getFxmlFilename();

	/**
	 * Gets the primary {@link Stage}. This is? a direct access to the {@link #getRootPane()} parent
	 *
	 * @version 0.0.0.20170426
	 * @since 0.0
	 *
	 * @return the primary stage
	 */
	public Stage getPrimaryStage();

	/**
	 * Gets the root {@link Pane} that this controller's UI elements are contained in
	 *
	 * @version 0.0.0.20170426
	 * @since 0.0
	 *
	 * @return the root pane
	 */
	public Pane getRootPane();
}
