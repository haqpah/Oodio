package github.haqpah.oodio.application;

import java.io.File;

import github.haqpah.oodio.application.controller.SystemPlayerController;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * TODO Javadoc all this
 *
 * @version 0.0.0.20170425
 * @since 0.0
 *
 */
public class SystemPlayer
{
	private SystemPlayerController systemPlayerController_;

	private MediaPlayer mediaPlayer_;

	public SystemPlayer()
	{
		systemPlayerController_ = new SystemPlayerController();
		setupPlayer();
	}

	/**
	 * Setup the {@link OodioPlayer} for this controller
	 *
	 * @version 0.0.0.20170425
	 * @since 0.0
	 */
	private void setupPlayer()
	{
		File file = new File("C:/Users/schel/Music/Instrumentals/Jay IDK - Two Hoes.mp3");
		Media media = new Media(file.toURI().toString());

		mediaPlayer_ = new MediaPlayer(media);
	}

	public SystemPlayerController getController()
	{
		return systemPlayerController_;
	}

	public MediaPlayer getPlayer()
	{
		return mediaPlayer_;
	}

}
