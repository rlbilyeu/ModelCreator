package com.mrcrayfish.modelcreator;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.assertj.swing.timing.Timeout;
import org.junit.Test;
import java.awt.Frame;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.application;


public class AlternateAppTest extends AssertJSwingJUnitTestCase {
    FrameFixture  windowFrame;

    @Override
    protected void onSetUp() {
        application(AlternateApp.class).start();
        windowFrame = getVisibleFrameByName("AlternateApp");

    }

    public FrameFixture getVisibleFrameByName(String title){
        FrameFixture frameByName;
        frameByName = findFrame(new GenericTypeMatcher<Frame>(Frame.class) {
            protected boolean isMatching(Frame frame) {
                return title.equals(frame.getTitle()) && frame.isShowing();
            }
        }).using(robot() );
        return frameByName;
    }


    @Test
    public void shouldDisplayFrame(){
        windowFrame.requireVisible();
    }

    @Test
    public void shouldDisplayAndFocusWelcomeDialog(){
        DialogFixture dialog = windowFrame.dialog("Welcome", Timeout.timeout(5000));
        dialog.requireVisible();
        dialog.requireFocused();
    }


}
