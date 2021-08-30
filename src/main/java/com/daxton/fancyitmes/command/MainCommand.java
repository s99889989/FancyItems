package com.daxton.fancyitmes.command;
import com.daxton.fancycore.api.config.SearchConfigFile;
import com.daxton.fancycore.api.config.SearchConfigMap;
import com.daxton.fancycore.api.character.conversion.StringConversion;
import com.daxton.fancyitmes.FancyItems;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.gui.MainMenu;
import com.daxton.fancyitmes.item.CustomItem;
import com.daxton.fancyitmes.task.Reload;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args){
        if(sender instanceof Player && !sender.isOp()){
            return true;
        }

        if(args.length == 5 && args[0].equalsIgnoreCase("give")) {
            giveCommaned(args);
        }

        if(args.length == 1) {

            if(args[0].equalsIgnoreCase("reload")){
                Reload.execute();
                if(sender instanceof Player){
                    Player player = (Player) sender;
                    player.sendMessage(languageConfig.getString("OpMessage.Reload")+"");
                }
                FancyItems.fancyItems.getLogger().info(languageConfig.getString("LogMessage.Reload"));
            }
            if(args[0].equalsIgnoreCase("reload")){

            }
        }

        if(args.length == 1 && args[0].equalsIgnoreCase("edit")) {
            if(sender instanceof Player){
                Player player = (Player) sender;
                MainMenu.open(player);
            }
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
        if(!SearchConfigMap.fileNameList(FileConfig.config_Map, "item/", true).contains(itemType)){
            return;
        }
        String itmeID = args[3];
        if(FileConfig.config_Map.get("item/"+itemType+".yml") != null){
            FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+args[2]+".yml");
            if(!SearchConfigFile.sectionList(itemConfig, "").contains(itmeID)){
                return;
            }
        }
        int amount = StringConversion.getInt(1 ,args[4]);
        Player player = TabCommand.getPlayerNameMap().get(playerName);
        ItemStack itemStack = CustomItem.valueOf(player, itemType, itmeID, amount);
        player.getInventory().addItem(itemStack);
    }

}
