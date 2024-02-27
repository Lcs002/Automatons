package com.lvum;

import com.lvum.automaton.algorithms.Equivalency;
import com.lvum.automaton.Automaton;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Should test Equivalency between two DFAs
 * @see <a href="https://www.geeksforgeeks.org/equivalence-of-f-s-a-finite-state-automata/">Test Sources</a>
 */
public class EquivalencyTest {

    @ParameterizedTest
    @MethodSource("correctArgs")
    void correct(Automaton automaton1, Automaton automaton2) {
        // The two automata must be equivalent
        assertTrue(automaton1.run(new Equivalency(automaton2)));
    }

    static Stream<Arguments> correctArgs() {
        return Stream.of(
                Arguments.of(
                        automaton1a(),
                        automaton1b()
                ),
                Arguments.of(
                        automaton2a(),
                        automaton2b()
                ),
                Arguments.of(
                        automaton3a(),
                        automaton3b()
                ),
                Arguments.of(
                        automaton4a(),
                        automaton4b()
                )
        );
    }

    static Automaton automaton1a() {
        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b', 'c', 'd'));
        Automaton automaton = new Automaton(alphabet);
        automaton.addTransition("q0", "q1", 'a');
        automaton.addTransition("q1", "q2", 'b');
        automaton.addTransition("q2", "q3", 'c');
        automaton.addTransition("q3", "q3", 'd');
        automaton.setInitialState("q0");
        automaton.addFinalState("q3");
        return automaton;
    }

    static Automaton automaton1b() {
        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b', 'c', 'd'));
        Automaton automaton = new Automaton(alphabet);
        automaton.addTransition("p0", "p1", 'a');
        automaton.addTransition("p1", "p2", 'b');
        automaton.addTransition("p2", "p3", 'c');
        automaton.addTransition("p3", "p3", 'd');
        automaton.setInitialState("p0");
        automaton.addFinalState("p3");
        return automaton;
    }

    static Automaton automaton2a() {
        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b'));
        Automaton automaton = new Automaton(alphabet);
        automaton.addTransition("q0", "q1", 'a');
        automaton.addTransition("q0", "q2", 'b');
        automaton.addTransition("q1", "q2", 'a');
        automaton.addTransition("q1", "q0", 'b');
        automaton.addTransition("q2", "q0", 'a');
        automaton.addTransition("q2", "q1", 'b');
        automaton.addTransition("q2", "q3", 'a');
        automaton.addTransition("q2", "q3", 'b');
        automaton.addTransition("q3", "q3", 'a');
        automaton.addTransition("q3", "q3", 'b');
        automaton.setInitialState("q0");
        automaton.addFinalState("q2");
        automaton.addFinalState("q3");
        return automaton;
    }

    static Automaton automaton2b() {
        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b'));
        Automaton automaton = new Automaton(alphabet);
        automaton.addTransition("p0", "p1", 'a');
        automaton.addTransition("p0", "p2", 'b');
        automaton.addTransition("p1", "p2", 'a');
        automaton.addTransition("p1", "p0", 'b');
        automaton.addTransition("p2", "p0", 'a');
        automaton.addTransition("p2", "p1", 'b');
        automaton.addTransition("p2", "p3", 'a');
        automaton.addTransition("p2", "p3", 'b');
        automaton.addTransition("p3", "p3", 'a');
        automaton.addTransition("p3", "p3", 'b');
        automaton.setInitialState("p0");
        automaton.addFinalState("p2");
        automaton.addFinalState("p3");
        return automaton;
    }

    static Automaton automaton3a() {
        Set<Character> alphabet = new HashSet<>(Arrays.asList('c', 'd'));
        Automaton automaton = new Automaton(alphabet);
        automaton.addTransition("q1", "q1", 'c');
        automaton.addTransition("q1", "q2", 'd');
        automaton.addTransition("q2", "q3", 'c');
        automaton.addTransition("q2", "q1", 'd');
        automaton.addTransition("q3", "q3", 'd');
        automaton.addTransition("q3", "q2", 'c');
        automaton.setInitialState("q1");
        automaton.addFinalState("q1");
        return automaton;
    }

    static Automaton automaton3b() {
        Set<Character> alphabet = new HashSet<>(Arrays.asList('c', 'd'));
        Automaton automaton = new Automaton(alphabet);
        automaton.addTransition("q4", "q4", 'c');
        automaton.addTransition("q4", "q5", 'd');
        automaton.addTransition("q5", "q6", 'c');
        automaton.addTransition("q5", "q4", 'd');
        automaton.addTransition("q6", "q6", 'd');
        automaton.addTransition("q6", "q7", 'c');
        automaton.addTransition("q7", "q6", 'c');
        automaton.addTransition("q7", "q4", 'd');
        automaton.setInitialState("q4");
        automaton.addFinalState("q4");
        return automaton;

    }

    static Automaton automaton4a() {
        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b'));
        Automaton automaton = new Automaton(alphabet);
        automaton.addTransition("A", "B", 'a');
        automaton.addTransition("B", "C", 'a');
        automaton.addTransition("B", "C", 'b');
        automaton.addTransition("C", "D", 'a');
        automaton.addTransition("C", "D", 'b');
        automaton.addTransition("D", "F", 'a');
        automaton.addTransition("D", "E", 'b');
        automaton.addTransition("A", "G", 'b');
        automaton.addTransition("G", "H", 'a');
        automaton.addTransition("G", "H", 'b');
        automaton.addTransition("H", "D", 'a');
        automaton.addTransition("H", "D", 'b');
        automaton.addTransition("F", "F", 'a');
        automaton.addTransition("F", "F", 'b');
        automaton.addTransition("E", "E", 'a');
        automaton.addTransition("E", "E", 'b');
        automaton.setInitialState("A");
        automaton.addFinalState("F");
        return automaton;
    }

    static Automaton automaton4b() {
        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b'));
        Automaton automaton = new Automaton(alphabet);
        automaton.addTransition("A", "B", 'a');
        automaton.addTransition("A", "B", 'b');
        automaton.addTransition("B", "C", 'a');
        automaton.addTransition("B", "C", 'b');
        automaton.addTransition("C", "D", 'a');
        automaton.addTransition("C", "D", 'b');
        automaton.addTransition("D", "F", 'a');
        automaton.addTransition("D", "E", 'b');
        automaton.addTransition("F", "F", 'a');
        automaton.addTransition("F", "F", 'b');
        automaton.addTransition("E", "E", 'a');
        automaton.addTransition("E", "E", 'b');
        automaton.setInitialState("A");
        automaton.addFinalState("F");
        return automaton;
    }
}
