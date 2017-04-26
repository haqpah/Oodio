package application.fxml;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

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
	 * Gets the root {@link Node} that this controller's UI elements are contained in
	 *
	 * @version 0.0.0.20170426
	 * @since 0.0
	 *
	 * @return the root node
	 */
	public Pane getRootPane();
}
