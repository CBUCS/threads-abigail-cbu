package threads;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class UfoMapper implements Runnable {

    public static final Logger logger = LogManager.getLogger(UfoMapper.class);

    public static int id = 1;

    private int threadId = id;

    private boolean isDone = false;

    private List<String> ufoList = new ArrayList<String>();

    public static ConcurrentHashMap<String, Integer> ufoMap = new ConcurrentHashMap<String, Integer>();

    public UfoMapper(List<String> input) {
        this.threadId = id;
        logger.info("Creating thread: " + this.threadId);
        ufoList.addAll(input);
        UfoMapper.id++;
    }

    public int getId() {
        return this.threadId;
    }

    public void run() {
        int offset = 1;

        logger.info("Hello, from thread: " + this.threadId);

        for (String searchItem : this.ufoList) {
//            if (offset % 10000 == 0) {
//            logger.info(String.format("Thread: %d\toffset: %d\tdata: " + searchItem, this.threadId, offset));
//            }

//            logger.info(String.format("Thread: %d\toffset: %d\tdata: " + searchItem, this.threadId, offset));

            this.incrementAt(searchItem);

            offset++;
        }
        this.isDone = true;
        logger.info("Goodbye, from thread: " + this.threadId);
    }

    public void incrementAt(String key) {
        int value;
        if (ufoMap.containsKey(key)) {
            value = ufoMap.get(key) + 1;
        } else {
            ufoMap.put(key, 0);
            value = 1;
        }
        ufoMap.put(key, value);
    }

    public boolean isDone() {
        return this.isDone;
    } // this might not be true....

    public void clearMap() {
        this.ufoList = new ArrayList<String>();
        this.ufoMap = new ConcurrentHashMap<String, Integer>();
        this.id = 1;
    }

}
