package com.daxton.fancyitmes.gui.button.item.edit;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatEdit implements GuiAction {

	final Player player;
	final GUI gui;
	final String itemType;
	final String itemID;
	final String editString;

	public ChatEdit(Player player, GUI gui, String itemType, String itemID, String editString){
		this.player = player;
		this.gui = gui;
		this.itemType = itemType;
		this.itemID = itemID;
		this.editString = editString;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			gui.close();
			UUID uuid = player.getUniqueId();

			ManagerItems.player_Chat_Select.put(uuid, "Default");
			ManagerItems.player_ItemEditString.put(uuid, editString+"."+itemType+"."+itemID);
			if(editString.equalsIgnoreCase("Material")){
				player.sendTitle(" ", languageConfig.getString("Gui.EditItem.Material.Message") ,1, 40, 1);
			}
			if(editString.equalsIgnoreCase("Data")){
				player.sendTitle(" ", languageConfig.getString("Gui.EditItem.Data.Message") ,1, 40, 1);
			}
			if(editString.equalsIgnoreCase("CustomModelData")){
				player.sendTitle(" ", languageConfig.getString("Gui.EditItem.CustomModelData.Message") ,1, 40, 1);
			}
			if(editString.equalsIgnoreCase("DisplayName")){
				player.sendTitle(" ", languageConfig.getString("Gui.EditItem.DisplayName.Message") ,1, 40, 1);
			}

		}
		if(clickType == ClickType.RIGHT){
			if(editString.equalsIgnoreCase("Material")){
				ppp(0);
			}
		}

	}

	public void ppp(int page){
		gui.clearButtonFrom(10, 54);
		Integer[] integers = new Integer[]{18, 19, 27, 28, 36, 37};
		Material[] itemList = Material.values();
		for(int i = page*28 ; i < itemList.length-100 ; i++){
			if(i == 0){
				i++;
			}
			ItemStack itemStack = new ItemStack(itemList[i]);

			GuiButton itemButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(itemStack).
				setGuiAction(new MaterialSet(player, gui, itemType, itemID, itemList[i].name())).build();
			gui.addButton(itemButton, 11, 44, integers);

		}

		if(page != 0){
			GuiButton previousPageButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(languageConfig,"Gui.PreviousPage")).
				setGuiAction(new ItemTypListPrevious(this, page)).build();
			gui.setButton(previousPageButton, 6, 1);
		}

		if((page+1)*28 < itemList.length-100){
			GuiButton nextPageButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(languageConfig,"Gui.NextPage")).
				setGuiAction(new ItemTypListNext(this, page)).build();
			gui.setButton(nextPageButton, 6, 9);
		}

	}

}
