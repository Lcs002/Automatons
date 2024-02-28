package com.lvum.automaton.serialize.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lvum.automaton.Automata;
import com.lvum.automaton.serialize.AutomatonSerializer;

/**
 * <h1>Automata Serializer</h1>
 * <p>Class that serializes an Automata object into a JSON string.</p>
 */
public class AutomatonJsonSerializer implements AutomatonSerializer {

    @Override
    public String serialize(Automata automata) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(automata);
    }

    @Override
    public String serializePretty(Automata automata) throws JsonProcessingException {
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(automata);
    }
}
