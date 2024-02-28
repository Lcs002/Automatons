package com.lvum.automaton.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lvum.automaton.Automata;

public interface AutomatonDeserializer {
    Automata deserialize(String json) throws JsonProcessingException;
}
