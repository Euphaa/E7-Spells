package com.e7.spells;

import com.e7.spells.util.CCAComponents;
import com.e7.spells.util.PlayerNbtComponent;
import com.mojang.blaze3d.systems.RenderSystem;
import me.x150.renderer.render.Renderer2d;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.WindowSettings;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ManaOverlay
{
//    private static final Identifier BARS_CLASSIC = new Identifier(E7SpellsCommon.MODID, "textures/bars_classic.png");
    private static final Identifier MANA_BAR_FILL = new Identifier(E7SpellsCommon.MODID, "textures/gui/mana_bar_fill.png");
    private static final Identifier MANA_BAR_OVERLAY = new Identifier(E7SpellsCommon.MODID, "textures/gui/mana_bar_overlay.png");

    public static void renderMana(DrawContext context, PlayerEntity player)
    {
        PlayerNbtComponent nbt = CCAComponents.PLAYER_NBT.get(player);
        int mana = nbt.getMana();
        int maxMana = nbt.getMax_mana();
        MinecraftClient client = MinecraftClient.getInstance();
        int width = client.getWindow().getWidth();
        int height = client.getWindow().getHeight();

        int guiScale = MinecraftClient.getInstance().options.getGuiScale().getValue();
        if (guiScale == 0) guiScale = 5;
        int x = width / 2 / guiScale + 10;
        int y = height / guiScale - 47;
        int manaFilled = (81 * mana/maxMana);

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, MANA_BAR_FILL);
        Renderer2d.renderTexture(
                context.getMatrices(),
                x,
                y,
                manaFilled,
                5
        );

        RenderSystem.setShaderTexture(0, MANA_BAR_OVERLAY);
        Renderer2d.renderTexture(
                context.getMatrices(),
                x,
                y,
                81,
                5
        );


    }
}
