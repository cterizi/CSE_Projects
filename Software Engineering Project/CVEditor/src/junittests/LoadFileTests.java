package junittests;

import static org.junit.Assert.*;

import org.junit.Test;

import input.LATEXLoader;
import input.TXTLoader;

public class LoadFileTests {
	
	private TXTLoader txtLoader = new TXTLoader();
	private LATEXLoader latexLoader = new LATEXLoader();
	
	@Test
	public void correctLoad() {
		assertTrue(txtLoader.loadFile(txtLoader, "loadFiles\\correct1.txt"));
		assertTrue(txtLoader.loadFile(txtLoader, "loadFiles\\correct2.txt"));
		assertTrue(txtLoader.loadFile(txtLoader, "loadFiles\\correct3.txt"));
		assertTrue(latexLoader.loadFile(latexLoader, "loadFiles\\correct1.ltx"));
		assertTrue(latexLoader.loadFile(latexLoader, "loadFiles\\correct2.ltx"));
		assertTrue(latexLoader.loadFile(latexLoader, "loadFiles\\correct3.ltx"));
	}
	
	@Test
	public void incorrecttLoad() {
		assertFalse(txtLoader.loadFile(txtLoader, "loadFiles\\missing_label.txt"));
		assertFalse(txtLoader.loadFile(txtLoader, "loadFiles\\missing_section.txt"));
		assertFalse(txtLoader.loadFile(txtLoader, "loadFiles\\wrong_label.txt"));
		assertFalse(latexLoader.loadFile(latexLoader, "loadFiles\\wrong_end_block.ltx"));
		assertFalse(latexLoader.loadFile(latexLoader, "loadFiles\\wrong_start_block.ltx"));
	}
}