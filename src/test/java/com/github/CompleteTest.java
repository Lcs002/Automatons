package com.github;

import com.github.lcs002.automatons.automaton.Automaton;
import com.github.lcs002.automatons.automaton.algorithms.Complete;
import com.github.lcs002.automatons.automaton.algorithms.Equivalency;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompleteTest {

    @ParameterizedTest
    @MethodSource("correctArgs")
    void correct(Automaton automaton, Automaton expected) {
        // The two automaton must be equivalent
        Automaton result = automaton.run(new Complete());
        assertTrue(result.run(new Equivalency(expected)));
    }


    static Stream<Arguments> correctArgs() {
        return Stream.of(
                Arguments.of(
                        automaton1(),
                        expected1()
                )
        );
    }


    static Automaton automaton1() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("A", "B", 'a')
                .addTransition("B", "C", 'b')
                .addTransition("C", "A", 'a')
                .setInitialState("A")
                .addFinalState("C")
                .build();
    }

    static Automaton expected1() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("A", "B", 'a')
                .addTransition("B", "C", 'b')
                .addTransition("C", "A", 'a')
                .addTransition("A", Automaton.EMPTY_STATE, 'b')
                .addTransition("B", Automaton.EMPTY_STATE, 'a')
                .addTransition("C", Automaton.EMPTY_STATE, 'b')
                .addTransition(Automaton.EMPTY_STATE, Automaton.EMPTY_STATE, 'a')
                .addTransition(Automaton.EMPTY_STATE, Automaton.EMPTY_STATE, 'b')
                .setInitialState("A")
                .addFinalState("C")
                .build();
    }
}
