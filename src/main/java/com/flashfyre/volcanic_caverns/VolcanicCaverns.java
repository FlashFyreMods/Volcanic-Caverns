package com.flashfyre.volcanic_caverns;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import com.flashfyre.volcanic_caverns.carver.LavaCavernsCarver;
import com.flashfyre.volcanic_caverns.registry.VCFeatures;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.flashfyre.volcanic_caverns.data.VCWorldGenData;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

@Mod(VolcanicCaverns.MOD_ID)
public class VolcanicCaverns
{
	public static final String MOD_ID = "volcanic_caverns";
    public static final Logger LOGGER = LogManager.getLogger();
    public static VolcanicCaverns instance;

	public static final DeferredRegister<WorldCarver<?>> CARVERS = DeferredRegister.create(BuiltInRegistries.CARVER, MOD_ID);
	public static final Supplier<LavaCavernsCarver> LAVA_CAVERNS = VolcanicCaverns.CARVERS.register("lava_caverns", LavaCavernsCarver::new);

	public VolcanicCaverns(IEventBus modBus) {
    	
    	instance = this;

        IEventBus neoBus = NeoForge.EVENT_BUS;
        
        modBus.addListener(this::gatherData);
		neoBus.addListener(this::worldLoad);
		neoBus.addListener(this::createWorldSpawnPosition);
        
        CARVERS.register(modBus);
        VCFeatures.FEATURES.register(modBus);
    }
    
	public void worldLoad(LevelEvent.Load event) {
		setWorldSeed(event);
	}
	
	public void createWorldSpawnPosition(LevelEvent.CreateSpawnPosition event) {
		setWorldSeed(event);
	}
	
	private void setWorldSeed(LevelEvent event) {
		if(event.getLevel() instanceof ServerLevel serverLevel) {
			LAVA_CAVERNS.get().setSeed(serverLevel.getSeed());
		}
	}
	
	public void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		PackOutput packOutput = generator.getPackOutput();
		generator.addProvider(event.includeClient(), new VCWorldGenData(packOutput, lookupProvider));
	}

	public static class TagKeys {
		public static final TagKey<Block> LAVA_CAVERNS_REPLACEABLE_BLOCKS = TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, "lava_caverns_carver_replaceables"));
	    public static final TagKey<Block> LAVA_SPRING_VALID = TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, "is_lava_spring_valid"));
	}
}
