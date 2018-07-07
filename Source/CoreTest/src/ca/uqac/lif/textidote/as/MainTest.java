/*
    TeXtidote, a linter for LaTeX documents
    Copyright (C) 2018  Sylvain Hallé

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package ca.uqac.lif.textidote.as;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;
import org.junit.Test;

import ca.uqac.lif.textidote.Main;

public class MainTest 
{
	@Test(timeout = 1000)
	public void test1() throws IOException
	{
		Main.mainLoop(new String[] {"--help"}, System.in, System.out, System.err);
	}
	
	@Test(timeout = 1000)
	public void test2() throws IOException
	{
		Main.mainLoop(new String[] {"--help", "--quiet"}, System.in, System.out, System.err);
	}
	
	@Test(timeout = 1000)
	public void test3() throws IOException
	{
		Main.mainLoop(new String[] {"--help", "--quiet"}, System.in, System.out, System.err);
	}
	
	@Test(timeout = 1000)
	public void test4() throws IOException
	{
		Main.mainLoop(new String[] {"--version"}, System.in, System.out, System.err);
	}
	
	@Test(timeout = 2000)
	public void test5() throws IOException
	{
		InputStream in = MainTest.class.getResourceAsStream("data/test1.tex");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		int ret_code = Main.mainLoop(new String[] {}, in, out, System.err);
		String output = new String(baos.toByteArray());
		assertNotNull(output);
		assertEquals(0, ret_code);
	}
	
	@Test(timeout = 2000)
	public void test6() throws IOException
	{
		InputStream in = MainTest.class.getResourceAsStream("data/test-subsec-1.tex");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(baos);
		int ret_code = Main.mainLoop(new String[] {}, in, out, System.err);
		String output = new String(baos.toByteArray());
		assertNotNull(output);
		assertTrue(ret_code > 0);
	}
}
