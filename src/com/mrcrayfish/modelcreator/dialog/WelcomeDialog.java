package com.mrcrayfish.modelcreator.dialog;

import com.mrcrayfish.modelcreator.IconMap;

import java.awt.*;
import java.net.URL;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class WelcomeDialog
{
	static HashMap<String, Icon> iconsMap = IconMap.invoke();


	public static void show(JFrame parentFrame)
	{
		JPanel dialogContent = getDialogContent();
		JDialog welcomeDialog = getWelcomeDialog(parentFrame, dialogContent);
		showDialog(welcomeDialog);
	}

	private static JPanel getDialogContent()
	{
		JPanel container = new JPanel(new BorderLayout(20, 10));
		container.setBorder(new EmptyBorder(10, 10, 10, 10));

		applySticker(container);

		JPanel leftPanel = getLeftPanel();
		container.add(leftPanel, BorderLayout.CENTER);

		JPanel btnGrid = getButtonGrid();
		container.add(btnGrid, BorderLayout.SOUTH);
		return container;
	}

	private static void applySticker(JPanel container)
	{
		ImageIcon crayfishImage = (ImageIcon) iconsMap.get("sticker");
		container.add(new JLabel(crayfishImage), BorderLayout.EAST);
	}

	private static JPanel getLeftPanel()
	{
		JPanel leftPanel = new JPanel(new BorderLayout());

		JLabel title = new JLabel("<html><div style=\"font-size:16px;\"><b>Model Creator</b> by MrCrayfish<div></html>");
		leftPanel.add(title, BorderLayout.NORTH);

		JScrollPane message = getMessage();
		leftPanel.add(message, BorderLayout.CENTER);
		return leftPanel;
	}

	private static JScrollPane getMessage()
	{
		String message = "Thank you for downloading my program. I hope it encourages"
				+ " you to create awesome models. If you do create something awesome, I"
				+ " would love to see it. You can post your screenshots to me via Twitter"
				+ " or Facebook. If you are unsure how to use anything works, hover your "
				+ "mouse over the component and it will tell you what it does."
				+ "\n\n"
				+ "I've put a lot of work into this program, so if you are "
				+ "feeling generous, you can donate by clicking the button below. Thank you!"
				+ "";
		JTextArea textArea = new JTextArea(message);
		textArea.setEditable(false);
		textArea.setCursor(null);
		textArea.setFocusable(false);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setOpaque(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		return scrollPane;
	}

	private static JPanel getButtonGrid()
	{
		JPanel btnGrid = new JPanel(new GridLayout(1, 4, 5, 0));

		JButton btnDonate = new JButton("Donate");
		btnDonate.setIcon(iconsMap.get("coin"));
		btnDonate.addActionListener(a ->
				openUrl("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=HVXLDWFN4MNA2"));
		btnGrid.add(btnDonate);

		JButton btnTwitter = new JButton("Twitter");
		btnTwitter.setIcon(iconsMap.get("twitter"));
		btnTwitter.addActionListener(a -> openUrl("https://www.twitter.com/MrCraayfish"));
		btnGrid.add(btnTwitter);

		JButton btnFacebook = new JButton("Facebook");
		btnFacebook.setIcon(iconsMap.get("facebook"));
		btnFacebook.addActionListener(a ->
				openUrl("https://www.facebook.com/MrCrayfish"));
		btnGrid.add(btnFacebook);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(a ->
				SwingUtilities.getWindowAncestor(btnClose).dispose());
		btnGrid.add(btnClose);
		return btnGrid;
	}

	private static void openUrl(String url)
	{
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
		{
			try
			{
				desktop.browse(new URL(url).toURI());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private static JDialog getWelcomeDialog(JFrame parent, JPanel dialogContent)
	{
		JDialog dialog = new JDialog(parent, "Welcome", false);
		dialog.setName("Welcome");
		dialog.setResizable(false);
		dialog.setPreferredSize(new Dimension(500, 290));
		dialog.add(dialogContent);
		dialog.pack();
		return dialog;
	}

	private static void showDialog(JDialog dialog)
	{
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		dialog.requestFocusInWindow();
	}

}
