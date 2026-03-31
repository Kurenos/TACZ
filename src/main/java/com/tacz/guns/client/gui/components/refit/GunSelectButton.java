package com.tacz.guns.client.gui.components.refit;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tacz.guns.client.gui.GunRefitScreen;
import com.tacz.guns.client.gui.components.FlatColorButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public class GunSelectButton extends FlatColorButton {
    public GunSelectButton(int pX, int pY, int pWidth, int pHeight, int gunInvIndex, OnPress pOnPress) {
        super(pX, pY, pWidth, pHeight, Component.empty(), pOnPress);
        this.gunInvIndex = gunInvIndex;
    }

    public final int gunInvIndex;

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float pPartialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        int fillColor = new Color(69, 59, 84, 130).getRGB();
        int borderColor = new Color(34, 30, 41, 180).getRGB();
        graphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height, fillColor);

        if (this.isHoveredOrFocused() || player.getInventory().selected == gunInvIndex) {
            graphics.fill(this.getX(), this.getY() + 1, this.getX() + 1, this.getY() + this.height - 1, borderColor);
            graphics.fill(this.getX(), this.getY(), this.getX() + this.width, this.getY() + 1, borderColor);
            graphics.fill(this.getX() + this.width - 1, this.getY() + 1, this.getX() + this.width, this.getY() + this.height - 1, borderColor);
            graphics.fill(this.getX(), this.getY() + this.height - 1, this.getX() + this.width, this.getY() + this.height, borderColor);
        }

        this.renderScrollingString(graphics, font, 2, 0xF3EFE0);
        this.renderToolTip(graphics, minecraft.screen, mouseX, mouseY);



        ItemStack gun = player.getInventory().getItem(gunInvIndex);
        if (gun.isEmpty()) return;

        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        float scale = (width - 2) / 16f;
        graphics.pose().pushPose();
        graphics.pose().scale(scale, scale, 1);
        graphics.renderItem(gun, Math.round((getX() + 1) / scale), Math.round((getY() + 1) / scale));
        graphics.pose().popPose();

        RenderSystem.enableDepthTest();
        RenderSystem.disableBlend();
    }
}
