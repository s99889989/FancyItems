package com.daxton.fancyitmes.gui.button.item.edit.lore;

import com.daxton.fancycore.api.character.conversion.StringConversion;
import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.gui.button.item.EditItem;
import com.daxton.fancyitmes.item.CustomItem;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class LoreEdit implements GuiAction {

	final Player player;
	final GUI gui;
	final String itemType;
	final String itemID;
	final String editString;
	public LoreEdit(Player player, GUI gui, String itemType, String itemID, String editString){
		this.player = player;
		this.gui = gui;
		this.itemType = itemType;
		this.itemID = itemID;
		this.editString = editString;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			UUID uuid = player.getUniqueId();
			gui.close();
			player.sendTitle(" ", "請輸入要增加的Lore", 1, 40, 1);
			ManagerItems.player_Chat_Select.put(uuid, "LoreAdd");
			ManagerItems.player_ItemEditString.put(uuid, editString+"."+itemType+"."+itemID);
		}

		if(clickType == ClickType.RIGHT){
			right(player, gui, itemType, itemID);
		}
	}

	public static void right(Player player, GUI gui, String itemType, String itemID){
		gui.clearButtonFrom(10, 54);

		ItemStack itemStack = CustomItem.valueOf(player, itemType, itemID, 1);
		GuiButton stackButton = GuiButton.ButtonBuilder.getInstance().setItemStack(itemStack).build();
		gui.setButton(stackButton, 1, 5);

		FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");
		List<String> loreList = itemConfig.getStringList(itemID+".Lore");

		Integer[] integers = new Integer[]{18, 19, 27, 28, 36, 37};
		if(!loreList.isEmpty()){

			GuiButton loreFrontButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Lore.LoreFront")).
				setGuiAction(new RLoreEdit(player, gui, itemType, itemID, "LoreFront", 0)).build();
			gui.setButton(loreFrontButton, 2, 2);

			for(int i = 0 ; i < loreList.size() ; i++){
				String message = loreList.get(i);
				ItemStack button = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Lore.LoreEdit");
				List<String> buttonLore = new ArrayList<>();
				button.getLore().forEach(s -> {
					buttonLore.add(s.replace("{lore}", message));
				});
				button.setLore(buttonLore);

				GuiButton loreEditButton = GuiButton.ButtonBuilder.getInstance().
					setItemStack(button).
					setGuiAction(new RLoreEdit(player, gui, itemType, itemID, "LoreEdit", i)).build();
				gui.addButton(loreEditButton, 12, 44, integers);
			}
		}
		GuiButton loreAddButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Lore.LoreAdd")).
			setGuiAction(new RLoreEdit(player, gui, itemType, itemID, "LoreAdd", 0)).build();
		gui.addButton(loreAddButton, 12, 44, integers);
	}

	//增加Lore
	public static void addLore(Player player, String value){
		UUID uuid = player.getUniqueId();
		String itemString = ManagerItems.player_ItemEditString.get(uuid);
		if(itemString == null || itemString.split("\\.").length !=3){
			return;
		}

		String[] kkk = itemString.split("\\.");
		String key = kkk[0];
		String itemType = kkk[1];
		String itemID = kkk[2];
		ManagerItems.player_ItemEditString.put(uuid, "");

		FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");

		List<String> loreList = itemConfig.getStringList(itemID+".Lore");

		loreList.add(StringConversion.getColorString(value));

		itemConfig.set(itemID+".Lore", loreList);

		GUI gui = ManagerItems.gui_Map.get(uuid);
		EditItem.set(player, gui, itemType, itemID);
		gui.open(gui);

	}

}
