package com.daxton.fancyitmes.command;

import com.daxton.fancycore.api.config.SearchConfig;
import com.daxton.fancycore.api.config.SearchConfigFile;
import com.daxton.fancycore.api.conversion.StringConversion;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.item.CustomItem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args){
        if(sender instanceof Player && !sender.isOp()){
            return true;
        }

        if(args[0].equalsIgnoreCase("give") && args.length == 5) {
            giveCommaned(args);
        }

        return true;
    }
    //給物品指令
    public static void giveCommaned(String[] args){
        String playerName = args[1];
        if(!TabCommand.getPlayerNameList().contains(args[1])){
            return;
        }
        String itemType = args[2];
        if(!SearchConfigFile.nameList(FileConfig.config_Map, "item/", true).contains(itemType)){
            return;
        }
        String itmeID = args[3];
        if(FileConfig.config_Map.get("item/"+itemType+".yml") != null){
            FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+args[2]+".yml");
            if(!SearchConfig.sectionList(itemConfig, "").contains(itmeID)){
                return;
            }
        }
        int amount = StringConversion.getInt(1 ,args[4]);
        Player player = TabCommand.getPlayerNameMap().get(playerName);
        ItemStack itemStack = CustomItem.valueOf2(itemType, itmeID, amount);
        player.getInventory().addItem(itemStack);
    }

}
