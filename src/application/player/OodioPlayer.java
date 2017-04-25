package application.player;

import java.util.List;

import application.component.Button;
import application.component.ButtonActionType;
import application.component.ButtonList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public final class OodioPlayer
{
	/**
	 * The underlying {@link MediaPlayer} object
	 */
	private MediaPlayer player_;

	/**
	 * The {@link ButtonSet} for this player
	 */
	private List<Button> buttonList_;

	/**
	 * Constructor
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @param media
	 * @throws OodioPlayerException
	 */
	public OodioPlayer(Media media) throws OodioPlayerException
	{
		player_ = new MediaPlayer(media);
		buttonList_ = new ButtonList<Button>();

		try
		{
			initializeButtonListEventHandlers();
		}
		catch (UnsupportedOperationException uoe)
		{
			throw new OodioPlayerException("Unable to initialize button event handler", uoe);
		}
	}

	/**
	 * Appends this player to the passed pane
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @param pane
	 *            the pane to append the player too
	 */
	public void appendToPane(Pane pane)
	{
		for(Button button : buttonList_)
		{
			pane.getChildren().add(button);
		}
	}

	/**
	 * Initializes the event handlers for each button in this player's {@link #buttonList_
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @throws UnsupportedOperationException
	 *             the button has an unsupported {@link ButtonActionType}
	 */
	private void initializeButtonListEventHandlers() throws UnsupportedOperationException
	{
		buttonList_.forEach(button -> {
			ButtonActionType actionType = button.getButtonActionType();
			switch (actionType)
			{
				case PLAY:
					initPlay(button);
					break;
				case PAUSE:
					initPause(button);
					break;
				case STOP:
					initStop(button);
					break;
				case REWIND:
					initRewind(button);
					break;
				case FAST_FORWARD:
					initFastForward(button);
					break;
				default:
					throw new UnsupportedOperationException("Unsupported action type: " + actionType.name());
			}
		});
	}

	/**
	 * Initializes the play button for this player.
	 * <p>
	 * Note: The button's {@link ButtonActionType} has already been verified at this point of execution!
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @param button
	 *            the play button
	 */
	private void initPlay(Button button)
	{
		button.setOnAction(new EventHandler<ActionEvent>()
		{
			/**
			 * @version 0.0.0.20170423
			 * @since 0.0
			 */
			@Override
			public void handle(ActionEvent event)
			{
				player_.play();
			}
		});
	}

	/**
	 * Initializes the pause button for this player.
	 * <p>
	 * Note: The button's {@link ButtonActionType} has already been verified at this point of execution!
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @param button
	 *            the pause button
	 */
	private void initPause(Button button)
	{
		button.setOnAction(new EventHandler<ActionEvent>()
		{
			/**
			 * @version 0.0.0.20170423
			 * @since 0.0
			 */
			@Override
			public void handle(ActionEvent event)
			{
				player_.pause();
			}
		});
	}

	/**
	 * Initializes the stop button for this player.
	 * <p>
	 * Note: The button's {@link ButtonActionType} has already been verified at this point of execution!
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @param button
	 *            the stop button
	 */
	private void initStop(Button button)
	{
		button.setOnAction(new EventHandler<ActionEvent>()
		{
			/**
			 * @version 0.0.0.20170423
			 * @since 0.0
			 */
			@Override
			public void handle(ActionEvent event)
			{
				player_.stop();
			}
		});
	}

	/**
	 * Initializes the rewind button for this player.
	 * <p>
	 * Note: The button's {@link ButtonActionType} has already been verified at this point of execution!
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @param button
	 *            the rewind button
	 */
	private void initRewind(Button button)
	{
		// TODO Implement
	}

	/**
	 * Initializes the fast forward button for this player.
	 * <p>
	 * Note: The button's {@link ButtonActionType} has already been verified at this point of execution!
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @param button
	 *            the fast forward button
	 */
	private void initFastForward(Button button)
	{
		// TODO Implement
	}

	/**
	 * Accessor for the {@link Media} held within this player
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @return the media
	 */
	public Media getMedia()
	{
		return player_.getMedia();
	}

	/**
	 * Accessor for the {@link ButtonList} that controls the media held within this player
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @return the button list
	 */
	public List<Button> getButtonList()
	{
		return buttonList_;
	}
}
