package com.github.fizzhatmc.tryingarchloom;

import com.github.fizzhatmc.tryingarchloom.eventhandlers.eventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "tryingarchloom", useMetadata=true)
public class ExampleMod {

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForge.EVENT_BUS.register(new eventHandler());
    }





}
