package edu.uchicago.kjhawryluk.YelpDataIngestion;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.thrift.TSerializer;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;


public class SerializeYelpData {
	static TProtocol protocol;
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Please include a path to the file to be ingested, the output location and the file type.");
			return;
		}
		try {
			Configuration conf = new Configuration();
			conf.addResource(new Path("/home/mpcs53013/dist/hadoop/etc/hadoop/core-site.xml"));
			conf.addResource(new Path("/home/mpcs53013/dist/hadoop/etc/hadoop/hdfs-site.xml"));
			final Configuration finalConf = new Configuration(conf);
			final TSerializer ser = new TSerializer(new TBinaryProtocol.Factory());
			
			if(args[2].equals("YelpReview")) {
				System.out.println("Ingesting Yelp Reviews...");
				ingestYelpReview(args, finalConf, ser);
			} else if(args[2].equals("YelpBusiness")){
				System.out.println("Ingesting Yelp Businesses...");
				ingestYelpBusiness(args, finalConf, ser);
			} else if(args[2].equals("YelpUser")){
				System.out.println("Ingesting Yelp User...");
				ingestYelpUser(args, finalConf, ser);
			}else {
				System.out.println("Could Not Process:" + args[2]);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Starts ingesting reviews in json files found in directory.
	private static void ingestYelpReview(String[] args, Configuration finalConf, TSerializer ser)
			throws IOException {
		YelpReviewProcessor processor = new YelpReviewProcessor(finalConf, ser, "review", args[1]);
		processor.prcoessYelpReviewDirectory(args[0]);
	}
	
	//Starts ingesting businesses in json files found in directory.
	private static void ingestYelpBusiness(String[] args, Configuration finalConf, TSerializer ser)
			throws IOException {
		YelpBusinessProcesssor processor = new YelpBusinessProcesssor(finalConf, ser, "business", args[1]);
		processor.prcoessYelpReviewDirectory(args[0]);
	}
	
	//Starts ingesting users in json files found in directory.
	private static void ingestYelpUser(String[] args, Configuration finalConf, TSerializer ser)
			throws IOException {
		YelpUserProcessor processor = new YelpUserProcessor(finalConf, ser, "user", args[1]);
		processor.prcoessYelpReviewDirectory(args[0]);
	}
}
