package com.daxton.fancyitmes.gui.button.custom;

import com.daxton.fancycore.api.config.SearchConfigMap;
import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.FancyItems;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.gui.button.item.edit.ChatEdit;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
			UUID uuid = player.getUniqueId();

			//編輯物品用值
			String[] editKey = ManagerItems.player_ItemEditArray.get(uuid);
			editKey[2] = "CustomValue";
			ManagerItems.player_ItemEditArray.put(uuid, editKey);

			gui.clearButtonFrom(10, 54);

			Integer[] ignore = new Integer[]{18, 19, 27, 28, 36, 37};

			SearchConfigMap.fileNameList(FileConfig.config_Map, "custom-value/", true).forEach(s -> {
				FileConfiguration config = FileConfig.config_Map.get("custom-value/"+s+".yml");
				GuiButton materialButton = GuiButton.ButtonBuilder.getInstance().
					setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.CustomAttrs.Type", "{type}", "§f"+s)).
					setGuiAction(new ValueList(player, gui, config)).build();
				gui.addButton(materialButton, 11, 44, ignore);

			});
		}
	}

	public static List<String> getCustomList(Player player){
		List<String> customList = new ArrayList<>();
		UUID uuid = player.getUniqueId();
		//編輯物品用值
		String[] editKey = ManagerItems.player_ItemEditArray.get(uuid);
		String itemType = editKey[0];
		String itemID = editKey[1];
		FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");
		if(itemConfig.contains(itemID+".CustomValue")){
			for(String key : itemConfig.getConfigurationSection(itemID+".CustomValue").getKeys(false)){
				String value = itemConfig.getString(itemID+".CustomValue."+key);

				customList.add("§f"+key+":"+value);
			}
		}

		return customList;
	}

}
