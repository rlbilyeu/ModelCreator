package com.mrcrayfish.modelcreator;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mrcrayfish.modelcreator.element.ElementManager;
import com.mrcrayfish.modelcreator.screenshot.PendingScreenshot;
import com.mrcrayfish.modelcreator.screenshot.Screenshot;
import com.mrcrayfish.modelcreator.screenshot.ScreenshotCallback;
import com.mrcrayfish.modelcreator.screenshot.Uploader;
import com.mrcrayfish.modelcreator.sidebar.Sidebar;
import com.mrcrayfish.modelcreator.util.Util;
import com.sun.tools.internal.ws.processor.model.Model;

public class Menu extends JMenuBar
{
	private static final long serialVersionUID = 1L;

	private JFrame creator;

	/* File */
	private JMenu menuFile;
	private JMenuItem itemNew;
	private JMenuItem itemLoad;
	private JMenuItem itemSave;
	private JMenuItem itemImport;
	private JMenuItem itemExport;
	private JMenuItem itemTexturePath;
	private JMenuItem itemExit;

	/* Options */
	private JMenu menuOptions;
	private JMenuItem itemTransparency;

	/* Share */
	private JMenu menuScreenshot;
	private JMenuItem itemSaveToDisk;
	private JMenuItem itemShareFacebook;
	private JMenuItem itemShareTwitter;
	private JMenuItem itemShareReddit;
	private JMenuItem itemImgurLink;

	/* Extras */
	private JMenu menuHelp;
	private JMenu menuExamples;
	private JMenuItem itemModelCauldron;
	private JMenuItem itemModelChair;
	private JMenuItem itemDonate;
	private JMenuItem itemPM;
	private JMenuItem itemMF;
	private JMenuItem itemGitHub;

	public Menu(JFrame creator, ElementManager manager)
	{
		this.creator = creator;
		initMenu(manager);
	}

	private void initMenu(ElementManager manager)
	{
		menuFile = new JMenu("File");
		{
			itemNew = createItem("New", "New Model", KeyEvent.VK_N, new ImageIcon(getClass().getClassLoader().getResource("icons/new.png")));
			itemLoad = createItem("Load Project...", "Load Project from File", KeyEvent.VK_S, Icons.load);
			itemSave = createItem("Save Project...", "Save Project to File", KeyEvent.VK_S, Icons.disk);
			itemImport = createItem("Import JSON...", "Import Model from JSON", KeyEvent.VK_I, new ImageIcon(getClass().getClassLoader().getResource("icons/import.png")));
			itemExport = createItem("Export JSON...", "Export Model to JSON", KeyEvent.VK_E, new ImageIcon(getClass().getClassLoader().getResource("icons/export.png")));
			itemTexturePath = createItem("Set Texture Path...", "Set the base path to look for textures", KeyEvent.VK_S, new ImageIcon(getClass().getClassLoader().getResource("icons/texture.png")));
			itemExit = createItem("Exit", "Exit Application", KeyEvent.VK_E, new ImageIcon(getClass().getClassLoader().getResource("icons/exit.png")));
		}

		menuOptions = new JMenu("Options");
		{
			itemTransparency = createItem("Toggle Transparency", "Enables transparent rendering in program", KeyEvent.VK_E, Icons.transparent);
		}

		menuScreenshot = new JMenu("Screenshot");
		{
			itemSaveToDisk = createItem("Save to Disk...", "Save screenshot to disk.", KeyEvent.VK_S, Icons.disk);
			itemShareFacebook = createItem("Share to Facebook", "Share a screenshot of your model Facebook.", KeyEvent.VK_S, Icons.facebook);
			itemShareTwitter = createItem("Share to Twitter", "Share a screenshot of your model to Twitter.", KeyEvent.VK_S, Icons.twitter);
			itemShareReddit = createItem("Share to Minecraft Subreddit", "Share a screenshot of your model to Minecraft Reddit.", KeyEvent.VK_S, Icons.reddit);
			itemImgurLink = createItem("Get Imgur Link", "Get an Imgur link of your screenshot to share.", KeyEvent.VK_G, Icons.imgur);
		}

		menuHelp = new JMenu("More");
		{
			menuExamples = new JMenu("Examples");
			menuExamples.setIcon(Icons.new_);
			{
				itemModelCauldron = createItem("Cauldron", "<html>Model by MrCrayfish<br><b>Private use only</b></html>", KeyEvent.VK_C, Icons.model_cauldron);
				itemModelChair = createItem("Chair", "<html>Model by MrCrayfish<br><b>Private use only</b></html>", KeyEvent.VK_C, Icons.model_chair);
			}
			itemDonate = createItem("Donate (PayPal)", "Donate to MrCrayfish", KeyEvent.VK_D, Icons.coin);
			itemPM = createItem("Planet Minecraft", "Open PMC Post", KeyEvent.VK_P, Icons.planet_minecraft);
			itemMF = createItem("Minecraft Forum", "Open MF Post", KeyEvent.VK_M, Icons.minecraft_forum);
			itemGitHub = createItem("Github", "View Source Code", KeyEvent.VK_G, Icons.github);
		}

		initActions(manager);

		menuExamples.add(itemModelCauldron);
		menuExamples.add(itemModelChair);

		menuHelp.add(menuExamples);
		menuHelp.addSeparator();
		menuHelp.add(itemPM);
		menuHelp.add(itemMF);
		menuHelp.add(itemGitHub);
		menuHelp.addSeparator();
		menuHelp.add(itemDonate);

		menuOptions.add(itemTransparency);

		menuScreenshot.add(itemSaveToDisk);
		menuScreenshot.add(itemShareFacebook);
		menuScreenshot.add(itemShareTwitter);
		menuScreenshot.add(itemShareReddit);
		menuScreenshot.add(itemImgurLink);

		menuFile.add(itemNew);
		menuFile.addSeparator();
		menuFile.add(itemLoad);
		menuFile.add(itemSave);
		menuFile.addSeparator();
		menuFile.add(itemImport);
		menuFile.add(itemExport);
		menuFile.addSeparator();
		menuFile.add(itemTexturePath);
		menuFile.addSeparator();
		menuFile.add(itemExit);

		add(menuFile);
		add(menuOptions);
		add(menuScreenshot);
		add(menuHelp);
	}

