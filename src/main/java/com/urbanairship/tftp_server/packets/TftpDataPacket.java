package com.urbanairship.tftp_server.packets;

import com.urbanairship.tftp_server.io.FileManager;

import java.nio.ByteBuffer;
import java.util.Optional;

/**
 *   2 bytes     2 bytes      n bytes
 *   ----------------------------------
 *  | Opcode |   Block #  |   Data     |
 *   ----------------------------------
 *
 *  Data is actually transferred in DATA packets.
 *  DATA packets (opcode = 3) have a block number and data field.  The
 *  block numbers on data packets begin with one and increase by one for
 *  each new block of data.  This restriction allows the program to use a
 *  single number to discriminate between new packets and duplicates.
 *  The data field is from zero to 512 bytes long.  If it is 512 bytes
 *  long, the block is not the last block of data; if it is from zero to
 *  511 bytes long, it signals the end of the transfer.
 */
public class TftpDataPacket extends TftpPacket {
    public static final int BLOCK_NUMBER_OFFSET = 2;
    public static final int DATA_OFFSET = 4;
    private short blockNumber;

    public TftpDataPacket() { }

    public TftpDataPacket(ByteBuffer byteBuffer) {
        super(byteBuffer);
    }

    @Override
    protected void parseBytes(ByteBuffer byteBuffer) {
        blockNumber = byteBuffer.getShort(BLOCK_NUMBER_OFFSET);
        FileManager.getInstance().writeToFile(byteBuffer);
    }

    @Override
    public short getOpCode() {
        return 3;
    }

    @Override
    public ByteBuffer getBytes() {
        return null;
    }

    @Override
    public Optional<TftpPacket> getResponse() {
        TftpAckPacket tftpAckPacket = new TftpAckPacket();
        tftpAckPacket.setBlockNumber(blockNumber);

        return Optional.of(tftpAckPacket);
    }

    public String toString() {
        return "TftpDataPacket (DATA)";
    }

}
