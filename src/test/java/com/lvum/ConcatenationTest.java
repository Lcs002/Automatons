package com.lvum;

import com.lvum.automaton.Automaton;
import com.lvum.automaton.algorithms.Equivalency;
import com.lvum.automaton.algorithms.conversion.NFAToDFAEpsilon;
import com.lvum.automaton.algorithms.properties.Concatenation;
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


    private static Object automaton1a() {
        Set<Character> alphabet = Set.of('a', 'b');
        Automaton automaton = new Automaton(alphabet);
        automaton.addTransition("A", "B", 'b');
        automaton.addTransition("A", "C", 'a');
        automaton.addTransition("B", "B", 'a');
        automaton.addTransition("B", "B", 'b');
        automaton.addTransition("C", "C", 'a');
        automaton.addTransition("C", "C", 'b');
        automaton.setInitialState("A");
        automaton.addFinalState("C");
        return automaton;
    }

    private static Object automaton1b() {
        Set<Character> alphabet = Set.of('a', 'b');
        Automaton automaton = new Automaton(alphabet);
        automaton.addTransition("A", "B", 'b');
        automaton.addTransition("A", "A", 'a');
        automaton.addTransition("B", "A", 'a');
        automaton.addTransition("B", "B", 'b');
        automaton.setInitialState("A");
        automaton.addFinalState("B");
        return automaton;
    }

    private static Object expected1() {
        Set<Character> alphabet = Set.of('a', 'b');
        Automaton automaton = new Automaton(alphabet);
        automaton.addTransition("A", "B", 'a');
        automaton.addTransition("A", "D", 'b');
        automaton.addTransition("B", "B", 'a');
        automaton.addTransition("B", "C", 'b');
        automaton.addTransition("C", "B", 'a');
        automaton.addTransition("C", "C", 'b');
        automaton.addTransition("D", "D", 'a');
        automaton.addTransition("D", "D", 'b');
        automaton.setInitialState("A");
        automaton.addFinalState("C");
        return automaton;
    }


    private static Object automaton2a() {
        Set<Character> alphabet = Set.of('a', 'b');
        Automaton automaton = new Automaton(alphabet);
        automaton.addTransition("q0", "q1", 'a');
        automaton.addTransition("q1", "q2", 'b');
        automaton.setInitialState("q0");
        automaton.addFinalState("q2");
        return automaton;
    }

    private static Object automaton2b() {
        Set<Character> alphabet = Set.of('a', 'b');
        Automaton automaton = new Automaton(alphabet);
        automaton.addTransition("p0", "p1", 'a');
        automaton.setInitialState("p0");
        automaton.addFinalState("p1");
        return automaton;
    }

    private static Object expected2() {
        Set<Character> alphabet = Set.of('a', 'b');
        Automaton automaton = new Automaton(alphabet);
        automaton.addTransition("q0", "q1", 'a');
        automaton.addTransition("q1", "q2-p0", 'b');
        automaton.addTransition("q2-p0", "p1", 'a');
        automaton.setInitialState("q0");
        automaton.addFinalState("p1");
        return automaton;
    }


    private static Object automaton3a() {
        Set<Character> alphabet = Set.of('a', 'b', 'c');
        Automaton automaton = new Automaton(alphabet);
        automaton.addTransition("q0", "q1", 'a');
        automaton.addTransition("q0", "q3", 'b');
        automaton.addTransition("q0", "q2", 'c');
        automaton.addTransition("q1", "q3", 'a');
        automaton.addTransition("q1", "q3", 'b');
        automaton.addTransition("q1", "q3", 'c');
        automaton.addTransition("q2", "q3", 'a');
        automaton.addTransition("q2", "q1", 'b');
        automaton.addTransition("q2", "q3", 'c');
        automaton.addTransition("q3", "q3", 'a');
        automaton.addTransition("q3", "q3", 'b');
        automaton.addTransition("q3", "q3", 'c');
        automaton.setInitialState("q0");
        automaton.addFinalState("q1");
        automaton.addFinalState("q2");
        return automaton;
    }

    private static Object automaton3b() {
        Set<Character> alphabet = Set.of('a', 'b', 'c');
        Automaton automaton = new Automaton(alphabet);
        automaton.addTransition("p0", "p3", 'a');
        automaton.addTransition("p0", "p4", 'b');
        automaton.addTransition("p0", "p4", 'c');
        automaton.addTransition("p3", "p4", 'a');
        automaton.addTransition("p3", "p2", 'b');
        automaton.addTransition("p3", "p4", 'c');
        automaton.addTransition("p4", "p4", 'a');
        automaton.addTransition("p4", "p4", 'b');
        automaton.addTransition("p4", "p4", 'c');
        automaton.addTransition("p2", "p4", 'a');
        automaton.addTransition("p2", "p4", 'b');
        automaton.addTransition("p2", "p1", 'c');
        automaton.addTransition("p1", "p4", 'a');
        automaton.addTransition("p1", "p4", 'b');
        automaton.addTransition("p1", "p4", 'c');
        automaton.setInitialState("p0");
        automaton.addFinalState("p1");
        automaton.addFinalState("p0");
        return automaton;
    }

    private static Object expected3() {
        Set<Character> alphabet =Set.of('a', 'b', 'c');
        Automaton automaton = new Automaton(alphabet);
        automaton.addTransition("q0", "q1-p0", 'a');
        automaton.addTransition("q0", "q3", 'b');
        automaton.addTransition("q0", "q2-p0", 'c');

        automaton.addTransition("q1-p0", "q3-p3", 'a');
        automaton.addTransition("q1-p0", "q3-p4", 'b');
        automaton.addTransition("q1-p0", "q3-p4", 'c');

        automaton.addTransition("q3-p3", "q3-p4", 'a');
        automaton.addTransition("q3-p3", "q3-p2", 'b');
        automaton.addTransition("q3-p3", "q3-p4", 'c');

        automaton.addTransition("q3-p4", "q3-p4", 'a');
        automaton.addTransition("q3-p4", "q3-p4", 'b');
        automaton.addTransition("q3-p4", "q3-p4", 'c');

        automaton.addTransition("q3-p2", "q3-p4", 'a');
        automaton.addTransition("q3-p2", "q3-p4", 'b');
        automaton.addTransition("q3-p2", "q3-p1", 'c');

        automaton.addTransition("q3-p1", "q3-p4", 'a');
        automaton.addTransition("q3-p1", "q3-p4", 'b');
        automaton.addTransition("q3-p1", "q3-p4", 'c');

        automaton.addTransition("q2-p0", "q3-p3", 'a');
        automaton.addTransition("q2-p0", "q1-p4", 'b');
        automaton.addTransition("q2-p0", "q3-p4", 'c');

        automaton.addTransition("q1-p4", "q3-p4", 'a');
        automaton.addTransition("q1-p4", "q3-p4", 'b');
        automaton.addTransition("q1-p4", "q3-p4", 'c');

        automaton.addTransition("q3", "q3", 'a');
        automaton.addTransition("q3", "q3", 'b');
        automaton.addTransition("q3", "q3", 'c');
        automaton.setInitialState("q0");
        automaton.addFinalState("q1-p0");
        automaton.addFinalState("q2-p0");
        automaton.addFinalState("q3-p1");
        return automaton;
    }


    static Automaton automaton4a() {
        Set<Character> alphabet = Set.of('a', 'b');
        Automaton automaton = new Automaton(alphabet);
        automaton.addTransition("A", "B", 'a');
        automaton.addTransition("B", "B", 'b');
        automaton.setInitialState("A");
        automaton.addFinalState("A");
        automaton.addFinalState("B");
        return automaton;
    }

    static Automaton automaton4b() {
        Set<Character> alphabet = Set.of('a', 'b');
        Automaton automaton = new Automaton(alphabet);
        automaton.addTransition("A", "A", 'a');
        automaton.addTransition("A", "B", 'b');
        automaton.addTransition("B", "B", 'a');
        automaton.addTransition("B", "B", 'b');
        automaton.setInitialState("A");
        automaton.addFinalState("A");
        return automaton;
    }

    static Automaton expected4() {
        Set<Character> alphabet = Set.of('a', 'b');
        Automaton automaton = new Automaton(alphabet);
        automaton.addTransition("A1-A2", "B1-A2", 'a');
        automaton.addTransition("A1-A2", "B2", 'b');
        automaton.addTransition("B1-A2", "A2", 'a');
        automaton.addTransition("B1-A2", "B1-A2-B2", 'b');
        automaton.addTransition("B1-A2-B2", "A2-B2", 'a');
        automaton.addTransition("B1-A2-B2", "B1-A2-B2", 'b');
        automaton.addTransition("A2-B2", "A2-B2", 'a');
        automaton.addTransition("A2-B2", "B2", 'b');
        automaton.addTransition("B2", "B2", 'a');
        automaton.addTransition("B2", "B2", 'b');
        automaton.addTransition("A2", "A2", 'a');
        automaton.addTransition("A2", "B2", 'b');
        automaton.setInitialState("A1-A2");
        automaton.addFinalState("B1-A2");
        automaton.addFinalState("A1-A2");
        automaton.addFinalState("A2-B2");
        automaton.addFinalState("B1-A2-B2");
        automaton.addFinalState("A2");
        return automaton;
    }
}
