package com.lvum;

import com.lvum.algorithms.Equivalency;
import com.lvum.algorithms.NFAToDFA;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class NFAToDFATest {
    @Nested
    class SameLanguage {
        @Test
        void test1() {
            // The language of the automaton must not change after the conversion
            Set<Character> language = new HashSet<>(Arrays.asList('0', '1'));
            Automaton automaton = new Automaton(language);

            automaton.addTransition("S1", "S2", '0');
            automaton.addTransition("S1", "S1", '0');
            automaton.addTransition("S1", "S1", '1');
            automaton.addTransition("S2", "S1", '0');
            automaton.addTransition("S2", "S2", '1');
            automaton.setInitialState("S1");
            automaton.addFinalState("S1");

            Automaton result = automaton.run(new NFAToDFA());

            assertEquals(result.getLanguage(), language);
        }
    }

    @Nested
    class Deterministic {
        @Test
        void test1() {
            // The resulting automaton must not have any state with the same entry more than once
            Set<Character> language = new HashSet<>(Arrays.asList('0', '1'));
            Automaton automaton = new Automaton(language);

            automaton.addTransition("S1", "S2", '0');
            automaton.addTransition("S1", "S1", '0');
            automaton.addTransition("S1", "S1", '1');
            automaton.addTransition("S2", "S1", '0');
            automaton.addTransition("S2", "S2", '1');
            automaton.setInitialState("S1");
            automaton.addFinalState("S1");

            Automaton result = automaton.run(new NFAToDFA());

            for (Automaton.Transition transition : result.getTransitions()) {
                assertEquals(1, result.getTransitions().stream()
                        .filter(t -> t.from().equals(transition.from()))
                        .filter(t -> t.entry().equals(transition.entry()))
                        .count());
            }
        }
    }

    @Nested
    class Correct {
        @Test
        void test1() {
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

            Automaton automaton = new Automaton(new HashSet<>(Arrays.asList('a', 'b')));
            automaton.addTransition("q0", "q0", 'a');
            automaton.addTransition("q0", "q1", 'a');
            automaton.addTransition("q0", "q0", 'b');
            automaton.addTransition("q1", "q2", 'b');
            automaton.setInitialState("q0");
            automaton.addFinalState("q2");

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

    @Nested
    class Equivalent {
        @Test
        void test1() {
            // The resulting automaton must be equivalent to the original one
            Automaton original = new Automaton(new HashSet<>(Arrays.asList('0', '1')));

            original.addTransition("S1", "S2", '0');
            original.addTransition("S1", "S1", '0');
            original.addTransition("S1", "S1", '1');
            original.addTransition("S2", "S1", '0');
            original.addTransition("S2", "S2", '1');
            original.setInitialState("S1");
            original.addFinalState("S1");

            Automaton result = original.run(new NFAToDFA());

            // Test
            fail();
        }
    }
}
