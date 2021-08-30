package com.daxton.fancyitmes.gui.button.top;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiAction;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;

public class SaveMenu implements GuiAction {

    private final GUI gui;
    private final Player player;
    private final String uuidString;

    public SaveMenu(GUI gui, Player player){
        this.gui = gui;
        this.player = player;
        this.uuidString = player.getUniqueId().toString();
    }

    public void execute(ClickType clickType, InventoryAction action, int slot){

    }


}
