package com.lvum;

import com.lvum.automaton.Automata;
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
        Automata automata = new Automata(language);

        automata.addTransition("S1", "S2", '0');
        automata.addTransition("S1", "S1", '1');
        automata.addTransition("S2", "S1", '0');
        automata.addTransition("S2", "S2", '1');
        automata.addTransition("S3", "S2", '1');
        automata.addTransition("S3", "S1", '0');
        automata.setInitialState("S1");

        AutomatonSerializer serializer = new AutomatonJsonSerializer();
        AutomatonDeserializer deserializer = new AutomatonJsonDeserializer();
        String json = null;

        try {
            json = serializer.serialize(automata);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (json != null) {
            try {
                Automata deserializedAutomata = deserializer.deserialize(json);
                System.out.println(deserializedAutomata);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}