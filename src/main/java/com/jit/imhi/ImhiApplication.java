package com.jit.imhi;

import com.mina.socket.MinaService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.jit.imhi.mapper")
@SpringBootApplication
public class ImhiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImhiApplication.class, args);
		MinaService.start();
	}
}
