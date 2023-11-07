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

public class MatchTest
{
    @Test
    public void testToString()
    {
        Match match = new Match("Hello world", 5);
        assertEquals("Hello world (5)", match.toString());
    }
    
}
