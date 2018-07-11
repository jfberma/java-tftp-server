package com.urbanairship.tftp_server.packets;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

public class TftpAckPacketTest {

    @Test
    public void testGetBytes() {
        short blockNumber = 3;
        TftpAckPacket tftpAckPacket = new TftpAckPacket();
        tftpAckPacket.setBlockNumber(blockNumber);
        ByteBuffer bytes = tftpAckPacket.getBytes();
        bytes.flip();

        assertEquals(tftpAckPacket.getOpCode(), bytes.getShort());
        assertEquals(tftpAckPacket.getBlockNumber(), bytes.getShort());
    }
}
