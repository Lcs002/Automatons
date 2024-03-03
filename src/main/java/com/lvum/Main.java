package com.lvum;

import com.lvum.automaton.Automaton;
import com.lvum.automaton.algorithms.conversion.NFAToDFAEpsilon;
import com.lvum.automaton.machine.AutomatonMachine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        new Main().example();
    }


    private void example() {
        Set<Character> language = new HashSet<>(Arrays.asList('0', '1', Automaton.EPSILON));
        Automaton automaton = new Automaton(language);
        automaton.addTransition("q0", "q1", '0');
        automaton.addTransition("q0", "q2", '1');
        automaton.addTransition("q0", "q3", Automaton.EPSILON);
        automaton.addTransition("q1", "q3", '0');
        automaton.addTransition("q2", "q3", '0');
        automaton.addTransition("q3", "q4", '0');
        automaton.addTransition("q4", "q5", '1');
        automaton.addTransition("q5", "q6", '0');
        automaton.addTransition("q5", "q6", '1');
        automaton.addTransition("q5", "q6", Automaton.EPSILON);
        automaton.addTransition("q6", "q0", Automaton.EPSILON);
        automaton.setInitialState("q0");
        automaton.addFinalState("q0");
        automaton.addFinalState("q6");

        System.out.println(automaton);
        System.out.println(automaton.run(new NFAToDFAEpsilon()));
    }
}