	private void initActions(ElementManager manager)
	{
		itemNew.addActionListener(a ->
		{
			int returnVal = JOptionPane.showConfirmDialog(creator, "You current work will be cleared, are you sure?", "Note", JOptionPane.YES_NO_OPTION);
			if (returnVal == JOptionPane.YES_OPTION)
			{
				manager.reset();
				manager.updateValues();
			}
		});

		itemLoad.addActionListener(a ->
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Output Directory");
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setApproveButtonText("Load");

			FileNameExtensionFilter filter = new FileNameExtensionFilter("Model (.model)", "model");
			chooser.setFileFilter(filter);

			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				if (manager.getElementCount() > 0)
				{
					returnVal = JOptionPane.showConfirmDialog(null, "Your current project will be cleared, are you sure you want to continue?", "Warning", JOptionPane.YES_NO_OPTION);
				}
				if (returnVal != JOptionPane.NO_OPTION && returnVal != JOptionPane.CLOSED_OPTION)
				{
					ProjectManager.loadProject(manager, chooser.getSelectedFile().getAbsolutePath());
				}
			}
		});

		itemSave.addActionListener(a ->
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Output Directory");
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setApproveButtonText("Save");

			FileNameExtensionFilter filter = new FileNameExtensionFilter("Model (.model)", "model");
			chooser.setFileFilter(filter);

			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				if (chooser.getSelectedFile().exists())
				{
					returnVal = JOptionPane.showConfirmDialog(null, "A file already exists with that name, are you sure you want to override?", "Warning", JOptionPane.YES_NO_OPTION);
				}
				if (returnVal != JOptionPane.NO_OPTION && returnVal != JOptionPane.CLOSED_OPTION)
				{
					String filePath = chooser.getSelectedFile().getAbsolutePath();
					if (!filePath.endsWith(".model"))
						filePath += ".model";
					ProjectManager.saveProject(manager, filePath);
				}
			}
		});

		itemImport.addActionListener(e ->
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Input File");
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setApproveButtonText("Import");

			FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON (.json)", "json");
			chooser.setFileFilter(filter);

			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				if (manager.getElementCount() > 0)
				{
					returnVal = JOptionPane.showConfirmDialog(null, "Your current project will be cleared, are you sure you want to continue?", "Warning", JOptionPane.YES_NO_OPTION);
				}
				if (returnVal != JOptionPane.NO_OPTION && returnVal != JOptionPane.CLOSED_OPTION)
				{
					Importer importer = new Importer(manager, chooser.getSelectedFile().getAbsolutePath());
					importer.importFromJSON();
				}
				manager.updateValues();
			}
		});

		itemExport.addActionListener(e ->
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Output Directory");
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setApproveButtonText("Export");

			FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON (.json)", "json");
			chooser.setFileFilter(filter);

			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				if (chooser.getSelectedFile().exists())
				{
					returnVal = JOptionPane.showConfirmDialog(null, "A file already exists with that name, are you sure you want to override?", "Warning", JOptionPane.YES_NO_OPTION);
				}
				if (returnVal != JOptionPane.NO_OPTION && returnVal != JOptionPane.CLOSED_OPTION)
				{
					String filePath = chooser.getSelectedFile().getAbsolutePath();
					if (!filePath.endsWith(".json"))
						chooser.setSelectedFile(new File(filePath + ".json"));
					Exporter exporter = new Exporter(manager);
					exporter.export(chooser.getSelectedFile());
				}
			}
		});

		itemTexturePath.addActionListener(e ->
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Texture Path");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				ModelCreator.texturePath = chooser.getSelectedFile().getAbsolutePath();
			}
		});

		itemExit.addActionListener(e ->
		{
			creator.dispose();
//			creator.close();
		});

		itemTransparency.addActionListener(a ->
		{
			ModelCreator.transparent ^= true;
			if (ModelCreator.transparent)
				JOptionPane.showMessageDialog(null, "<html>Transparent textures do not represent the same as in Minecraft.<br> " + "It depends if the model you are overwriting, allows transparent<br>" + "textures in the code. Blocks like Grass and Stone don't allow<br>" + "transparency, where as Glass and Cauldron do. Please take this into<br>" + "consideration when designing. Transparency is now turned on.<html>", "Rendering Warning", JOptionPane.INFORMATION_MESSAGE);
		});

		itemSaveToDisk.addActionListener(a ->
		{
			JFileChooser chooser = new JFileChooser();
			chooser.setDialogTitle("Output Directory");
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setApproveButtonText("Save");

			FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG (.png)", "png");
			chooser.setFileFilter(filter);

			int returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				if (chooser.getSelectedFile().exists())
				{
					returnVal = JOptionPane.showConfirmDialog(null, "A file already exists with that name, are you sure you want to override?", "Warning", JOptionPane.YES_NO_OPTION);
				}
				if (returnVal != JOptionPane.NO_OPTION && returnVal != JOptionPane.CLOSED_OPTION)
				{
					String filePath = chooser.getSelectedFile().getAbsolutePath();
					if (!filePath.endsWith(".png"))
						chooser.setSelectedFile(new File(filePath + ".png"));
					ModelCreator.setSidebar(null);
					ModelCreator.startScreenshot(new PendingScreenshot(chooser.getSelectedFile(), null));
				}
			}
		});

		itemShareFacebook.addActionListener(a ->
		{
			ModelCreator.setSidebar(null);
			ModelCreator.startScreenshot(new PendingScreenshot(null, new ScreenshotCallback()
			{
				@Override
				public void callback(File file)
				{
					try
					{
						String url = Uploader.upload(file);
						Screenshot.shareToFacebook(url);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}));
		});

		itemShareTwitter.addActionListener(a ->
		{
			ModelCreator.setSidebar(null);
			ModelCreator.startScreenshot(new PendingScreenshot(null, new ScreenshotCallback()
			{
				@Override
				public void callback(File file)
				{
					try
					{
						String url = Uploader.upload(file);
						Screenshot.shareToTwitter(url);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}));
		});

		itemShareReddit.addActionListener(a ->
		{
			ModelCreator.setSidebar(null);
			ModelCreator.startScreenshot(new PendingScreenshot(null, new ScreenshotCallback()
			{
				@Override
				public void callback(File file)
				{
					try
					{
						String url = Uploader.upload(file);
						Screenshot.shareToReddit(url);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}));
		});

		itemImgurLink.addActionListener(a ->
		{
			ModelCreator.setSidebar(null);
			ModelCreator.startScreenshot(new PendingScreenshot(null, new ScreenshotCallback()
			{
				@Override
				public void callback(File file)
				{
					SwingUtilities.invokeLater(new Runnable()
					{
						@Override
						public void run()
						{
							try
							{
								String url = Uploader.upload(file);

								JOptionPane message = new JOptionPane();
								String title;

								if (url != null && !url.equals("null"))
								{
									StringSelection text = new StringSelection(url);
									Toolkit.getDefaultToolkit().getSystemClipboard().setContents(text, null);
									title = "Success";
									message.setMessage("<html><b>" + url + "</b> has been copied to your clipboard.</html>");
								}
								else
								{
									title = "Error";
									message.setMessage("Failed to upload screenshot. Check your internet connection then try again.");
								}

								JDialog dialog = message.createDialog(Menu.this, title);
								dialog.setLocationRelativeTo(null);
								dialog.setModal(false);
								dialog.setVisible(true);
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}
					});
				}
			}));
		});

		itemMF.addActionListener(a ->
		{
			JOptionPane.showMessageDialog(null, "This option has not been added yet. Please wait until the next preview.");
		});

		itemPM.addActionListener(a ->
		{
			JOptionPane.showMessageDialog(null, "This option has not been added yet. Please wait until the next preview.");
		});

		itemGitHub.addActionListener(a ->
		{
			Util.openUrl("https://github.com/MrCrayfish/ModelCreator");
		});

		itemDonate.addActionListener(a ->
		{
			Util.openUrl("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=HVXLDWFN4MNA2");
		});

		itemModelCauldron.addActionListener(a ->
		{
			Util.loadModelFromJar(manager, getClass(), "models/cauldron");
		});

		itemModelChair.addActionListener(a ->
		{
			Util.loadModelFromJar(manager, getClass(), "models/modern_chair");
		});
	}

	private JMenuItem createItem(String name, String tooltip, int mnemonic, Icon icon)
	{
		JMenuItem item = new JMenuItem(name);
		item.setToolTipText(tooltip);
		item.setMnemonic(mnemonic);
		item.setIcon(icon);
		return item;
	}
}
