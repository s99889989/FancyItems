package com.daxton.fancyitmes.gui.button.enchantments;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

public class EnchantmentsPrevious implements GuiAction {

	final Player player;
	final GUI gui;

	//附魔列表上一頁
	public EnchantmentsPrevious(Player player, GUI gui){
		this.player = player;
		this.gui = gui;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			EnchantmentsMain.page(player, gui, 0);
		}
	}
}
