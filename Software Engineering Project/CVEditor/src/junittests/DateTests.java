package junittests;

import static org.junit.Assert.*;

import org.junit.Test;

import section.CareerSummarySection;

public class DateTests {

	@Test
	public void correctDateTest() {
		CareerSummarySection css = new CareerSummarySection();
		assertTrue(css.checkDate("12/05/2015 - 20/03/2016"));
		assertTrue(css.checkDate("12/10/2015 - 12/11/2015"));
		assertTrue(css.checkDate("12/10/2005 -            20/03/2017"));
	}
	
	@Test
	public void incorrectDateTest(){
		CareerSummarySection css = new CareerSummarySection();
		assertFalse(css.checkDate("12/05/2015 - 20/03/2013"));
		assertFalse(css.checkDate("12/10/201512/11/2015"));
		assertFalse(css.checkDate("eg.12/10/2005 - 20/03/2017"));
		assertFalse(css.checkDate(""));
		assertFalse(css.checkDate("12/10/2005"));
		assertFalse(css.checkDate("-"));
	}

}
