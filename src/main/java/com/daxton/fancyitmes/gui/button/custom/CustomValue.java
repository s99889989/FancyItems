package com.daxton.fancyitmes.gui.button.custom;

import com.daxton.fancycore.api.config.SearchConfigMap;
import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.FancyItems;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.gui.button.item.edit.ChatEdit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import java.util.ArrayList;
import java.util.List;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class CustomValue implements GuiAction {

	final GUI gui;
	final Player player;

	public CustomValue(Player player, GUI gui){
		this.gui = gui;
		this.player = player;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			gui.clearButtonFrom(10, 54);

			Integer[] ignore = new Integer[]{18, 19, 27, 28, 36, 37};

			SearchConfigMap.fileNameList(FileConfig.config_Map, "CustomValue/", true).forEach(s -> {
				FileConfiguration config = FileConfig.config_Map.get("CustomValue/"+s+".yml");
				GuiButton materialButton = GuiButton.ButtonBuilder.getInstance().
					setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.CustomAttrs.Type", "{type}", "§f"+s)).
					setGuiAction(new ValueList(player, gui, config)).build();
				gui.addButton(materialButton, 11, 44, ignore);

			});
		}
	}

}
