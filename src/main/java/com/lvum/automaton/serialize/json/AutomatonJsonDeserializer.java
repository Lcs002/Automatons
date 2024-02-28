package com.lvum.automaton.serialize.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvum.automaton.Automata;
import com.lvum.automaton.serialize.AutomatonDeserializer;

/**
 * <h1>Automata Deserializer</h1>
 * <p>Class that deserializes a JSON string into an Automata object.</p>
 */
public class AutomatonJsonDeserializer implements AutomatonDeserializer {
    @Override
    public Automata deserialize(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, Automata.class);
    }
}
