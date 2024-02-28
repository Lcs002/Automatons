package com.lvum;

import com.lvum.automaton.Automata;
import com.lvum.automaton.algorithms.Equivalency;
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
    void correct(Automata automata1, Automata automata2) {
        // The two automata must be equivalent
        assertTrue(automata1.run(new Equivalency(automata2)));
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

    static Automata automaton1a() {
        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b', 'c', 'd'));
        Automata automata = new Automata(alphabet);
        automata.addTransition("q0", "q1", 'a');
        automata.addTransition("q1", "q2", 'b');
        automata.addTransition("q2", "q3", 'c');
        automata.addTransition("q3", "q3", 'd');
        automata.setInitialState("q0");
        automata.addFinalState("q3");
        return automata;
    }

    static Automata automaton1b() {
        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b', 'c', 'd'));
        Automata automata = new Automata(alphabet);
        automata.addTransition("p0", "p1", 'a');
        automata.addTransition("p1", "p2", 'b');
        automata.addTransition("p2", "p3", 'c');
        automata.addTransition("p3", "p3", 'd');
        automata.setInitialState("p0");
        automata.addFinalState("p3");
        return automata;
    }

    static Automata automaton2a() {
        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b'));
        Automata automata = new Automata(alphabet);
        automata.addTransition("q0", "q1", 'a');
        automata.addTransition("q0", "q2", 'b');
        automata.addTransition("q1", "q2", 'a');
        automata.addTransition("q1", "q0", 'b');
        automata.addTransition("q2", "q0", 'a');
        automata.addTransition("q2", "q1", 'b');
        automata.addTransition("q2", "q3", 'a');
        automata.addTransition("q2", "q3", 'b');
        automata.addTransition("q3", "q3", 'a');
        automata.addTransition("q3", "q3", 'b');
        automata.setInitialState("q0");
        automata.addFinalState("q2");
        automata.addFinalState("q3");
        return automata;
    }

    static Automata automaton2b() {
        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b'));
        Automata automata = new Automata(alphabet);
        automata.addTransition("p0", "p1", 'a');
        automata.addTransition("p0", "p2", 'b');
        automata.addTransition("p1", "p2", 'a');
        automata.addTransition("p1", "p0", 'b');
        automata.addTransition("p2", "p0", 'a');
        automata.addTransition("p2", "p1", 'b');
        automata.addTransition("p2", "p3", 'a');
        automata.addTransition("p2", "p3", 'b');
        automata.addTransition("p3", "p3", 'a');
        automata.addTransition("p3", "p3", 'b');
        automata.setInitialState("p0");
        automata.addFinalState("p2");
        automata.addFinalState("p3");
        return automata;
    }

    static Automata automaton3a() {
        Set<Character> alphabet = new HashSet<>(Arrays.asList('c', 'd'));
        Automata automata = new Automata(alphabet);
        automata.addTransition("q1", "q1", 'c');
        automata.addTransition("q1", "q2", 'd');
        automata.addTransition("q2", "q3", 'c');
        automata.addTransition("q2", "q1", 'd');
        automata.addTransition("q3", "q3", 'd');
        automata.addTransition("q3", "q2", 'c');
        automata.setInitialState("q1");
        automata.addFinalState("q1");
        return automata;
    }

    static Automata automaton3b() {
        Set<Character> alphabet = new HashSet<>(Arrays.asList('c', 'd'));
        Automata automata = new Automata(alphabet);
        automata.addTransition("q4", "q4", 'c');
        automata.addTransition("q4", "q5", 'd');
        automata.addTransition("q5", "q6", 'c');
        automata.addTransition("q5", "q4", 'd');
        automata.addTransition("q6", "q6", 'd');
        automata.addTransition("q6", "q7", 'c');
        automata.addTransition("q7", "q6", 'c');
        automata.addTransition("q7", "q4", 'd');
        automata.setInitialState("q4");
        automata.addFinalState("q4");
        return automata;

    }

    static Automata automaton4a() {
        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b'));
        Automata automata = new Automata(alphabet);
        automata.addTransition("A", "B", 'a');
        automata.addTransition("B", "C", 'a');
        automata.addTransition("B", "C", 'b');
        automata.addTransition("C", "D", 'a');
        automata.addTransition("C", "D", 'b');
        automata.addTransition("D", "F", 'a');
        automata.addTransition("D", "E", 'b');
        automata.addTransition("A", "G", 'b');
        automata.addTransition("G", "H", 'a');
        automata.addTransition("G", "H", 'b');
        automata.addTransition("H", "D", 'a');
        automata.addTransition("H", "D", 'b');
        automata.addTransition("F", "F", 'a');
        automata.addTransition("F", "F", 'b');
        automata.addTransition("E", "E", 'a');
        automata.addTransition("E", "E", 'b');
        automata.setInitialState("A");
        automata.addFinalState("F");
        return automata;
    }

    static Automata automaton4b() {
        Set<Character> alphabet = new HashSet<>(Arrays.asList('a', 'b'));
        Automata automata = new Automata(alphabet);
        automata.addTransition("A", "B", 'a');
        automata.addTransition("A", "B", 'b');
        automata.addTransition("B", "C", 'a');
        automata.addTransition("B", "C", 'b');
        automata.addTransition("C", "D", 'a');
        automata.addTransition("C", "D", 'b');
        automata.addTransition("D", "F", 'a');
        automata.addTransition("D", "E", 'b');
        automata.addTransition("F", "F", 'a');
        automata.addTransition("F", "F", 'b');
        automata.addTransition("E", "E", 'a');
        automata.addTransition("E", "E", 'b');
        automata.setInitialState("A");
        automata.addFinalState("F");
        return automata;
    }
}
