//import com.imnu.cloudDisk.hadoop.HadoopConfig;
//import com.imnu.cloudDisk.util.WordFilter;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.fs.FSDataInputStream;
//import org.apache.hadoop.fs.FSDataOutputStream;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.fs.Path;
//import org.apache.hadoop.hdfs.DistributedFileSystem;
//import org.apache.hadoop.io.IOUtils;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.IOException;
//import java.net.URI;
//import java.util.Arrays;
//
///**
// * <Description> <br>
// *
// * @author 宁志洋
// * @CreateDate 2021/7/27 <br>
// */
//
//
//class aTest {
//
//    @Autowired
//    HadoopConfig hadoopConfig;
//
//    @Test
//    public void testY() throws IOException {
//        String xxx = "国家都是共产党的";
//        String replace = WordFilter.replaceWords(xxx);
//        System.out.println(replace);
//    }
//
//    @Test
//    public void testX() throws IOException {
//        String uri = "hdfs://47.99.39.91:9000/ayCloudDisk/test";
//        Configuration configuration = new Configuration();
//        FileSystem fileSystem = FileSystem.get(URI.create(uri),configuration);
////        DistributedFileSystem dfs = (DistributedFileSystem) fileSystem;
////        System.out.println(dfs.getDiskStatus().getUsed());
//        Path path = new Path(uri);
//        System.out.println(fileSystem.getContentSummary(path).getSpaceConsumed());
//    }
//    @Test
//    public void testA() throws IOException {
//        String uri = "hdfs://47.99.39.91:9000/ayCloudDisk";
//        String srcPath = "C:\\Users\\Administrator\\Desktop\\答辩PPT.pptx";
//        Configuration configuration = new Configuration();
//        configuration.set("dfs.replication", "1");
//        configuration.set("dfs.client.use.datanode.hostname","true");
//        FileSystem fileSystem = FileSystem.get(URI.create(uri),configuration);
//        Path path = new Path(srcPath);
//        Path dstPath = new Path(uri+"/答辩PPT.pptx");
//        fileSystem.setTimes(path,0,3000);
//        FSDataInputStream inputStream = null;
//        FSDataOutputStream outputStream = null;
//        inputStream = fileSystem.open(dstPath);
//        outputStream = fileSystem.create(path);
//        IOUtils.copyBytes(inputStream, outputStream, 1024 * 1024 * 64, false);
////        fileSystem.copyToLocalFile(dstPath, path);
//        fileSystem.close();
//    }
//    @Test
//    public void testQuery() throws IOException {
//        String uri = "hdfs://47.99.39.91:9000/ayCloudDisk/test";
////        String srcPath = "C:\\Users\\Administrator\\Desktop\\答辩PPT.pptx";
//        Configuration configuration = new Configuration();
////        configuration.set("dfs.replication", "1");
////        configuration.set("dfs.client.use.datanode.hostname","true");
//        FileSystem fileSystem = FileSystem.get(URI.create(uri),configuration);
//        Path path = new Path(uri);
//        fileSystem.mkdirs(path);
////        Path dstPath = new Path(uri+"/答辩PPT.pptx");
////        fileSystem.setTimes(path,0,3000);
////        FSDataInputStream inputStream = null;
////        FSDataOutputStream outputStream = null;
////        inputStream = fileSystem.open(dstPath);
////        outputStream = fileSystem.create(path);
////        IOUtils.copyBytes(inputStream, outputStream, 1024 * 1024 * 64, false);
////        fileSystem.copyToLocalFile(dstPath, path);
////        fileSystem.close();
//
//        // 创建文件夹
////        fileSystem.mkdirs(path);
//        // 列出文件夹或文件
////        System.out.println(fileSystem.getContentSummary(path).getSpaceConsumed());
////        FileStatus fileStatus[] = fileSystem.listStatus(path);
////        for (FileStatus status : fileStatus) {
////            System.out.println(status.getPath());
////            System.out.println(status.getPath().getName() +"size:"+status.getLen());
////            String [] s = status.getPath().getName().split(".");
////            System.out.println(s);
//        //读取txt文件数据
////        InputStream in = fileSystem.open(path);
////        IOUtils.copyBytes(in,System.out,4096,false);
//        //删除文件或目录
////        boolean isdel = fileSystem.delete(path,true) ;//true删除递归  false不删除递归文件夹下的文件
//        //文件是否存在
////        boolean exitf = fileSystem.exists(path);//true是存在   false是不存在
//        //文件上传
//
//        //文件下载
//    }
//}
//
//
