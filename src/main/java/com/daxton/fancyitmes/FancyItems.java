package com.daxton.fancyitmes;

import com.daxton.fancyitmes.command.MainCommand;
import com.daxton.fancyitmes.command.TabCommand;
import com.daxton.fancyitmes.listener.PlayerListener;
import com.daxton.fancyitmes.task.Start;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;


public class FancyItems extends JavaPlugin{

    public static FancyItems fancyItems;

    @Override
    public void onEnable() {
        fancyItems = this;
        //前置插件
        if(!DependPlugins.depend()){
            fancyItems.setEnabled(false);
            return;
        }
        //指令
        Objects.requireNonNull(Bukkit.getPluginCommand("fancyitems")).setExecutor(new MainCommand());
        Objects.requireNonNull(Bukkit.getPluginCommand("fancyitems")).setTabCompleter(new TabCommand());
        //監聽
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), fancyItems);
        Start.execute();

    }

    @Override
    public void onDisable() {
        FancyItems.fancyItems.getLogger().info("§4FancyItems uninstall.");
    }

}
