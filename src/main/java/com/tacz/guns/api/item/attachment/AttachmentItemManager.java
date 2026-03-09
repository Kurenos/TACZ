package com.tacz.guns.api.item.attachment;

import com.google.common.collect.Maps;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.item.AttachmentItem;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.Map;

public class AttachmentItemManager {
    private static final Map<String, RegistryObject<? extends AttachmentItem>> ATTACHMENT_ITEM_MAP = Maps.newHashMap();

    /**
     * 建议在 RegistryEvent.Register<Item> 事件时注册此枪械变种
     */
    public static void registerAttachmentItem(String name, RegistryObject<? extends AttachmentItem> registryObject) {
        ATTACHMENT_ITEM_MAP.put(name, registryObject);
    }

    public static RegistryObject<? extends AttachmentItem> getAttachmentItemRegistryObject(String key) {
        return ATTACHMENT_ITEM_MAP.get(key);
    }

    public static Collection<RegistryObject<? extends AttachmentItem>> getAllAttachmentItems() {
        return ATTACHMENT_ITEM_MAP.values();
    }
}
