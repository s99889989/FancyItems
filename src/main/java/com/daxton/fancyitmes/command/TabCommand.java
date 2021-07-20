package com.daxton.fancyitmes.command;

import com.daxton.fancycore.api.config.SearchConfig;
import com.daxton.fancycore.api.config.SearchConfigFile;
import com.daxton.fancyitmes.config.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class TabCommand implements TabCompleter {

    private final String[] subCommands = {"give"};

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args){
        List<String> commandList = new ArrayList<>();

        if (args.length == 1){
            commandList = Arrays.stream(subCommands).filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
        }
        if (args.length == 2){
            commandList = getPlayerNameList().stream().filter(s -> s.startsWith(args[1])).collect(Collectors.toList());
        }
        if (args.length == 3){
            List<String> itmeTypeLst = SearchConfigFile.nameList(FileConfig.config_Map, "item/", true);
            commandList = itmeTypeLst.stream().filter(s -> s.startsWith(args[2])).collect(Collectors.toList());
        }
        if (args.length == 4){
            if(FileConfig.config_Map.get("item/"+args[2]+".yml") != null){
                FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+args[2]+".yml");
                commandList = SearchConfig.sectionList(itemConfig, "").stream().filter(s -> s.startsWith(args[3])).collect(Collectors.toList());
            }

        }
        if (args.length == 5){
            commandList = getAmount().stream().filter(s -> s.startsWith(args[4])).collect(Collectors.toList());
        }

        return commandList;
    }
    //數量1~64列表
    public static List<String> getAmount(){

        List<String> amountList = new ArrayList<>();
        for(int i = 1 ;i < 65 ; i++){
            amountList.add(String.valueOf(i));
        }

        return amountList;
    }

    //獲取線上玩家名稱List
    public static List<String> getPlayerNameList(){

        List<String> playerNameList = new ArrayList<>();

        Bukkit.getOnlinePlayers().forEach(player ->  playerNameList.add(player.getName()));

        return playerNameList;
    }

    //獲取線上玩家名稱Map
    public static Map<String, Player> getPlayerNameMap(){

        Map<String,Player> playerMap = new HashMap<>();
        Bukkit.getOnlinePlayers().forEach(player2 -> playerMap.put(player2.getName(),player2));

        return playerMap;
    }

}
