package github.haqpah.oodio.application.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

import github.haqpah.oodio.application.fxml.FxmlController;
import github.haqpah.oodio.services.SystemPathService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;

/**
 * TODO
 *
 * @version 0.0.0.20170422
 * @since 0.0
 */
public class SystemPlayerController extends HBox implements FxmlController
{
	/**
	 * The FXML file name for this controller
	 */
	private static final String FXML_FILENAME_ = "SystemPlayer.fxml";

	/**
	 * The loader has access to the underlying FXML document the defines
	 * the {@link javafx.scene.Node}s being controlled by this controller
	 */
	private FXMLLoader fxmlLoader_;

	/**
	 * The root {@link Pane} that this controller's UI elements are contained in, defined by the FXML
	 */
	private HBox rootPane_;

	/**
	 * The {@link MediaPlayer} that this controller can control
	 */
	private MediaPlayer systemPlayer_;

	/**
	 * Constructor
	 *
	 * @version 0.0.0.20170425
	 * @since 0.0
	 *
	 * @throws IOException
	 */
	public SystemPlayerController()
	{
		try
		{
			fxmlLoader_ = new FXMLLoader();
			fxmlLoader_.setController(this);

			Path fxmlPath = SystemPathService.getFxmlDirectory().resolve(FXML_FILENAME_);
			FileInputStream stream = new FileInputStream(fxmlPath.toString());
			rootPane_ = (HBox) fxmlLoader_.load(stream);
		}
		catch (IOException ioe)
		{

		}
	}

	/**
	 * Plays the loaded track
	 *
	 * @version 0.0.0.20170425
	 * @since 0.0
	 */
	@FXML
	protected void playTrack()
	{
		systemPlayer_.play();
	}

	/**
	 * Pauses the loaded track
	 *
	 * @version 0.0.0.20170425
	 * @since 0.0
	 */
	@FXML
	protected void pauseTrack()
	{
		systemPlayer_.pause();
	}

	/**
	 * Stops the loaded track
	 *
	 * @version 0.0.0.20170425
	 * @since 0.0
	 */
	@FXML
	protected void stopTrack()
	{
		systemPlayer_.stop();
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
