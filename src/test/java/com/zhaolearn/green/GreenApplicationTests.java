package com.zhaolearn.green;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GreenApplicationTests {
    private static GreenTools greenTools=new GreenTools();
    private static String fileName="myfile.txt";
    @Test
    public void testClone() throws IOException, GitAPIException {
        greenTools.testClone();
    }
    @Test
    public void testAdd() throws IOException, GitAPIException {
        greenTools.testAdd("myfile.txt");
    }
    @Test
    public void writeFile() {
        greenTools.writeFile(fileName,"。");
    }

    @Test
    public void testCommit() throws IOException, GitAPIException {
        greenTools.testCommit();
    }

    @Test
    public void testPush() throws IOException, GitAPIException {
        greenTools.testPush();
    }

    @Test
    public void testAll() throws IOException, GitAPIException {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 50; j++) {
                greenTools.writeFile(fileName, "。");
                greenTools.testCommit();
            }
            UpdateSystemTime.updateSysDateTime();
        }
        greenTools.testPush();
    }

}
