package ca.uqac.lif.textidote.as;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.junit.Test;

import ca.uqac.lif.dag.Node;
import ca.uqac.lif.dag.Pin;
import ca.uqac.lif.petitpoucet.AndNode;
import ca.uqac.lif.petitpoucet.ComposedPart;
import ca.uqac.lif.petitpoucet.Part;
import ca.uqac.lif.petitpoucet.PartNode;
import ca.uqac.lif.petitpoucet.function.strings.Range;
import ca.uqac.lif.textidote.as.AnnotatedString.Line;

import static ca.uqac.lif.textidote.as.AnnotatedString.CRLF;
import static ca.uqac.lif.textidote.as.AnnotatedString.CRLF_S;

public class AnnotatedStringTest
{
	@Test
	public void testEmpty()
	{
		String s = "";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals("", as.toString());
	}

	@Test
	public void testLinear1()
	{
		String s = "abcdef";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals(-1, as.getIndex(new Position(1, 3)));
	}
	
	@Test
	public void testLinear2()
	{
		String s = "abcdef";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals(3, as.getIndex(new Position(0, 3)));
	}
	
	@Test
	public void testLinear3()
	{
		String s = "abcdef";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals(-1, as.getIndex(new Position(0, 10)));
	}
	
	@Test
	public void testLinear4()
	{
		String s = "abc" + CRLF + "def";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals(s.indexOf("e"), as.getIndex(new Position(1, 1)));
	}
	
	@Test
	public void testLinear5()
	{
		String s = "abc" + CRLF + "def";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals(s.indexOf("c"), as.getIndex(new Position(0, 2)));
	}
	
	@Test
	public void testLinear6()
	{
		String s = "abc" + CRLF + "def";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals(-1, as.getIndex(new Position(0, 10)));
	}
	
	@Test
	public void testLinear7()
	{
		String s = "abc" + CRLF + "def";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals(-1, as.getIndex(new Position(1, 3)));
	}
	
	@Test
	public void testLinear8()
	{
		String s = "abc" + CRLF + CRLF + "def";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals(s.indexOf("e"), as.getIndex(new Position(2, 1)));
	}
	
	@Test
	public void testLinear9()
	{
		String s = "abc" + CRLF + CRLF + "def";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals(s.indexOf("c"), as.getIndex(new Position(0, 2)));
	}
	
	@Test
	public void testLinear10()
	{
		String s = "abc" + CRLF + CRLF + "def";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals(-1, as.getIndex(new Position(0, 10)));
	}
	
	@Test
	public void testLinear11()
	{
		String s = "abc" + CRLF + CRLF + "def";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals(-1, as.getIndex(new Position(2, 3)));
	}
	
	@Test
	public void testLinear12()
	{
		String s = "abc" + CRLF + CRLF + "def";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals(-1, as.getIndex(new Position(3, 3)));
	}
	
	@Test
	public void testPosition1()
	{
		String s = "abcdef";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals(new Position(0, 2), as.getPosition(2));
		assertEquals(new Position(0, 1), as.positionOf("bc"));
	}
	
	@Test
	public void testPosition2()
	{
		String s = "abcdef";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals(Position.NOWHERE, as.getPosition(-1));
	}
	
	@Test
	public void testPosition3()
	{
		String s = "abcdef";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals(Position.NOWHERE, as.getPosition(6));
	}
	
	@Test
	public void testPosition4()
	{
		String s = "abc" + CRLF + "def";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals(new Position(1, 0), as.getPosition(4));
	}
	
	@Test
	public void testPosition5()
	{
		String s = "abc" + CRLF + CRLF + "def";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals(new Position(1, 0), as.getPosition(4));
		assertEquals(new Position(2, 0), as.getPosition(5));
	}
	
	@Test
	public void testPosition6()
	{
		String s = "abc" + CRLF + "defghijklmno" + CRLF + "pqrstuvw" + CRLF;
		AnnotatedString as = new AnnotatedString(s);
		assertEquals(new Position(1, 1 + CRLF_S), as.getPosition(6));
	}
	
