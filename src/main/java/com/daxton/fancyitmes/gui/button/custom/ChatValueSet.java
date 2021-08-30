package com.daxton.fancyitmes.gui.button.custom;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiChatAction;
import org.bukkit.entity.Player;

public class ChatValueSet implements GuiChatAction {

	final GUI gui;
	public ChatValueSet(GUI gui){
		this.gui = gui;
	}

	public void execute(Player player, String chatMessage){
		player.sendMessage("測試功能:"+ chatMessage);
	}

}
