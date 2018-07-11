package com.urbanairship.tftp_server;

import com.urbanairship.tftp_server.packets.TftpPacket;
import com.urbanairship.tftp_server.packets.TftpPacketFactory;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class TftpServer {
    private static final int PORT = 69;
    private static final int BUFFER_SIZE = 1024;

    private void start() {
        System.out.println("Server started");
        try {
            DatagramChannel channel = DatagramChannel.open();
            DatagramSocket socket = channel.socket();
            SocketAddress address = new InetSocketAddress(PORT);
            socket.bind(address);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);

            while (true) {
                byteBuffer.clear();
                SocketAddress client = channel.receive(byteBuffer);

                // Listen for incoming packets
                TftpPacket receivedPacket = TftpPacketFactory.getTftpPacket(byteBuffer);
                System.out.println(String.format("Received %s", receivedPacket.toString()));

                // Respond if neccessary
                receivedPacket.getResponse().ifPresent(tftpPacket -> {
                    ByteBuffer respondingBuffer = tftpPacket.getBytes();
                    byteBuffer.clear();
                    byteBuffer.put(respondingBuffer.array(), 0, respondingBuffer.limit());
                    byteBuffer.flip();
                    try {
                        System.out.println(String.format("Responding with %s", tftpPacket.toString()));
                        channel.send(byteBuffer, client);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        TftpServer tftpServer = new TftpServer();
        tftpServer.start();
    }
}
