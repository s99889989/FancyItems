package com.daxton.fancyitmes.gui.button.action;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancycore.other.taskaction.MapGetKey;
import com.daxton.fancycore.other.taskaction.StringToMap;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.gui.button.attributes.AddAttr;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class MainAction implements GuiAction {

	final GUI gui;
	final Player player;

	public MainAction(Player player, GUI gui){
		this.gui = gui;
		this.player = player;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			mainAction(player, gui);
		}
	}

	public static void mainAction(Player player, GUI gui){
		UUID uuid = player.getUniqueId();
		//編輯物品用值
		String[] editKey = ManagerItems.player_ItemEditArray.get(uuid);
		String itemType = editKey[0];
		String itemID = editKey[1];
		FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");

		gui.clearButtonFrom(10, 54);

		Integer[] integers = new Integer[]{18, 19, 27, 28, 36, 37};

		int i = 0;
		for(String s : itemConfig.getStringList(itemID+".Action")){
			String[] attr = s.split(":");
			if(attr.length == 2){

				ItemStack itemStack = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Action.MainEdit");

				GuiButton loreEditButton = GuiButton.ButtonBuilder.getInstance().
					setItemStack(getActionItem(player, itemStack, s)).
					setGuiAction(new EditAction(player, gui, i)).
					build();
				gui.addButton(loreEditButton, 11, 17, integers);
			}
			i++;
		}

		GuiButton loreEditButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Action.MainAdd")).
			setGuiAction(new AddAction(player, gui)).
			build();
		gui.addButton(loreEditButton, 11, 17, integers);
	}

	public static List<String> getNameList(Player player){
		List<String> nameList = new ArrayList<>();
		UUID uuid = player.getUniqueId();
		//編輯物品用值
		String[] editKey = ManagerItems.player_ItemEditArray.get(uuid);
		String itemType = editKey[0];
		String itemID = editKey[1];
		FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");
		List<String> actionList = itemConfig.getStringList(itemID+".Action");
		for(String actionString : actionList){
			String[] attr = actionString.split(":");
			if(attr.length == 2){
				nameList.add("§f"+attr[0]);
			}
		}

		return nameList;
	}


	public static ItemStack getActionItem(Player player, ItemStack itemStack, String actionString){
		String[] actionStringArray = actionString.split(":");
		if(actionStringArray.length == 2){
			String name = actionStringArray[0];
			Map<String, String> action_Map = StringToMap.toActionMap(actionStringArray[1]);
			MapGetKey actionMapHandle = new MapGetKey(action_Map, player, null);
			String action = actionMapHandle.getString(new String[]{"a", "action"},"null");
			String needTarget = actionMapHandle.getString(new String[]{"nt", "NeedTarget"},"false");
			String count = actionMapHandle.getString(new String[]{"c", "Count"},"1");
			String countPeriod = actionMapHandle.getString(new String[]{"cp", "CountPeriod"},"1");
			String mark = actionMapHandle.getString(new String[]{"m", "Mark"},"null");
			String stop = actionMapHandle.getString(new String[]{"s", "stop"},"false");
			String trigger = actionMapHandle.getString(new String[]{"triggerkey"},"null");


			ItemMeta itemMeta = itemStack.getItemMeta();
			itemMeta.setDisplayName(itemMeta.getDisplayName().replace("{name}", name));
			itemStack.setItemMeta(itemMeta);
			List<String> newLore = new ArrayList<>();
			for(String lore : itemStack.getLore()){
				if(lore.contains("{action}")){
					lore = lore.replace("{action}", action);
				}
				if(lore.contains("{need}")){
					lore = lore.replace("{need}", needTarget);
				}
				if(lore.contains("{mark}")){
					lore = lore.replace("{mark}", mark);
				}
				if(lore.contains("{stop}")){
					lore = lore.replace("{stop}", stop);
				}
				if(lore.contains("{count}")){
					lore = lore.replace("{count}", count);
				}
				if(lore.contains("{period}")){
					lore = lore.replace("{period}", countPeriod);
				}
				if(lore.contains("{trigger}")){
					lore = lore.replace("{trigger}", trigger);
				}
				newLore.add(lore);
			}
			itemStack.setLore(newLore);


		}

		return itemStack;
	}

}
