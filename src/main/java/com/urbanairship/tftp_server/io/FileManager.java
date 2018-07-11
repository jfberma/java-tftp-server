package com.urbanairship.tftp_server.io;

import com.urbanairship.tftp_server.packets.TftpDataPacket;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileAlreadyExistsException;

/**
 * File manager singleton for managing IO operations
 */
public class FileManager {
    private static FileManager instance;
    private String fileName;

    private FileManager() {
    }

    public static FileManager getInstance() {
        if (instance == null) {
            instance = new FileManager();
        }
        return instance;
    }

    /**
     * Sets the name for the file being transferred. If a file with the same name
     * already exists, throw FileAlreadyExistsException
     * @param fileName the name of the file
     * @throws FileAlreadyExistsException
     */
    public void setFileName(String fileName) throws FileAlreadyExistsException {
        File file = new File(fileName);
        if (file.exists()) {
            throw new FileAlreadyExistsException(String.format("File %s already exists", fileName));
        }
        this.fileName = fileName;
    }

    /**
     * Write bytes from the buffer to a file
     * @param byteBuffer a ByteBuffer
     */
    public void writeToFile(ByteBuffer byteBuffer) {
        if (fileName != null) {
            File file = new File(fileName);

            // Data won't always fill the entire buffer
            int length = byteBuffer.capacity() - byteBuffer.remaining();
            byteBuffer.position(TftpDataPacket.DATA_OFFSET);
            byteBuffer.limit(length);

            try {
                FileChannel fileChannel = new FileOutputStream(file, true).getChannel();
                fileChannel.write(byteBuffer);
                fileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
