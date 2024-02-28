package com.lvum;

import com.lvum.automaton.Automata;
import com.lvum.automaton.algorithms.Equivalency;
import com.lvum.automaton.algorithms.NFAToDFA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class NFAToDFATest {
    protected Set<Character> language;
    protected Automata automata;

    @BeforeEach
    void beforeEach() {
        language = new HashSet<>(Arrays.asList('a', 'b'));
        automata = new Automata(language);
        automata.addTransition("q0", "q0", 'a');
        automata.addTransition("q0", "q1", 'a');
        automata.addTransition("q0", "q0", 'b');
        automata.addTransition("q1", "q2", 'b');
        automata.setInitialState("q0");
        automata.addFinalState("q2");
    }

    @Test
    public void deterministic() {
        // The resulting automata must not have any state with the same entry more than once
        Automata result = automata.run(new NFAToDFA());

        for (Automata.Transition transition : result.getTransitions()) {
            assertEquals(1, result.getTransitions().stream()
                    .filter(t -> t.from().equals(transition.from()))
                    .filter(t -> t.entry().equals(transition.entry()))
                    .count());
        }
    }

    @Test
    public void correct() {
        // Given the NFA automata:
        // Language: {a, b}
        // | State | a      | b  |
        // | ->q0  | q0, q1 | q0 |
        // | q1    |        | q2 |
        // | *q2   |        |    |
        // The resulting DFA automata must be:
        // | State  | a     | b     |
        // | ->q0   | q0-q1 | q0    |
        // | q0-q1  | q0-q1 | q0-q2 |
        // | *q0-q2 | q0-q1 | q0    |
        // Example from: https://www.geeksforgeeks.org/conversion-from-nfa-to-dfa/

        Set<Character> expectedAlphabet = new HashSet<>(Arrays.asList('a', 'b'));
        Automata expected = new Automata(expectedAlphabet);
        expected.addTransition("q0", "q0-q1", 'a');
        expected.addTransition("q0", "q0", 'b');
        expected.addTransition("q0-q1", "q0-q1", 'a');
        expected.addTransition("q0-q1", "q0-q2", 'b');
        expected.addTransition("q0-q2", "q0-q1", 'a');
        expected.addTransition("q0-q2", "q0", 'b');
        expected.setInitialState("q0");
        expected.addFinalState("q0-q2");

        Automata result = automata.run(new NFAToDFA());

        assertTrue(expected.run(new Equivalency(result)));
    }
}
