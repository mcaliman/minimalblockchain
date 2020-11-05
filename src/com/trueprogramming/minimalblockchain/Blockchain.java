package com.trueprogramming.minimalblockchain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

public class Blockchain extends ArrayList<Block> {

    private static final Logger LOG = Logger.getLogger(Blockchain.class.getName());

    public Blockchain(int initialCapacity) {
        super(initialCapacity);
    }

    public Blockchain() {
    }

    public Blockchain(Collection<? extends Block> c) {
        super(c);
    }

}
