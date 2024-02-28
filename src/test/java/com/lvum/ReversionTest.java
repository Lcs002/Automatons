package com.lvum;

import com.lvum.automaton.Automaton;
import com.lvum.automaton.algorithms.Equivalency;
import com.lvum.automaton.algorithms.NFAToDFAEpsilon;
import com.lvum.automaton.algorithms.properties.Reversion;
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
        Automaton result = automaton.run(new Reversion()).run(new NFAToDFAEpsilon());
        assertTrue(result.run(new Equivalency(expected.run(new NFAToDFAEpsilon()))));
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
        Automaton automaton = new Automaton(Set.of('a', 'b'));
        automaton.addTransition("A", "B", 'a');
        automaton.addTransition("A", "A", 'b');
        automaton.addTransition("B", "B", 'a');
        automaton.addTransition("B", "A", 'b');
        automaton.setInitialState("A");
        automaton.addFinalState("B");
        return automaton;
    }

    static Automaton expected1() {
        Automaton expected = new Automaton(Set.of('a', 'b'));
        expected.addTransition("B", "A", 'a');
        expected.addTransition("B", "B", 'a');
        expected.addTransition("A", "A", 'b');
        expected.addTransition("A", "B", 'b');
        expected.setInitialState("B");
        expected.addFinalState("A");
        return expected;
    }
}
