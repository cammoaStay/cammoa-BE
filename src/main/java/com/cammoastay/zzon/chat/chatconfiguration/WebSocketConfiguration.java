	package com.cammoastay.zzon.chat.chatconfiguration;

    import com.cammoastay.zzon.chat.handlers.WebSocketChatHandler;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.socket.config.annotation.EnableWebSocket;
	import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
    import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

	@Configuration
	@EnableWebSocket
	public class WebSocketConfiguration implements WebSocketConfigurer {

		private final WebSocketChatHandler webSocketChatHandler;

        public WebSocketConfiguration(WebSocketChatHandler webSocketChatHandler) {
            this.webSocketChatHandler = webSocketChatHandler;
        }
        @Override
		public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
            registry.addHandler(webSocketChatHandler, "/ws/chats");
		}
	}
