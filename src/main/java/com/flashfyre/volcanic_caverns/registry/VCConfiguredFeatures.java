package com.flashfyre.volcanic_caverns.registry;

import java.util.List;

import com.flashfyre.volcanic_caverns.VolcanicCaverns;
import com.flashfyre.volcanic_caverns.feature.ImprovedLargeDripstoneConfig;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DeltaFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.Fluids;

public class VCConfiguredFeatures {
	
	public static final ResourceKey<ConfiguredFeature<?, ?>> DELTA = createKey("delta");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LAVA_SPRING = createKey("lava_spring");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BASALT_PILLAR = createKey("basalt_pillar");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BASALT_SPIKE = createKey("basalt_spike");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UNDERGROUND_MAGMA = createKey("underground_magma");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UNDERGROUND_SMOOTH_BASALT = createKey("underground_smooth_basalt");
	public static final ResourceKey<ConfiguredFeature<?, ?>> EXTRA_COAL_ORE = createKey("extra_coal_ore");
	public static final ResourceKey<ConfiguredFeature<?, ?>> EXTRA_DIAMOND_ORE = createKey("extra_diamond_ore");
	
	public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> ctx) {
		HolderGetter<Block> holderGetter = ctx.lookup(Registries.BLOCK);
		RuleTest overworldStoneTest = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
		RuleTest stoneReplaceableOreTest = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
		RuleTest deepslateReplaceableOreTest = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
		
		List<OreConfiguration.TargetBlockState> diamondTargets = List.of(OreConfiguration.target(stoneReplaceableOreTest, Blocks.DIAMOND_ORE.defaultBlockState()), OreConfiguration.target(deepslateReplaceableOreTest, Blocks.DEEPSLATE_DIAMOND_ORE.defaultBlockState()));
		List<OreConfiguration.TargetBlockState> coalTargets = List.of(OreConfiguration.target(stoneReplaceableOreTest, Blocks.COAL_ORE.defaultBlockState()), OreConfiguration.target(deepslateReplaceableOreTest, Blocks.DEEPSLATE_COAL_ORE.defaultBlockState()));
		
		FeatureUtils.register(ctx, DELTA, Feature.DELTA_FEATURE, new DeltaFeatureConfiguration(Blocks.LAVA.defaultBlockState(), Blocks.MAGMA_BLOCK.defaultBlockState(), UniformInt.of(3, 7), UniformInt.of(0, 2)));
		FeatureUtils.register(ctx, LAVA_SPRING, Feature.SPRING, new SpringConfiguration(Fluids.LAVA.defaultFluidState(), false, 4, 1, holderGetter.getOrThrow(VolcanicCaverns.TagKeys.LAVA_SPRING_VALID)));
		FeatureUtils.register(ctx, BASALT_PILLAR, VCFeatures.PILLAR.get(), new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.BASALT)));
		FeatureUtils.register(ctx, BASALT_SPIKE, VCFeatures.LARGE_SPIKE.get(), new ImprovedLargeDripstoneConfig(BlockStateProvider.simple(Blocks.BASALT), 30, UniformInt.of(3, 19), UniformFloat.of(0.4F, 2.0F), 0.33F, UniformFloat.of(0.3F, 0.9F), UniformFloat.of(0.4F, 1.0F), ConstantFloat.of(0.0F), 4, 0.6F));
		FeatureUtils.register(ctx, UNDERGROUND_MAGMA, Feature.ORE, new OreConfiguration(overworldStoneTest, Blocks.MAGMA_BLOCK.defaultBlockState(), 33));
		FeatureUtils.register(ctx, UNDERGROUND_SMOOTH_BASALT, Feature.ORE, new OreConfiguration(overworldStoneTest, Blocks.SMOOTH_BASALT.defaultBlockState(), 37));
		FeatureUtils.register(ctx, EXTRA_COAL_ORE, Feature.ORE, new OreConfiguration(coalTargets, 9));
		FeatureUtils.register(ctx, EXTRA_DIAMOND_ORE, Feature.ORE, new OreConfiguration(diamondTargets, 4));
	}
	
	public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String id) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(VolcanicCaverns.MOD_ID, id));
	}
}
