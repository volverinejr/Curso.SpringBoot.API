package com.springcourse.service.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HashUtilTests {

	
	@DisplayName("Método: getSecureHashTest")
	@Test
	public void getSecureHashTest() {
		String hash = HashUtil.getSecureHash("123456");
		
		assertThat(hash.length()).isEqualTo(64);
	}
}
