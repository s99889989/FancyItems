package com.daxton.fancyitmes.listener;

import com.daxton.fancycore.manager.PlayerManagerCore;
import com.daxton.fancycore.other.playerdata.PlayerDataFancy;
import com.daxton.fancyitmes.FancyItems;
import com.daxton.fancyitmes.gui.button.item.EditItem;
import com.daxton.fancyitmes.gui.button.item.edit.lore.LoreEdit;
import com.daxton.fancyitmes.gui.button.item.edit.lore.RLoreEdit;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class PlayerListener implements Listener {

    //登入時
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        custom(player);
    }
    //登出時
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        ManagerItems.gui_Map.remove(uuid);

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

    @EventHandler//關閉背包
    public void onInventoryClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        custom(player);
    }

    //當按下切換1~9時
    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event){
        Player player = event.getPlayer();

        int key = event.getNewSlot();
        int oldKey = event.getPreviousSlot();
        if(key != oldKey){
            custom(player);
        }
    }

    public static void custom(Player player){
        new BukkitRunnable() {
            @Override
            public void run() {
                PlayerDataFancy playerDataFancy = PlayerManagerCore.player_Data_Map.get(player.getUniqueId());
                ItemStack mainItem = player.getInventory().getItemInMainHand();

                playerDataFancy.removeEqmAction("Main");
                playerDataFancy.addEqmAction("Main", mainItem);

                playerDataFancy.removeCustomValue("Main");
                playerDataFancy.addEqmCustomValue("Main", mainItem);
            }
        }.runTaskLater(FancyItems.fancyItems, 1);

    }

}
