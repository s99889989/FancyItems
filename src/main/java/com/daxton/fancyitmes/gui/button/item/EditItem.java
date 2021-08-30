package com.daxton.fancyitmes.gui.button.item;

import com.daxton.fancycore.api.character.conversion.StringConversion;
import com.daxton.fancycore.api.config.SearchConfigMap;
import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiEditItem;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.FancyItems;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.gui.button.custom.CustomValue;
import com.daxton.fancyitmes.gui.button.item.edit.ChatEdit;
import com.daxton.fancyitmes.gui.button.item.edit.lore.LoreEdit;
import com.daxton.fancyitmes.item.CustomItem;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class EditItem implements GuiAction {

	final Player player;
	final GUI gui;
	final String itemType;
	final String itemID;

	public EditItem(Player player, GUI gui, String itemType, String itemID){
		this.player = player;
		this.gui = gui;
		this.itemType = itemType;
		this.itemID = itemID;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		//左鍵編輯物品
		if(clickType == ClickType.LEFT){
			set(player, gui, itemType, itemID);
		}
		//右鍵取得物品
		if(clickType == ClickType.RIGHT){
			ItemStack itemStack = CustomItem.valueOf(player, itemType, itemID, 1);
			player.getInventory().addItem(itemStack);
		}
	}

	public static ItemStack se(ItemStack itemStack, String key, String reKey){
		ItemMeta itemMeta = itemStack.getItemMeta();
		if(itemMeta.getDisplayName().contains(key)){
			itemMeta.setDisplayName(itemMeta.getDisplayName().replace(key, reKey));
		}
		List<String> newList = new ArrayList<>();

		itemStack.setItemMeta(itemMeta);

		return itemStack;
	}

	public static void set(Player player, GUI gui, String itemType, String itemID){
		gui.clearButtonFrom(10, 54);
		ItemStack itemStack = CustomItem.valueOf(player, itemType, itemID, 1);

		List<String> addLore = languageConfig.getStringList("Gui.EditItem.ItemEdit");
		GuiEditItem.loreTail(itemStack, addLore);

		//物品
		GuiButton itemButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(itemStack).
			setGuiAction(new GetItem(player, itemStack)).build();
		gui.setButton(itemButton, 1, 5);
		//材質
		GuiButton materialButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Material", "{material}", itemStack.getType().name())).
			setGuiAction(new ChatEdit(player, gui, itemType, itemID, "Material")).build();
		gui.setButton(materialButton, 2, 2);
		//CMD
		GuiButton cmdButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.CustomModelData", "{cmd}", itemStack.getItemMeta().getCustomModelData()+"")).
			setGuiAction(new ChatEdit(player, gui, itemType, itemID, "CustomModelData")).build();
		gui.setButton(cmdButton, 2, 3);
		//顯示名稱
		GuiButton displayNameButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.DisplayName", "{name}", itemStack.getItemMeta().getDisplayName())).
			setGuiAction(new ChatEdit(player, gui, itemType, itemID, "DisplayName")).build();
		gui.setButton(displayNameButton, 2, 4);
		//Lore
		ItemStack loreItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Lore.LoreMain");
		GuiEditItem.loreInsert(loreItem, "{lore}", CustomItem.valueOf(player, itemType, itemID, 1).getLore());
		GuiButton loreButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(loreItem).
			setGuiAction(new LoreEdit(player, gui, itemType, itemID, "LoreAdd")).build();
		gui.setButton(loreButton, 2, 5);
		//原版屬性
		ItemStack attrItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Attributes");
		GuiEditItem.loreInsert(attrItem, "{attributes}", GuiEditItem.attrGet(CustomItem.valueOf(player, itemType, itemID, 1)));
		GuiButton attributesButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(attrItem).build();
		gui.setButton(attributesButton, 2, 6);

		//不會損壞
		GuiButton unbreakableButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Unbreakable", "{bedrock}", !itemStack.getItemMeta().isUnbreakable()+"")).
			build();
		gui.setButton(unbreakableButton, 3, 2);
		//損壞值
		GuiButton dataButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Data", "{data}", GuiEditItem.getData(itemStack)+"")).
			setGuiAction(new ChatEdit(player, gui, itemType, itemID, "Data")).build();
		gui.setButton(dataButton, 3, 3);

		//自訂值
		GuiButton customAttrsButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.CustomAttrs.Main")).
			setGuiAction(new CustomValue(player, gui)).build();
		gui.setButton(customAttrsButton, 4, 2);
		//動作
		GuiButton actionButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Action")).build();
		gui.setButton(actionButton, 4, 3);
	}



	//基本的聊天輸入修改
	public static void chatEdit(Player player, String value){
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

		if(key.equalsIgnoreCase("Material")){
			itemConfig.set(itemID+"."+key, value);
		}
		if(key.equalsIgnoreCase("Data")){
			itemConfig.set(itemID+"."+key, StringConversion.getInt(0, value));
		}
		if(key.equalsIgnoreCase("CustomModelData")){
			itemConfig.set(itemID+"."+key, StringConversion.getInt(0, value));
		}
		if(key.equalsIgnoreCase("DisplayName")){
			itemConfig.set(itemID+"."+key, StringConversion.getColorString(value));
		}

		GUI gui = ManagerItems.gui_Map.get(uuid);
		set(player, gui, itemType, itemID);
		gui.open(gui);

	}

}
