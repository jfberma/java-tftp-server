package com.urbanairship.tftp_server.packets;

import java.nio.ByteBuffer;

public class TftpPacketFactory {
    public static TftpPacket getTftpPacket(ByteBuffer byteBuffer) {
        TftpPacket tftpPacket = null;
        short opCode = byteBuffer.getShort(0);
        switch (opCode) {
            case 1: // Read request (RRQ) - Do not implement
                throw new UnsupportedOperationException("Read request (RRQ) not yet supported.");
            case 2: // Write request (WRQ)
                tftpPacket = new TftpWrqPacket(byteBuffer);
                break;
            case 3: // Data (DATA)
                tftpPacket = new TftpDataPacket(byteBuffer);
                break;
            case 4: // Acknowledgment (ACK)
                tftpPacket = new TftpAckPacket(byteBuffer);
                break;
            case 5: // Error (ERROR)
                tftpPacket = new TftpErrorPacket(byteBuffer);
                break;
        }
        return tftpPacket;
    }
}
