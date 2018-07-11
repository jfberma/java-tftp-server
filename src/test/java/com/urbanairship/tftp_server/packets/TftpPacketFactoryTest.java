package com.urbanairship.tftp_server.packets;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class TftpPacketFactoryTest {

    @Test
    public void testGetTftpPacket_TftpWrqPacket() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.putShort((short) 2);

        TftpPacket tftpPacket = TftpPacketFactory.getTftpPacket(byteBuffer);
        assertThat(tftpPacket, instanceOf(TftpWrqPacket.class));
    }

    @Test
    public void testGetTftpPacket_TftpDataPacket() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.putShort((short) 3);

        TftpPacket tftpPacket = TftpPacketFactory.getTftpPacket(byteBuffer);
        assertThat(tftpPacket, instanceOf(TftpDataPacket.class));
    }

    @Test
    public void testGetTftpPacket_TftpAckPacket() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.putShort((short) 4);

        TftpPacket tftpPacket = TftpPacketFactory.getTftpPacket(byteBuffer);
        assertThat(tftpPacket, instanceOf(TftpAckPacket.class));
    }

    @Test
    public void testGetTftpPacket_TftpErrorPacket() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.putShort((short) 5);

        TftpPacket tftpPacket = TftpPacketFactory.getTftpPacket(byteBuffer);
        assertThat(tftpPacket, instanceOf(TftpErrorPacket.class));
    }
}
