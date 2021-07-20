package com.daxton.fancyitmes.item;

import com.daxton.fancyitmes.FancyItems;
import com.daxton.fancyitmes.config.FileConfig;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemProject {

    //設定物品的ID
    public static void setID(FileConfiguration itemConfig, String patch, String itemID, ItemStack newItemStack){
        ItemMeta itemMeta = newItemStack.getItemMeta();

        FancyItems fancyItems = FancyItems.fancyItems;

        PersistentDataContainer data = itemMeta.getPersistentDataContainer();
        NamespacedKey xd = new NamespacedKey(fancyItems, "itemID");
        data.set(xd , PersistentDataType.STRING, patch+"."+itemID);

        newItemStack.setItemMeta(itemMeta);
    }

    //設定物品自訂屬性
    public static void setCustomAttrs(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        List<String> itemCustomAttrs = itemConfig.getStringList(itemID+".CustomAttrs");
        ItemMeta itemMeta = newItemStack.getItemMeta();
        List<String> itemLore = new ArrayList<>();
        if(itemMeta.getLore() != null){
            itemLore = itemMeta.getLore();
        }
        FancyItems fancyItems = FancyItems.fancyItems;
        for(String s : itemCustomAttrs){
            String[] strings = s.split(":");
            if(strings.length == 2){

                if(FileConfig.config_Map.get("CustomAttributes.yml") != null){
                    FileConfiguration caConfig = FileConfig.config_Map.get("CustomAttributes.yml");
                    if(caConfig.getString("CustomAttributes."+strings[0].trim()) != null){
                        String ll = strings[0].replace(strings[0].trim(),caConfig.getString("CustomAttributes."+strings[0].trim()));
                        s = ll+":"+strings[1];
                    }
                }
                itemLore.add(s);


                String left = strings[0].trim();
                String right = strings[1].trim();
                try {
                    PersistentDataContainer data = itemMeta.getPersistentDataContainer();
                    NamespacedKey xd = new NamespacedKey(fancyItems, left);
                    data.set(xd , PersistentDataType.STRING, right);
                }catch (Exception exception){
                    //exception.printStackTrace();
                }

            }
        }
        itemMeta.setLore(itemLore);

        newItemStack.setItemMeta(itemMeta);
    }
    //設定物品禁止攻擊
    public static void setDisableAttack(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        ItemMeta itemMeta = newItemStack.getItemMeta();

        FancyItems fancyItems = FancyItems.fancyItems;

        boolean disableAttack = itemConfig.getBoolean(itemID+".DisableAttack");
        if(disableAttack){
            PersistentDataContainer data = itemMeta.getPersistentDataContainer();
            NamespacedKey xd = new NamespacedKey(fancyItems, "DisableAttack");
            data.set(xd , PersistentDataType.STRING, "true");
        }

        newItemStack.setItemMeta(itemMeta);
    }
    //設定物品的左鍵CD
    public static void setCoolDownLeftClick(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        ItemMeta itemMeta = newItemStack.getItemMeta();

        FancyItems fancyItems = FancyItems.fancyItems;

        String coolDownString = itemConfig.getString(itemID+".CoolDown.LeftClick");
        PersistentDataContainer data = itemMeta.getPersistentDataContainer();
        NamespacedKey xd = new NamespacedKey(fancyItems, "CoolDownLeftClick");
        data.set(xd , PersistentDataType.STRING, coolDownString);

        newItemStack.setItemMeta(itemMeta);
    }
    //設定物品的右鍵CD
    public static void setCoolDownRightClick(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        ItemMeta itemMeta = newItemStack.getItemMeta();

        FancyItems fancyItems = FancyItems.fancyItems;

        String coolDownString = itemConfig.getString(itemID+".CoolDown.RightClick");
        PersistentDataContainer data = itemMeta.getPersistentDataContainer();
        NamespacedKey xd = new NamespacedKey(fancyItems, "CoolDownRightClick");
        data.set(xd , PersistentDataType.STRING, coolDownString);

        newItemStack.setItemMeta(itemMeta);
    }

    //設定物的頭值
    public static void setHeadValue(LivingEntity self, LivingEntity target, FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        FancyItems fancyItems = FancyItems.fancyItems;

        Material material = newItemStack.getType();

        if(material == Material.PLAYER_HEAD){
            SkullMeta skullMeta = (SkullMeta) newItemStack.getItemMeta();
            String headValue = itemConfig.getString(itemID+".HeadValue");
            if(headValue != null){
                OfflinePlayer targetPlayer = fancyItems.getServer().getOfflinePlayer(headValue);
                skullMeta.setOwningPlayer(targetPlayer);
                newItemStack.setItemMeta(skullMeta);
//                if(headValue.length() < 50){
//                    OfflinePlayer targetPlayer = fancyItems.getServer().getOfflinePlayer(headValue);
//                    skullMeta.setOwningPlayer(targetPlayer);
//                    newItemStack.setItemMeta(skullMeta);
//                }else {
//                    try {
//                        PlayerProfile playerProfile = Bukkit.createProfile(UUID.randomUUID(), null);
//                        playerProfile.getProperties().add(new ProfileProperty("textures", headValue));
//                        skullMeta.setPlayerProfile(playerProfile);
//                        newItemStack.setItemMeta(skullMeta);
//                    }catch (Exception exception){
//                        fancyItems.getLogger().info("頭的值只能在paper伺服器使用。");
//                        fancyItems.getLogger().info("The value of the header can only be used on the paper server.");
//                    }
            }

        }
    }

    //設定物品的動作
    public static void setAction(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        ItemMeta itemMeta = newItemStack.getItemMeta();

        FancyItems fancyItems = FancyItems.fancyItems;

        List<String> itemLore = new ArrayList<>();
        if(itemMeta.getLore() != null){
            itemLore = itemMeta.getLore();
        }


        List<String> actionList = itemConfig.getStringList(itemID+".Action");
        if(!actionList.isEmpty()){
            PersistentDataContainer data = itemMeta.getPersistentDataContainer();
            int i = 0;
            for(String action : actionList){
                i++;
                NamespacedKey xd = new NamespacedKey(fancyItems, "Action"+i);
                String[] actionArray = action.split(":");
                if(actionArray.length == 2){
                    data.set(xd , PersistentDataType.STRING, actionArray[1]);
                    if(!actionArray[0].equals("null")){
                        itemLore.add(actionArray[0]);
                        itemMeta.setLore(itemLore);
                    }
                }
            }
        }

        newItemStack.setItemMeta(itemMeta);
    }

    //設定物品隱藏屬性
    public static void setItemFlagsHideAttributes(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        boolean itemFlagsHideAttributes = itemConfig.getBoolean(itemID+".ItemFlags.HideAttributes");
        if(itemFlagsHideAttributes){
            ItemMeta itemMeta = newItemStack.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            newItemStack.setItemMeta(itemMeta);
        }
    }
    //設定物品隱藏可破壞的方塊
    public static void setItemFlagsHideDestroys(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        boolean itemFlagsHideDestroys = itemConfig.getBoolean(itemID+".ItemFlags.HideDestroys");
        if(itemFlagsHideDestroys){
            ItemMeta itemMeta = newItemStack.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
            newItemStack.setItemMeta(itemMeta);
        }
    }
    //設定物品隱藏染料
    public static void setItemFlagsHideDye(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        boolean itemFlagsHideDye = itemConfig.getBoolean(itemID+".ItemFlags.HideDye");
        if(itemFlagsHideDye){
            ItemMeta itemMeta = newItemStack.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_DYE);
            newItemStack.setItemMeta(itemMeta);
        }
    }
    //設定物品隱藏附魔
    public static void setItemFlagsHideEnchants(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        boolean itemFlagsHideEnchants = itemConfig.getBoolean(itemID+".ItemFlags.HideEnchants");
        if(itemFlagsHideEnchants){
            ItemMeta itemMeta = newItemStack.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            newItemStack.setItemMeta(itemMeta);
        }
    }
    //設定物品隱藏可放置的方塊
    public static void setItemFlagsHidePlacedOn(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        boolean itemFlagsHidePlacedOn = itemConfig.getBoolean(itemID+".ItemFlags.HidePlacedOn");
        if(itemFlagsHidePlacedOn){
            ItemMeta itemMeta = newItemStack.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
            newItemStack.setItemMeta(itemMeta);
        }
    }
    //設定物品隱藏藥水效果
    public static void setItemFlagsHidePotionEffects(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        boolean itemFlagsHidePotionEffects = itemConfig.getBoolean(itemID+".ItemFlags.HidePotionEffects");
        if(itemFlagsHidePotionEffects){
            ItemMeta itemMeta = newItemStack.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            newItemStack.setItemMeta(itemMeta);
        }
    }
    //設定物品隱藏無法破壞
    public static void setItemFlagsHideUnbreakable(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        boolean itemFlagsHideUnbreakable = itemConfig.getBoolean(itemID+".ItemFlags.HideUnbreakable");
        if(itemFlagsHideUnbreakable){
            ItemMeta itemMeta = newItemStack.getItemMeta();
            itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            newItemStack.setItemMeta(itemMeta);
        }
    }
    //設定物品不會損壞
    public static void setUnbreakable(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        boolean itemUnbreakable = itemConfig.getBoolean(itemID+".Unbreakable");
        ItemMeta itemMeta = newItemStack.getItemMeta();
        itemMeta.setUnbreakable(itemUnbreakable);
        newItemStack.setItemMeta(itemMeta);
    }
    //設定物品原生屬性
    public static void setAttributes(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        ItemMeta itemMeta = newItemStack.getItemMeta();

        List<String> attrList = itemConfig.getStringList(itemID+".Attributes");
        attrList.forEach(s -> {
            String[] strings = s.split(":");
            if(strings.length == 4){
                String equipmentSlot = strings[0];
                String inherit = strings[1];
                String operation = strings[2];
                double attrAmount = Double.parseDouble(strings[3]);
                try {
                    if(inherit != null && operation != null && attrAmount != 0 && equipmentSlot != null){
                        if(equipmentSlot.toLowerCase().contains("all")) {
                            itemMeta.addAttributeModifier(Enum.valueOf(Attribute.class,inherit.toUpperCase()),new AttributeModifier(UUID.randomUUID(), String.valueOf(UUID.randomUUID()), attrAmount, Enum.valueOf(AttributeModifier.Operation.class,operation)));
                        }else {
                            itemMeta.addAttributeModifier(Enum.valueOf(Attribute.class,inherit.toUpperCase()),new AttributeModifier(UUID.randomUUID(), String.valueOf(UUID.randomUUID()), attrAmount, Enum.valueOf(AttributeModifier.Operation.class,operation), Enum.valueOf(EquipmentSlot.class,equipmentSlot.toUpperCase())));
                        }
                    }
                }catch (Exception exception){
                    //
                }
            }
        });

        newItemStack.setItemMeta(itemMeta);
    }
    //設定物品附魔
    public static void setEnchantments(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        List<String> itemEnchantment = itemConfig.getStringList(itemID+".Enchantments");
        ItemMeta itemMeta = newItemStack.getItemMeta();
        itemEnchantment.forEach(s -> {
            String[] strings = s.split(":");
            if(strings.length == 2){
                try {
                    NamespacedKey key = NamespacedKey.minecraft(strings[0]);
                    Enchantment enchant = Enchantment.getByKey(key);
                    itemMeta.addEnchant(enchant,Integer.parseInt(strings[1]),false);
                }catch (Exception exception){
                    //
                }

            }
        });
        newItemStack.setItemMeta(itemMeta);
    }
    //設定物品CustomModelData
    public static void setCustomModelData(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        int itemCustomModelData = itemConfig.getInt(itemID+".CustomModelData");
        ItemMeta itemMeta = newItemStack.getItemMeta();
        itemMeta.setCustomModelData(itemCustomModelData);
        newItemStack.setItemMeta(itemMeta);
    }
    //設定物品損壞Data
    public static void setData(FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        int itemData = itemConfig.getInt(itemID+".Data");
        ItemMeta itemMeta = newItemStack.getItemMeta();
        ((Damageable) itemMeta).setDamage(itemData);
        newItemStack.setItemMeta(itemMeta);
    }
    //設定物品Lore
    public static void setLore(LivingEntity self, LivingEntity target, FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        List<String> itemDisplayName = itemConfig.getStringList(itemID+".Lore");
        ItemMeta itemMeta = newItemStack.getItemMeta();
        itemMeta.setLore(itemDisplayName);
        newItemStack.setItemMeta(itemMeta);
    }
    //設定物品顯示名稱
    public static void setDisplayName(LivingEntity self, LivingEntity target, FileConfiguration itemConfig, String itemID, ItemStack newItemStack){
        String itemDisplayName = itemConfig.getString(itemID+".DisplayName");
        ItemMeta itemMeta = newItemStack.getItemMeta();
        itemMeta.setDisplayName(itemDisplayName);
        newItemStack.setItemMeta(itemMeta);
    }

    //設定物品材質
    public static ItemStack setMaterial(FileConfiguration itemConfig, String itemID, int amount){
        ItemStack newItemStack;

        String itemMaterial = itemConfig.getString(itemID+".Material");
        try {
            Material material = Enum.valueOf(Material.class,itemMaterial.replace(" ","").toUpperCase());
            newItemStack = new ItemStack(material, amount);
        }catch (Exception exception){
            newItemStack = new ItemStack(Material.STONE, amount);
        }
        return newItemStack;
    }

}
