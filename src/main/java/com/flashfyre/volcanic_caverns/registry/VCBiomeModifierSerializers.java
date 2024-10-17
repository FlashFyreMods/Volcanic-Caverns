package com.flashfyre.volcanic_caverns.registry;

import com.flashfyre.volcanic_caverns.VolcanicCaverns;
import com.flashfyre.volcanic_caverns.biomemodifier.AddCarverBiomeModifier;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class VCBiomeModifierSerializers {
	
	public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, VolcanicCaverns.MOD_ID);
	
	public static final RegistryObject<Codec<AddCarverBiomeModifier>> ADD_CARVER = BIOME_MODIFIER_SERIALIZERS.register("add_carver", () ->
    RecordCodecBuilder.create(builder -> builder.group(
        Biome.LIST_CODEC.fieldOf("biomes").forGetter(AddCarverBiomeModifier::biomes),
        ConfiguredWorldCarver.CODEC.fieldOf("carver").forGetter(AddCarverBiomeModifier::carver)
      ).apply(builder, AddCarverBiomeModifier::new)));

}
