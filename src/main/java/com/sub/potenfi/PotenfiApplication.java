package com.sub.potenfi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sub.potenfi.mapper") // 매퍼 경로 지정
public class PotenfiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PotenfiApplication.class, args);
	}

}
