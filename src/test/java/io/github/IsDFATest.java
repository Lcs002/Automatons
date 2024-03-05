package io.github;

import io.github.lcs002.automatons.automaton.Automaton;
import io.github.lcs002.automatons.automaton.algorithms.utility.IsDFA;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsDFATest {
    @ParameterizedTest
    @MethodSource("trueWhenNoTransitionsWithSameEntryFromSameStateArgs")
    void trueWhenNoTransitionsWithSameEntryFromSameState(Automaton automaton) {
        // The two automaton must be equivalent
        Boolean result = automaton.run(new IsDFA());
        assertTrue(result);
    }

    @ParameterizedTest
    @MethodSource("falseWhenTransitionsWithSameEntryFromSameStateArgs")
    void falseWhenTransitionsWithSameEntryFromSameState(Automaton automaton) {
        // The two automaton must be equivalent
        Boolean result = automaton.run(new IsDFA());
        assertFalse(result);
    }

    @ParameterizedTest
    @MethodSource("falseWhenEpsilonOnAlphabetArgs")
    void falseWhenEpsilonOnAlphabet(Automaton automaton) {
        // The two automaton must be equivalent
        Boolean result = automaton.run(new IsDFA());
        assertFalse(result);
    }


    static Stream<Arguments> trueWhenNoTransitionsWithSameEntryFromSameStateArgs() {
        return Stream.of(
                Arguments.of(
                        trueWhenNoTransitionsWithSameEntryFromSameStateArgs1()
                )
        );
    }

    static Stream<Arguments> falseWhenTransitionsWithSameEntryFromSameStateArgs() {
        return Stream.of(
                Arguments.of(
                        falseWhenTransitionsWithSameEntryFromSameStateArgs1()
                )
        );
    }

    static Stream<Arguments> falseWhenEpsilonOnAlphabetArgs() {
        return Stream.of(
                Arguments.of(
                        falseWhenEpsilonOnAlphabetArgs1()
                )
        );
    }


    static Automaton trueWhenNoTransitionsWithSameEntryFromSameStateArgs1() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("A", "B", 'a')
                .addTransition("B", "A", 'b')
                .setInitialState("A")
                .addFinalState("B")
                .build();
    }

    static Automaton falseWhenTransitionsWithSameEntryFromSameStateArgs1() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b'))
                .addTransition("A", "B", 'a')
                .addTransition("A", "A", 'a')
                .addTransition("B", "A", 'b')
                .setInitialState("A")
                .addFinalState("B")
                .build();
    }

    static Automaton falseWhenEpsilonOnAlphabetArgs1() {
        return new Automaton.Builder()
                .setAlphabet(Set.of('a', 'b', Automaton.EPSILON))
                .addTransition("A", "B", 'a')
                .addTransition("B", "A", 'b')
                .setInitialState("A")
                .addFinalState("B")
                .build();
    }
}
