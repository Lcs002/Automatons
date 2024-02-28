package com.lvum;

import com.lvum.automaton.Automata;
import com.lvum.automaton.algorithms.utility.IsDFA;
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
    void trueWhenNoTransitionsWithSameEntryFromSameState(Automata automata) {
        // The two automata must be equivalent
        Boolean result = automata.run(new IsDFA());
        assertTrue(result);
    }

    @ParameterizedTest
    @MethodSource("falseWhenTransitionsWithSameEntryFromSameStateArgs")
    void falseWhenTransitionsWithSameEntryFromSameState(Automata automata) {
        // The two automata must be equivalent
        Boolean result = automata.run(new IsDFA());
        assertFalse(result);
    }

    @ParameterizedTest
    @MethodSource("falseWhenEpsilonOnAlphabetArgs")
    void falseWhenEpsilonOnAlphabet(Automata automata) {
        // The two automata must be equivalent
        Boolean result = automata.run(new IsDFA());
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


    static Automata trueWhenNoTransitionsWithSameEntryFromSameStateArgs1() {
        Automata automata = new Automata(Set.of('a', 'b'));
        automata.addTransition("A", "B", 'a');
        automata.addTransition("B", "A", 'b');
        automata.setInitialState("A");
        automata.addFinalState("B");
        return automata;
    }

    static Automata falseWhenTransitionsWithSameEntryFromSameStateArgs1() {
        Automata automata = new Automata(Set.of('a', 'b'));
        automata.addTransition("A", "B", 'a');
        automata.addTransition("A", "A", 'a');
        automata.addTransition("B", "A", 'b');
        automata.setInitialState("A");
        automata.addFinalState("B");
        return automata;
    }

    static Automata falseWhenEpsilonOnAlphabetArgs1() {
        Automata automata = new Automata(Set.of('a', 'b', Automata.EPSILON));
        automata.addTransition("A", "B", 'a');
        automata.addTransition("B", "A", 'b');
        automata.setInitialState("A");
        automata.addFinalState("B");
        return automata;
    }
}
