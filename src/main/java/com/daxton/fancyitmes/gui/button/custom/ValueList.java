package com.daxton.fancyitmes.gui.button.custom;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class ValueList implements GuiAction {

	final GUI gui;
	final Player player;
	final FileConfiguration config;

	public ValueList(Player player, GUI gui, FileConfiguration config){
		this.gui = gui;
		this.player = player;
		this.config = config;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			valueList(player, gui, config);
		}
	}

	public static void valueList(Player player, GUI gui, FileConfiguration config){
		gui.clearButtonFrom(10, 54);
		Integer[] ignore = new Integer[]{18, 19, 27, 28, 36, 37};

		config.getConfigurationSection("").getKeys(false).forEach(s -> {
			GuiButton materialButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.CustomAttrs.Key", "{value}", "§f"+s)).
				setGuiAction(new ValueSet(player, gui, config, s)).build();
			gui.addButton(materialButton, 11, 44, ignore);
		});
	}

}
