package com.lvum.automaton.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lvum.automaton.Automaton;

public interface AutomatonDeserializer {
    Automaton deserialize(String json) throws JsonProcessingException;
}
