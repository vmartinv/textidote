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

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ca.uqac.lif.petitpoucet.AndNode;
import ca.uqac.lif.petitpoucet.ComposedPart;
import ca.uqac.lif.petitpoucet.Part;
import ca.uqac.lif.petitpoucet.PartNode;
import ca.uqac.lif.petitpoucet.function.strings.Range;

public class RangeFetcherTest
{
    @Test
    public void testFetchesRangesOfReplaceAllSingle()
    {
        String s = "Hello World";
        AnnotatedString as = new AnnotatedString(s);
        as.replaceAll("llo", "");
        assertEquals("He World", as.toString());
        Range r = new Range(0, as.length()-1);
        List<Range> expected_ranges = new ArrayList<>(Arrays.asList(new Range(0, 1), new Range(5, 10)));
        assertEquals(expected_ranges, as.trackToInput(r));
        PartNode explanation = as.getExplanation(r);
        RangeFetcher fetcher = new RangeFetcher(explanation);
        fetcher.crawl();
        assertEquals(expected_ranges, fetcher.getRanges());
    }
}
