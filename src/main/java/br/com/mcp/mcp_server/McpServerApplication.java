package br.com.mcp.mcp_server;

import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.mcp.mcp_server.infrastructure.controller.UsuarioController;

@SpringBootApplication
public class McpServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpServerApplication.class, args);
	}

	@Bean
	public ToolCallbackProvider toolCallbackProvider(UsuarioController usuarioController) {
		return MethodToolCallbackProvider.builder()
				.toolObjects(usuarioController)
				.build();
	}

}
