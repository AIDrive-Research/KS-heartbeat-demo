package com.github.paicoding.forum.web.front.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.DatagramSocket;
import java.net.DatagramPacket;


@Slf4j
@Component
public class UdpServer {

    private static final int PORT = 10002;


    @PostConstruct
    public void start() {
        new Thread(this::listen).start();
    }

    private void listen() {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            log.info("UDP Server started on port " + PORT);
            byte[] buffer = new byte[102400];
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength()).trim();
                log.info("❤️ Heartbeat from: {} ", packet.getAddress());
                log.info("📦 Data received: {}", message);
            }
        } catch (Exception e) {
            log.error("UDP Server error: {}", e.getMessage(), e);
        }
    }


}