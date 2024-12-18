package com.hbm.items.weapon.sedna.factory;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import com.hbm.items.ModItems;
import com.hbm.items.weapon.sedna.BulletConfig;
import com.hbm.items.weapon.sedna.Crosshair;
import com.hbm.items.weapon.sedna.GunConfig;
import com.hbm.items.weapon.sedna.ItemGunBaseNT;
import com.hbm.items.weapon.sedna.Receiver;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.LambdaContext;
import com.hbm.items.weapon.sedna.ItemGunBaseNT.WeaponQuality;
import com.hbm.items.weapon.sedna.factory.GunFactory.EnumAmmo;
import com.hbm.items.weapon.sedna.mags.MagazineBelt;
import com.hbm.items.weapon.sedna.mags.MagazineFullReload;
import com.hbm.main.MainRegistry;
import com.hbm.particle.SpentCasing;
import com.hbm.particle.SpentCasing.CasingType;
import com.hbm.render.anim.BusAnimation;
import com.hbm.render.anim.BusAnimationSequence;
import com.hbm.render.anim.BusAnimationKeyframe.IType;
import com.hbm.render.anim.HbmAnimations.AnimType;
import com.hbm.util.DamageResistanceHandler.DamageClass;

import net.minecraft.item.ItemStack;

public class XFactory762mm {

	public static BulletConfig r762_sp;
	public static BulletConfig r762_fmj;
	public static BulletConfig r762_jhp;
	public static BulletConfig r762_ap;
	public static BulletConfig r762_du;

	public static BulletConfig energy_lacunae;
	public static BulletConfig energy_lacunae_overcharge;
	public static BulletConfig energy_lacunae_ir;

