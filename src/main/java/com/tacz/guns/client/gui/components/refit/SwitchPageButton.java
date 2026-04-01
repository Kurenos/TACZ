package com.tacz.guns.client.gui.components.refit;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tacz.guns.GunMod;
import com.tacz.guns.client.gui.components.FlatColorButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public class SwitchPageButton extends FlatColorButton {
    public SwitchPageButton(int pX, int pY, int pWidth, int pHeight, ResourceLocation texture, OnPress pOnPress) {
        super(pX, pY, pWidth, pHeight, Component.empty(), pOnPress);
        this.rl = texture;
    }

    public final ResourceLocation rl;

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float pPartialTick) {

        graphics.blit(rl, this.getX(), this.getY(), 0, this.getTextureYOffset(this.isHovered()), this.width, this.height, this.width, this.height * 3);
    }

    protected int getTextureYOffset(boolean hovered)
    {
        return hovered ? this.height : 0;
    }
}
