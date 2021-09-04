package com.daxton.fancyitmes.gui.other;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.gui.button.main.ItemType;
import com.daxton.fancyitmes.gui.button.main.TypeNextPage;
import com.daxton.fancyitmes.gui.button.main.TypePreviousPage;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class ItemTypeList {

	//物品類別列表
	public static void setItemType(Player player, GUI gui){

		UUID uuid = player.getUniqueId();
		FileConfiguration itemMenuConfig = FileConfig.config_Map.get("ItemMenu.yml");

		Integer[] integers = new Integer[]{18, 19, 27, 28, 36, 37};

		List<String> typeStringList = new ArrayList<>(itemMenuConfig.getConfigurationSection("ItemType").getKeys(false));
		int page = ManagerItems.type_Page.get(uuid);
		for(int i = page*28; i < typeStringList.size() ; i++){

			String typeString = typeStringList.get(i);

			FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+ typeString +".yml");
			int itemAmount = itemConfig.getConfigurationSection("").getKeys(false).size();

			ItemStack itemStack = GuiItem.valueOf(player, itemMenuConfig, "ItemType."+typeString);
			List<String> loreList = new ArrayList<>();
			if(itemStack.getItemMeta().getLore() != null){
				for(String s : itemStack.getItemMeta().getLore()){
					s = s.replace("{amount}", itemAmount+"");
					loreList.add(s);
				}
			}
			if(itemAmount < 1){
				itemAmount = 1;
			}
			itemStack.setLore(loreList);
			itemStack.setAmount(itemAmount);

			GuiButton typeButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(itemStack).
				setGuiAction(new ItemType(player, gui, typeString)).build();
			gui.addButton(typeButton, 11, 44, integers);


		}

		if(page != 0){
			GuiButton previousPageButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(languageConfig,"Gui.PreviousPage")).
				setGuiAction(new TypePreviousPage(player, gui)).build();
			gui.setButton(previousPageButton, 6, 1);
		}

		if((page+1)*28 < typeStringList.size()){
			GuiButton typeButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(languageConfig,"Gui.NextPage")).
				setGuiAction(new TypeNextPage(player, gui)).build();
			gui.setButton(typeButton, 6, 9);
		}

	}

}
