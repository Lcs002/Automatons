package com.lvum;

import com.lvum.algorithms.Equivalency;
import com.lvum.algorithms.NFAToDFA;
import com.lvum.algorithms.NFAToDFAEpsilon;
import com.lvum.algorithms.properties.Union;
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
        Automaton expected = new Automaton(alphabet);
        expected.addTransition("S", "A'", Automaton.EPSILON);
        expected.addTransition("A'", "B'", 'a');
        expected.addTransition("A'", "D'", 'b');
        expected.addTransition("B'", "B'", 'a');
        expected.addTransition("B'", "C'", 'b');
        expected.addTransition("C'", "B'", 'a');
        expected.addTransition("C'", "C'", 'b');
        expected.addTransition("D'", "D'", 'a');
        expected.addTransition("D'", "D'", 'b');
        expected.addTransition("S", "A''", Automaton.EPSILON);
        expected.addTransition("A''", "D''", 'a');
        expected.addTransition("A''", "B''", 'b');
        expected.addTransition("B''", "C''", 'a');
        expected.addTransition("B''", "B''", 'b');
        expected.addTransition("C''", "C''", 'a');
        expected.addTransition("C''", "B''", 'b');
        expected.addTransition("D''", "D''", 'a');
        expected.addTransition("D''", "D''", 'b');
        expected.setInitialState("S");
        expected.addFinalState("C'");
        expected.addFinalState("C''");
        // The union of the two automata is an NFA
        Automaton result = automaton1.run(new Union(automaton2)).run(new NFAToDFAEpsilon());
        System.out.println(result);
        assertTrue(expected.run(new NFAToDFAEpsilon()).run(new Equivalency(result)));
    }
}
