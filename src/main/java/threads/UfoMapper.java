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

    private List<String> countryList = new ArrayList<String>();

    public static ConcurrentHashMap<String, Integer> countryMapper = new ConcurrentHashMap<String, Integer>();

    public UfoMapper(List<String> input) {
        this.threadId = UfoMapper.id++;
        logger.info("Creating thread: " + this.threadId);
        countryList.addAll(input);
    }

    public int getId() {
        return this.threadId;
    }

    public void run() {
        int offset = 1;

        logger.info("Hello, from thread: " + this.threadId);

        for (String country : this.countryList) {
//            if (offset % 10000 == 0) {
//                logger.info(String.format("Thread: %d\toffset: %d\tdata: %d", this.threadId, offset, country));
//            }

            logger.info(String.format("Thread: %d\toffset: %d\tdata: " + country, this.threadId, offset));

            this.incrementAt(country);

            offset++;
        }
        this.isDone = true;
        logger.info("Goodbye, from thread: " + this.threadId);
    }

    public void incrementAt(String key) {
        int value;
        if (countryMapper.containsKey(key)) {
            value = countryMapper.get(key) + 1;
        } else {
            countryMapper.put(key, 0);
            value = 1;
        }
        countryMapper.put(key, value);
    }

    public boolean isDone() {
        return this.isDone;
    }

}
