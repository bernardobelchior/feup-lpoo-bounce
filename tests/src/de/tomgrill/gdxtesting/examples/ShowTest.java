package de.tomgrill.gdxtesting.examples;

import com.badlogic.gdx.Gdx;

import org.junit.Test;
import org.junit.runner.RunWith;

import de.tomgrill.gdxtesting.GdxTestRunner;
import static org.junit.Assert.assertTrue;


/**
 * Created by Bernardo on 04-06-2016.
 */
@RunWith(GdxTestRunner.class)
public class ShowTest {

    @Test
    public void testTest() {
        Gdx.app.log("Hello", "test");
        assertTrue(true);
    }
}
