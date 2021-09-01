package com.daxton.fancyitmes.gui.button.attributes;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancycore.api.other.DigitConversion;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

import java.math.BigDecimal;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class AmountSwitch implements GuiAction {

	final GUI gui;
	final Player player;
	final EditAttr editAttr;
	double amount;

	public AmountSwitch(Player player, GUI gui, EditAttr editAttr){
		this.gui = gui;
		this.player = player;
		this.editAttr = editAttr;
		this.amount = Double.parseDouble(editAttr.amount);
	}
	public static double add(double v1,double v2){
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}
	public void execute(ClickType clickType, InventoryAction action, int slot){
		if(clickType == ClickType.LEFT){
			amount = add(amount, editAttr.add);
			editAttr.amount = DigitConversion.NumberUtil(amount, "0.####");
			//名稱
			GuiButton nameButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Attributes.Amount", "{amount}", editAttr.amount)).
				setGuiAction(this).
				build();
			gui.setButton(nameButton, 4, 5);

		}
		if(clickType == ClickType.RIGHT){
			amount = add(amount, -editAttr.add);
			editAttr.amount = DigitConversion.NumberUtil(amount, "0.####");
			//名稱
			GuiButton nameButton = GuiButton.ButtonBuilder.getInstance().
				setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.EditItem.Attributes.Amount", "{amount}", editAttr.amount)).
				setGuiAction(this).
				build();
			gui.setButton(nameButton, 4, 5);
		}
		if(clickType == ClickType.SHIFT_LEFT){
			editAttr.add *= 10;
		}
		if(clickType == ClickType.SHIFT_RIGHT){
			editAttr.add *= 0.1;
		}
	}

}
