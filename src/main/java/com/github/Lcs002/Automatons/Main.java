package com.github.Lcs002.Automatons;

import com.github.Lcs002.Automatons.automaton.Automaton;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        new Main().example();
    }


    private void example() {
        Set<Character> alphabet = new HashSet<>(Arrays.asList('0', '1'));
        Automaton.Builder builder = new Automaton.Builder();
        Automaton automaton = builder.build();
    }
}