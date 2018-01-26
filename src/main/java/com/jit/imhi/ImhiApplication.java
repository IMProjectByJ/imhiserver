package com.jit.imhi;

import com.jit.imhi.socket.MinaService;
import com.jit.imhi.socket.MyIoHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.jit.imhi.mapper")

@SpringBootApplication
public class ImhiApplication {

	public static void main(String[] args) {

	//	SpringApplication.run(ImhiApplication.class, args);
		ConfigurableApplicationContext applicationContext = SpringApplication.run(ImhiApplication.class, args);
		MyIoHandler.setApplicationContext(applicationContext);
        MinaService.start();
	}
}