	@Test
	public void testPositionRange()
	{
		String s = "abc" + CRLF + "defghijklmno" + CRLF + "pqrstuvw" + CRLF;
		AnnotatedString as = new AnnotatedString(s);
		assertEquals(new Position(0, 2), as.getPositionRange(2, 6).getStart());
		assertEquals(new Position(1, 1 + CRLF_S), as.getPositionRange(2, 6).getEnd());
		assertEquals(new PositionRange(new Position(0, 2), new Position(1, 1 + CRLF_S)), as.getPositionRange(2, 6));
	}
	
	@Test
	public void testContains()
	{
		String s = "Hello World";
		AnnotatedString as = new AnnotatedString(s);
		assertTrue(as.contains("World"));
	}
	
	@Test
	public void testInsertAt()
	{
		String s = "He World";
		AnnotatedString as = new AnnotatedString(s);
		as.insertAt("llo", 2);
		assertEquals("Hello World", as.toString());
		assertEquals(-1, as.findOriginalIndex(2));
		assertTrue(as.trackToInput(2, 3).isEmpty());
	}
	
	@Test
	public void testGetExplanationNoOp()
	{
		String s = "Hello World";
		AnnotatedString as = new AnnotatedString(s);
		PartNode explanation = as.getExplanation(new Range(0, s.length()));
		assertEquals(as, explanation.getSubject());
		assertEquals(new Range(0, s.length()), explanation.getPart());
	}
	
	@Test
	public void testGetExplanationNoOpNothing()
	{
		String s = "Hello World";
		AnnotatedString as = new AnnotatedString(s);
		PartNode explanation = as.getExplanation(Part.nothing);
		assertEquals(as, explanation.getSubject());
		assertTrue(explanation.getPart() instanceof Part.Nothing);
		assertEquals(1, explanation.getInputArity());
		assertEquals(1, explanation.getOutputArity());
		assertEquals(0, explanation.getInputNodeCount());
		assertEquals(0, explanation.getOutputNodeCount());
	}
	
	@Test
	public void testGetExplanationNoOpAll()
	{
		String s = "Hello World";
		AnnotatedString as = new AnnotatedString(s);
		PartNode explanation = as.getExplanation(Part.all);
		assertEquals(as, explanation.getSubject());
		assertTrue(explanation.getPart() instanceof Part.All);
		assertEquals(1, explanation.getInputArity());
		assertEquals(1, explanation.getOutputArity());
		assertEquals(0, explanation.getInputNodeCount());
		assertEquals(0, explanation.getOutputNodeCount());
	}
	
	@Test
	public void testGetExplanationOfAddedText()
	{
		String s = "He World";
		AnnotatedString as = new AnnotatedString(s);
		as.insertAt("llo", 2);
		Range r = new Range(2, 3);
		assertTrue(as.trackToInput(r).isEmpty());
		PartNode explanation = as.getExplanation(r);
		assertEquals(as, explanation.getSubject());
		assertEquals(r, explanation.getPart());
		assertEquals(1, explanation.getInputArity());
		assertEquals(1, explanation.getOutputArity());
		assertEquals(0, explanation.getInputNodeCount());
		assertEquals(1, explanation.getOutputNodeCount());
		PartNode child = (PartNode)explanation.getOutputLinks(0).iterator().next().getNode();
		assertTrue(child.getPart() instanceof Part.Nothing);
		assertEquals(as, child.getSubject());
		assertEquals(1, child.getInputArity());
		assertEquals(1, child.getOutputArity());
		assertEquals(1, child.getInputNodeCount());
		assertEquals(0, child.getOutputNodeCount());
	}
	
