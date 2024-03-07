package io.github;

import io.github.lcs002.automatons.automaton.Automaton;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReversionTest {
    @ParameterizedTest
    @MethodSource("correctArgs")
    void correct(Automaton automaton, Automaton expected) {
        // The two automaton must be equivalent
        Automaton result = automaton.reverse().nfaEpsilonToDfa();
        assertTrue(result.isEquivalent(expected.nfaEpsilonToDfa()));
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
                .addTransition("A", "A", 'b')
                .addTransition("B", "B", 'a')
                .addTransition("B", "A", 'b')
                .setInitialState("A")
                .addFinalState("B")
                .build();
    }

    static Automaton expected1() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("B", "A", 'a')
                .addTransition("B", "B", 'a')
                .addTransition("A", "A", 'b')
                .addTransition("A", "B", 'b')
                .setInitialState("B")
                .addFinalState("A")
                .build();
    }
}
