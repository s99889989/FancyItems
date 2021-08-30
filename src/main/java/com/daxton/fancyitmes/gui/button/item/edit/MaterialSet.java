package com.daxton.fancyitmes.gui.button.item.edit;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.gui.button.item.EditItem;
import com.daxton.fancyitmes.item.CustomItem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class MaterialSet implements GuiAction {

	final Player player;
	final GUI gui;
	final String itemType;
	final String itemID;
	final String value;

	public MaterialSet(Player player, GUI gui, String itemType, String itemID, String value){
		this.player = player;
		this.gui = gui;
		this.itemType = itemType;
		this.itemID = itemID;
		this.value = value;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");
			itemConfig.set(itemID+".Material", value);

			EditItem.set(player, gui, itemType, itemID);

		}
		if(clickType == ClickType.RIGHT){
			FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");
			itemConfig.set(itemID+".Material", value);



			ItemStack itemStack = CustomItem.valueOf(player, itemType, itemID, 1);

			GuiButton nextPageButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(itemStack).build();
			gui.setButton(nextPageButton, 1, 5);

		}
	}

}
