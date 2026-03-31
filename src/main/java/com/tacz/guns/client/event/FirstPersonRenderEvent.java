package com.tacz.guns.client.event;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.platform.Window;
import com.tacz.guns.GunMod;
import com.tacz.guns.api.client.animation.statemachine.AnimationStateMachine;
import com.tacz.guns.api.client.other.KeepingItemRenderer;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.client.gui.GunRefitScreen;
import com.tacz.guns.client.renderer.item.AnimateGeoItemRenderer;
import com.tacz.guns.compat.oculus.OculusCompat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = GunMod.MOD_ID)
public class FirstPersonRenderEvent {
    private static AnimationStateMachine<?> lastStateMachine = null;


    public static TextureTarget tt;

    @SubscribeEvent
    public static void tickEvent(TickEvent.ClientTickEvent tickEvent){
        if (tickEvent.phase != TickEvent.Phase.START) return;
        if (tt != null){
            Window window = Minecraft.getInstance().getWindow();
            if (tt.width != window.getWidth() || tt.height != window.getHeight()){
                tt.resize(window.getWidth(), window.getHeight(), Minecraft.ON_OSX);
            }
        } else tt = new TextureTarget(1, 1, true, Minecraft.ON_OSX);

    }

    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent event) {

//        RenderTarget mainRenderTarget = Minecraft.getInstance().getMainRenderTarget();
//        boolean refit = Minecraft.getInstance().screen instanceof GunRefitScreen;
//        if (refit) {
//            tt.copyDepthFrom(mainRenderTarget);
//            tt.bindWrite(Minecraft.ON_OSX);
//        }
//        if (Minecraft.getInstance().screen instanceof GunRefitScreen) return;

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        if (event.getHand() == InteractionHand.OFF_HAND) {
            ItemStack stack = KeepingItemRenderer.getRenderer().getCurrentItem();
            if (stack.getItem() instanceof IGun) {
                event.setCanceled(true);
            }
            return;
        }
        // 事件事件给的是被延长渲染修改过后的物品，不是玩家实际手持的
        ItemStack stack = event.getItemStack();

        // 获取 TransformType
        ItemDisplayContext transformType;
        if (event.getHand() == InteractionHand.MAIN_HAND) {
            transformType = ItemDisplayContext.FIRST_PERSON_RIGHT_HAND;
        } else {
            transformType = ItemDisplayContext.FIRST_PERSON_LEFT_HAND;
        }

        // 渲染相关内容整理到物品的IClientItemExtensions了，这个接口有待进一步抽象
        if (IClientItemExtensions.of(stack.getItem()).getCustomRenderer() instanceof AnimateGeoItemRenderer<?, ?> renderer) {
            // 如果旧的状态机已经不再使用且未正常退出，使其静默退出
            AnimationStateMachine<?> machine = renderer.getStateMachine(stack);
            if (machine != lastStateMachine) {
                if (lastStateMachine != null && lastStateMachine.isInitialized()) {
                    lastStateMachine.exit();
                }
                lastStateMachine = machine;
            }
            // 物品处于后台时，阻止状态机初始化
            boolean flag = ItemStack.matches(player.getMainHandItem(), stack);
            if (flag && renderer.needReInit(stack)) {
                renderer.tryInit(stack, player, event.getPartialTick());
            }

			// 防止内存泄漏
			OculusCompat.endBatch(Minecraft.getInstance().renderBuffers().bufferSource());

            renderer.renderFirstPerson(player, stack, transformType, event.getPoseStack(), event.getMultiBufferSource(),
                    event.getPackedLight(), event.getPartialTick());
            event.setCanceled(true);
        }

//        if (refit) mainRenderTarget.bindWrite(Minecraft.ON_OSX);
    }
}
