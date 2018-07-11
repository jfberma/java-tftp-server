package com.urbanairship.tftp_server.packets;

import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

public class TftpWrqPacketTest {

    @Test
    public void testGetFileName() {
        String fileName = "file.txt";
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.putShort((short) 0);
        for (char c : fileName.toCharArray()) {
            byteBuffer.put((byte) c);
        }
        byteBuffer.put((byte) 0);

        TftpWrqPacket tftpWrqPacket = new TftpWrqPacket();
        assertEquals(fileName, tftpWrqPacket.getFileName(byteBuffer));
    }
}
