import com.mrcrayfish.modelcreator.WindowFrame;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.edt.GuiQuery;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Test;


public class AlternateAppTest extends AssertJSwingJUnitTestCase {
    private FrameFixture frameFixture;

    @Override
    protected void onSetUp() {
        WindowFrame frameHandle = GuiActionRunner.execute(new GuiQuery<WindowFrame>() {
            @Override
            protected WindowFrame executeInEDT() throws Throwable {
                return new WindowFrame();
            }
        });
        //DO NOT CREATE your own robot!! Use robot from AssertJSwingJUintTestCase
        frameFixture = new FrameFixture(robot(), frameHandle);
        frameFixture.show(); //show frame to test
    }

    @Test
    public void shouldShowEnabledFrame(){
        assert frameFixture.isEnabled();
    }


}
