package com.lvum.algorithms;

import com.lvum.Automaton;

public interface Algorithm<T> {
    T run(Automaton automaton);
}
