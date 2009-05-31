/*******************************************************************************
 * Copyright (c) 2001, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.datatools.sqltools.data.internal.core.load;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class DataFileTokenizer
{
    protected String filePath;
    protected String delims;
    
    protected static final String ENDL = System.getProperty("line.separator"); //$NON-NLS-1$
    
    protected BufferedReader reader;
    protected StringTokenizer st = null;
    protected String nextToken = null;
    
    public DataFileTokenizer(String filePath, String delims)
    {
        this.filePath = filePath;
        this.delims = delims;
    }
    
    /**
     * The BOM (Byte Order Marks) is a Unicode special marker placed at the top of the file that indicate its encoding. 
     * The UTF-8 representation of the BOM is the byte sequence EF BB BF. The BOM is optional for UTF-8.
     * Before loading the file, a check is made if BOM are introduced in the UTF-8 encoded file. 
     * If the first three bytes are 0xEF, 0xBB, 0xBF the first character is skipped as it is BOM.
     */
    public boolean bomEncoding() throws IOException
    {
    	FileInputStream fis=new FileInputStream(filePath);
    	boolean isBOM=false;
    	int byteOne = fis.read();
		int byteTwo = fis.read();
		int byteThree = fis.read();
		
		//Check the BOM for UTF-8 encoding
		if (byteOne == 0xEF && byteTwo == 0xBB && byteThree == 0xBF)
		{
			// UTF-8
			isBOM=true;
		}
		return isBOM;
    }
    
    /**
     * Opens the extracted file to be loaded into the table.
     * The method assumes the file was saved using UTF-8 character encoding
     */
    public void open() throws IOException
    {
    	FileInputStream fis = new FileInputStream(filePath);
		InputStreamReader isw = new InputStreamReader(fis, "UTF-8");
		reader = new BufferedReader(isw);    	
		
		//Skip the first character if it is BOM
		if(bomEncoding() == true)
			reader.skip(1);
		
        String line = reader.readLine();        
        if (line!=null)
            st = new StringTokenizer(line, delims, true);
    }
    
    public String peek() throws IOException
    {
        if (nextToken==null)
            nextToken = nextToken();
        
        return nextToken;
    }
    
    public void consume(String s) throws Exception
    {
        String token = nextToken();
        if (!token.equals(s))
            throw new DataFormatException("Unexpected token (found " + token + ", expected " + s + ")"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }
    
    public String nextToken() throws IOException
    {
        if (nextToken!=null) {
            String s = nextToken;
            nextToken = null;
            return s;
        }
        
        if (st==null || !st.hasMoreTokens()) {
            String line = reader.readLine();
        	if (line==null)
        	    return null;
        	st = new StringTokenizer(line, delims, true);
            return ENDL;
        }
        
        return st.nextToken();
    }
    
    public void close() throws IOException
    {
        reader.close();
    }
}