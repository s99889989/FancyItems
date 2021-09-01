package com.daxton.fancyitmes.gui.button.item.edit;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.gui.button.item.EditItem;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import java.util.UUID;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class FlagSwitch implements GuiAction {

	final GUI gui;
	final Player player;
	boolean b;
	public FlagSwitch(Player player, GUI gui, boolean b){
		this.gui = gui;
		this.player = player;
		this.b = b;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			UUID uuid = player.getUniqueId();
			b = !b;
			GuiButton flagButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Flag", "{flag}", !b+"")).
				setGuiAction(this).
				build();
			gui.setButton(flagButton, 3, 4);

			//編輯物品用值
			String[] editKey = ManagerItems.player_ItemEditArray.get(uuid);
			String itemType = editKey[0];
			String itemID = editKey[1];
			FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");
			itemConfig.set(itemID+".HideItemFlags", b);

			EditItem.upItem(player, gui);

		}
	}

}
