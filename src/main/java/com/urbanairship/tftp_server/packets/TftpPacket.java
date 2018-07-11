package com.urbanairship.tftp_server.packets;

import java.nio.ByteBuffer;
import java.util.Optional;

public abstract class TftpPacket {
    public TftpPacket() {
    }

    public TftpPacket(ByteBuffer byteBuffer) {
        parseBytes(byteBuffer);
    }

    protected abstract void parseBytes(ByteBuffer byteBuffer);

    public abstract short getOpCode();

    public abstract ByteBuffer getBytes();

    public abstract Optional<TftpPacket> getResponse();
}
