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
}
