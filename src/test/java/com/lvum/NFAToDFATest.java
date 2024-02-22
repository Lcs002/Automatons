package com.lvum;

import com.lvum.algorithms.Equivalency;
import com.lvum.algorithms.NFAToDFA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Inherited;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class NFAToDFATest {
    protected Set<Character> language;
    protected Automaton automaton;

    @BeforeEach
    void beforeEach() {
        language = new HashSet<>(Arrays.asList('a', 'b'));
        automaton = new Automaton(language);
        automaton.addTransition("q0", "q0", 'a');
        automaton.addTransition("q0", "q1", 'a');
        automaton.addTransition("q0", "q0", 'b');
        automaton.addTransition("q1", "q2", 'b');
        automaton.setInitialState("q0");
        automaton.addFinalState("q2");
    }

    @Test
    public void sameLanguage() {
        // The language of the automaton must not change after the conversion
        Automaton result = automaton.run(new NFAToDFA());
        assertEquals(result.getLanguage(), language);
    }

    @Test
    public void deterministic() {
        // The resulting automaton must not have any state with the same entry more than once
        Automaton result = automaton.run(new NFAToDFA());

        for (Automaton.Transition transition : result.getTransitions()) {
            assertEquals(1, result.getTransitions().stream()
                    .filter(t -> t.from().equals(transition.from()))
                    .filter(t -> t.entry().equals(transition.entry()))
                    .count());
        }
    }

    // This equivalency method is good for when both are DFA's
    // TODO A new method should be created to compare NFA's and DFA's
    @Test
    public void equivalent() {
        // The resulting automaton must be equivalent to the original one
        Automaton result = automaton.run(new NFAToDFA());
        assertTrue(automaton.run(new Equivalency(result)));
    }

    @Test
    public void correct() {
        // Given the NFA automaton:
        // Language: {a, b}
        // | State | a      | b  |
        // | ->q0  | q0, q1 | q0 |
        // | q1    |        | q2 |
        // | *q2   |        |    |
        // The resulting DFA automaton must be:
        // | State  | a     | b     |
        // | ->q0   | q0-q1 | q0    |
        // | q0-q1  | q0-q1 | q0-q2 |
        // | *q0-q2 | q0-q1 | q0    |
        // Example from: https://www.geeksforgeeks.org/conversion-from-nfa-to-dfa/

        Automaton expected = new Automaton(new HashSet<>(Arrays.asList('a', 'b')));
        expected.addTransition("q0", "q0-q1", 'a');
        expected.addTransition("q0", "q0", 'b');
        expected.addTransition("q0-q1", "q0-q1", 'a');
        expected.addTransition("q0-q1", "q0-q2", 'b');
        expected.addTransition("q0-q2", "q0-q1", 'a');
        expected.addTransition("q0-q2", "q0", 'b');
        expected.setInitialState("q0");
        expected.addFinalState("q0-q2");

        Automaton result = automaton.run(new NFAToDFA());

        assertTrue(expected.run(new Equivalency(result)));
    }
}
