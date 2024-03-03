package com.github;

import com.github.Lcs002.Automatons.automaton.Automaton;
import com.github.Lcs002.Automatons.automaton.algorithms.Equivalency;
import com.github.Lcs002.Automatons.automaton.algorithms.properties.Complement;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComplementTest {

    @ParameterizedTest
    @MethodSource("correctArgs")
    void correct(Automaton automaton, Automaton expected) {
        // The two automaton must be equivalent
        Automaton result = automaton.run(new Complement());
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
        Automaton.Builder builder = new Automaton.Builder();
        builder.setAlphabet(Set.of('a', 'b'));
        builder.addTransition("A", "B", 'a');
        builder.addTransition("A", "B", 'b');
        builder.addTransition("B", "B", 'a');
        builder.addTransition("B", "A", 'b');
        builder.setInitialState("A");
        builder.addFinalState("A");
        return builder.build();
    }

    static Automaton expected1() {
        Automaton.Builder expected = new Automaton.Builder();
        expected.setAlphabet(Set.of('a', 'b'));
        expected.addTransition("A", "B", 'a');
        expected.addTransition("A", "B", 'b');
        expected.addTransition("B", "B", 'a');
        expected.addTransition("B", "A", 'b');
        expected.setInitialState("A");
        expected.addFinalState("B");
        return expected.build();
    }
}
