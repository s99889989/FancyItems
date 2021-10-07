package com.daxton.fancyitmes.gui.button.item;

import com.daxton.fancycore.api.character.conversion.StringConversion;
import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiEditItem;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.gui.button.action.MainAction;
import com.daxton.fancyitmes.gui.button.attributes.AttrEditMain;
import com.daxton.fancyitmes.gui.button.bullet.BulletMain;
import com.daxton.fancyitmes.gui.button.cd.CoolDownMain;
import com.daxton.fancyitmes.gui.button.classes.ClassesMain;
import com.daxton.fancyitmes.gui.button.custom.CustomList;
import com.daxton.fancyitmes.gui.button.custom.CustomTypeList;
import com.daxton.fancyitmes.gui.button.disable.DisableMain;
import com.daxton.fancyitmes.gui.button.durable.DurableMain;
import com.daxton.fancyitmes.gui.button.enchantments.EnchantmentsMain;
import com.daxton.fancyitmes.gui.button.item.edit.BreakSwitch;
import com.daxton.fancyitmes.gui.button.item.edit.ChatEdit;
import com.daxton.fancyitmes.gui.button.item.edit.FlagSwitch;
import com.daxton.fancyitmes.gui.button.item.edit.lore.LoreEdit;
import com.daxton.fancyitmes.gui.button.placemodel.PlaceModelMain;
import com.daxton.fancyitmes.gui.select.SelectItem;
import com.daxton.fancyitmes.item.CustomItem;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	public static void set(Player player, GUI gui, String itemType, String itemID){
		UUID uuid = player.getUniqueId();
		//編輯物品用值
		ManagerItems.player_ItemEditArray.put(uuid, new String[]{itemType, itemID, "0", "0"});

		FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+itemType+".yml");

		gui.clearButtonFrom(10, 54);
		ItemStack itemStack = CustomItem.valueOf(player, itemType, itemID, 1);

		List<String> addLore = languageConfig.getStringList("Gui.EditItem.ItemEdit");
		GuiEditItem.loreTail(itemStack, addLore);

		//物品
		GuiButton itemButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(itemStack).
			setGuiAction(new GetItem(player, itemStack, gui, itemType, itemID)).build();
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
		ItemStack attrItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Attributes.Main");
		GuiEditItem.loreInsert(attrItem, "{attributes}", GuiEditItem.attrGet(CustomItem.valueOf(player, itemType, itemID, 1)));
		GuiButton attributesButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(attrItem).
			setGuiAction(new AttrEditMain(player, gui)).build();
		gui.setButton(attributesButton, 2, 6);
		//附魔
		ItemStack enchantItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Enchantments.Main");
		GuiEditItem.loreInsert(enchantItem ,"{enchantments}", EnchantmentsMain.getEnLore(player));
		GuiEditItem.loreInsert(attrItem, "{enchantments}", GuiEditItem.attrGet(CustomItem.valueOf(player, itemType, itemID, 1)));
		GuiButton enchantButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(enchantItem).
			setGuiAction(new EnchantmentsMain(player, gui)).build();
		gui.setButton(enchantButton, 2, 7);
		//---------------------------------------------------------//
		//不會損壞
		GuiButton unbreakableButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Unbreakable", "{bedrock}", !itemStack.getItemMeta().isUnbreakable()+"")).
			setGuiAction(new BreakSwitch(player, gui, itemStack.getItemMeta().isUnbreakable())).
			build();
		gui.setButton(unbreakableButton, 3, 2);
		//損壞值
		GuiButton dataButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Data", "{data}", GuiEditItem.getData(itemStack)+"")).
			setGuiAction(new ChatEdit(player, gui, itemType, itemID, "Data")).build();
		gui.setButton(dataButton, 3, 3);
		//標籤
		GuiButton flagButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Flag", "{flag}", !itemConfig.getBoolean(itemID)+"")).
			setGuiAction(new FlagSwitch(player, gui, itemConfig.getBoolean(itemID))).
			build();
		gui.setButton(flagButton, 3, 4);
		//---------------------------------------------------------//
		//自訂值
		ItemStack customItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.CustomAttrs.Main");
		GuiEditItem.loreInsert(customItem, "{custonvalue}", CustomList.getCustomList(player));
		GuiButton customAttrsButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(customItem).
			setGuiAction(new CustomTypeList(player, gui)).build();
		gui.setButton(customAttrsButton, 4, 2);
		//動作
		ItemStack actionItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Action.Main");
		GuiEditItem.loreInsert(actionItem, "{action}", MainAction.getNameList(player));
		GuiButton actionButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(actionItem).
			setGuiAction(new MainAction(player, gui)).
			build();
		gui.setButton(actionButton, 4, 3);
		//動作CD
		ItemStack cdItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.CD.Main");
		Map<String, String> cdMap = new HashMap<>();
		cdMap.put("{left}", SelectItem.getCD(player, "LeftClick"));
		cdMap.put("{right}", SelectItem.getCD(player, "RightClick"));
		GuiEditItem.replaceLore(cdItem, cdMap);
		GuiButton cdButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(cdItem).
			setGuiAction(new CoolDownMain(player, gui)).
			build();
		gui.setButton(cdButton, 4, 4);
		//職業
		ItemStack classesItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Classes.Main");
		Map<String, String> classMap = new HashMap<>();
		classMap.put("{class}", ClassesMain.getClassesString(player));
		classMap.put("{level}", ClassesMain.getLevelString(player));
		GuiEditItem.replaceLore(classesItem, classMap);
		GuiButton classesButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(classesItem).
			setGuiAction(new ClassesMain(player, gui)).
			build();
		gui.setButton(classesButton, 4, 5);
		//禁止
		ItemStack disableItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Disable.Main");
		GuiButton disableButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(disableItem).
			setGuiAction(new DisableMain(player, gui)).
			build();
		gui.setButton(disableButton, 4, 6);
		//自訂耐久
		ItemStack durableItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Durable");
		GuiButton durableButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(durableItem).
			setGuiAction(new DurableMain(player, gui)).
			build();
		gui.setButton(durableButton, 4, 7);
		//自訂彈藥
		ItemStack bulletItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Bullet.Main");
		GuiButton bulletButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(bulletItem).
			setGuiAction(new BulletMain(player, gui)).
			build();
		gui.setButton(bulletButton, 4, 8);
		//放置模型
		ItemStack placeModelItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.PlaceModel.Main");
		GuiButton placeModelButton = GuiButton.ButtonBuilder.getInstance().
			setItemStack(placeModelItem).
			setGuiAction(new PlaceModelMain(player, gui)).
			build();
		gui.setButton(placeModelButton, 5, 2);
	}
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
