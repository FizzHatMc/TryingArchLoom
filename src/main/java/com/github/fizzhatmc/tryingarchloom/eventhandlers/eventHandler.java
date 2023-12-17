package com.github.fizzhatmc.tryingarchloom.eventhandlers;

import com.github.fizzhatmc.tryingarchloom.utils.WaypointUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class eventHandler {

    public boolean flagWaypoint = false;

    @SubscribeEvent
    public void pickupItem(EntityItemPickupEvent event) {
        String item = String.valueOf(Item.getByNameOrId(event.item.getEntityItem().getDisplayName()));
        System.out.println(item);

    }

    public static final Pattern CHAT_PATTERN = Pattern.compile("^(?:\\[[^\\]]+\\] *|)(?:\\[[^\\]]+\\] *|)(?<name>[0-9A-Za-z_]{1,16}): (?<message>.+)$");
    private static final Pattern FORMAT_PATTERN = Pattern.compile("\u00a7.");
    public static final ResourceLocation TEXTURE_BEACON_BEAM = new ResourceLocation("textures/entity/beacon_beam.png");

    public static String getUsername(String chatMessage) {
        Matcher formatMatcher = FORMAT_PATTERN.matcher(chatMessage);
        Matcher matcher = CHAT_PATTERN.matcher(formatMatcher.replaceAll(""));


        if (matcher.matches()) {
            String sender = matcher.group("sender");
            String message = matcher.group("message");

            return sender;
        }


        return null;
    }

    @SubscribeEvent
    public void clientChatReceived(ClientChatReceivedEvent event){
        String message = event.message.getFormattedText();


        if (message.contains(".help")){
            Minecraft.getMinecraft().thePlayer.sendChatMessage("KazzyWazzy Client for Chat.");
            Minecraft.getMinecraft().thePlayer.sendChatMessage("pt to transfer to urself");
            Minecraft.getMinecraft().thePlayer.sendChatMessage("warp for party warp");
            Minecraft.getMinecraft().thePlayer.sendChatMessage("allinv to enable all invite");
            Minecraft.getMinecraft().thePlayer.sendChatMessage("rp for reparty");
            Minecraft.getMinecraft().thePlayer.sendChatMessage("ptTO\"IGN\" to transfer to specific Player");
            Minecraft.getMinecraft().thePlayer.sendChatMessage("beacon");
        }

        if (message.contains(".pt")){
            String sender = getUsername(message);
            Minecraft.getMinecraft().thePlayer.sendChatMessage("party transfer "+sender);
        }

        if (message.contains(".beacon")){
            flagWaypoint = !flagWaypoint;
        }
    }
    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (flagWaypoint) {
            Entity viewer = Minecraft.getMinecraft().getRenderViewEntity();
            Color color = new Color(255, 255, 255);
            double waypointY, waypointX, waypointZ;
            double viewerX = viewer.lastTickPosX + (viewer.posX - viewer.lastTickPosX) * event.partialTicks;
            double viewerY = viewer.lastTickPosY + (viewer.posY - viewer.lastTickPosY) * event.partialTicks;
            double viewerZ = viewer.lastTickPosZ + (viewer.posZ - viewer.lastTickPosZ) * event.partialTicks;

            waypointY = 10;
            waypointX = 5;
            waypointZ = 5;

            double x = waypointX - viewerX;
            double y = waypointY - viewerY;
            double z = waypointZ - viewerZ;

            WaypointUtils.renderBeaconBeam(x, y, z, color.getRGB(), 0.5f, event.partialTicks);
        }
    }
}
//EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;