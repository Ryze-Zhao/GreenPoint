package com.zhaolearn.green;

import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;





public class GreenTools {
    //远程库路径
    private static String remotePath = "https://github.com/Giggs-Zhao/TestGreen.git";
    //下载已有仓库到本地路径
    private static String localPath = "D:\\Test\\";
    //本地路径新建
    private static String initPath = "D:\\Test\\Hehe";
    //追加文件的文件名


    //设置远程服务器上的用户名和密码
    private static UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider = new
            UsernamePasswordCredentialsProvider(User.userName, User.pw);

    /**
     * 克隆远程库
     *
     * @throws IOException
     * @throws GitAPIException
     */
    public void testClone() throws GitAPIException {
        //克隆代码库命令
        CloneCommand cloneCommand = Git.cloneRepository();
        Git git = cloneCommand.setURI(remotePath) //设置远程URI
                .setBranch("master") //设置clone下来的分支
                .setDirectory(new File(localPath)) //设置下载存放路径
                .setCredentialsProvider(usernamePasswordCredentialsProvider) //设置权限验证
                .call();
        System.out.print(git.tag());
    }

    /**
     * 本地新建仓库
     */
    public void testCreate() throws IOException {
        //本地新建仓库地址
        Repository newRepo = FileRepositoryBuilder.create(new File(initPath + "/.git"));
        newRepo.create();
    }

    /**
     * 增加文件
     *
     * @param fileName 文件名字：test.txt
     * @date: 2019/3/6 10:23
     */
    public void testAdd(String fileName) throws IOException, GitAPIException {
        File myfile = new File(localPath + "/myfile.txt");
        myfile.createNewFile();
        //git仓库地址
        Git git = new Git(new FileRepository(localPath + "/.git"));
        //添加文件
        git.add().addFilepattern("myfile").call();
    }

    /**
     * 本地提交代码
     */
    public void testCommit() throws IOException, GitAPIException,
            JGitInternalException {
        //git仓库地址
        Git git = new Git(new FileRepository(localPath + "/.git"));
        //提交代码
        git.commit().setMessage("test jGit").call();
    }


    /**
     * 拉取远程仓库内容到本地
     */
    public void testPull() throws IOException, GitAPIException {

        //git仓库地址
        Git git = new Git(new FileRepository(localPath + "/.git"));
        git.pull().setRemoteBranchName("master").
                setCredentialsProvider(usernamePasswordCredentialsProvider).call();
    }

    /**
     * push本地代码到远程仓库地址
     */
    public void testPush() throws IOException, JGitInternalException,
            GitAPIException {
        //git仓库地址
        Git git = new Git(new FileRepository(localPath + "/.git"));
        git.push().setRemote("origin").setCredentialsProvider(usernamePasswordCredentialsProvider).call();
    }


    /**
     * 将字符串追加到文件已有内容后面
     *
     * @param fileName 文件名字：test.txt
     * @param content  需要写入的内容
     */
    public static void writeFile(String fileName, String content) {
        FileOutputStream fos = null;
        try {
            //true不覆盖已有内容
            fos = new FileOutputStream(localPath + "/" + fileName, true);
            //写入
            fos.write(content.getBytes());
            // 写入一个换行
            fos.write("\r\n".getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
