package github.haqpah.oodio.application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.media.MediaPlayer;

/**
 * The controller for the system player
 *
 * @version 0.0.0.20170422
 * @since 0.0
 */
public final class SystemPlayerController extends AbstractController
{
	/**
	 * The FXML file name for this controller
	 */
	private static final String FXML_FILENAME_ = "SystemPlayer.fxml";

	/**
	 * The {@link MediaPlayer} that this controller can control
	 */
	private MediaPlayer systemPlayer_;

	/**
	 * The controller for the system player
	 *
	 * @version 0.0.0.20170425
	 * @since 0.0
	 */
	public SystemPlayerController()
	{
		super(FXML_FILENAME_);
	}

	/**
	 * Plays the loaded track
	 *
	 * @version 0.0.0.20170425
	 * @since 0.0
	 */
	@FXML
	protected void playTrack(ActionEvent event)
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
	protected void pauseTrack(ActionEvent event)
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
	protected void stopTrack(ActionEvent event)
	{
		systemPlayer_.stop();
	}

	/**
	 * @version 0.0.0.20170426
	 * @since 0.0
	 */
	@Override
	public String getFxmlFilename()
	{
		return FXML_FILENAME_;
	}
}
