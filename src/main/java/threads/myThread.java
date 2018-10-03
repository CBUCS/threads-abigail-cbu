package threads;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class myThread {
    protected int numThreads = 4;

    // Hold all our threads.
    private List<Thread> threads = new ArrayList<Thread>();

    public static final Logger logger = LogManager.getLogger(myThread.class);

    public myThread(int threads) {
        this.numThreads = threads;
    }

    public void main() {

        String csvFile = "src/main/resources/ufo-sightings/scrubbed.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] ufoInfo = line.split(cvsSplitBy);

                logger.info("DateTime [code= " + ufoInfo[0] + " , City=" + ufoInfo[1] + "]");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

//        for (int x = 0; x < this.numThreads; x++) {
////            NumberMapper nm = new NumberMapper(this.intMap.get(x));
////            this.numberMappers.add(nm);
////            Thread thread = new Thread(nm);
////            this.threads.add(thread);
//        }
//
//        int x = 1;
//        for (Thread thread : this.threads) {
////            logger.debug("Running thread: " + x);
//            thread.start();
//            x++;
//        }
//
//        int isDone = numThreads;
//
//        while (isDone > 0) {
////            for (NumberMapper thread : this.numberMappers) {
////                if (thread.isDone()) {
////                    isDone--;
////                }
////            }
//        }
//
//        try {
//            TimeUnit.SECONDS.sleep(2);
//        } catch (Exception e) {
//        }
//
////        logger.info(NumberMapper.mapper);


    }
}
