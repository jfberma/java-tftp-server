package com.urbanairship.tftp_server.packets;

import java.nio.ByteBuffer;
import java.util.Optional;

/**
 *   2 bytes    2 bytes
 *   -------------------
 *  | 04    |   Block #  |
 *   --------------------
 *
 *  The block number in an ACK echoes the block number of the DATA packet being
 *  acknowledged. A WRQ is acknowledged with an ACK packet having a block number of zero.
 */
public class TftpAckPacket extends TftpPacket {
    private short blockNumber;

    public TftpAckPacket() {
    }

    public TftpAckPacket(ByteBuffer byteBuffer) {
        super(byteBuffer);
    }

    @Override
    protected void parseBytes(ByteBuffer byteBuffer) {

    }

    @Override
    public short getOpCode() {
        return 4;
    }

    @Override
    public ByteBuffer getBytes() {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putShort(getOpCode());
        buffer.putShort(blockNumber);

        return buffer;
    }

    @Override
    public Optional<TftpPacket> getResponse() {
        return Optional.empty();
    }

    public short getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(short blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String toString() {
        return String.format("TftpAckPacket (ACK) with block number %d", blockNumber);
    }

}
