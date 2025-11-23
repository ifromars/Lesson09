package suites;


import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import tests.*;

@Suite
@SelectClasses({
        ArticleTests.class,
        ChangeAppConditionTests.class,
        GetStartedTest.class,
        MyListsTests.class,
        SearchTests.class
})
public class TestSuite {
}
