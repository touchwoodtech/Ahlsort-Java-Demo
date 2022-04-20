import com.ahlsort.api.*;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;

/**
 * 
 * Sample Ahlsort Driver - Copyright (c) 2012-2022 - Ahlbrandt Software, Inc.
 * 
 * @author Ahlbrandt Software, Inc. <support@ahlsort.com>
 *
 */
public class AhlsortDemo implements IMessageCallback {

    public static void main(String[] args) throws AhlsortFileNotFoundException, AhlsortAccessViolationException, AhlsortException, UnsupportedEncodingException {
    	//AhlsortJNI.loadLibrary("C:\\ahlsort\\ahlsortJNI64.dll");
    	AhlsortJNI jni = new AhlsortJNI();
    	System.out.println("You are running version "+jni.version()+" of Ahlsort.");

    	AhlsortDemo drv=new AhlsortDemo();
    	drv.runStraightAPI();
    	drv.runSimpleAPI();
    	drv.runRecordAPI();
    }

	private void runStraightAPI() throws AhlsortFileNotFoundException, AhlsortAccessViolationException, AhlsortException, UnsupportedEncodingException {
		// To specify a specific location of the Ahlsort JNI .dll or Shared Object Library (.so) use the LoadLibrary function
    	// AhlsortSimpleAPI.loadLibrary("C:\\ahlsort\\ahlsortJNI64.dll");
        AhlsortStraightAPI ahlsort=new AhlsortStraightAPI();  // instantiate an Ahlsort Straight API object
        
        // Ahlsort messages are available to your program using the IMessageCallback.onMessage function 
        //AhlsortMessageLogger msgs=new AhlsortMessageLogger();        
        //ahlsort.setMessageCallback(msgs);
        ahlsort.setMessageCallback(this); // this implements IMessageCallback
        
        ahlsort.Sort("control.txt", "in1.txt,dcb=(lrecl=1,recfm=f)","out1.txt");  // provide the control file, the input file with DCB information and the output file

        System.out.println("Records In="+ahlsort.stats.getRecordsIn());  // get the statistics about the run
        System.out.println("Records Out="+ahlsort.stats.getRecordsOut());
        System.out.println("SortWork Used="+ahlsort.stats.getSortworkBytesUsed());        
        System.out.println("AHLSORT Simple API Demo Complete");        

        ahlsort.dispose();       
	}
	private void runSimpleAPI() throws AhlsortFileNotFoundException, AhlsortAccessViolationException, AhlsortException, UnsupportedEncodingException {
    	//AhlsortSimpleAPI.loadLibrary("C:\\ahlsort\\ahlsortJNI64.dll");
        AhlsortSimpleAPI ahlsort=new AhlsortSimpleAPI();  // instantiate an Ahlsort Simple API object
        
        //AhlsortMessageLogger msgs=new AhlsortMessageLogger();        
        //ahlsort.setMessageCallback(msgs);
        ahlsort.setMessageCallback(this);

        //ahlsort.setOption("HEADER","1");
        //ahlsort.setOption("THREADS","1");
        //ahlsort.setOption("CORE","6g");
        ahlsort.setInput("in2.txt");  // Set the input file name
        ahlsort.setOutput("out2.txt");  // Set the output file name
        ahlsort.setInputDelimiter("|");
		ahlsort.addSortFieldNumber(1, AhlsortSortSequence.Ascending,AhlsortDataType.ZD);
		ahlsort.addSortFieldNumber(3, AhlsortSortSequence.Descending,AhlsortDataType.ZD);
        ahlsort.Sort();  // Do the sort
        System.out.println("Records In="+ahlsort.stats.getRecordsIn());  // get the statistics about the run
        System.out.println("Records Out="+ahlsort.stats.getRecordsOut());
        System.out.println("SortWork Used="+ahlsort.stats.getSortworkBytesUsed());        
        System.out.println("AHLSORT Simple API Demo Complete");        

        ahlsort.dispose();       
	}

	private void runRecordAPI() throws AhlsortException {
    	//AhlsortRecordAPI.loadLibrary("C:\\ahlsort\\ahlsortJNI64.dll");
    	AhlsortRecordAPI recordAPI=new AhlsortRecordAPI("Record type=T,length=500 sort fields=(#1,zd,a,#3,ch,d) option ifs='|'", this);
    	recordAPI.add("4|Input Record 1|A".getBytes());
    	recordAPI.add("10|Input Record 2|A".getBytes());
    	recordAPI.add("64|Input Record 3|A".getBytes());
    	recordAPI.add("208|Input Record 4|A".getBytes());
    	recordAPI.add("99|Input Record 5|A".getBytes());
    	recordAPI.add("208|Input Record 6|B".getBytes());
    	recordAPI.add("208|Input Record 7|C".getBytes());
    	recordAPI.add("208|Input Record 8|D".getBytes());
    	recordAPI.add("208|Input Record 9|B".getBytes());

    	Iterator<byte[]> it=recordAPI.getIterator();
    	Integer outCount=0;
    	while (it.hasNext()) {
    		byte[] rec=it.next();
    		outCount++;
    		String ss=new String(rec);
    		System.out.println(ss);
    	}
    	System.out.println("Records out="+outCount.toString());
        System.out.println("AHLSORT Record API Demo Complete");        
    	
    	return;
	}

	public void onMessage(String messageText) {
		System.out.println(messageText);				
	}
}
