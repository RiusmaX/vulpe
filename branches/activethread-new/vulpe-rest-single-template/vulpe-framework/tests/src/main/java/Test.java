import org.apache.log4j.Logger;

public class Test {

	private static final Logger LOG = Logger.getLogger(Test.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String test = "test";
		LOG.info(test.charAt(test.length()-1) == "t".charAt(0));
	}

}
