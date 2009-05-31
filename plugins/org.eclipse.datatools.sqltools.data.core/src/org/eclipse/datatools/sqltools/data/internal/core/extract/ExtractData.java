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

package org.eclipse.datatools.sqltools.data.internal.core.extract;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.datatools.modelbase.sql.tables.Table;
import org.eclipse.datatools.sqltools.data.internal.core.DataCorePlugin;
import org.eclipse.datatools.sqltools.data.internal.core.common.Output;
import org.eclipse.datatools.sqltools.data.internal.core.common.data.DataSerializer;
import org.eclipse.datatools.sqltools.data.internal.core.common.data.ResultSetReader;

/**
 * Class contains facilities to extract the table content into a system file.
 * The character encoding used to save the file is UTF-8.
 */
public class ExtractData
{
    
    protected Connection connection;
    protected Table table;
    protected String filePath;
    
    protected String colDelim = ","; //$NON-NLS-1$
    protected static final String ENDL = System.getProperty("line.separator"); //$NON-NLS-1$
    protected String stringDelim = "\""; //$NON-NLS-1$
    
    protected HashSet errorColumns = new HashSet();
    protected int rowsExtracted = 0;
   
    public ExtractData(Table table, String filePath)
    {
        this.connection = ((ICatalogObject)table).getConnection();
        this.table = table;
        this.filePath = filePath;
    }
    
    public void setDelims(String colDelim, String stringDelim)
    {
        this.colDelim = colDelim;
        this.stringDelim = stringDelim;
    }
    
    /**
     * Extracts content to a file.
     * Uses UTF-8 as the character encoding.
     */
    public int doExtract(Output output)
    {                
        output.write( Messages.getString("ExtractData.Extracting") + " " + getFullyQualifiedName() + "..." );  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        
        try {
            doExtract1();
        } catch (Exception ex) {
            DataCorePlugin.getDefault().writeLog(IStatus.ERROR, 0, ex.getMessage(), ex);
            output.write( ex.toString() );
            output.write( Messages.getString("ExtractData.DataExtractionFailed") ); //$NON-NLS-1$
            return Output.STATUS_FAILED;
        }
        
        output.write( Messages.getString("ExtractData.DataExtractionSuccessful") ); //$NON-NLS-1$
        output.write( String.valueOf(rowsExtracted) + Messages.getString("ExtractData.RowsExtracted") );         //$NON-NLS-1$
        
        if (errorColumns.size()>0) {
            StringBuffer sb = new StringBuffer();
            sb.append(Messages.getString("ExtractData.ErrorRetrieving")); //$NON-NLS-1$
            Iterator it = errorColumns.iterator();
            while (it.hasNext()) {
                sb.append(it.next().toString());
                if (it.hasNext())
                    sb.append(", "); //$NON-NLS-1$
            }
            output.write( sb.toString() );
            return Output.STATUS_WARNING;
        } else {
            return Output.STATUS_SUCCEEDED;
        }
    }
    
    protected void doExtract1() throws SQLException, IOException
    {
        Statement st = null;
        ResultSet rs = null;
        BufferedWriter fw = null;
        
		
		try {
		    st = connection.createStatement();
			rs = st.executeQuery( getQuery() );
						
			FileOutputStream fos = new FileOutputStream(filePath);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			fw = new BufferedWriter(osw);
			
		    writeData(rs, fw);
		} finally {
		    if (rs!=null)
		        rs.close();
		    if (st!=null)
		        st.close();
		    if (fw!=null)
		        fw.close();
		}
    }
    
    protected void writeData(ResultSet rs, Writer fw) throws SQLException, IOException
    {
        ResultSetMetaData md = rs.getMetaData();

        while (rs.next()) {
			for (int i=0; i<md.getColumnCount(); ++i) {			    
			    if (i!=0)
					fw.write(colDelim);
			    String s = getVal(rs, i);
			    if (s!=null)
			        fw.write(s);
			}
			rowsExtracted++;
			fw.write(ENDL);			
		}

    }
    
    public String getVal(ResultSet rs, int column) throws SQLException, IOException
    {
        ResultSetMetaData md = rs.getMetaData();
        try {
            Object o = ResultSetReader.read(rs, column);
            String s = DataSerializer.write(o, md.getColumnType(column+1), stringDelim);
            return s;
        } catch (Exception ex) {
            errorColumns.add(md.getColumnName(column+1));
            return null;
        }
    }
    
    protected String getQuery()
    {
        return "select * from " + getFullyQualifiedName(); //$NON-NLS-1$
    }
    
    protected String getFullyQualifiedName() {
    	return DataCorePlugin.getQualifiedTableName(table);
    }
    
}