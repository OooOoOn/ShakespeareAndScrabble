package com.dataductus;

import static org.junit.Assert.*;
import java.sql.SQLException;
import org.junit.Test;

public class ShakespeareAndScrabbleTests {
	
	Scrabble s1 = new Scrabble();

	@Test
	public void successfulRun() throws SQLException {
		assertEquals("Successful", s1.start());
	}

}
