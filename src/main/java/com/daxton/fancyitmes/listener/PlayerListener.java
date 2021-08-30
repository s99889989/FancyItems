package com.daxton.fancyitmes.listener;

import com.daxton.fancyitmes.gui.button.item.EditItem;
import com.daxton.fancyitmes.gui.button.item.edit.lore.LoreEdit;
import com.daxton.fancyitmes.gui.button.item.edit.lore.RLoreEdit;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerListener implements Listener {

    //登入時
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

    }

    //當玩家聊天
    @EventHandler
    public void onChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String message = event.getMessage();
        String select = ManagerItems.player_Chat_Select.get(uuid);
        if(select != null && !select.isEmpty()){
            event.setCancelled(true);
            if(select.equalsIgnoreCase("Default")){
                EditItem.chatEdit(player, message);
            }
            if(select.equalsIgnoreCase("LoreAdd")){
                LoreEdit.addLore(player, message);
            }
            if(select.equalsIgnoreCase("RLore")){
                RLoreEdit.editLore(player, message);
            }
            ManagerItems.player_Chat_Select.put(uuid, "");
        }
    }

}
