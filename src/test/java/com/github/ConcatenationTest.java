package com.github;

import com.github.lcs002.automatons.automaton.Automaton;
import com.github.lcs002.automatons.automaton.algorithms.Equivalency;
import com.github.lcs002.automatons.automaton.algorithms.conversion.NFAToDFAEpsilon;
import com.github.lcs002.automatons.automaton.algorithms.properties.Concatenation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConcatenationTest {

    @ParameterizedTest
    @MethodSource("correctArgs")
    void correct(Automaton automaton1, Automaton automaton2, Automaton expected) {
        // The two automaton must be equivalent
        Automaton resultDFA = automaton1.run(new Concatenation(automaton2)).run(new NFAToDFAEpsilon());
        assertTrue(resultDFA.run(new Equivalency(expected)));
    }

    static Stream<Arguments> correctArgs() {
        return Stream.of(
                // https://www.geeksforgeeks.org/concatenation-process-in-dfa/
                Arguments.of(
                        automaton1a(),
                        automaton1b(),
                        expected1()
                ),
                // https://www.tutorialspoint.com/explain-the-concatenation-process-in-dfa
                Arguments.of(
                        automaton2a(),
                        automaton2b(),
                        expected2()
                ),
                Arguments.of(
                        automaton4a(),
                        automaton4b(),
                        expected4()
                )
        );
    }


    private static Automaton automaton1a() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("A", "B", 'b')
                .addTransition("A", "C", 'a')
                .addTransition("B", "B", 'a')
                .addTransition("B", "B", 'b')
                .addTransition("C", "C", 'a')
                .addTransition("C", "C", 'b')
                .setInitialState("A")
                .addFinalState("C")
                .build();
    }

    private static Automaton automaton1b() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("A", "B", 'b')
                .addTransition("A", "A", 'a')
                .addTransition("B", "A", 'a')
                .addTransition("B", "B", 'b')
                .setInitialState("A")
                .addFinalState("B")
                .build();
    }

    private static Automaton expected1() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("A", "B", 'a')
                .addTransition("A", "D", 'b')
                .addTransition("B", "B", 'a')
                .addTransition("B", "C", 'b')
                .addTransition("C", "B", 'a')
                .addTransition("C", "C", 'b')
                .addTransition("D", "D", 'a')
                .addTransition("D", "D", 'b')
                .setInitialState("A")
                .addFinalState("C")
                .build();
    }


    private static Automaton automaton2a() {
        return new Automaton.Builder().setAlphabet(Set.of('a', 'b'))
                .addTransition("q0", "q1", 'a')
                .addTransition("q1", "q2", 'b')
                .setInitialState("q0")
                .addFinalState("q2")
                .build();
    }

    private static Automaton automaton2b() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("p0", "p1", 'a')
                .setInitialState("p0")
                .addFinalState("p1")
                .build();
    }

    private static Automaton expected2() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("q0", "q1", 'a')
                .addTransition("q1", "q2-p0", 'b')
                .addTransition("q2-p0", "p1", 'a')
                .setInitialState("q0")
                .addFinalState("p1")
                .build();
    }


    static Automaton automaton4a() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("A", "B", 'a')
                .addTransition("B", "B", 'b')
                .setInitialState("A")
                .addFinalState("A")
                .addFinalState("B")
                .build();
    }

    static Automaton automaton4b() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("A", "A", 'a')
                .addTransition("A", "B", 'b')
                .addTransition("B", "B", 'a')
                .addTransition("B", "B", 'b')
                .setInitialState("A")
                .addFinalState("A")
                .build();
    }

    static Automaton expected4() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("A1-A2", "B1-A2", 'a')
                .addTransition("A1-A2", "B2", 'b')
                .addTransition("B1-A2", "A2", 'a')
                .addTransition("B1-A2", "B1-A2-B2", 'b')
                .addTransition("B1-A2-B2", "A2-B2", 'a')
                .addTransition("B1-A2-B2", "B1-A2-B2", 'b')
                .addTransition("A2-B2", "A2-B2", 'a')
                .addTransition("A2-B2", "B2", 'b')
                .addTransition("B2", "B2", 'a')
                .addTransition("B2", "B2", 'b')
                .addTransition("A2", "A2", 'a')
                .addTransition("A2", "B2", 'b')
                .setInitialState("A1-A2")
                .addFinalState("B1-A2")
                .addFinalState("A1-A2")
                .addFinalState("A2-B2")
                .addFinalState("B1-A2-B2")
                .addFinalState("A2")
                .build();
    }
}
