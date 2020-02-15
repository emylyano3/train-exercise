package mule;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(value = {GraphFacadeTest.class, GraphLoaderTest.class, GraphServiceProtocolTest.class})
public class GraphExersiceTest {

}
