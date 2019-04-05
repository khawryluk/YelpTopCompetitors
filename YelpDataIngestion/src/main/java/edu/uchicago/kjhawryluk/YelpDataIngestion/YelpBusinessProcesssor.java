package edu.uchicago.kjhawryluk.YelpDataIngestion;

import java.io.File;
import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.thrift.TException;
import org.apache.thrift.TSerializer;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class YelpBusinessProcesssor extends YelpDataProcessor {
		YelpBusinessProcesssor(Configuration finalConf, TSerializer ser, String dataType, String outputDir) {
		super(finalConf, ser, dataType, outputDir);
	}

		// writes a yelp business to HDFS. 
		void processYelpBusiness(YelpBusiness business, File file) throws IOException{
			try {
				getWriter(this.config, this.outputDir + "/" + Long.toString(System.currentTimeMillis()), file).append(new IntWritable(1), new BytesWritable(this.serializer.serialize(business)));
			} catch (TException e) {
				throw new IOException(e);
			}
		}
		
		//Parse the review and write out to HDFS
		@Override
		void processLine(String line, File file) throws IOException {
			try {
				processYelpBusiness(yelpBusinessFromLine(line), file);
			}  catch (JsonGenerationException e) {
				System.out.println(e);
			} catch (JsonMappingException e) {
				System.out.println(e);
			} 
		}
		
		//Use Jackson to parse business and return
		YelpBusiness yelpBusinessFromLine(String line) throws JsonParseException, JsonMappingException, IOException {
			JacksonYelpBusiness review = mapper.readValue(line, JacksonYelpBusiness.class);
			return review.convertToYelpBusiness(this.mapper);
		}	
}
