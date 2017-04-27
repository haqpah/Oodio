package github.haqpah.oodio.application.controller;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public final class SystemMenuController extends AbstractController
{
	/**
	 * The FXML file name for this controller
	 */
	private static final String FXML_FILENAME_ = "SystemMenu.fxml";

	/**
	 * Constructor
	 *
	 * @version 0.0.0.20170426
	 * @since 0.0
	 */
	public SystemMenuController()
	{
		super(FXML_FILENAME_);
	}

	@FXML
	public void newPlaylist(ActionEvent event)
	{

	}

	@FXML
	public void addTrack(ActionEvent event)
	{

	}

	@FXML
	public void exitApplication(ActionEvent event)
	{
		// TODO hook into application stop somehow
		System.exit(0);
	}

	@FXML
	public void about(ActionEvent event)
	{
		if(Desktop.isDesktopSupported())
		{
			try
			{
				Desktop.getDesktop().browse(new URI("https://github.com/haqpah/Oodio/blob/master/README.md"));
			}
			catch (IOException | URISyntaxException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void wiki(ActionEvent event)
	{
		if(Desktop.isDesktopSupported())
		{
			try
			{
				Desktop.getDesktop().browse(new URI("http:/www.github.com/haqpah/Oodio/wiki"));
			}
			catch (IOException | URISyntaxException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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
