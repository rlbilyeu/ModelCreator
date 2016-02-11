package com.mrcrayfish.modelcreator;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class IconMap {
        static HashMap<String, Icon> iconMap;

        public static HashMap<String, Icon> invoke() {
            iconMap = new HashMap<String, Icon>();

            loadIcon("sticker", "sticker.png");

            loadIcon("coin", "coin.png");
            loadIcon("twitter", "twitter.png");
            loadIcon("facebook", "facebook.png");
            loadIcon("reddit", "reddit.png");
            loadIcon("imgur", "imgur.png");
            loadIcon("planet_minecraft", "planet_minecraft.png");
            loadIcon("minecraft_forum", "minecraft_forum.png");
            loadIcon("github", "github.png");

            loadIcon("cube", "cube.png");
            loadIcon("bin", "bin.png");
            loadIcon("bin_open", "bin_open.png");
            loadIcon("add", "add.png");
            loadIcon("add_rollover", "add_rollover.png");
            loadIcon("new", "new.png");
            loadIcon("import", "import.png");
            loadIcon("export", "export.png");
            loadIcon("texture", "texture.png");
            loadIcon("clear", "clear.png");
            loadIcon("copy", "copy.png");
            loadIcon("clipboard", "clipboard.png");
            loadIcon("transparent", "transparent.png");
            loadIcon("load", "load.png");
            loadIcon("disk", "disk.png");
            loadIcon("exit", "exit.png");
            loadIcon("box_off", "box_off.png");
            loadIcon("box_on", "box_on.png");

            loadIcon("arrow_up", "arrow_up.png");
            loadIcon("arrow_down", "arrow_down.png");

            loadIcon("model_cauldron", "model_cauldron.png");
            loadIcon("model_chair", "model_chair.png");

            return iconMap;
        }

        private static void loadIcon(String keyname, String filename) {
            Toolkit loader = Toolkit.getDefaultToolkit();
            String path = "res/icons/" + filename;
            ImageIcon icon = new ImageIcon(loader.getImage(path));
            iconMap.put(keyname, icon);
        }

}