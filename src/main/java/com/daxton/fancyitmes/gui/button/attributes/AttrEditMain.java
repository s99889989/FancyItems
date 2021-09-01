package com.daxton.fancyitmes.gui.button.attributes;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class AttrEditMain implements GuiAction {

	final GUI gui;
	final Player player;
	public String key = "";

	public AttrEditMain(Player player, GUI gui){
		this.gui = gui;
		this.player = player;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			attrList(player, gui);
		}
	}

	public static void attrList(Player player, GUI gui){
		UUID uuid = player.getUniqueId();
		//編輯物品用值
		String[] editKey = ManagerItems.player_ItemEditArray.get(uuid);
		String itemType = editKey[0];
		String itemID = editKey[1];
		FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");

		gui.clearButtonFrom(10, 54);

		Integer[] integers = new Integer[]{18, 19, 27, 28, 36, 37};

		int i = 0;
		for(String s : itemConfig.getStringList(itemID+".Attributes")){
			String[] attr = s.split(":");
			if(attr.length == 4){
				String name = attr[1];
				String slot = attr[0];
				String operation = attr[2];
				String amount = attr[3];

				ItemStack itemStack = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Attributes.Edit");
				ItemMeta itemMeta = itemStack.getItemMeta();
				itemMeta.setDisplayName(itemMeta.getDisplayName().replace("{name}", name));
				itemStack.setItemMeta(itemMeta);
				List<String> newLore = new ArrayList<>();
				for(String lore : itemStack.getLore()){
					if(lore.contains("{body}")){
						lore = lore.replace("{body}", slot);
					}
					if(lore.contains("{add}")){
						lore = lore.replace("{add}", operation);
					}
					if(lore.contains("{value}")){
						lore = lore.replace("{value}", amount);
					}
					newLore.add(lore);
				}
				itemStack.setLore(newLore);


				GuiButton loreEditButton = GuiButton.ButtonBuilder.getInstance().
					setItemStack(itemStack).
					setGuiAction(new EditAttr(player, gui, i, name, slot, operation, amount)).
					build();
				gui.addButton(loreEditButton, 11, 26, integers);
			}
			i++;
		}

		GuiButton loreEditButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Attributes.Add")).
			setGuiAction(new AddAttr(player, gui)).
			build();
		gui.addButton(loreEditButton, 11, 25, integers);
	}

}
