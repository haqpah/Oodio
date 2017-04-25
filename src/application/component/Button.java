package application.component;

/**
 * A simple button control. The button control can contain text and/or a graphic. A button control has three different modes
 * <ul>
 * <li>Normal: A normal push button.
 * <li>Default: A default Button is the button that receives a keyboard VK_ENTER press, if no other node in the scene consumes it.
 * <li>Cancel: A Cancel Button is the button that receives a keyboard VK_ESC press, if no other node in the scene consumes it.
 * </ul>
 * When a button is pressed and released a ActionEvent is sent. Your application can perform some action based on this event by implementing an
 * EventHandler to process the ActionEvent. Buttons can also respond to mouse events by implementing an EventHandler to process the MouseEvent
 * <p>
 * MnemonicParsing is enabled by default for Button.
 * <p>
 * Example: Button button = new Button("Play");
 *
 * @version 0.0.0.20170423
 * @since 0.0
 */
public class Button extends javafx.scene.control.Button implements OodioComponent
{
	/**
	 * The type of action this button will perform
	 */
	private ButtonActionType actionType_;

	/**
	 * A simple button control. The button control can contain text and/or a graphic. A button control has three different modes
	 * <ul>
	 * <li>Normal: A normal push button.
	 * <li>Default: A default Button is the button that receives a keyboard VK_ENTER press, if no other node in the scene consumes it.
	 * <li>Cancel: A Cancel Button is the button that receives a keyboard VK_ESC press, if no other node in the scene consumes it.
	 * </ul>
	 * When a button is pressed and released a ActionEvent is sent. Your application can perform some action based on this event by implementing an
	 * EventHandler to process the ActionEvent. Buttons can also respond to mouse events by implementing an EventHandler to process the MouseEvent
	 * <p>
	 * MnemonicParsing is enabled by default for Button.
	 * <p>
	 * Example: Button button = new Button("Play");
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @param buttonText
	 *            the text to display on the button
	 */
	public Button(String buttonText, ButtonActionType buttonActionType)
	{
		super(buttonText);

		actionType_ = buttonActionType;
	}

	/**
	 * The {@link ButtonActionType} that this button executes on click
	 *
	 * @version 0.0.0.20170423
	 * @since 0.0
	 *
	 * @return the action type
	 */
	public ButtonActionType getButtonActionType()
	{
		return actionType_;
	}
}
