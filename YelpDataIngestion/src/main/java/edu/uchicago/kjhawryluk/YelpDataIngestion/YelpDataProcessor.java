package edu.uchicago.kjhawryluk.YelpDataIngestion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.thrift.TSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class YelpDataProcessor {
	Map<Integer, SequenceFile.Writer> sequenceMap = new HashMap<Integer, SequenceFile.Writer>();
	Integer defaultKey = 1;
	ObjectMapper mapper = new ObjectMapper();
	String yelpDataType;
	Configuration config;
    TSerializer serializer;
    String outputDir;
	
	YelpDataProcessor (Configuration finalConf, TSerializer ser, String dataType, String outputDir) {
		this.config = finalConf;
		this.serializer = ser;
		this.yelpDataType = dataType;
		this.outputDir = outputDir;
	}
	
	//Parse the line and write out to HDFS
	abstract void processLine(String line, File file) throws IOException;
	
	
	//Process reviews line by line from Json file.
	void processYelpDataFile(File file) throws IOException {	
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line;
		while((line = br.readLine()) != null) {
			processLine(line, file);
		}
		br.close();
	}
	
	//Get a writer for writing sequence map to HDFS
	public Writer getWriter(Configuration finalConf, String path, File file) throws IOException {
		//Everything is being written to the same sequence file in this case.
		if(!sequenceMap.containsKey(defaultKey)) {
			sequenceMap.put(defaultKey, 
					SequenceFile.createWriter(finalConf,
							SequenceFile.Writer.file(new Path(path)),
							SequenceFile.Writer.keyClass(IntWritable.class),
							SequenceFile.Writer.valueClass(BytesWritable.class),
							SequenceFile.Writer.compression(CompressionType.NONE)));
		}
		return sequenceMap.get(defaultKey);
	}
	
	//Loop through directory and process files of the type specified by arguments.
	void prcoessYelpReviewDirectory(String directoryName) throws IOException {
		File directory = new File(directoryName);
		File[] directoryListing = directory.listFiles();
		for(File file : directoryListing)
		{
			System.out.println(file.getName());
			if(file.getName().toLowerCase().indexOf(yelpDataType) > -1 && file.getName().toLowerCase().indexOf("json") > -1 )
				processYelpDataFile(file);
		}
	}
}
