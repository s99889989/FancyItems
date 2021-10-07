package com.daxton.fancyitmes.gui.select;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiEditItem;
import com.daxton.fancycore.api.judgment.NumberJudgment;
import com.daxton.fancyitmes.FancyItems;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.gui.button.item.GetItem;
import com.daxton.fancyitmes.item.CustomItem;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class SelectItem {

	//更新物品
	public static void upItem(Player player, GUI gui){
		UUID uuid = player.getUniqueId();
		//編輯物品用值
		String[] editKey = ManagerItems.player_ItemEditArray.get(uuid);
		String itemType = editKey[0];
		String itemID = editKey[1];

		ItemStack itemStack = CustomItem.valueOf(player, itemType, itemID, 1);

		List<String> addLore = languageConfig.getStringList("Gui.EditItem.ItemEdit");
		GuiEditItem.loreTail(itemStack, addLore);

		//物品
		GuiButton itemButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(itemStack).
			setGuiAction(new GetItem(player, itemStack, gui, itemType, itemID)).build();
		gui.setButton(itemButton, 1, 5);
	}

	//獲取附魔等級
	public static int getEnchantmentsLevel(Player player, String key){
		String[] editKey = ManagerItems.player_ItemEditArray.get(player.getUniqueId());
		String itemType = editKey[0];
		String itemID = editKey[1];
		FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");
		if(itemConfig.contains(itemID+".Enchantments")){
			List<String> enchantmentsList = itemConfig.getStringList(itemID+".Enchantments");
			for(String enchantmentsString : enchantmentsList){
				if(enchantmentsString.contains(key)){
					String[] enchantTwo = enchantmentsString.split(":");
					if(enchantTwo.length == 2){
						if(NumberJudgment.isNumber(enchantTwo[1])){
							return Integer.parseInt(enchantTwo[1]);
						}
					}
				}
			}
		}
		return 0;
	}
	//設置附魔等級
	public static void addEnchantmentsLevel(Player player, String key, int level){
		int nowLevel = getEnchantmentsLevel(player, key);
		nowLevel += level;
		if(nowLevel < 0){
			return;
		}
		String[] editKey = ManagerItems.player_ItemEditArray.get(player.getUniqueId());
		String itemType = editKey[0];
		String itemID = editKey[1];
		FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");
		List<String> enchantmentsList = itemConfig.getStringList(itemID+".Enchantments");
		for(int i = 0 ; i < enchantmentsList.size() ; i++){
			String enchantmentsString = enchantmentsList.get(i);
			if(enchantmentsString.contains(key)){
				enchantmentsList.remove(i);
				break;
			}
		}

		if(nowLevel > 0){
			enchantmentsList.add(key+":"+nowLevel);
		}
		itemConfig.set(itemID+".Enchantments", enchantmentsList);
	}
	//獲取CD值
	public static String getCD(Player player, String key){
		String[] editKey = ManagerItems.player_ItemEditArray.get(player.getUniqueId());
		String itemType = editKey[0];
		String itemID = editKey[1];
		FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");
		if(itemConfig.contains(itemID+".CoolDown."+key)){
			return itemConfig.getString(itemID+".CoolDown."+key);
		}
		return "0";
	}
	//增加設定值
	public static void addCD(Player player, String key, int value){
		String[] editKey = ManagerItems.player_ItemEditArray.get(player.getUniqueId());
		String itemType = editKey[0];
		String itemID = editKey[1];
		FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");

		if(itemConfig.contains(itemID+".CoolDown."+key)){
			String nowValueString = itemConfig.getString(itemID+".CoolDown."+key);
			if(NumberJudgment.isNumber(nowValueString)){
				int nowValue = Integer.parseInt(nowValueString);
				nowValue += value;
				if(nowValue > 0){
					itemConfig.set(itemID+".CoolDown."+key, nowValue);
				}else {
					itemConfig.set(itemID+".CoolDown."+key, null);
				}
			}
		}else {
			if(value > 0){
				itemConfig.set(itemID+".CoolDown."+key, value);
			}
		}

	}
	//設置CD值
	public static void setCD(Player player, String key, String value){
		String[] editKey = ManagerItems.player_ItemEditArray.get(player.getUniqueId());
		String itemType = editKey[0];
		String itemID = editKey[1];
		FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");
		if(NumberJudgment.isNumber(value)){
			int nowValue = Integer.parseInt(value);
			if(nowValue < 1){
				return;
			}
		}
		itemConfig.set(itemID+".CoolDown."+key, value);
	}

}