	@Test
	public void testGetExplanationOfReplaceAllSingle()
	{
		String s = "Hello World";
		AnnotatedString as = new AnnotatedString(s);
		as.replaceAll("llo", "");
		assertEquals("He World", as.toString());
		Range r = new Range(0, as.length()-1);
		List<Range> expected_ranges = new ArrayList<>(Arrays.asList(new Range(0, 1), new Range(5, 10)));
		assertEquals(expected_ranges, as.trackToInput(r));
		PartNode explanation = as.getExplanation(r);
		assertEquals(as, explanation.getSubject());
		assertEquals(r, explanation.getPart());
		assertEquals(1, explanation.getInputArity());
		assertEquals(1, explanation.getOutputArity());
		assertEquals(0, explanation.getInputNodeCount());
		assertEquals(1, explanation.getOutputNodeCount());
		AndNode and_node = (AndNode)explanation.getOutputLinks(0).iterator().next().getNode();
		assertEquals(1, and_node.getInputArity());
		assertEquals(1, and_node.getOutputArity());
		assertEquals(1, and_node.getInputNodeCount());
		assertEquals(2, and_node.getOutputNodeCount());
		Iterator<Pin<? extends Node>> it_node = and_node.getOutputLinks(0).iterator();
		for(Range expected_range: expected_ranges){
			PartNode child = (PartNode)it_node.next().getNode();
			assertEquals(1, child.getInputArity());
			assertEquals(1, child.getOutputArity());
			assertEquals(1, child.getInputNodeCount());
			assertEquals(0, child.getOutputNodeCount());
			assertEquals(expected_range, child.getPart());
			assertEquals(as, child.getSubject());
		}
	}
	
	@Test
	public void testSubstring()
	{
		String s = "Hello World";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals("World", as.substring(new Position(0, 6)).toString());
	}
	
	@Test
	public void testSubstring2()
	{
		String s = "abc" + CRLF + "defghijklmno" + CRLF + "pqrstuvw" + CRLF;
		AnnotatedString as = new AnnotatedString(s);
		assertEquals("fghi", as.substring(new Position(1, 2), new Position(1, 6)).toString());
	}
	
	@Test
	public void testSubstring3()
	{
		String s = "Hello World";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals("World", as.substring(6).toString());
	}
	
	@Test
	public void testPosition7()
	{
		String s = "abc" + CRLF;
		AnnotatedString as = new AnnotatedString(s);
		// The CRLF is the last character of the line
		assertEquals(new Position(0, 3), as.getPosition(3));
	}
	
	@Test
	public void testPosition8()
	{
		String s = "abc";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals(new Position(0, 2), as.getPosition(2));
	}
	
	@Test
	public void testPosition9()
	{
		String s = "abc" + CRLF + "def";
		AnnotatedString as = new AnnotatedString(s);
	// The CRLF is the last character of the line
		assertEquals(new Position(0, 3), as.getPosition(3));
	}
	
	@Test
	public void testLineCount1()
	{
		AnnotatedString as = new AnnotatedString("abc");
		assertEquals(1, as.lineCount());
	}
	
	@Test
	public void testLineCount2()
	{
		AnnotatedString as = new AnnotatedString("abc" + CRLF + "defghijklmno" + CRLF + "pqrstuvw" + CRLF);
		assertEquals(4, as.lineCount());
	}
	
	@Test
	public void testLineCount3()
	{
		AnnotatedString as = new AnnotatedString("abc" + CRLF + "defghijklmno" + CRLF + "pqrstuvw");
		assertEquals(3, as.lineCount());
	}
	
	@Test
	public void testGetLines1()
	{
		AnnotatedString as = new AnnotatedString("abc" + CRLF + "defghijklmno" + CRLF + "pqrstuvw");
		List<Line> lines = as.getLines();
		{
			Line l = lines.get(0);
			assertEquals(0, l.getOffset());
		}
		{
			Line l = lines.get(1);
			assertEquals(3 + CRLF_S, l.getOffset());
		}
		{
			Line l = lines.get(2);
			assertEquals(15 + 2 * CRLF_S, l.getOffset());
		}
	}
	
	@Test
	public void testGetLastLine()
	{
		String s = "abc" + CRLF + "defghijklmno" + CRLF + "pqrstuvw";
		Line l = AnnotatedString.getLine(s, 2);
		assertEquals(15 + 2 * CRLF_S, l.getOffset());
	}
	
