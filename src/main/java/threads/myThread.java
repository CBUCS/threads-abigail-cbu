package threads;

import java.io.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.text.DateFormatter;


public class myThread {
    protected int numThreads = 4;
    protected int counter = 0;

    // Hold all our threads.
    private List<Thread> threads = new ArrayList<Thread>();
    public List<UfoMapper> mapper = new ArrayList<UfoMapper>();

    private Map<Integer, List<String>> stringMap = new HashMap<Integer, List<String>>();

    public static final Logger logger = LogManager.getLogger(myThread.class);

    public myThread() {
    }

    ;

    public myThread(int threads) {
        this.numThreads = threads;
        this.counter = 0;
        this.stringMap = new HashMap<Integer, List<String>>();
        this.mapper = new ArrayList<UfoMapper>();
        this.threads = new ArrayList<Thread>();
    }

    public enum SearchItem {
        datetime(0),
        city(1),
        state(2),
        country(3),
        shape(4),
        secDuration(5),
        hrMinDuration(6),
        comments(7),
        datePosted(8),
        latitude(9),
        longitude(10);

        private int index;

        SearchItem(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }

    public String main(SearchItem search) {

        String csvFile = "src/main/resources/ufo-sightings/scrubbed.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            br.readLine(); // skip first line where column headers are located

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] ufoInfo = line.split(cvsSplitBy);

//                logger.info("DateTime [code= " + ufoInfo[0] + " , City=" + ufoInfo[1] + "]");

                if (search == SearchItem.country) {
                    // create key for country
                    if (!ufoInfo[SearchItem.country.getIndex()].isEmpty()) {
//                        logger.debug("Key for StringMap: " + counter % this.numThreads);
                        if (!this.stringMap.containsKey(counter % this.numThreads)) {
                            this.stringMap.put(counter % this.numThreads, new ArrayList<String>());
                        }
                        this.stringMap.get(counter % this.numThreads).add(ufoInfo[SearchItem.country.getIndex()]);
                    }
                } else if (search == SearchItem.state) {
                    // create key for state
                    if (!ufoInfo[SearchItem.state.getIndex()].isEmpty() && (ufoInfo[SearchItem.country.getIndex()].equals("us") || ufoInfo[SearchItem.country.getIndex()].equals("ca"))) {
//                        logger.debug("Key for StringMap: " + counter % this.numThreads);
                        if (!this.stringMap.containsKey(counter % this.numThreads)) {
                            this.stringMap.put(counter % this.numThreads, new ArrayList<String>());
                        }
                        this.stringMap.get(counter % this.numThreads).add(ufoInfo[SearchItem.state.getIndex()]);
                    }
                } else if (search == SearchItem.datetime) {
                    if (!ufoInfo[SearchItem.datetime.getIndex()].isEmpty()) {

                        String[] dateTime = ufoInfo[SearchItem.datetime.getIndex()].split(" ");
                        String[] date = dateTime[0].split("/");

                        if (!this.stringMap.containsKey(counter % this.numThreads)) {
                            this.stringMap.put(counter % this.numThreads, new ArrayList<String>());
                        }
                        this.stringMap.get(counter % this.numThreads).add(date[0]); // get month

                        counter++;
                        if (!this.stringMap.containsKey(counter % this.numThreads)) {
                            this.stringMap.put(counter % this.numThreads, new ArrayList<String>());
                        }
                        this.stringMap.get(counter % this.numThreads).add(date[2]); // get year

                    }
                }
                counter++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        // Create 10 threads in mapper
        for (int x = 0; x < this.numThreads; x++) {
            UfoMapper nm = new UfoMapper(this.stringMap.get(x));
            this.mapper.add(nm);
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
            for (UfoMapper thread : this.mapper) {
                if (thread.isDone()) {
                    logger.debug("Thread : " + thread.getId() + " isDone");
                    isDone--;
                }
            }
        }

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
        }

        return UfoMapper.ufoMap.toString();

        /*** Country ***/
        //[INFO ] threads.myThread - {country=1, de=105, au=538, gb=1887, us=48204, ca=2945}
        // [INFO ] threads.myThread - {country=1, de=105, au=538, gb=1905, us=65114, ca=3000}
        //[INFO ] threads.myThread - {country=1, de=105, au=538, gb=1905, us=65114, ca=3000}

        /*** State ***/
        //{hi=262, pq=64, de=166, pr=25, tx=3447, qc=124, yk=2, ma=1256, mb=127, md=837,
        // me=558, ia=678, id=521, yt=5, mi=1836, ut=622, mn=1012, ab=288, mo=1458, il=2499,
        // in=1288, ms=375, mt=478, ak=319, al=642, va=1273, ar=588, nb=86, nc=1740, nd=129,
        // ne=381, ri=228, nf=15, az=2414, nh=486, nj=1255, vt=260, nm=720, bc=691, ns=101,
        // fl=3835, nt=13, nv=803, wa=3966, ny=2980, sa=27, sc=1003, sd=183, wi=1232, sk=78,
        // oh=2275, ga=1255, ok=724, ca=8912, on=1354, wv=448, wy=175, or=1747, ks=613, co=1413,
        // ky=855, pa=2366, ct=892, la=558, pe=10, tn=1119, dc=7}

        // {hi=262, pq=64, de=166, pr=25, tx=3447, qc=124, yk=2, ma=1256, mb=127, md=837,
        // me=558, ia=678, id=521, yt=5, mi=1836, ut=622, mn=1012, ab=288, mo=1458, il=2499,
        // in=1288, ms=375, mt=478, ak=319, al=642, va=1273, ar=588, nb=86, nc=1740, nd=129,
        // ne=381, ri=228, nf=15, az=2414, nh=486, nj=1255, vt=260, nm=720, bc=691, ns=101,
        // fl=3835, nt=13, nv=803, wa=3966, ny=2980, sa=27, sc=1003, sd=183, wi=1232, sk=78,
        // oh=2275, ga=1255, ok=724, ca=8912, on=1354, wv=448, wy=175, or=1747, ks=613, co=1413,
        // ky=855, pa=2366, ct=892, la=558, pe=10, tn=1119, dc=7}


    }
}
