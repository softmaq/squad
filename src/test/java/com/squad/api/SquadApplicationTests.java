package com.squad.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SquadApplicationTests {

	@Test
	void contextLoads() {
		boolean b=true;
		assertEquals(true,b);
	}

}
