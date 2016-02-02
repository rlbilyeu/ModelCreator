package com.mrcrayfish.modelcreator;

import com.jtattoo.plaf.fast.FastLookAndFeel;

import javax.swing.*;
import java.util.Properties;

public class Start
{
	public static void main(String[] args)
	{
		try
		{
			checkJVMversion();
			setupUI();
			new ModelCreator("Model Creator - pre4");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void checkJVMversion()
	{
		Double version = Double.parseDouble(System.getProperty("java.specification.version"));
		if (version < 1.8)
			throw new IllegalStateException("You need Java 1.8 or higher to run this program.");
	}

	private static void setupUI() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		System.setProperty("org.lwjgl.util.Debug", "true");
		UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
		Properties properties = getThemeProperties();
		FastLookAndFeel.setTheme(properties);
	}

	private static Properties getThemeProperties() {
		Properties p = new Properties();
		p.put("logoString", "");
		p.put("centerWindowTitle", "on");
		p.put("buttonBackgroundColor", "127 132 145");
		p.put("buttonForegroundColor", "255 255 255");
		p.put("windowTitleBackgroundColor", "97 102 115");
		p.put("windowTitleForegroundColor", "255 255 255");
		p.put("backgroundColor", "221 221 228");
		p.put("menuBackgroundColor", "221 221 228");
		p.put("controlForegroundColor", "120 120 120");
		p.put("windowBorderColor", "97 102 110");
		return p;
	}



}
