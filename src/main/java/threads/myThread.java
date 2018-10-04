package threads;

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
    private List<UfoMapper> ufoCountryMapper = new ArrayList<UfoMapper>();

    private Map<Integer, List<String>> stringMap = new HashMap<Integer, List<String>>();

    public static final Logger logger = LogManager.getLogger(myThread.class);

    public myThread(int threads) {
        this.numThreads = threads;
    }

    public void main() {

        String csvFile = "src/main/resources/ufo-sightings/scrubbed.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            int counter = 0;
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] ufoInfo = line.split(cvsSplitBy);

                // 0 = date time
                // 1 = city
                // 2 = state
                // 3 = country
                // 4 = shape
                // 5 = duration (seconds)
                // 6 = duration (hours/min)
                // 7 = comments,
                // 8 = date posted
                // 9 = latitude
                // 10 = longitude

//                logger.info("DateTime [code= " + ufoInfo[0] + " , City=" + ufoInfo[1] + "]");

                if (!ufoInfo[3].isEmpty()) {
                    if (!this.stringMap.containsKey(counter % this.numThreads)) {
                        this.stringMap.put(counter % this.numThreads, new ArrayList<String>());
                    }
                    this.stringMap.get(counter % this.numThreads).add(ufoInfo[3]); // starting with country
                    counter++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        // Create 10 threads in ufoCountryMapper
        for (int x = 0; x < this.numThreads; x++) {
            UfoMapper nm = new UfoMapper(this.stringMap.get(x));
            this.ufoCountryMapper.add(nm);
            Thread thread;
            thread = new Thread(nm);
            this.threads.add(thread);
        }

        int x = 1;
        for (Thread thread : this.threads) {
            logger.debug("Running thread: " + x);
            thread.start();
            x++;
        }

        int isDone = numThreads;

        while (isDone > 0) {
            for (UfoMapper thread : this.ufoCountryMapper) {
                if (thread.isDone()) {
                    isDone--;
                }
            }
        }

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
        }

        logger.info(UfoMapper.countryMapper);

        //[INFO ] threads.myThread - {country=1, de=105, au=538, gb=1887, us=48204, ca=2945}
        // [INFO ] threads.myThread - {country=1, de=105, au=538, gb=1905, us=65114, ca=3000}
        //[INFO ] threads.myThread - {country=1, de=105, au=538, gb=1905, us=65114, ca=3000}


    }
}
