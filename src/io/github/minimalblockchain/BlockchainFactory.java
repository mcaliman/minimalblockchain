package io.github.minimalblockchain;

import static java.awt.SystemColor.text;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BlockchainFactory {

    private static final Logger LOG = Logger.getLogger(BlockchainFactory.class.getName());
    private Blockchain blockchain;

    public BlockchainFactory() {
        this.blockchain = new Blockchain();
        this.blockchain.add(getGenesisBlock());
    }

    private Block getGenesisBlock() {
        return new Block(0L, "GenesisBlock", timestamp(), "816534932c2b7154836da6afc367695e6337db8a921823784c14378abed4f7d7", "0");
    }

    public Block generateNextBlock(String blockData) {
        Block previousBlock = getLatestBlock();
        long nextIndex = previousBlock.getIndex() + 1;
        long nextTimestamp = new Date().getTime() / 1000;
        String nextHash = calculateHash(nextIndex, previousBlock.getHash(), nextTimestamp, blockData);
        return new Block(nextIndex, blockData, nextTimestamp, nextHash, previousBlock.getHash());
    }

    private Block getLatestBlock() {
        return this.blockchain.get(this.blockchain.size() - 1);
    }

    private Long timestamp() {
        return (new Date()).getTime() / 1000;
    }

    private String calculateHashForBlock(Block block) {
        return calculateHash(block.getIndex(), block.getPrevHash(), block.getTimestamp(), block.getData());
    }

    private String calculateHash(long index, String previousHash, long timestamp, String data) {
        return sha256(index + previousHash + timestamp + data);
    }

    private String sha256(String text) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        String encoded = Base64.getEncoder().encodeToString(hash);
        return encoded;
    }

    private boolean isValidNewBlock(Block newBlock, Block previousBlock) {
        if (previousBlock.getIndex() + 1 != newBlock.getIndex()) {
            LOG.log(Level.SEVERE, "invalid index");
            return false;
        } else if (!previousBlock.getHash().equalsIgnoreCase(newBlock.getPrevHash())) {
            LOG.log(Level.SEVERE, "invalid previoushash");
            return false;
        } else if (!calculateHashForBlock(newBlock).equalsIgnoreCase(newBlock.getHash())) {
            LOG.log(Level.SEVERE, "" + newBlock.getHash() + " " + calculateHashForBlock(newBlock));
            LOG.log(Level.SEVERE, "invalid hash: " + calculateHashForBlock(newBlock) + " " + newBlock.getHash());
            return false;
        }
        return true;
    }

    public void addBlock(Block newBlock) {
        if (isValidNewBlock(newBlock, getLatestBlock())) {
            this.blockchain.add(newBlock);
        }
    }

    //public API BEGIN
    public void blocks() {
        this.blockchain.forEach((b) -> {
            System.out.println(b);
        });
    }

    public void mineBlock(String data) {
        Block newBlock = generateNextBlock(data);
        addBlock(newBlock);
        //broadcast(responseLatestMsg());
        LOG.log(Level.INFO, "block added: " + newBlock.toString());
    }

    public void peers() {

    }

    public void addPeer() {

    }
    //public API END

    public static void main(String[] args) {
        BlockchainFactory factory = new BlockchainFactory();
        Block b1 = factory.generateNextBlock("FIRST");
        factory.blocks();
        System.out.println(b1);

        factory.addBlock(b1);
        factory.blocks();
        factory.mineBlock("SECOND");
        factory.blocks();
    }
}
