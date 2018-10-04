import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import threads.myThread;

/**
 * Name: Abigail Lu
 * Date: 10/1/2018
 * threads Homework
 * CSC 527
 * <p>
 * Description:
 * I would like to know how many UFO sightings occur in each of the following:
 * <p>
 * Country
 * State, (If within the US/Canada)
 * Occur during the following times:
 * 8:01-12:00
 * 12:01-16:00
 * 16:01-20:00
 * 20:01-0:00
 * 0:01-8:00
 * Month
 * Year
 */
public class Main {

    public static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

//        logger.info("Hello, world");
        myThread main = new myThread(2);
        main.main(myThread.SearchItem.country);

        myThread main2 = new myThread(2);
        main2.main(myThread.SearchItem.state);

        myThread main3 = new myThread(8);
        main3.main(myThread.SearchItem.datetime); // if running all of them it is displaying all values found

    }
}
