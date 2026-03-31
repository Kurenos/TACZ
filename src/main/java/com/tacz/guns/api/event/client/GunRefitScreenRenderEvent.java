package com.tacz.guns.api.event.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.eventbus.api.Event;

public class GunRefitScreenRenderEvent extends Event {

    public GuiGraphics guiGraphics;
    public float partialTick;

    private GunRefitScreenRenderEvent(GuiGraphics guiGraphics, float partialTick) {
        this.guiGraphics = guiGraphics;
        this.partialTick = partialTick;
    }

    public static class Pre extends GunRefitScreenRenderEvent {
        public Pre(GuiGraphics guiGraphics, float partialTick) {
            super(guiGraphics, partialTick);
        }
    }

    public static class Post extends GunRefitScreenRenderEvent {
        public Post(GuiGraphics guiGraphics, float partialTick) {
            super(guiGraphics, partialTick);
        }
    }
}
