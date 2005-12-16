/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.datatools.sqltools.sqleditor.internal.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * This class defines colors used in the SQL editor.
 */
public class SQLColorProvider {

    public static final RGB SQL_COMMENT_COLOR              = new RGB( 0, 0, 128 );  // dark blue
    public static final RGB SQL_MULTILINE_COMMENT_COLOR    = new RGB( 128, 0, 0 );  // dark red
    public static final RGB SQL_QUOTED_LITERAL_COLOR       = new RGB( 0, 0, 255 );  // bright blue
    public static final RGB SQL_KEYWORD_COLOR              = new RGB( 128, 0, 0 );  // dark red
    public static final RGB SQL_IDENTIFIER_COLOR           = new RGB( 0, 0, 128 );  // dark blue 
    public static final RGB SQL_DELIMITED_IDENTIFIER_COLOR = new RGB( 0, 128, 0 );  // dark green
    public static final RGB SQL_DEFAULT_COLOR              = new RGB( 0, 0, 0 );    // black
    
    // Define colors that can be used when the display is in "high contrast" mode.
    // (High contrast is a Windows feature that helps vision impaired people.) 
    public static final RGB SQL_HC_COMMENT_COLOR              = new RGB( 255, 0, 0 );     // bright red
    public static final RGB SQL_HC_MULTILINE_COMMENT_COLOR    = new RGB( 0, 0, 255 );     // bright blue
    public static final RGB SQL_HC_QUOTED_LITERAL_COLOR       = new RGB( 0, 255, 0 );     // bright green
    public static final RGB SQL_HC_KEYWORD_COLOR              = new RGB( 255, 255, 0 );   // yellow
    public static final RGB SQL_HC_IDENTIFIER_COLOR           = new RGB( 0, 0, 255 );     // bright blue
    public static final RGB SQL_HC_DELIMITED_IDENTIFIER_COLOR = new RGB( 255, 0, 0 ) ;    // bright red
    public static final RGB SQL_HC_DEFAULT_COLOR              = new RGB( 255, 255, 255 ); // bright white
    
    protected Map fColorTable = new HashMap(10);

    /**
     * Release all of the color resources held onto by the receiver.
     */
    public void dispose() {
        Iterator e = fColorTable.values().iterator();
        while (e.hasNext())
            ((Color) e.next()).dispose();
    }
    
    /**
     * Gets a Color object for the given RGB value.
     * 
     * @param rgb the RGB value for which the Color object is needed
     * @return the Color object corresponding to the RGB value
     */
    public Color getColor( RGB rgb ) {
        Color color = (Color) fColorTable.get( rgb );
        // If this is first time the color has been requested, create the
        // color and put it in our Map associated with its RGB value.
        if (color == null) {
            color = new Color( Display.getCurrent(), rgb );
            fColorTable.put( rgb, color );
        }
        return color;
    }
    
} // end class