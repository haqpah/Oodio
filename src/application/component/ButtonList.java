package application.component;

import java.util.ArrayList;

import javafx.scene.media.MediaPlayer;

/**
 * An {@link OodioComponent} containing Play, Pause, Stop, Rewind, and Fast Forward
 *
 * @version 0.0.0.20170423
 * @param <T>
 * @since 0.0
 */
public class ButtonList<T extends Button> extends ArrayList<T> implements OodioComponent
{
	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 8847614160454504561L;

	/**
	 * Constructor
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @param player
	 *            The {@link MediaPlayer} containing the media to be played
	 */
	@SuppressWarnings("unchecked")
	public ButtonList()
	{
		T playBtn = (T) new Button(">", ButtonActionType.PLAY);
		T pauseBtn = (T) new Button("||", ButtonActionType.PAUSE);
		T stopBtn = (T) new Button(new String("\u25A1"), ButtonActionType.STOP); // ASCII Char for no-fill square
		T rewindBtn = (T) new Button("<<", ButtonActionType.REWIND);
		T fastForwardBtn = (T) new Button(">>", ButtonActionType.FAST_FORWARD);

		this.add(playBtn);
		this.add(pauseBtn);
		this.add(stopBtn);
		this.add(rewindBtn);
		this.add(fastForwardBtn);
	}
}
