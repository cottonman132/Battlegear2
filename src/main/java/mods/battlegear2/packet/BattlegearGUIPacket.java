package mods.battlegear2.packet;

import mods.battlegear2.Battlegear;

import net.minecraft.entity.player.EntityPlayer;

import io.netty.buffer.ByteBuf;

/**
 * User: nerd-boy Date: 26/06/13 Time: 1:38 PM TODO: Add discription
 */
public final class BattlegearGUIPacket extends AbstractMBPacket {

    public static final String packetName = "MB2|GUI";

    public BattlegearGUIPacket(int equipid) {
        this.equipid = equipid;
    }

    public BattlegearGUIPacket() {}

    private int equipid = -1;

    @Override
    public void process(ByteBuf in, EntityPlayer player) {
        equipid = in.readInt();
        if (equipid != -1) {
            player.openGui(Battlegear.INSTANCE, equipid, player.worldObj, 0, 0, 0);
        }
    }

    @Override
    public String getChannel() {
        return packetName;
    }

    @Override
    public void write(ByteBuf out) {
        out.writeInt(equipid);
    }
}
