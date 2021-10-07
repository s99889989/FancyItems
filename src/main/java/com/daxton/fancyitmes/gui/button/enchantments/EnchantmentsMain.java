package com.daxton.fancyitmes.gui.button.enchantments;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiEditItem;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.gui.select.SelectItem;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class EnchantmentsMain implements GuiAction {

	final GUI gui;
	final Player player;

	public EnchantmentsMain(Player player, GUI gui){
		this.gui = gui;
		this.player = player;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			page(player, gui, 0);
		}

	}

	public static void page(Player player, GUI gui, int page){
		gui.clearButtonFrom(10, 54);
		Integer[] ignore = new Integer[]{18, 19, 27, 28, 36, 37};

		Enchantment[] enchantments = Enchantment.values();
		for(int i = page * 28; i < enchantments.length ; i++){

			ItemStack enchantmentItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Enchantments.List");
			//名稱
			String name = enchantments[i].getName();
			String disName = name;
			if(languageConfig.contains("Enchantments."+name)){
				disName = languageConfig.getString("Enchantments."+name);
			}
			Map<String, String> nameMap = new HashMap<>();
			nameMap.put("{name}", disName);
			GuiEditItem.replaceName(enchantmentItem, nameMap);
			//等級
			int level = SelectItem.getEnchantmentsLevel(player, name);
			Map<String, String> levelMap = new HashMap<>();
			levelMap.put("{level}", String.valueOf(level));
			GuiEditItem.replaceLore(enchantmentItem, levelMap);

			GuiButton enchantmentButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(enchantmentItem).
				setGuiAction(new EnchantmentsLevel(player, gui, name)).build();
			gui.addButton(enchantmentButton, 11, 44, ignore);
		}


		if(page == 0){
			GuiButton typeButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(languageConfig,"Gui.NextPage")).
				setGuiAction(new EnchantmentsNext(player, gui)).build();
			gui.setButton(typeButton, 6, 9);
		}

		if(page == 1){
			GuiButton typeButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(languageConfig,"Gui.PreviousPage")).
				setGuiAction(new EnchantmentsPrevious(player, gui)).build();
			gui.setButton(typeButton, 6, 1);
		}

	}

	public static List<String> getEnLore(Player player){
		String[] editKey = ManagerItems.player_ItemEditArray.get(player.getUniqueId());
		String itemType = editKey[0];
		String itemID = editKey[1];
		FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");

		List<String> reLore = new ArrayList<>();
		List<String> enchantmentsList = itemConfig.getStringList(itemID+".Enchantments");
		if(enchantmentsList.size() > 0){
			for(int i = 0 ; i < enchantmentsList.size() ; i++){
				String enchantmentsString = enchantmentsList.get(i);
				String[] enTwo = enchantmentsString.split(":");
				if(enTwo.length == 2){
					String key = enTwo[0];
					if(languageConfig.contains("Enchantments."+key)){
						key = languageConfig.getString("Enchantments."+key);
					}
					reLore.add("§f"+key+":"+enTwo[1]);
				}
			}
		}

		return reLore;
	}

}
