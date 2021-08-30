package com.daxton.fancyitmes.gui;

import com.daxton.fancycore.api.gui.GUI;
import com.daxton.fancycore.api.gui.button.GuiButton;
import com.daxton.fancycore.api.gui.item.GuiItem;
import com.daxton.fancyitmes.FancyItems;
import com.daxton.fancyitmes.config.FileConfig;
import com.daxton.fancyitmes.gui.button.typelist.ItemType;
import com.daxton.fancyitmes.gui.button.typelist.TypeNextPage;
import com.daxton.fancyitmes.gui.button.typelist.TypePreviousPage;
import com.daxton.fancyitmes.gui.button.top.CloseMenu;
import com.daxton.fancyitmes.gui.button.top.ToMain;
import com.daxton.fancyitmes.manager.ManagerItems;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.daxton.fancyitmes.config.FileConfig.languageConfig;

public class MainMenu {
    //打開物品編輯器
    public static void open(Player player){
        UUID uuid = player.getUniqueId();
        if(ManagerItems.gui_Map.get(uuid) == null){
            GUI gui = GUI.GUIBuilder.getInstance().setPlayer(player).setSize(54).setTitle(languageConfig.getString("Title")).build();
            ManagerItems.type_Page.put(uuid, 0);
            ManagerItems.player_Chat_Select.put(uuid, "");
            //主選單
            GuiButton mainButton = GuiButton.ButtonBuilder.getInstance().
                setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.ToMain")).
                setGuiAction(new ToMain(player)).build();
            gui.setButton(mainButton, 1, 1);
            //關閉
            GuiButton closeButton = GuiButton.ButtonBuilder.getInstance().
                setItemStack(GuiItem.valueOf(player, languageConfig,"Gui.Close")).
                setGuiAction(new CloseMenu(gui)).build();
            gui.setButton(closeButton, 1, 9);
            //物品類別列表
            setItemType(player, gui);

            gui.setMove(false);
            ManagerItems.gui_Map.put(uuid, gui);
            gui.open(gui);
        }else {
            GUI gui = ManagerItems.gui_Map.get(uuid);
            gui.removeButton(1, 5);
            gui.clearButtonFrom(10, 54);
            //物品類別列表
            setItemType(player, gui);

            gui.open(gui);
        }

    }
    //物品類別列表
    public static void setItemType(Player player,  GUI gui){

        UUID uuid = player.getUniqueId();
        FileConfiguration itemMenuConfig = FileConfig.config_Map.get("ItemMenu.yml");

        Integer[] integers = new Integer[]{18, 19, 27, 28, 36, 37};

        List<String> typeStringList = new ArrayList<>(itemMenuConfig.getConfigurationSection("ItemType").getKeys(false));
        int page = ManagerItems.type_Page.get(uuid);
        for(int i = page*28; i < typeStringList.size() ; i++){

            String typeString = typeStringList.get(i);



            FileConfiguration itemConfig = FileConfig.config_Map.get("item/"+ typeString +".yml");
            int itemAmount = itemConfig.getConfigurationSection("").getKeys(false).size();

            ItemStack itemStack = GuiItem.valueOf(player, itemMenuConfig, "ItemType."+typeString);
            List<String> loreList = new ArrayList<>();
            if(itemStack.getItemMeta().getLore() != null){
                for(String s : itemStack.getItemMeta().getLore()){
                    s = s.replace("{amount}", itemAmount+"");
                    loreList.add(s);
                }
            }
            if(itemAmount < 1){
                itemAmount = 1;
            }
            itemStack.setLore(loreList);
            itemStack.setAmount(itemAmount);

            GuiButton typeButton = GuiButton.ButtonBuilder.getInstance().
                setItemStack(itemStack).
                setGuiAction(new ItemType(player, gui, typeString)).build();
            gui.addButton(typeButton, 11, 44, integers);


        }

        if(page != 0){
            GuiButton previousPageButton = GuiButton.ButtonBuilder.getInstance().
                setItemStack(GuiItem.valueOf(languageConfig,"Gui.PreviousPage")).
                setGuiAction(new TypePreviousPage(player, gui)).build();
            gui.setButton(previousPageButton, 6, 1);
        }

        if((page+1)*28 < typeStringList.size()){
            GuiButton typeButton = GuiButton.ButtonBuilder.getInstance().
                setItemStack(GuiItem.valueOf(languageConfig,"Gui.NextPage")).
                setGuiAction(new TypeNextPage(player, gui)).build();
            gui.setButton(typeButton, 6, 9);
        }

    }



}
