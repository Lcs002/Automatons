package com.lvum;

import com.lvum.automaton.algorithms.utility.IsDFA;
import com.lvum.automaton.Automaton;
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
        // The two automata must be equivalent
        Boolean result = automaton.run(new IsDFA());
        assertTrue(result);
    }

    @ParameterizedTest
    @MethodSource("falseWhenTransitionsWithSameEntryFromSameStateArgs")
    void falseWhenTransitionsWithSameEntryFromSameState(Automaton automaton) {
        // The two automata must be equivalent
        Boolean result = automaton.run(new IsDFA());
        assertFalse(result);
    }

    @ParameterizedTest
    @MethodSource("falseWhenEpsilonOnAlphabetArgs")
    void falseWhenEpsilonOnAlphabet(Automaton automaton) {
        // The two automata must be equivalent
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
        Automaton automaton = new Automaton(Set.of('a', 'b'));
        automaton.addTransition("A", "B", 'a');
        automaton.addTransition("B", "A", 'b');
        automaton.setInitialState("A");
        automaton.addFinalState("B");
        return automaton;
    }

    static Automaton falseWhenTransitionsWithSameEntryFromSameStateArgs1() {
        Automaton automaton = new Automaton(Set.of('a', 'b'));
        automaton.addTransition("A", "B", 'a');
        automaton.addTransition("A", "A", 'a');
        automaton.addTransition("B", "A", 'b');
        automaton.setInitialState("A");
        automaton.addFinalState("B");
        return automaton;
    }

    static Automaton falseWhenEpsilonOnAlphabetArgs1() {
        Automaton automaton = new Automaton(Set.of('a', 'b', Automaton.EPSILON));
        automaton.addTransition("A", "B", 'a');
        automaton.addTransition("B", "A", 'b');
        automaton.setInitialState("A");
        automaton.addFinalState("B");
        return automaton;
    }
}
