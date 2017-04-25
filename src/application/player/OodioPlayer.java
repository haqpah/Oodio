package application.player;

import java.util.List;

import application.component.Button;
import application.component.ButtonActionType;
import application.component.ButtonList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
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
	 * Constructor. It is important to note that this constructor comes with baggage.
	 * <p>
	 * This constructor will not be tied to the system menu bar. In order to load new media
	 * into the player tied to the system menu bar, use {@link #newInstanceOfSystemPlayer(Media)
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @param media
	 *            the new media to load
	 * @throws OodioPlayerException
	 *             if there was a problem loading the new media
	 */
	public OodioPlayer(Media media) throws OodioPlayerException
	{
		newInstanceOfSystemPlayer(media);
	}

	/**
	 * Effectively loads new media to this player.
	 * <p>
	 * In order to do so, the {@link #player_} field must re-instantiated with the passed media, which
	 * involves some heavy lifting. See {@link #newInstanceOfSystemPlayer(Media)}
	 *
	 * @version 0.0.0.20170425
	 * @since 0.0
	 *
	 * @param media
	 *            the new media to load
	 * @throws OodioPlayerException
	 *             if there was a problem loading the new media
	 */
	public void newMedia(Media media) throws OodioPlayerException
	{
		newInstanceOfSystemPlayer(media);
	}

	/**
	 * Convenience method to house shared logic between {@link #newMedia(Media)} and the constructor
	 *
	 * @version 0.0.0.20170425
	 * @since 0.0
	 *
	 * @param media
	 *            the new media to load
	 * @throws OodioPlayerException
	 *             if there was a problem loading the new media
	 */
	private void newInstanceOfSystemPlayer(Media media) throws OodioPlayerException
	{
		// Stop any existing media that may have been playing
		if(player_ != null)
		{
			player_.stop();
		}

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
	 * @param node
	 *            the node to append the player too
	 */
	public void appendToNode(Node node)
	{
		for(Button button : buttonList_)
		{
			// TODO Remove bad cast
			((Pane) node).getChildren().add(button);
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