	public static void init() {
		SpentCasing casing762 = new SpentCasing(CasingType.BOTTLENECK).setColor(SpentCasing.COLOR_CASE_BRASS);
		r762_sp = new BulletConfig().setItem(EnumAmmo.R762_SP)
				.setCasing(casing762.clone().register("r762"));
		r762_fmj = new BulletConfig().setItem(EnumAmmo.R762_FMJ).setDamage(0.8F).setThresholdNegation(5F).setArmorPiercing(0.1F)
				.setCasing(casing762.clone().register("r762fmj"));
		r762_jhp = new BulletConfig().setItem(EnumAmmo.R762_JHP).setDamage(1.5F).setArmorPiercing(-0.25F)
				.setCasing(casing762.clone().register("r762jhp"));
		r762_ap = new BulletConfig().setItem(EnumAmmo.R762_AP).setDoesPenetrate(true).setDamageFalloutByPen(false).setDamage(1.5F).setThresholdNegation(12.5F).setArmorPiercing(0.15F)
				.setCasing(casing762.clone().setColor(SpentCasing.COLOR_CASE_44).register("r762ap"));
		r762_du = new BulletConfig().setItem(EnumAmmo.R762_DU).setDoesPenetrate(true).setDamageFalloutByPen(false).setDamage(2.5F).setThresholdNegation(15F).setArmorPiercing(0.25F)
				.setCasing(casing762.clone().setColor(SpentCasing.COLOR_CASE_44).register("r762du"));
		
		energy_lacunae = new BulletConfig().setItem(EnumAmmo.CAPACITOR).setupDamageClass(DamageClass.LASER).setBeam().setReloadCount(40).setSpread(0.0F).setLife(5).setRenderRotations(false).setOnBeamImpact(BulletConfig.LAMBDA_STANDARD_BEAM_HIT);
		energy_lacunae_overcharge = new BulletConfig().setItem(EnumAmmo.CAPACITOR_OVERCHARGE).setupDamageClass(DamageClass.LASER).setBeam().setReloadCount(40).setSpread(0.0F).setLife(5).setRenderRotations(false).setDoesPenetrate(true).setOnBeamImpact(BulletConfig.LAMBDA_STANDARD_BEAM_HIT);
		energy_lacunae_ir = new BulletConfig().setItem(EnumAmmo.CAPACITOR_IR).setupDamageClass(DamageClass.FIRE).setBeam().setReloadCount(40).setSpread(0.0F).setLife(5).setRenderRotations(false).setOnBeamImpact(XFactoryEnergy.LAMBDA_IR_HIT);

		ModItems.gun_carbine = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(3_000).draw(10).inspect(31).reloadSequential(true).crosshair(Crosshair.CIRCLE).smoke(LAMBDA_SMOKE)
				.rec(new Receiver(0)
						.dmg(15F).delay(5).dry(15).spread(0.0F).reload(30, 0, 15, 0).jam(60).sound("hbm:weapon.fire.blackPowder", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 14).addConfigs(r762_sp, r762_fmj, r762_jhp, r762_ap, r762_du))
						.offset(1, -0.0625 * 2.5, -0.25D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_CARBINE))
				.setupStandardConfiguration()
				.anim(LAMBDA_CARBINE_ANIMS).orchestra(Orchestras.ORCHESTRA_CARBIBE)
				).setUnlocalizedName("gun_carbine");

		ModItems.gun_minigun = new ItemGunBaseNT(WeaponQuality.A_SIDE, new GunConfig()
				.dura(50_000).draw(20).inspect(20).crosshair(Crosshair.L_CIRCLE).smoke(LAMBDA_SMOKE)
				.rec(new Receiver(0)
						.dmg(6F).delay(1).auto(true).dry(15).spread(0.01F).sound("hbm:weapon.fire.blackPowder", 1.0F, 1.0F)
						.mag(new MagazineBelt().addConfigs(r762_sp, r762_fmj, r762_jhp, r762_ap, r762_du))
						.offset(1, -0.0625 * 2.5, -0.25D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_MINIGUN))
				.setupStandardConfiguration()
				.anim(LAMBDA_MINIGUN_ANIMS).orchestra(Orchestras.ORCHESTRA_MINIGUN)
				).setUnlocalizedName("gun_minigun");
		ModItems.gun_minigun_lacunae = new ItemGunBaseNT(WeaponQuality.LEGENDARY, new GunConfig()
				.dura(50_000).draw(20).inspect(20).crosshair(Crosshair.L_CIRCLE)
				.rec(new Receiver(0)
						.dmg(12F).delay(1).auto(true).dry(15).reload(15).spread(0.01F).sound("hbm:weapon.fire.laserGatling", 1.0F, 1.0F)
						.mag(new MagazineFullReload(0, 200).addConfigs(energy_lacunae, energy_lacunae_overcharge, energy_lacunae_ir))
						.offset(1, -0.0625 * 2.5, -0.25D)
						.setupStandardFire().recoil(LAMBDA_RECOIL_LACUNAE))
				.setupStandardConfiguration()
				.anim(LAMBDA_MINIGUN_ANIMS).orchestra(Orchestras.ORCHESTRA_MINIGUN)
				).setUnlocalizedName("gun_minigun_lacunae");
	}
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_SMOKE = (stack, ctx) -> {
		Lego.handleStandardSmoke(ctx.entity, stack, 1500, 0.075D, 1.1D, 0);
	};
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_CARBINE = (stack, ctx) -> {
		ItemGunBaseNT.setupRecoil(5, (float) (ctx.getPlayer().getRNG().nextGaussian() * 0.5));
	};
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_MINIGUN = (stack, ctx) -> {
		ItemGunBaseNT.setupRecoil((float) (ctx.getPlayer().getRNG().nextGaussian() * 0.5), (float) (ctx.getPlayer().getRNG().nextGaussian() * 0.5));
	};
	
	public static BiConsumer<ItemStack, LambdaContext> LAMBDA_RECOIL_LACUNAE = (stack, ctx) -> { };

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_CARBINE_ANIMS = (stack, type) -> {
		boolean empty = ((ItemGunBaseNT) stack.getItem()).getConfig(stack, 0).getReceivers(stack)[0].getMagazine(stack).getAmount(stack, MainRegistry.proxy.me().inventory) <= 0;
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(45, 0, 0, 0).addPos(0, 0, 0, 500, IType.SIN_FULL));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, ItemGunBaseNT.getIsAiming(stack) ? -0.25 : -0.5, 50, IType.SIN_DOWN).addPos(0, 0, 0, 100, IType.SIN_FULL))
				.addBus("SLIDE", new BusAnimationSequence().addPos(0, 0, -1, 50, IType.SIN_DOWN).addPos(0, 0, 0, 100, IType.SIN_UP))
				.addBus(empty ? "NULL" : "REL", new BusAnimationSequence().addPos(0, 0, 0.25, 50).addPos(0, 0.125, 1.25, 100, IType.SIN_UP));
		case CYCLE_DRY: return new BusAnimation()
				.addBus("SLIDE", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(0, 0, -1, 100, IType.SIN_DOWN).addPos(0, 0, -1, 50).addPos(0, 0, 0, 100, IType.SIN_UP));
		case RELOAD: return new BusAnimation()
				.addBus("MAG", new BusAnimationSequence().addPos(0, -4, 0, 250, IType.SIN_UP).addPos(0, -4, 0, 750).addPos(0, 0, 0, 500, IType.SIN_DOWN))
				.addBus("LIFT", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(-25, 0, 0, 250, IType.SIN_FULL).addPos(-25, 0, 0, 1000))
				.addBus("BULLET", new BusAnimationSequence().addPos(empty ? 1 : 0, 0, 0, 0).addPos(0, 0, 0, 1000));
		case RELOAD_END: return new BusAnimation()
				.addBus("LIFT", new BusAnimationSequence().addPos(-25, 0, 0, 0).addPos(-25, 0, 0, 750).addPos(0, 0, 0, 500, IType.SIN_FULL))
				.addBus("SLIDE", new BusAnimationSequence().addPos(0, 0, 0, 250).addPos(0, 0, -1, 100, IType.SIN_DOWN).addPos(0, 0, -1, 50).addPos(0, 0, 0, 100, IType.SIN_UP))
				.addBus("REL", new BusAnimationSequence().addPos(0, 0, 0, 250).addPos(0, 0, 0.25, 150).addPos(0, 0.125, 1.25, 100, IType.SIN_UP));
		case JAMMED: return new BusAnimation()
				.addBus("LIFT", new BusAnimationSequence().addPos(-25, 0, 0, 0).addPos(-25, 0, 0, 750).addPos(0, 0, 0, 500, IType.SIN_FULL).addPos(0, 0, 0, 250).addPos(-25, 0, 0, 250, IType.SIN_FULL).addPos(-25, 0, 0, 750).addPos(0, 0, 0, 500, IType.SIN_FULL))
				.addBus("SLIDE", new BusAnimationSequence().addPos(0, 0, 0, 250).addPos(0, 0, -1, 100, IType.SIN_DOWN).addPos(0, 0, -1, 50).addPos(0, 0, -0.25, 100, IType.SIN_UP).addPos(0, 0, -0.25, 1250).addPos(0, 0, -1, 100, IType.SIN_DOWN).addPos(0, 0, -1, 50).addPos(0, 0, 0, 100, IType.SIN_UP))
				.addBus("REL", new BusAnimationSequence().addPos(0, 0, 0, 250).addPos(0, 0, 0.25, 150).addPos(0, 0.125, 1, 100, IType.SIN_UP).addPos(0, 0.125, 1, 1250).addPos(0, 0.125, 0.25, 100, IType.SIN_DOWN).addPos(0, 0.125, 1, 100, IType.SIN_UP));
		case INSPECT: return new BusAnimation()
				.addBus("LIFT", new BusAnimationSequence().addPos(-25, 0, 0, 250, IType.SIN_FULL).addPos(-25, 0, 0, 1500).addPos(0, 0, 0, 500, IType.SIN_FULL))
				.addBus("SLIDE", new BusAnimationSequence().addPos(0, 0, 0, 500).addPos(0, 0, -0.75, 150, IType.SIN_DOWN).addPos(0, 0, -0.75, 1000).addPos(0, 0, 0, 100, IType.SIN_UP))
				.addBus(empty ? "NULL" : "REL", new BusAnimationSequence().addPos(0, 0.125, 1.25, 0).addPos(0, 0.125, 1.25, 500).addPos(0, 0.125, 0.5, 150, IType.SIN_DOWN).addPos(0, 0.125, 0.5, 1000).addPos(0, 0.125, 1.25, 100, IType.SIN_UP));
		}
		
		return null;
	};

	@SuppressWarnings("incomplete-switch") public static BiFunction<ItemStack, AnimType, BusAnimation> LAMBDA_MINIGUN_ANIMS = (stack, type) -> {
		switch(type) {
		case EQUIP: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(45, 0, 0, 0).addPos(0, 0, 0, 1000, IType.SIN_FULL));
		case CYCLE: return new BusAnimation()
				.addBus("RECOIL", new BusAnimationSequence().addPos(0, 0, ItemGunBaseNT.getIsAiming(stack) ? -0.25 : -0.5, 0).addPos(0, 0, ItemGunBaseNT.getIsAiming(stack) ? -0.25 : -0.5, 100).addPos(0, 0, 0, 150, IType.SIN_FULL))
				.addBus("ROTATE", new BusAnimationSequence().addPos(0, 0, 60, 50).addPos(0, 0, 720, 1000, IType.SIN_DOWN));
		case CYCLE_DRY: return new BusAnimation()
				.addBus("ROTATE", new BusAnimationSequence().addPos(0, 0, 60, 50).addPos(0, 0, 720, 1000, IType.SIN_DOWN));
		case RELOAD: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(-15, 0, 0, 250, IType.SIN_DOWN).addPos(0, 0, 0, 500, IType.SIN_FULL))
				.addBus("ROTATE", new BusAnimationSequence().addPos(0, 0, 60, 50).addPos(0, 0, 720, 1000, IType.SIN_DOWN));
		case INSPECT: return new BusAnimation()
				.addBus("EQUIP", new BusAnimationSequence().addPos(3, 0, 0, 150, IType.SIN_DOWN).addPos(0, 0, 0, 100, IType.SIN_FULL))
				.addBus("ROTATE", new BusAnimationSequence().addPos(0, 0, -720, 1000, IType.SIN_DOWN));
		}
		
		return null;
	};
}
