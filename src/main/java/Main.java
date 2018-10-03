import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import threads.myThread;

/**
 * Name: Abigail Lu
 * Date: 10/1/2018
 * threads Homework
 * CSC 527
 *
 * Description:
 * I would like to know how many UFO sightings occur in each of the following:

     Country
     State, (If within the US/Canada)
     Occur during the following times:
         8:01-12:00
         12:01-16:00
         16:01-20:00
         20:01-0:00
         0:01-8:00
     Month
     Year
 */
public class Main {

    public static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

//        logger.info("Hello, world");
        myThread main = new myThread(10);
        main.main();

    }
}
