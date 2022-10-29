import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class loadTest {
    @Test
    public void testMember2XML(){
        String[][] testResults = {{"1", "YuCong", "18", "Java learn"}, {"2", "DiLiReBa", "18", "Golan learn"}};
        String[][] actualResults = new loadData().loadData(new File("src/main/member.xml"));

        //Test if there are data in the XML
        Assert.assertTrue("The converted XML file is empty", actualResults[0].length != 0);

        //Test if data is correct
        for (int i=0; i < actualResults.length; i++){
            for (int j=0; j < actualResults[0].length; j++) {
                Assert.assertTrue("The value is not correctly matched.", testResults[i][j].equals(actualResults[i][j]));
            }
        }

    }

}
