package com.daxton.fancyitmes.gui.button.custom;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiEditItem;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancycore.manager.OtherManager;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.gui.button.item.EditItem;
import com.daxton.fancyitmes.manager.ManagerItems;
import com.google.common.collect.Lists;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Locale;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class CustomKeyList implements GuiAction {

	final GUI gui;
	final Player player;
	final FileConfiguration config;
	int page;

	public CustomKeyList(Player player, GUI gui, FileConfiguration config){
		this.gui = gui;
		this.player = player;
		this.config = config;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			valueList(player, gui, config, 0);
		}
	}

	public static void valueList(Player player, GUI gui, FileConfiguration config, int page){
		gui.clearButtonFrom(10, 54);
		Integer[] ignore = new Integer[]{18, 19, 27, 28, 36, 37};

		String[] editKey = ManagerItems.player_ItemEditArray.get(player.getUniqueId());
		String itemType = editKey[0];
		String itemID = editKey[1];
		FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");

		List<String> customKeyList = Lists.newArrayList(config.getConfigurationSection("").getKeys(false));

		for(int i = page*28 ; i < customKeyList.size() ; i++){
			String key = customKeyList.get(i);

			ItemStack keyItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.CustomAttrs.Key", "{key}", "§f"+key);

			String lowKey = key.toLowerCase();
			String name = "";
			String value = OtherManager.custom_Value_Default.get(lowKey);
			if(OtherManager.custom_Value.get(lowKey) != null){
				name = OtherManager.custom_Value.get(lowKey);
			}
			if(itemConfig.contains(itemID+".CustomValue."+key)){
				value = itemConfig.getString(itemID+".CustomValue."+key);
			}

			GuiEditItem.loreReplace(keyItem, "{name}", name);
			GuiEditItem.loreReplace(keyItem, "{value}", value);


			GuiButton materialButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(keyItem).
				setGuiAction(new ValueSet(player, gui, config, key, page)).build();
			gui.addButton(materialButton, 11, 44, ignore);
		}

		if(page != 0){
			GuiButton previousPageButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(languageConfig,"Gui.PreviousPage")).
				setGuiAction(new PreviousCustom(player, gui, config, page)).build();
			gui.setButton(previousPageButton, 6, 1);
		}

		if((page+1)*28 < customKeyList.size()){
			GuiButton previousPageButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(languageConfig,"Gui.NextPage")).
				setGuiAction(new NextCustom(player, gui, config, page)).build();
			gui.setButton(previousPageButton, 6, 9);
		}
		EditItem.upItem(player, gui);
	}


}
