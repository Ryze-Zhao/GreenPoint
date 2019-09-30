package com.zhaolearn.green;

import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Component
//@ConfigurationProperties(prefix = "application.yaml")
public class GreenTools {
    //Git账号
    @Value("${GitUser.UserName}")
    private String userName;

    //Git账号的密码
    @Value("${GitUser.PassWord}")
    private String passWord;

    //远程库路径
    @Value("${GitUser.ProjectGitUrl}")
    private String projectGitUrl;

    //项目名称
    @Value("${GitUser.ProjectName}")
    private String projectName;

    //下载已有仓库到本地路径
    @Value("${GitUser.Path}")
    private String path;

    //追加文件的文件名
    @Value("${GitUser.CommitFileName}")
    private String commitFileName;

    //本地路径新建项目
    @Value("${GitUser.NewPath}")
    private String initPath;

    /**
     * 输出一下参数
     *
     * @param
     * @return: void
     * @author: HeHaoZhao
     * @date: 2019/3/18 14:06
     */
    public void printParams() {
        System.out.println(userName + "-----" + passWord + "-----" + projectGitUrl + "-----" + path + "-----" + commitFileName);
    }

    /**
     * 本地新建仓库
     */
    public void projectCreate() throws IOException {
        //本地新建仓库地址
        Repository newRepo = FileRepositoryBuilder.create(new File(path + "/.git"));
        newRepo.create();
    }

    /**
     * 克隆远程库
     *
     * @throws IOException
     * @throws GitAPIException
     */
    public void projectClone() throws GitAPIException {
        //克隆代码库命令
        CloneCommand cloneCommand = Git.cloneRepository();
        Git git = cloneCommand.setURI(projectGitUrl) //设置远程URI
                .setBranch("master") //设置clone下来的分支
                .setDirectory(new File(path)) //设置下载存放路径
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(userName, passWord)) //设置权限验证
                .call();
        System.out.print(git.tag());
    }

    /**
     * 增加文件,文件名字为yaml中的GitUser.CommitFileName
     *
     * @date: 2019/3/6 10:23
     */
    public void addFile() throws IOException, GitAPIException {
        File myfile = new File(path + projectName + "////" + commitFileName);
        myfile.createNewFile();
        //git仓库地址
        Git git = new Git(new FileRepository(path + projectName + "/.git"));
        //添加文件
        git.add().addFilepattern(commitFileName).call();
    }


    /**
     * 本地提交代码
     */
    public void commit(String commitMessage) throws IOException, GitAPIException,
            JGitInternalException {
        //git仓库地址
        Git git = new Git(new FileRepository(path + projectName + "/.git"));
        //提交代码
        git.commit().setMessage(commitMessage).call();
    }


    /**
     * 拉取远程仓库内容到本地
     */
    public void pull() throws IOException, GitAPIException {
        //git仓库地址
        Git git = new Git(new FileRepository(path + projectName + "/.git"));
        git.pull().setRemoteBranchName("master").
                setCredentialsProvider(new UsernamePasswordCredentialsProvider(userName, passWord)).call();
    }

    /**
     * push本地代码到远程仓库地址
     *
     * @param remoteName git中remoteName，通常为origin
     * @return: void
     * @author: HeHaoZhao
     * @date: 2019/3/18 14:37
     */
    public void push(String remoteName) throws IOException, JGitInternalException,
            GitAPIException {
        //git仓库地址
        Git git = new Git(new FileRepository(path + projectName + "/.git"));
        git.push().setRemote(remoteName).setCredentialsProvider(new UsernamePasswordCredentialsProvider(userName, passWord)).call();
    }


    /**
     * 将字符串追加到文件已有内容后面
     *
     * @param content 需要写入的内容
     */
    public void writeFile(String content) {
        FileOutputStream fos = null;
        try {
            //true不覆盖已有内容
            fos = new FileOutputStream(path + projectName + "////" + commitFileName, true);
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


    /**
     * 给入总天数，每天提交次数，项目路径
     *
     * @param days
     * @param commitNum
     * @throws IOException
     * @throws GitAPIException
     */
    public void flushAllGreen(int days, int commitNum) throws IOException, GitAPIException {
        for (int i = 0; i < days; i++) {
            for (int j = 0; j < commitNum; j++) {
                writeFile("。");
                commit("test" + j);
            }
            UpdateSystemTime.updateSysDateTime();
        }
        push("origin");
    }

}
