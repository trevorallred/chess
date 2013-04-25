package chess;

import org.junit.Assert;
import org.junit.Test;


public class LocationTest {

	@Test
	public void testLocationString_a1() throws Exception {
		Location location = new Location("a1");
		Assert.assertEquals(File.a, location.getX());
		Assert.assertEquals(1, location.getY());
	}

	@Test
	public void testLocationString_h8() throws Exception {
		Location location = new Location("h8");
		Assert.assertEquals(File.h, location.getX());
		Assert.assertEquals(8, location.getY());
	}
	
}
