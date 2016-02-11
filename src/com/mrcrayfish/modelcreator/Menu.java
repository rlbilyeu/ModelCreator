package com.mrcrayfish.modelcreator;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.mrcrayfish.modelcreator.element.ElementManager;
import com.mrcrayfish.modelcreator.screenshot.PendingScreenshot;
import com.mrcrayfish.modelcreator.screenshot.Screenshot;
import com.mrcrayfish.modelcreator.screenshot.ScreenshotCallback;
import com.mrcrayfish.modelcreator.screenshot.Uploader;
import com.mrcrayfish.modelcreator.util.Util;

public class Menu extends JMenuBar
{
	private static final long serialVersionUID = 1L;
	static HashMap<String, Icon> iconsMap = IconMap.invoke();


	private JFrame creator;
	private ElementManager manager;


	public Menu(JFrame creator, ElementManager manager)
	{
		this.creator = creator;
		this.manager = manager;

		JMenu menuFile = initFileMenu();
		add(menuFile);

		JMenu menuOptions = initOptionsMenu();
		add(menuOptions);

		JMenu menuScreenshot = initScreenshotMenu();
		add(menuScreenshot);

		JMenu menuHelp = initHelpMenu();
		add(menuHelp);
	}

	private JMenu initFileMenu() {
		JMenu menu = new JMenu("File");
		{
			menu.add( menuItemNew() );
			menu.addSeparator();

			menu.add( menuItemLoad() );
			menu.add( menuItemSave() );
			menu.addSeparator();

			menu.add( menuItemImport());
			menu.add( menuItemExport());
			menu.addSeparator();

			menu.add( menuItemTexturePath() );
			menu.addSeparator();

			menu.add( menuItemExit() );
		}
		return menu;
	}

	private JMenuItem menuItemNew() {
		JMenuItem item = createItem("New", "New Model", KeyEvent.VK_N, iconsMap.get("new"));
		item.addActionListener((ActionEvent newModelCreation) ->
		{
			int returnVal = JOptionPane.showConfirmDialog(creator, "You current work will be cleared, are you sure?", "Note", JOptionPane.YES_NO_OPTION);
			if (returnVal == JOptionPane.YES_OPTION)
			{
				manager.reset();
				manager.updateValues();
			}
		});
		return item;
	}

