package com.mrcrayfish.modelcreator;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.junit.Ignore;
import org.junit.Test;

import javax.swing.SwingUtilities;
import java.awt.Frame;

import static org.assertj.swing.finder.WindowFinder.findFrame;
import static org.assertj.swing.launcher.ApplicationLauncher.application;
import static org.junit.Assert.fail;


public class WindowFrameTest extends AssertJSwingJUnitTestCase {
    private FrameFixture windowFrame;

    @Override
    protected void onSetUp() {
        application(AlternateApp.class).start();
        windowFrame = getVisibleFrameByName("AlternateApp");
    }

    public FrameFixture getVisibleFrameByName(String title){
        FrameFixture frameByName = findFrame(new GenericTypeMatcher<Frame>(Frame.class) {
            protected boolean isMatching(Frame frame) {
                return title.equals(frame.getTitle()) && frame.isShowing();
            }
        }).using(robot() );
        return frameByName;
    }


    @Ignore
    @Test
    public void shouldBeOnEventDispatchThread(){
        assert SwingUtilities.isEventDispatchThread();
    }

    @Test
    public void shouldDisplayFrame(){
       windowFrame.requireVisible();
        windowFrame.isEnabled();
    }

    @Test
    public void shouldContainScrollPane(){
        windowFrame.scrollPane("scrollpane").requireVisible();
    }

    @Ignore
    @Test
    public void shouldContainCanvas(){
        fail("test not written");
    }

    @Ignore
    @Test
    public void shouldContainMenuBar(){
        fail("test not written");
    }






}