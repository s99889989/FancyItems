package com.daxton.fancyitmes.gui.button.enchantments;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiEditItem;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.gui.select.SelectItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class EnchantmentsLevel implements GuiAction {

	final GUI gui;
	final Player player;
	final String enchantmentsString;
	//改變附魔等級
	public EnchantmentsLevel(Player player, GUI gui, String enchantmentsString){
		this.gui = gui;
		this.player = player;
		this.enchantmentsString = enchantmentsString;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		int place = slot+1;

		if(clickType == ClickType.LEFT){
			SelectItem.addEnchantmentsLevel(player, enchantmentsString, 1);
			SelectItem.upItem(player, gui);
			set(place);
		}
		if(clickType == ClickType.RIGHT){
			SelectItem.addEnchantmentsLevel(player, enchantmentsString, -1);
			SelectItem.upItem(player, gui);
			set(place);
		}
	}

	public void set(int place){
		ItemStack enchantmentItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Enchantments.List");
		//名稱
		String name = enchantmentsString;
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
			setGuiAction(this).build();

		gui.setButton(enchantmentButton, place);

	}

}
