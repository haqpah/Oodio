package github.haqpah.oodio.application.controller;

import java.io.File;

import org.apache.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

/**
 * Controller for the system player UI elements defined in the SystemPlayer.fxml
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
	 * Controller for the system player UI elements defined in the SystemPlayer.fxml
	 *
	 * @version 0.0.0.20170425
	 * @since 0.0
	 *
	 * @param primaryStage
	 *            the stage that this controller's UI elements will be attached to
	 * @param systemLogger
	 *            for logging information from this player to the system log
	 */
	public SystemPlayerController(final Stage primaryStage, Logger systemLogger)
	{
		super(primaryStage, systemLogger, FXML_FILENAME_);

		// TODO loading default song
		File file = new File("C:/Users/schel/Music/Instrumentals/Jay IDK - Two Hoes.mp3");
		Media media = new Media(file.toURI().toString());

		systemPlayer_ = new MediaPlayer(media);
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
