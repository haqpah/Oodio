package application.component;

import application.player.OodioPlayer;

/**
 * Types of actions that {@link Button}s can have in an {@link OodioPlayer}'s {@link ButtonSet}
 *
 * @version 0.0.0.20170423
 * @since 0.0
 */
public enum ButtonActionType
{
	/**
	 * Plays a song
	 */
	PLAY,

	/**
	 * Pauses a song
	 */
	PAUSE,

	/**
	 * Stops a song
	 */
	STOP,

	/**
	 * Rewinds a song
	 */
	REWIND,

	/**
	 * Fast forwards a song
	 */
	FAST_FORWARD,

	;
}
