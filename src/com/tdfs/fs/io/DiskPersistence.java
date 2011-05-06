package com.tdfs.fs.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

public class DiskPersistence<T> {
	private FileOutputStream fileOut;
	private FileInputStream fileIn;
	private ObjectOutputStream objectOut;
	private ObjectInputStream objectIn;
	
	private static Logger logger = Logger.getLogger(DiskPersistence.class);
	
	public void writeObjectToDisk(T obj,String path)
	{
		try {
			fileOut = new FileOutputStream(path);
			objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(obj);
			objectOut.flush();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			this.closeOutStreams();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public T readObjectFromDisk(String path)
	{
		T object = null;
		try {
			fileIn = new FileInputStream(path);
			objectIn = new ObjectInputStream(fileIn);
			object = (T) objectIn.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			this.closeInStreams();
			
		}
		return object;
	}
	
	public boolean removeFileFromDisk(String path)
	{
		File file = new File(path);
		if(!file.exists())
		{
			logger.error("File "+path+" doesnot exist");
			return false;
		}
		if(!file.canWrite())
		{
			logger.error("File "+path+" is write protected");
			return false;
		}
		
		boolean success = file.delete();
		if(success)
		{
			logger.info("File "+path+" removed successfully!");
		}
		else{
			logger.error("Error occurred while deleting file "+path);
			return false;
		}
		
		return true;
		
		
	}
	
	private boolean closeInStreams()
	{
		try {
			if(objectIn != null)
				objectIn.close();
			if(fileIn != null)
				fileIn.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	private boolean closeOutStreams()
	{
		try {
			if(objectOut != null)
				objectOut.close();
			if(fileOut != null)
				fileOut.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}

}
