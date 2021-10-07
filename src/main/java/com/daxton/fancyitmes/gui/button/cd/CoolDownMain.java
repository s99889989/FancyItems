package com.daxton.fancyitmes.gui.button.cd;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiEditItem;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.gui.button.classes.ClassesMain;
import com.daxton.fancyitmes.gui.select.SelectItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class CoolDownMain implements GuiAction {

	final GUI gui;
	final Player player;

	public CoolDownMain(Player player, GUI gui){
		this.gui = gui;
		this.player = player;
	}

	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			gui.clearButtonFrom(10, 54);

			//左鍵
			ItemStack cdLeftItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.CD.Left");
			Map<String, String> cdLeftMap = new HashMap<>();
			cdLeftMap.put("{left}", SelectItem.getCD(player, "LeftClick"));
			GuiEditItem.replaceLore(cdLeftItem, cdLeftMap);
			GuiButton cdLeftButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(cdLeftItem).
				setGuiAction(new CDLeft(player, gui)).
				build();
			gui.setButton(cdLeftButton, 2, 2);
			//右鍵
			ItemStack cdRightItem = GuiItem.valueOf(player, languageConfig,"Gui.EditItem.CD.Right");
			Map<String, String> cdRightMap = new HashMap<>();
			cdRightMap.put("{right}", SelectItem.getCD(player, "RightClick"));
			GuiEditItem.replaceLore(cdRightItem, cdRightMap);
			GuiButton cdRightButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(cdRightItem).
				setGuiAction(new CDRight(player, gui)).
				build();
			gui.setButton(cdRightButton, 2, 3);

		}
	}

}
