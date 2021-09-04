package com.daxton.fancyitmes.manager;

import com.daxton.fancycore.api.gui.GUI;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ManagerItems {
    //GUI
    public static Map<UUID, GUI> gui_Map = new HashMap<>();
    //類型頁數
    public static Map<UUID, Integer> type_Page = new HashMap<>();
    //聊天觸發類型
    public static Map<UUID, String> player_Chat_Select = new HashMap<>();
    //物品編輯值
    public static Map<UUID, String> player_ItemEditString = new HashMap<>();
    //物品編輯值2
    public static Map<UUID, String[]> player_ItemEditArray = new HashMap<>();

}
