/*
    TeXtidote, a linter for LaTeX documents
    Copyright (C) 2023  Sylvain Hallé

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

import static org.junit.Assert.*;

import org.junit.Test;

import ca.uqac.lif.petitpoucet.function.strings.Range;

public class PositionRangeTest
{
    @Test
    public void testCompareToDifferentStarts()
    {
        PositionRange position1 = new PositionRange(new Position(1, 1), new Position(1, 3));
        PositionRange position2 = new PositionRange(new Position(2, 3), new Position(2, 5));
        assertEquals(0, position1.compareTo(position1));
        assertEquals(-1, position1.compareTo(position2));
        assertEquals(1, position2.compareTo(position1));
    }
    
    @Test
    public void testCompareToSameStarts()
    {
        PositionRange position1 = new PositionRange(new Position(0, 1), new Position(1, 3));
        PositionRange position2 = new PositionRange(new Position(0, 1), new Position(2, 5));
        assertEquals(0, position1.compareTo(position1));
        assertEquals(-1, position1.compareTo(position2));
        assertEquals(1, position2.compareTo(position1));
    }
    
    @Test
    public void testEqualsSameStarts()
    {
        PositionRange position1 = new PositionRange(new Position(0, 1), new Position(1, 3));
        PositionRange position2 = new PositionRange(new Position(0, 1), new Position(2, 5));
        assertFalse(position1.equals(null));
        assertFalse(position1.equals(new Range(0, 1)));
        assertTrue(position1.equals(position1));
        assertFalse(position1.equals(position2));
    }
    
    
    @Test
    public void testEqualsSameEnds()
    {
        PositionRange position1 = new PositionRange(new Position(1, 3), new Position(5, 1));
        PositionRange position2 = new PositionRange(new Position(2, 5), new Position(5, 1));
        assertFalse(position1.equals(position2));
    }
    
    @Test
    public void testHashCode()
    {
        PositionRange position1 = new PositionRange(new Position(0, 0), new Position(0, 1));
        PositionRange position2 = new PositionRange(new Position(0, 0), new Position(1, 0));
        assertTrue(position1.hashCode()!=position2.hashCode());
    }
}
