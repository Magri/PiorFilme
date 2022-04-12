package br.magri.texoit;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.h2.tools.Server;

import java.sql.SQLException;

@SpringBootApplication
public class TexoitApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(TexoitApplication.class, args);
	}

	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server h2Server() throws SQLException, SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "8090");
	}
}
