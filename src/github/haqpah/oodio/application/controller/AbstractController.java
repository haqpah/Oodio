package github.haqpah.oodio.application.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

import github.haqpah.oodio.services.SystemPathService;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

abstract class AbstractController implements FxmlController
{
	/**
	 * The loader has access to the underlying FXML document the defines
	 * the {@link javafx.scene.Node}s being controlled by this controller
	 */
	private FXMLLoader fxmlLoader_;

	/**
	 * The stage this controllers parent is attached to
	 */
	// TODO do better
	private static Stage primaryStage_;

	/**
	 * The root {@link Pane} that this controller's UI elements are contained in, defined by the FXML
	 */
	private HBox rootPane_;

	/**
	 *
	 *
	 * @version 0.0.0.20170426
	 * @since 0.0
	 */
	public AbstractController(Stage primaryStage, String fxmlFilename)
	{
		primaryStage_ = primaryStage;

		try
		{
			fxmlLoader_ = new FXMLLoader();
			fxmlLoader_.setController(this);

			Path fxmlPath = SystemPathService.getFxmlDirectory().resolve(fxmlFilename);
			FileInputStream stream = new FileInputStream(fxmlPath.toString());
			rootPane_ = (HBox) getFxmlLoader().load(stream);
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public Stage getPrimaryStage()
	{
		return primaryStage_;
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
