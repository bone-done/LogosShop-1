package com.vansisto.logosshop;

import com.vansisto.logosshop.dto.TestDTO;
import com.vansisto.logosshop.entity.TestEntity;
import com.vansisto.logosshop.service.TestService;
import com.vansisto.logosshop.util.ModelMapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogosShopApplication implements CommandLineRunner {

	@Autowired
	private TestService testService;

	@Autowired
	private ModelMapperUtil modelMapperUtil;

	public static void main(String[] args) {
		SpringApplication.run(LogosShopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 10; i++){
			TestEntity entity = new TestEntity();
			entity.setName("Name " + i);

			testService.create(modelMapperUtil.map(entity, TestDTO.class));
		}
	}
}
