package main.java.test;

import org.junit.After;
import org.junit.Before;
import main.java.conway.domain.LifeInterface;
 
/*
 * TEST PLAN
 */
public class LifeTest {
private LifeInterface lifeModel;

	@Before
	public void setup() {
		System.out.println("GridTest | setup");	
		lifeModel = null;
	}
	@After
	public void down() {
		System.out.println("GridTest | down");
	}

}
