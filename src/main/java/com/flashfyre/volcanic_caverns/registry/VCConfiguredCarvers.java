package com.flashfyre.volcanic_caverns.registry;

import com.flashfyre.volcanic_caverns.VolcanicCaverns;
import com.flashfyre.volcanic_caverns.carver.LavaCavernsConfiguration;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;

public class VCConfiguredCarvers {
	
public static final ResourceKey<ConfiguredWorldCarver<?>> LAVA_CAVERNS = createKey("lava_caverns");
	
	public static void bootstrap(BootstapContext<ConfiguredWorldCarver<?>> ctx) {
		HolderGetter<Block> holderGetter = ctx.lookup(Registries.BLOCK);
		ctx.register(LAVA_CAVERNS, VolcanicCaverns.LAVA_CAVERNS.get().configured(new LavaCavernsConfiguration(
				-0.1F,
				1.0F,
				ConstantHeight.of(VerticalAnchor.aboveBottom(16)), // y
				ConstantFloat.of(40.0F), // y scale
				VerticalAnchor.aboveBottom(9), // lava level 
				CarverDebugSettings.of(Blocks.ACACIA_BUTTON.defaultBlockState(), Blocks.BLUE_STAINED_GLASS_PANE.defaultBlockState(), Blocks.ORANGE_STAINED_GLASS_PANE.defaultBlockState(), Blocks.GLASS.defaultBlockState()),
				holderGetter.getOrThrow(VolcanicCaverns.TagKeys.LAVA_CAVERNS_REPLACEABLE_BLOCKS))));
	}
	
	private static ResourceKey<ConfiguredWorldCarver<?>> createKey(String id) {
		return ResourceKey.create(Registries.CONFIGURED_CARVER, new ResourceLocation(VolcanicCaverns.MOD_ID, id));
	}
}
