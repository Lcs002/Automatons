package com.lvum;

import com.lvum.automaton.algorithms.Equivalency;
import com.lvum.automaton.algorithms.NFAToDFAEpsilon;
import com.lvum.automaton.algorithms.properties.Union;
import com.lvum.automaton.Automaton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnionTest {
    Set<Character> alphabet;
    Automaton automaton1;
    Automaton automaton2;

    @BeforeEach
    void beforeEach() {
        alphabet = Set.of('a', 'b');

        automaton1 = new Automaton(alphabet);
        automaton1.addTransition("A", "B", 'a');
        automaton1.addTransition("A", "D", 'b');
        automaton1.addTransition("B", "B", 'a');
        automaton1.addTransition("B", "C", 'b');
        automaton1.addTransition("C", "B", 'a');
        automaton1.addTransition("C", "C", 'b');
        automaton1.addTransition("D", "D", 'a');
        automaton1.addTransition("D", "D", 'b');
        automaton1.setInitialState("A");
        automaton1.addFinalState("C");

        automaton2 = new Automaton(alphabet);
        automaton2.addTransition("A", "D", 'a');
        automaton2.addTransition("A", "B", 'b');
        automaton2.addTransition("B", "C", 'a');
        automaton2.addTransition("B", "B", 'b');
        automaton2.addTransition("C", "C", 'a');
        automaton2.addTransition("C", "B", 'b');
        automaton2.addTransition("D", "D", 'a');
        automaton2.addTransition("D", "D", 'b');
        automaton2.setInitialState("A");
        automaton2.addFinalState("C");
    }

    @Test
    void correct() {
        // Test 1
        // The union of the two automata must be correct
        Set<Character> expectedAlphabet = Set.of('a', 'b');
        Automaton expected = new Automaton(expectedAlphabet);
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

        Automaton result = automaton1.run(new Union(automaton2)).run(new NFAToDFAEpsilon());
        System.out.println(result);
        assertTrue(result.run(new Equivalency(expected)));
    }
}
