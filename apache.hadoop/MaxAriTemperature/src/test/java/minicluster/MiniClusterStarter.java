package minicluster;

import com.google.common.io.Files;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.mapreduce.v2.MiniMRYarnCluster;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by yimin on 15/5/9.
 */
public class MiniClusterStarter {

    //startUpMiniDFSAndMRYarnCluster
    public static void main(String[] args) throws IOException {
        Configuration configuration = new Configuration();
        File tempDir = Files.createTempDir();
        System.out.println("temp dir absolute path is "+tempDir.getAbsolutePath());
        configuration.set(MiniDFSCluster.HDFS_MINIDFS_BASEDIR, tempDir.getAbsolutePath());
        new MiniDFSCluster.Builder(configuration)
                .numDataNodes(2)
                .build();

        MiniMRYarnCluster yarnCluster = new MiniMRYarnCluster("MaxAriTemperature-MiniCluster", 1);
        yarnCluster.init(configuration);
        yarnCluster.start();

        //保存cluster的config文件，以便启动job的时候使用
        yarnCluster.getConfig().writeXml(new FileOutputStream(new File("conf.xml")));

        //hang up
        System.in.read();
    }
}
