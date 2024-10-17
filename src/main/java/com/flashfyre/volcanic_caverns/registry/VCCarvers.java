package com.flashfyre.volcanic_caverns.registry;

import com.flashfyre.volcanic_caverns.VolcanicCaverns;
import com.flashfyre.volcanic_caverns.carver.LavaCavernsCarver;

import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VCCarvers {
	
	public static final DeferredRegister<WorldCarver<?>> CARVERS = DeferredRegister.create(ForgeRegistries.Keys.WORLD_CARVERS, VolcanicCaverns.MOD_ID);
			
	public static final RegistryObject<LavaCavernsCarver> LAVA_CAVERNS = CARVERS.register("lava_caverns", () -> new LavaCavernsCarver());
	
	
}
