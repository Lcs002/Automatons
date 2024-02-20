package com.lvum;

import com.lvum.algorithms.NFAToDFA;
import com.lvum.algorithms.NFAToDFAEpsilon;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Automaton automaton = new Automaton(new HashSet<>(Arrays.asList('0', '1', Automaton.EPSILON)));
        automaton.addTransition("S1", "S2", '1');
        automaton.addTransition("S1", "S1", '0');
        automaton.addTransition("S2", "S2", '1');
        automaton.addTransition("S2", "S1", '0');
        automaton.addTransition("S2", "S3", Automaton.EPSILON);
        automaton.addTransition("S3", "S1", '0');
        automaton.addTransition("S3", "S3", '1');
        automaton.setInitialState("S1");

        System.out.println(automaton);
        System.out.println(automaton.run(new NFAToDFA()));
        System.out.println(automaton.run(new NFAToDFAEpsilon()));
    }
}