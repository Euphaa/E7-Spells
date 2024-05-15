package com.e7.spells.entity.client;

import com.e7.spells.entity.custom.GoopEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

// Made with Blockbench 4.10.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class GoopModel <T extends GoopEntity> extends SinglePartEntityModel<T> {

	private final ModelPart Goop;
	private final ModelPart bone2;
	private final ModelPart bone;
	private final ModelPart bone3;
	public GoopModel(ModelPart root) {
		this.Goop = root.getChild("Goop");
		this.bone2 = root.getChild("bone2");
		this.bone = root.getChild("bone");
		this.bone3 = root.getChild("bone3");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData Goop = modelPartData.addChild("Goop", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData bone2 = Goop.addChild("bone2", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, -3.0F, -4.0F));

		ModelPartData cube_r1 = bone2.addChild("cube_r1", ModelPartBuilder.create().uv(24, 12).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 1.5708F));

		ModelPartData bone = Goop.addChild("bone", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, -4.0F, -6.0F));

		ModelPartData bone3 = Goop.addChild("bone3", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -5.0F, -1.0F, 9.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 0.0F, -4.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(GoopEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		Goop.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return Goop;
	}
}