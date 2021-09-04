package com.daxton.fancyitmes.gui.button.main;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.gui.button.item.EditItem;
import com.daxton.fancyitmes.gui.button.item.ItemListNext;
import com.daxton.fancyitmes.gui.button.item.ItemListPrevious;
import com.daxton.fancyitmes.item.CustomItem;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;


public class ItemType implements GuiAction {

	final Player player;
	final String itemType;
	final GUI gui;
	List<String> itemList;

	//物品類別
	public ItemType(Player player, GUI gui, String itemType){
		this.player = player;
		this.gui = gui;
		this.itemType = itemType;

	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+ itemType +".yml");
			this.itemList = new ArrayList<>(itemConfig.getConfigurationSection("").getKeys(false));
			ppp(0);
		}
	}

	public void ppp(int page){
		gui.clearButtonFrom(10, 54);
		Integer[] integers = new Integer[]{18, 19, 27, 28, 36, 37};
		for(int i = page*28 ; i < itemList.size() ; i++){
			String itemID = itemList.get(i);
			ItemStack itemStack = CustomItem.valueOf(player, itemType, itemID, 1);

			List<String> iLore = itemStack.getLore();
			List<String> addLore = languageConfig.getStringList("Gui.EditItem.Item");
			if(iLore != null){
				iLore.addAll(addLore);
				itemStack.setLore(iLore);
			}else {
				itemStack.setLore(addLore);
			}

			GuiButton itemButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(itemStack).
				setGuiAction(new EditItem(player, gui, itemType, itemID)).build();
			gui.addButton(itemButton, 11, 44, integers);
		}

		if(page != 0){
			GuiButton previousPageButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(languageConfig,"Gui.PreviousPage")).
				setGuiAction(new ItemListPrevious(this, page)).build();
			gui.setButton(previousPageButton, 6, 1);
		}

		if((page+1)*28 < itemList.size()){
			GuiButton nextPageButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(languageConfig,"Gui.NextPage")).
				setGuiAction(new ItemListNext(this, page)).build();
			gui.setButton(nextPageButton, 6, 9);
		}
	}


}
