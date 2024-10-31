package com.flashfyre.volcanic_caverns.data;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import com.flashfyre.volcanic_caverns.VolcanicCaverns;
import com.flashfyre.volcanic_caverns.registry.VCConfiguredCarvers;
import com.flashfyre.volcanic_caverns.registry.VCConfiguredFeatures;
import com.flashfyre.volcanic_caverns.registry.VCPlacedFeatures;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.VanillaRegistries;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

public class VCWorldGenData extends DatapackBuiltinEntriesProvider {
	
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.CONFIGURED_CARVER, VCConfiguredCarvers::bootstrap)
			.add(Registries.CONFIGURED_FEATURE, VCConfiguredFeatures::bootstrap)
			.add(Registries.PLACED_FEATURE, VCPlacedFeatures::bootstrap);
	
	public VCWorldGenData(PackOutput output, CompletableFuture<Provider> registries) {
		super(output, registries, BUILDER, Collections.singleton(VolcanicCaverns.MOD_ID));
	}
}
