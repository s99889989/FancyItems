package com.daxton.fancyitmes.gui.button.top;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

public class CloseMenu implements GuiAction {

    private final GUI gui;

    public CloseMenu(GUI gui){
        this.gui = gui;
    }

    public void execute(ClickType clickType, InventoryAction action, int slot){
        if(clickType == ClickType.LEFT){
            gui.close();
        }
    }


}
