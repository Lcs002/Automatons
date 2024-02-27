package com.lvum;

import com.lvum.automaton.algorithms.Equivalency;
import com.lvum.automaton.algorithms.NFAToDFA;
import com.lvum.automaton.Automaton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        Set<Character> expectedAlphabet = new HashSet<>(Arrays.asList('a', 'b'));
        Automaton expected = new Automaton(expectedAlphabet);
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
