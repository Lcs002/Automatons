package com.lvum;

import com.lvum.automaton.Automaton;
import com.lvum.automaton.serialize.AutomatonDeserializer;
import com.lvum.automaton.serialize.AutomatonSerializer;
import com.lvum.automaton.serialize.json.AutomatonJsonDeserializer;
import com.lvum.automaton.serialize.json.AutomatonJsonSerializer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        new Main().example();
    }


    private void example() {
        Set<Character> language = new HashSet<>(Arrays.asList('0', '1'));
        Automaton automaton = new Automaton(language);

        automaton.addTransition("S1", "S2", '0');
        automaton.addTransition("S1", "S1", '1');
        automaton.addTransition("S2", "S1", '0');
        automaton.addTransition("S2", "S2", '1');
        automaton.addTransition("S3", "S2", '1');
        automaton.addTransition("S3", "S1", '0');
        automaton.setInitialState("S1");

        AutomatonSerializer serializer = new AutomatonJsonSerializer();
        AutomatonDeserializer deserializer = new AutomatonJsonDeserializer();
        String json = null;

        try {
            json = serializer.serialize(automaton);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (json != null) {
            try {
                Automaton deserializedAutomaton = deserializer.deserialize(json);
                System.out.println(deserializedAutomaton);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}