	private JMenuItem menuItemLoad() {
		JMenuItem item = createItem("Load Project...", "Load Project from File", KeyEvent.VK_S, iconsMap.get("load"));
		item.addActionListener(a ->
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
		return item;
	}

	private JMenuItem menuItemSave() {
		JMenuItem item = createItem("Save Project...", "Save Project to File", KeyEvent.VK_S, iconsMap.get("disk"));
		item.addActionListener(a ->
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
		return item;
	}

	private JMenuItem menuItemImport() {
		JMenuItem item = createItem("Import JSON...", "Import Model from JSON", KeyEvent.VK_I, iconsMap.get("import"));
		item.addActionListener(e ->
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
		return item;
	}

	private JMenuItem menuItemExport() {
		JMenuItem item = createItem("Export JSON...", "Export Model to JSON", KeyEvent.VK_E, iconsMap.get("export"));
		item.addActionListener(e ->
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
		return item;
	}

	private JMenuItem menuItemTexturePath() {
		JMenuItem item = createItem("Set Texture Path...", "Set the base path to look for textures", KeyEvent.VK_S, iconsMap.get("texture"));
		item.addActionListener(e ->
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
		return item;
	}

	private JMenuItem menuItemExit() {
		JMenuItem item = createItem("Exit", "Exit Application", KeyEvent.VK_E, iconsMap.get("exit"));
		item.addActionListener(e ->
		{
			creator.dispose();
//			creator.close();
		});
		return item;
	}



	private JMenu initOptionsMenu() {
		JMenu menu = new JMenu("Options");
		{
			menu.add( menuItemTransparency() );
		}
		return menu;
	}

	private JMenuItem menuItemTransparency() {
		JMenuItem item = createItem("Toggle Transparency", "Enables transparent rendering in program", KeyEvent.VK_E, iconsMap.get("transparent"));
		item.addActionListener(a ->
		{
			ModelCreator.transparent ^= true;
			if (ModelCreator.transparent)
				JOptionPane.showMessageDialog(null, "<html>Transparent textures do not represent the same as in Minecraft.<br> " + "It depends if the model you are overwriting, allows transparent<br>" + "textures in the code. Blocks like Grass and Stone don't allow<br>" + "transparency, where as Glass and Cauldron do. Please take this into<br>" + "consideration when designing. Transparency is now turned on.<html>", "Rendering Warning", JOptionPane.INFORMATION_MESSAGE);
		});
		return item;
	}



	private JMenu initScreenshotMenu() {
		JMenu menu;
		menu = new JMenu("Screenshot");
		{
			menu.add(menuItemSaveToDisk());
			menu.add(menuItemFacebook() );
			menu.add(menuItemSaveToTwitter());
			menu.add(menuItemSaveToReddit());
			menu.add(menuItemSaveToImgur() );
		}
		return menu;
	}

	private JMenuItem menuItemSaveToDisk() {
		JMenuItem item = createItem("Save to Disk...", "Save screenshot to disk.", KeyEvent.VK_S, iconsMap.get("disk"));
		item.addActionListener(a ->
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
		return item;
	}

	private JMenuItem menuItemFacebook() {
		JMenuItem item = createItem("Share to Facebook", "Share a screenshot of your model Facebook.", KeyEvent.VK_S, iconsMap.get("facebook"));
		item.addActionListener(a ->
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
		return item;
	}

	private JMenuItem menuItemSaveToTwitter() {
		JMenuItem item = createItem("Share to Twitter", "Share a screenshot of your model to Twitter.", KeyEvent.VK_S, iconsMap.get("twitter"));
		item.addActionListener(a ->
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
		return item;
	}

	private JMenuItem menuItemSaveToReddit() {
		JMenuItem item = createItem("Share to Minecraft Subreddit", "Share a screenshot of your model to Minecraft Reddit.", KeyEvent.VK_S, iconsMap.get("reddit"));
		item.addActionListener(a ->
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
		return item;
	}

	private JMenuItem menuItemSaveToImgur() {
		JMenuItem item = createItem("Get Imgur Link", "Get an Imgur link of your screenshot to share.", KeyEvent.VK_G, iconsMap.get("imgur"));
		item.addActionListener(a ->
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
		return item;
	}



	private JMenu initHelpMenu() {
		JMenu menuHelp;
		menuHelp = new JMenu("More");
		{
			menuHelp.add(menuExamples());
			menuHelp.addSeparator();

			menuHelp.add(menuItemPM());
			menuHelp.add(menuItemMF());
			menuHelp.add(menuItemGitHub());
			menuHelp.addSeparator();
			menuHelp.add(menuItemDonate());
		}
		return menuHelp;
	}

	private JMenu menuExamples() {
		JMenu menu = new JMenu("Examples");
		menu.setIcon(iconsMap.get("new_"));
		{
			menu.add(modelItemCauldron());
			menu.add(modelItemChair());
		}
		return menu;
	}

	private JMenuItem modelItemCauldron() {
		JMenuItem item = createItem("Cauldron", "<html>Model by MrCrayfish<br><b>Private use only</b></html>", KeyEvent.VK_C, iconsMap.get("model_cauldron"));
		item.addActionListener(a ->
		{
			Util.loadModelFromJar(manager, getClass(), "models/cauldron");
		});
		return item;
	}

	private JMenuItem modelItemChair() {
		JMenuItem item = createItem("Chair", "<html>Model by MrCrayfish<br><b>Private use only</b></html>", KeyEvent.VK_C, iconsMap.get("model_chair"));
		item.addActionListener(a ->
		{
			Util.loadModelFromJar(manager, getClass(), "models/modern_chair");
		});
		return item;
	}

	private JMenuItem menuItemPM() {
		JMenuItem item = createItem("Planet Minecraft", "Open PMC Post", KeyEvent.VK_P, iconsMap.get("planet_minecraft"));
		item.addActionListener(a ->
		{
			JOptionPane.showMessageDialog(null, "This option has not been added yet. Please wait until the next preview.");
		});
		return item;
	}

	private JMenuItem menuItemMF() {
		JMenuItem item = createItem("Minecraft Forum", "Open MF Post", KeyEvent.VK_M, iconsMap.get("minecraft_forum"));
		item.addActionListener(a ->
		{
			JOptionPane.showMessageDialog(null, "This option has not been added yet. Please wait until the next preview.");
		});
		return item;
	}

	private JMenuItem menuItemGitHub() {
		JMenuItem item = createItem("Github", "View Source Code", KeyEvent.VK_G, iconsMap.get("github"));
		item.addActionListener(a ->
		{
			Util.openUrl("https://github.com/MrCrayfish/ModelCreator");
		});
		return item;
	}

	private JMenuItem menuItemDonate() {
		JMenuItem item = createItem("Donate (PayPal)", "Donate to MrCrayfish", KeyEvent.VK_D, iconsMap.get("coin"));
		item.addActionListener(a ->
		{
			Util.openUrl("https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=HVXLDWFN4MNA2");
		});
		return item;
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