	@Test
	public void testLastPositionOf()
	{
		AnnotatedString as = new AnnotatedString("abcabc");
		assertEquals(new Position(0, 3), as.lastPositionOf("abc"));
	}
	
	@Test
	public void testIndexOf()
	{
		AnnotatedString as = new AnnotatedString("xabcabc");
		assertEquals(1, as.indexOf("abc"));
	}
	
	@Test
	public void testLastIndexOf()
	{
		AnnotatedString as = new AnnotatedString("xabcabc");
		assertEquals(4, as.lastIndexOf("abc"));
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testNonExistentLastLine()
	{
		String s = "abc" + CRLF + "defghijklmno" + CRLF + "pqrstuvw";
		AnnotatedString.getLine(s, 3);
	}
	
	@Test
	public void testGetLineOf()
	{
		String s = "abc" + CRLF + "defghijklmno" + CRLF + "pqrstuvw";
		assertEquals("defghijklmno", new AnnotatedString(s).getLineOf(3+CRLF.length()).toString());
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testNonExistentIndexLastLineOf()
	{
		String s = "abc" + CRLF + "defghijklmno" + CRLF + "pqrstuvw";
		AnnotatedString.getLineOf(s, -1);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testNonExistentIndexLastLineOf2()
	{
		String s = "abc" + CRLF + "defghijklmno" + CRLF + "pqrstuvw";
		new AnnotatedString(s).getLineOf(s.length());
	}
	
	@Test
	public void testTrimFrom()
	{
		String s = "abc" + CRLF + "defghijklmno" + CRLF + "pqrstuvw";
		AnnotatedString as = new AnnotatedString(s);
		assertEquals("abc" + CRLF + CRLF + "pqrstuvw", new AnnotatedString(s).trimFrom(3+CRLF.length()).toString());
		assertEquals("abc" + CRLF + "defghijkl" + CRLF + "pqrstuvw", new AnnotatedString(s).trimFrom(3+CRLF.length()+9).toString());
		assertEquals("abc" + CRLF + "defghijklmno" + CRLF + "p", new AnnotatedString(s).trimFrom(3+CRLF.length()+12+CRLF.length()+1).toString());
		assertEquals("abc" + CRLF + "defghijklmno" + CRLF + "pqrstuv", new AnnotatedString(s).trimFrom(s.length()-1).toString());
	}
	
	@Test
	public void testConstructor()
	{
		assertEquals("", new AnnotatedString().toString());
	}

	@Test
	public void testInvert1()
	{
		AnnotatedString as = new AnnotatedString("abcdefg");
		as.replaceAll("def", "foo").substring(2, 4);
		assertEquals("cf", as.toString());
		List<Range> ranges = as.trackToInput(0, 0);
		assertEquals(1, ranges.size());
		assertEquals(new Range(2, 2), ranges.get(0));
	}
	
	@Test
	public void testRegex1()
	{
		AnnotatedString original = AnnotatedString.read(new Scanner("$\\frac{x}{y}$ $x*$"));
		AnnotatedString replaced = original.replaceAll("\\$.*?\\$", "X");
		assertEquals("X X", replaced.toString());
		Range r = replaced.findOriginalRange(new Range(0, original.length() - 1));
		assertEquals(0, r.getStart());
		assertEquals(17, r.getEnd());
	}
	
	@Test
	public void testReplace1()
	{
		AnnotatedString original = AnnotatedString.read(new Scanner("$\\frac{x}{y}$ $x*$"));
		original = original.replaceAll("\\\\frac\\{", "");
		original = original.replaceAll(Pattern.quote("$x}{y}$"), "X");
		original = original.replaceAll(Pattern.quote("$x*$"), "X");
		Range r = original.findOriginalRange(new Range(0, original.length() - 1));
		assertEquals(0, r.getStart());
		assertEquals(17, r.getEnd());
	}
	
	@Test
	public void testReplaceSelfBy()
	{
		String s = "$\\frac{x}{y}$ $x*$";
		AnnotatedString as = AnnotatedString.read(new Scanner(s));
		as = as.replaceAll("\\\\frac\\{", "");
		as = as.replaceAll(Pattern.quote("$x}{y}$"), "X");
		as = as.replaceAll(Pattern.quote("$x*$"), "X");
		Range r = as.findOriginalRange(new Range(0, as.length() - 1));
		assertEquals(new Range(0, 17), r);
		List<Range> ranges = as.trackToOutput(0, 1);
		assertEquals(new Range(0, 0), ranges.get(0));
		assertEquals(1, ranges.size());
		Range orig_r = as.findCurrentRange(0, s.length() - 1);
		assertEquals(new Range(0, as.length() -1), orig_r);
	}
	
	@Test
	public void testReplaceSelfByFromSelf()
	{
		assertEquals(new Range(3, 10), AnnotatedString.replaceSelfBy(Part.self, new Range(3, 10)));
	}
	
	@Test
	public void testReplaceSelfByFromNothing()
	{
		assertEquals(Part.nothing, AnnotatedString.replaceSelfBy(Part.nothing, new Range(3, 10)));
	}
	
	@Test
	public void testReplaceSelfByFromAll()
	{
		assertEquals(Part.all, AnnotatedString.replaceSelfBy(Part.all, new Range(3, 10)));
	}
	
	@Test
	public void testReplaceSelfByFromComposedWithoutSelf()
	{
		List<Part> parts = new ArrayList<>(Arrays.asList((Part)new Range(8, 13), (Part)new Range(0, 3), (Part)new Range(4, 6), (Part)new Range(10, 15)));
		ComposedPart composed = new ComposedPart(parts);
		assertEquals(composed, AnnotatedString.replaceSelfBy(composed, new Range(3, 10)));
	}
	
	
	@Test
	public void testReplaceSelfByFromComposedSelfTwice()
	{
		List<Part> parts = new ArrayList<>(Arrays.asList((Part)new Range(8, 13), Part.self, (Part)new Range(0, 3), (Part)new Range(4, 6), (Part)new Range(10, 15), Part.self));
		ComposedPart composed = new ComposedPart(parts);
		List<Part> expected = new ArrayList<>(Arrays.asList((Part)new Range(8, 13), new Range(3, 10), (Part)new Range(0, 3), (Part)new Range(4, 6), (Part)new Range(10, 15), Part.self));
		assertEquals(new ComposedPart(expected), AnnotatedString.replaceSelfBy(composed, new Range(3, 10)));
	}
	
	@Test
	public void testOverlappingSortAndMerge()
	{
		List<Range> ranges = new ArrayList<>(Arrays.asList(new Range(8, 13), new Range(0, 3), new Range(4, 6), new Range(10, 15)));
		AnnotatedString.sortAndMerge(ranges);
		System.err.println(ranges);
		assertEquals(3, ranges.size());
		assertEquals(new Range(0, 3), ranges.get(0));
		assertEquals(new Range(4, 6), ranges.get(1));
		assertEquals(new Range(8, 15), ranges.get(2));
	}
	
	@Test
	public void testUniteRangesEmpty()
	{
		List<Range> ranges = new ArrayList<>();
		Range result = AnnotatedString.uniteSortedRanges(ranges);
		assertEquals(null, result);
	}
	
	@Test
	public void testUniteRangesExample()
	{
		List<Range> ranges = new ArrayList<>(Arrays.asList(new Range(8, 13), new Range(0, 3), new Range(4, 6), new Range(10, 15)));
		AnnotatedString.sortAndMerge(ranges);
		Range result = AnnotatedString.uniteSortedRanges(ranges);
		assertEquals(new Range(0, 15), result);
	}
	
	/*@Test
	public void testAppend1()
	{
		AnnotatedString as1 = new AnnotatedString("foobarbaz").substring(3, 6);
		AnnotatedString as2 = new AnnotatedString("Hello world ");
		as2.append(as1);
		assertEquals("Hello world bar", as2.toString());
		List<Range> ranges = as2.invert(12, 14);
		assertEquals(1, ranges.size());
		assertEquals(new Range(15, 17), ranges.get(0));
	}*/
}
