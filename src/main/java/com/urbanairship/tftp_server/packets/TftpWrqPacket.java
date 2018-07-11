package com.urbanairship.tftp_server.packets;

import com.urbanairship.tftp_server.io.FileManager;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.file.FileAlreadyExistsException;
import java.util.Optional;

/**
 * 2 bytes     string    1 byte     string   1 byte
 * ------------------------------------------------
 * | Opcode |  Filename  |   0  |    Mode    |   0  |
 * ------------------------------------------------
 * <p>
 * WRQ Packet (opcode 2) contains a filename and a mode. The file name is a
 * sequence of bytes in netascii terminated by a zero byte.  The mode field
 * contains the string "netascii", "octet", or "mail" (or any combination
 * of upper and lower case, such as "NETASCII", NetAscii", etc.) in netascii
 * indicating the three modes defined in the protocol.
 */
public class TftpWrqPacket extends TftpPacket {
    private TftpPacket errorResponse;

    public TftpWrqPacket() {
    }

    public TftpWrqPacket(ByteBuffer byteBuffer) {
        super(byteBuffer);
    }

    @Override
    protected void parseBytes(ByteBuffer byteBuffer) {
        try {
            FileManager.getInstance().setFileName(getFileName(byteBuffer));
        } catch (FileAlreadyExistsException e) {
            errorResponse = new TftpErrorPacket(byteBuffer);
        }
    }

    /**
     * Parse the file name from the ByteBuffer
     *
     * @param byteBuffer
     * @return the file name
     */
    String getFileName(ByteBuffer byteBuffer) {
        int dataOffset = 2;
        int length = byteBuffer.capacity() - byteBuffer.remaining();
        byteBuffer.position(dataOffset);
        byteBuffer.limit(length);

        int counter = 0;
        while (byteBuffer.hasRemaining()) {
            byte currentByte = byteBuffer.get();
            if (currentByte == 0) {
                break;
            }
            counter++;
        }

        byteBuffer.position(dataOffset);
        byte fileNameBytes[] = new byte[counter];
        byteBuffer.get(fileNameBytes, 0, counter);

        try {
            return new String(fileNameBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public short getOpCode() {
        return 2;
    }

    @Override
    public ByteBuffer getBytes() {
        return null;
    }

    /**
     * Returns a ACK response packet unless there was an ERROR
     *
     * @return the response packet
     */
    @Override
    public Optional<TftpPacket> getResponse() {
        if (errorResponse != null) {
            return Optional.of(errorResponse);
        }
        return Optional.of(new TftpAckPacket(null));
    }

    public String toString() {
        return "TftpWrqPacket (WRQ)";
    }
}
