package io.github.sakuraryoko.testux.network;

import javax.annotation.Nullable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import io.github.sakuraryoko.testux.TestUX;

public class TestData
{
    private String modName;
    private String modVersion;
    private int protocolVersion;
    private NbtCompound nbt = new NbtCompound();

    public TestData(String name, String ver, int protocol, @Nullable NbtCompound data)
    {
        this.modName = name;
        this.modVersion = ver;
        this.protocolVersion = protocol;
        if (data != null && data.isEmpty() == false)
        {
            this.nbt.copyFrom(data);
        }
    }

    public int getProtocolVersion()
    {
        return this.protocolVersion;
    }

    public String getModName()
    {
        return this.modName;
    }

    public String getModVersion()
    {
        return this.modVersion;
    }

    @Nullable
    public NbtCompound getNbt()
    {
        return this.nbt;
    }

    public void replaceNbt(NbtCompound input)
    {
        this.nbt = new NbtCompound();
        this.nbt.copyFrom(input);
    }

    public void copyFrom(NbtCompound input)
    {
        this.nbt.copyFrom(input);
    }

    public void toPacket(PacketByteBuf output)
    {
        output.writeString(this.modName);
        output.writeString(this.modVersion);
        output.writeInt(this.protocolVersion);
        if (this.nbt.isEmpty() == false)
        {
            output.writeBoolean(true);
            output.writeNbt(this.nbt);
        }
        else
        {
            output.writeBoolean(false);
        }
    }

    public static TestData fromPacket(PacketByteBuf input)
    {
        return new TestData(input.readString(), input.readString(), input.readInt(), input.readBoolean() ? input.readNbt() : null);
    }

    public void dump()
    {
        TestUX.logger.info("TestData --> modName: {}, modVersion {}, protocolVersion: {}", this.modName, this.modVersion, this.protocolVersion);
        if (this.nbt.isEmpty() == false)
        {
            TestUX.logger.info("NBT --> {}", this.nbt.toString());
        }
        else
        {
            TestUX.logger.info("NBT --> EMPTY");
        }
    }
}
