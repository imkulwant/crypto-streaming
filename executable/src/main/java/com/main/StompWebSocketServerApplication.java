package com.main;

import com.websocket.config.WebSocketClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({WebSocketClientConfig.class})
public class StompWebSocketServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StompWebSocketServerApplication.class, args);
	}

}
