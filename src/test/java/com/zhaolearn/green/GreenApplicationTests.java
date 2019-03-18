package com.zhaolearn.green;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GreenApplicationTests {
    @Autowired
    private GreenTools greenTools;
    //追加文件的文件名
    @Value("${GitUser.CommitFileName}")
    private String commitFileName;

    @Test
    public void printParams()  {
        greenTools.printParams();
    }

    @Test
    public void prin() throws IOException, GitAPIException {
        greenTools.flushAllGreen(3,50);
    }






//
//
//
//
//    @Test
//    public void testClone() throws IOException, GitAPIException {
//        greenTools.testClone();
//    }
//    @Test
//    public void testAdd() throws IOException, GitAPIException {
//        greenTools.testAdd("myfile.txt");
//    }
//    @Test
//    public void writeFile() {
//        greenTools.writeFile(fileName,"。");
//    }
//
//    @Test
//    public void testCommit() throws IOException, GitAPIException {
//        greenTools.testCommit();
//    }
//
//    @Test
//    public void testPush() throws IOException, GitAPIException {
//        greenTools.testPush();
//    }
//
//    @Test
//    public void testAll() throws IOException, GitAPIException {
//        greenTools.flushAllGreen(10,10,localPath);
//    }
//


}
