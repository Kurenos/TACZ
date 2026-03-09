package com.tacz.guns.api.item.builder;

import com.tacz.guns.api.DefaultAssets;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.api.item.attachment.AttachmentItemManager;
import com.tacz.guns.api.item.gun.AbstractGunItem;
import com.tacz.guns.api.item.gun.GunItemManager;
import com.tacz.guns.init.ModItems;
import com.tacz.guns.item.AttachmentItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

public class AttachmentItemBuilder {
    private int count = 1;
    private ResourceLocation attachmentId = DefaultAssets.DEFAULT_ATTACHMENT_ID;

    private AttachmentItemBuilder() {
    }

    public static AttachmentItemBuilder create() {
        return new AttachmentItemBuilder();
    }

    public AttachmentItemBuilder setCount(int count) {
        this.count = Math.max(count, 1);
        return this;
    }

    public AttachmentItemBuilder setId(ResourceLocation id) {
        this.attachmentId = id;
        return this;
    }

    @Deprecated
    public AttachmentItemBuilder setSkinId(ResourceLocation skinId) {
        return this;
    }

    public ItemStack build() {
        String itemType = TimelessAPI.getCommonAttachmentIndex(attachmentId).map(index -> index.getPojo().getItemType()).orElse(null);
        if (itemType == null) {
            return ItemStack.EMPTY;
        }

        RegistryObject<? extends AttachmentItem> attachmentItemRegistryObject = AttachmentItemManager.getAttachmentItemRegistryObject(itemType);
        if (attachmentItemRegistryObject == null) {
            return ItemStack.EMPTY;
        }

        ItemStack attachment = new ItemStack(attachmentItemRegistryObject.get(), this.count);
        if (attachment.getItem() instanceof IAttachment iAttachment) {
            iAttachment.setAttachmentId(attachment, this.attachmentId);
        }
        return attachment;
    }
}
