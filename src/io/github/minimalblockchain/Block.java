package io.github.minimalblockchain;

import java.util.logging.Logger;

public class Block {

    private static final Logger LOG = Logger.getLogger(Block.class.getName());

    private Long index;
    private String data;
    private Long timestamp;
    private String hash;
    private String prevHash;

    public Block(Long index, String data, Long timestamp, String hash, String prevHash) {
        this.index = index;
        this.data = data;
        this.timestamp = timestamp;
        this.hash = hash;
        this.prevHash = prevHash;
    }

    public Long getIndex() {
        return index;
    }

    public String getData() {
        return data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getHash() {
        return hash;
    }

    public String getPrevHash() {
        return prevHash;
    }

    @Override
    public String toString() {
        return "Block{" + "index=" + index + ", data=" + data + ", timestamp=" + timestamp + ", hash=" + hash + ", prevHash=" + prevHash + '}';
    }

}
