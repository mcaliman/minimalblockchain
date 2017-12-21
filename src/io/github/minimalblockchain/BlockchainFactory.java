package io.github.minimalblockchain;

import java.util.Date;
import java.util.logging.Logger;

public class BlockchainFactory {

    private static final Logger LOG = Logger.getLogger(BlockchainFactory.class.getName());

    public BlockchainFactory() {
    }

    public Block getGenesisBlock() {
        return new Block(0L, "GenesisBlock", timestamp(), hash(), "0");
    }

    public String hash() {
        return "TODO";
    }

    public Long timestamp() {
        return (new Date()).getTime() / 1000;
    }
}
