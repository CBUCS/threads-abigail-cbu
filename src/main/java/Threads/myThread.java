package Threads;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class myThread {
    protected int numThreads = 4;

    // Hold all our threads.
    private List<Thread> threads = new ArrayList<Thread>();
//    private List<NumberMapper> numberMappers = new ArrayList<NumberMapper>();

    private Map<Integer, List<Integer>> intMap = new HashMap<Integer, List<Integer>>();

//    public static final Logger logger = LogManager.getLogger(ThreadMain.class);

    private File inputFile = new File("src/main/resources/numbers.txt");

    public myThread(int threads) {
        this.numThreads = threads;
    }

    public void main() {

        FileInputStream fis;
        BufferedReader bis;

        try {
            fis = new FileInputStream(this.inputFile);
            bis = new BufferedReader(new InputStreamReader(fis));

            String line = bis.readLine();

            int counter = 0;
            while (line != null) {
                if (!StringUtils.isEmpty(line)) {
                    if (!this.intMap.containsKey(counter % this.numThreads)) {
                        this.intMap.put(counter % this.numThreads, new ArrayList<Integer>());
                    }
                    this.intMap.get(counter % this.numThreads).add(Integer.parseInt(StringUtils.strip(line)));
                    counter++;
                }
                line = bis.readLine();
            }
            bis.close();
        } catch (IOException e) {
//            logger.fatal(e.toString());
            e.printStackTrace();
        }

        for (int x = 0; x < this.numThreads; x++) {
//            NumberMapper nm = new NumberMapper(this.intMap.get(x));
//            this.numberMappers.add(nm);
//            Thread thread = new Thread(nm);
//            this.threads.add(thread);
        }

        int x = 1;
        for (Thread thread : this.threads) {
//            logger.debug("Running thread: " + x);
            thread.start();
            x++;
        }

        int isDone = numThreads;

        while (isDone > 0) {
//            for (NumberMapper thread : this.numberMappers) {
//                if (thread.isDone()) {
//                    isDone--;
//                }
//            }
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
        }

//        logger.info(NumberMapper.mapper);


    }
}
