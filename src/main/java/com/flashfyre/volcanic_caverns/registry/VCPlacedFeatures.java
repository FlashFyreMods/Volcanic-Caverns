package com.flashfyre.volcanic_caverns.registry;

import java.util.List;

import com.flashfyre.volcanic_caverns.VolcanicCaverns;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.BiasedToBottomHeight;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

public class VCPlacedFeatures {	
	public static final ResourceKey<PlacedFeature> LAVA_SPRING = createKey("lava_spring");
	public static final ResourceKey<PlacedFeature> DELTA = createKey("delta");
	public static final ResourceKey<PlacedFeature> BASALT_PILLAR = createKey("basalt_pillar");
	public static final ResourceKey<PlacedFeature> BASALT_SPIKE = createKey("basalt_spike");
	public static final ResourceKey<PlacedFeature> UNDERGROUND_MAGMA = createKey("underground_magma");
	public static final ResourceKey<PlacedFeature> UNDERGROUND_SMOOTH_BASALT = createKey("underground_smooth_basalt");
	public static final ResourceKey<PlacedFeature> EXTRA_COAL_ORE = createKey("extra_coal_ore");
	public static final ResourceKey<PlacedFeature> EXTRA_DIAMOND_ORE = createKey("extra_diamond_ore");
	
	public static void bootstrap(BootstrapContext<PlacedFeature> ctx) {
		HolderGetter<ConfiguredFeature<?, ?>> holdergetter = ctx.lookup(Registries.CONFIGURED_FEATURE);
		Holder<ConfiguredFeature<?, ?>> lavaSpring = holdergetter.getOrThrow(VCConfiguredFeatures.LAVA_SPRING);
		Holder<ConfiguredFeature<?, ?>> delta = holdergetter.getOrThrow(VCConfiguredFeatures.DELTA);
		Holder<ConfiguredFeature<?, ?>> basaltPillar = holdergetter.getOrThrow(VCConfiguredFeatures.BASALT_PILLAR);
		Holder<ConfiguredFeature<?, ?>> basaltSpike = holdergetter.getOrThrow(VCConfiguredFeatures.BASALT_SPIKE);
		Holder<ConfiguredFeature<?, ?>> undergroundMagma = holdergetter.getOrThrow(VCConfiguredFeatures.UNDERGROUND_MAGMA);
		Holder<ConfiguredFeature<?, ?>> undergroundSmoothBasalt = holdergetter.getOrThrow(VCConfiguredFeatures.UNDERGROUND_SMOOTH_BASALT);
		Holder<ConfiguredFeature<?, ?>> extraCoalOre = holdergetter.getOrThrow(VCConfiguredFeatures.EXTRA_COAL_ORE);
		Holder<ConfiguredFeature<?, ?>> extraDiamondOre = holdergetter.getOrThrow(VCConfiguredFeatures.EXTRA_DIAMOND_ORE);
		
		PlacementUtils.register(ctx, LAVA_SPRING, lavaSpring, CountPlacement.of(25), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(10), VerticalAnchor.aboveBottom(36)), BiomeFilter.biome());
		PlacementUtils.register(ctx, DELTA, delta, orePlacement(6, HeightRangePlacement.of(BiasedToBottomHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.aboveBottom(18), 1))));
		PlacementUtils.register(ctx, BASALT_PILLAR, basaltPillar, CountPlacement.of(4), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(9), VerticalAnchor.aboveBottom(24)), BiomeFilter.biome());
		PlacementUtils.register(ctx, BASALT_SPIKE, basaltSpike, CountPlacement.of(UniformInt.of(2, 8)), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(2), VerticalAnchor.aboveBottom(28)), BiomeFilter.biome());
		PlacementUtils.register(ctx, UNDERGROUND_MAGMA, undergroundMagma, orePlacement(10, HeightRangePlacement.of(BiasedToBottomHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.aboveBottom(15), 1))));
		PlacementUtils.register(ctx, UNDERGROUND_SMOOTH_BASALT, undergroundSmoothBasalt, orePlacement(6, HeightRangePlacement.of(BiasedToBottomHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.aboveBottom(18), 1))));
		PlacementUtils.register(ctx, EXTRA_COAL_ORE, extraCoalOre, orePlacement(36, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-6), VerticalAnchor.aboveBottom(24))));
		PlacementUtils.register(ctx, EXTRA_DIAMOND_ORE, extraDiamondOre, orePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-32), VerticalAnchor.aboveBottom(32))));
	}
	
	public static ResourceKey<PlacedFeature> createKey(String id) {
		return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(VolcanicCaverns.MOD_ID, id));
	}
	
	private static List<PlacementModifier> orePlacement(int count, HeightRangePlacement heightRangePlacement) {
	      return List.of(CountPlacement.of(count), InSquarePlacement.spread(), heightRangePlacement, BiomeFilter.biome());
	}
}
