package com.lvum;

import com.lvum.automaton.Automata;
import com.lvum.automaton.algorithms.Equivalency;
import com.lvum.automaton.algorithms.NFAToDFAEpsilon;
import com.lvum.automaton.algorithms.properties.Union;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnionTest {
    Set<Character> alphabet;
    Automata automata1;
    Automata automata2;

    @BeforeEach
    void beforeEach() {
        alphabet = Set.of('a', 'b');

        automata1 = new Automata(alphabet);
        automata1.addTransition("A", "B", 'a');
        automata1.addTransition("A", "D", 'b');
        automata1.addTransition("B", "B", 'a');
        automata1.addTransition("B", "C", 'b');
        automata1.addTransition("C", "B", 'a');
        automata1.addTransition("C", "C", 'b');
        automata1.addTransition("D", "D", 'a');
        automata1.addTransition("D", "D", 'b');
        automata1.setInitialState("A");
        automata1.addFinalState("C");

        automata2 = new Automata(alphabet);
        automata2.addTransition("A", "D", 'a');
        automata2.addTransition("A", "B", 'b');
        automata2.addTransition("B", "C", 'a');
        automata2.addTransition("B", "B", 'b');
        automata2.addTransition("C", "C", 'a');
        automata2.addTransition("C", "B", 'b');
        automata2.addTransition("D", "D", 'a');
        automata2.addTransition("D", "D", 'b');
        automata2.setInitialState("A");
        automata2.addFinalState("C");
    }

    @Test
    void correct() {
        // Test 1
        // The union of the two automata must be correct
        Set<Character> expectedAlphabet = Set.of('a', 'b');
        Automata expected = new Automata(expectedAlphabet);
        expected.addTransition("A²-A¹-A¹-A²", "B¹-D²", 'a');
        expected.addTransition("A²-A¹-A¹-A²", "B²-D¹", 'b');
        expected.addTransition("B¹-D²", "B¹-D²", 'a');
        expected.addTransition("B¹-D²", "C¹-D²", 'b');
        expected.addTransition("B²-D¹", "D¹-C²", 'a');
        expected.addTransition("B²-D¹", "B²-D¹", 'b');
        expected.addTransition("C¹-D²", "B¹-D²", 'a');
        expected.addTransition("C¹-D²", "C¹-D²", 'b');
        expected.addTransition("D¹-C²", "D¹-C²", 'a');
        expected.addTransition("D¹-C²", "B²-D¹", 'b');
        expected.setInitialState("A²-A¹-A¹-A²");
        expected.addFinalState("C¹-D²");
        expected.addFinalState("D¹-C²");

        Automata result = automata1.run(new Union(automata2)).run(new NFAToDFAEpsilon());
        System.out.println(result);
        assertTrue(result.run(new Equivalency(expected)));
    }
}
