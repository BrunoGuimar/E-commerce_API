package src.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void itShouldSeeIfNumbersAreEquals() {
		assertThat(20).isEqualTo(20);
	}

}
