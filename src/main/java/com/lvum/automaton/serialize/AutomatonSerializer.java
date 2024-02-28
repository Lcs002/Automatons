package com.lvum.automaton.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lvum.automaton.Automata;

public interface AutomatonSerializer {
    String serialize(Automata automata) throws JsonProcessingException;
    String serializePretty(Automata automata) throws JsonProcessingException;
}
