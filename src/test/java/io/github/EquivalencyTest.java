package io.github;

import io.github.lcs002.automatons.automaton.Automaton;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
        // The two automaton must be equivalent
        assertTrue(automaton1.isEquivalent(automaton2));
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
        return new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b', 'c', 'd'))
                .addTransition("q0", "q1", 'a')
                .addTransition("q1", "q2", 'b')
                .addTransition("q2", "q3", 'c')
                .addTransition("q3", "q3", 'd')
                .setInitialState("q0")
                .addFinalState("q3")
                .build();
    }

    static Automaton automaton1b() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b', 'c', 'd'))
                .addTransition("p0", "p1", 'a')
                .addTransition("p1", "p2", 'b')
                .addTransition("p2", "p3", 'c')
                .addTransition("p3", "p3", 'd')
                .setInitialState("p0")
                .addFinalState("p3")
                .build();
    }

    static Automaton automaton2a() {
        return new Automaton.Builder().setAlphabet(Set.of('a', 'b'))
                .addTransition("q0", "q1", 'a')
                .addTransition("q0", "q2", 'b')
                .addTransition("q1", "q2", 'a')
                .addTransition("q1", "q0", 'b')
                .addTransition("q2", "q0", 'a')
                .addTransition("q2", "q1", 'b')
                .addTransition("q2", "q3", 'a')
                .addTransition("q2", "q3", 'b')
                .addTransition("q3", "q3", 'a')
                .addTransition("q3", "q3", 'b')
                .setInitialState("q0")
                .addFinalState("q2")
                .addFinalState("q3")
                .build();
    }

    static Automaton automaton2b() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("p0", "p1", 'a')
                .addTransition("p0", "p2", 'b')
                .addTransition("p1", "p2", 'a')
                .addTransition("p1", "p0", 'b')
                .addTransition("p2", "p0", 'a')
                .addTransition("p2", "p1", 'b')
                .addTransition("p2", "p3", 'a')
                .addTransition("p2", "p3", 'b')
                .addTransition("p3", "p3", 'a')
                .addTransition("p3", "p3", 'b')
                .setInitialState("p0")
                .addFinalState("p2")
                .addFinalState("p3")
                .build();
    }

    static Automaton automaton3a() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('c', 'd'))
                .addTransition("q1", "q1", 'c')
                .addTransition("q1", "q2", 'd')
                .addTransition("q2", "q3", 'c')
                .addTransition("q2", "q1", 'd')
                .addTransition("q3", "q3", 'd')
                .addTransition("q3", "q2", 'c')
                .setInitialState("q1")
                .addFinalState("q1")
                .build();
    }

    static Automaton automaton3b() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('c', 'd'))
                .addTransition("q4", "q4", 'c')
                .addTransition("q4", "q5", 'd')
                .addTransition("q5", "q6", 'c')
                .addTransition("q5", "q4", 'd')
                .addTransition("q6", "q6", 'd')
                .addTransition("q6", "q7", 'c')
                .addTransition("q7", "q6", 'c')
                .addTransition("q7", "q4", 'd')
                .setInitialState("q4")
                .addFinalState("q4")
                .build();
    }

    static Automaton automaton4a() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("A", "B", 'a')
                .addTransition("B", "C", 'a')
                .addTransition("B", "C", 'b')
                .addTransition("C", "D", 'a')
                .addTransition("C", "D", 'b')
                .addTransition("D", "F", 'a')
                .addTransition("D", "E", 'b')
                .addTransition("A", "G", 'b')
                .addTransition("G", "H", 'a')
                .addTransition("G", "H", 'b')
                .addTransition("H", "D", 'a')
                .addTransition("H", "D", 'b')
                .addTransition("F", "F", 'a')
                .addTransition("F", "F", 'b')
                .addTransition("E", "E", 'a')
                .addTransition("E", "E", 'b')
                .setInitialState("A")
                .addFinalState("F")
                .build();
    }

    static Automaton automaton4b() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("A", "B", 'a')
                .addTransition("A", "B", 'b')
                .addTransition("B", "C", 'a')
                .addTransition("B", "C", 'b')
                .addTransition("C", "D", 'a')
                .addTransition("C", "D", 'b')
                .addTransition("D", "F", 'a')
                .addTransition("D", "E", 'b')
                .addTransition("F", "F", 'a')
                .addTransition("F", "F", 'b')
                .addTransition("E", "E", 'a')
                .addTransition("E", "E", 'b')
                .setInitialState("A")
                .addFinalState("F")
                .build();
    }
}
