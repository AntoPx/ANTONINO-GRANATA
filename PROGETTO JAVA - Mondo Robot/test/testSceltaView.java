package Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import GameBoy.SceltaView;

class testSceltaView {

	SceltaView scelta = new SceltaView();
	
	@Test
	void testNotNull() {
		assertNotNull(scelta);
	}

}
