package com.urbanairship.tftp_server.packets;

import java.nio.ByteBuffer;
import java.util.Optional;

/**
 *   2 bytes     2 bytes      string    1 byte
 *   -----------------------------------------
 *  | Opcode |  ErrorCode |   ErrMsg   |   0  |
 *   -----------------------------------------
 *
 *  An ERROR packet had an opcode of 5.  An
 *  ERROR packet can be the acknowledgment of any other type of packet.
 *  The error code is an integer indicating the nature of the error.
 */
public class TftpErrorPacket extends TftpPacket {

    // I'm currently only handling "File already exists" error
    private static final short errorCode = 6;
    private static final String errorMessage = "File already exists";

    public TftpErrorPacket(ByteBuffer byteBuffer) {
        super(byteBuffer);
    }

    @Override
    protected void parseBytes(ByteBuffer byteBuffer) {
    }

    @Override
    public short getOpCode() {
        return 5;
    }

    @Override
    public ByteBuffer getBytes() {
        // Hard coded byteBuffer for now since were only dealing with one static case
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        byteBuffer.putShort(getOpCode());
        byteBuffer.putShort(errorCode);
        for (char c : errorMessage.toCharArray()) {
            byteBuffer.putChar(c);
        }
        byteBuffer.put((byte) 0);
        return byteBuffer;
    }

    @Override
    public Optional<TftpPacket> getResponse() {
        return Optional.empty();
    }

    public String toString() {
        return String.format("TftpErrorPacket (ERROR): %s", errorMessage);
    }
}
