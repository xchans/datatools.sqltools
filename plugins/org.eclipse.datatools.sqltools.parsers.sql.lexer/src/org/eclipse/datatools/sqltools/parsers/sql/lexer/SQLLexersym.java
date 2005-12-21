/*******************************************************************************
 * Copyright (c) 2004, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Eclipse Public License v1.0
 * which is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.datatools.sqltools.parsers.sql.lexer;

interface SQLLexersym {
    public final static int
      Char_CtlCharNotWS = 79,
      Char_LF = 53,
      Char_CR = 54,
      Char_HT = 65,
      Char_FF = 66,
      Char_StmtTerm = 78,
      Char_ParamMark = 55,
      Char_DelimIdQt = 41,
      Char_HostVarPrfx = 56,
      Char_A = 12,
      Char_B = 13,
      Char_C = 14,
      Char_D = 15,
      Char_E = 11,
      Char_F = 16,
      Char_G = 18,
      Char_H = 19,
      Char_I = 20,
      Char_J = 21,
      Char_K = 22,
      Char_L = 23,
      Char_M = 24,
      Char_N = 25,
      Char_O = 26,
      Char_P = 27,
      Char_Q = 28,
      Char_R = 29,
      Char_S = 30,
      Char_T = 31,
      Char_U = 32,
      Char_V = 33,
      Char_W = 34,
      Char_X = 17,
      Char_Y = 35,
      Char_Z = 36,
      Char__ = 37,
      Char_0 = 1,
      Char_1 = 2,
      Char_2 = 3,
      Char_3 = 4,
      Char_4 = 5,
      Char_5 = 6,
      Char_6 = 7,
      Char_7 = 8,
      Char_8 = 9,
      Char_9 = 10,
      Char_AfterASCII = 38,
      Char_Space = 45,
      Char_DoubleQuote = 67,
      Char_SingleQuote = 42,
      Char_Percent = 68,
      Char_VerticalBar = 46,
      Char_Exclaimation = 69,
      Char_AtSign = 70,
      Char_BackQuote = 71,
      Char_Tilde = 72,
      Char_Sharp = 39,
      Char_DollarSign = 40,
      Char_Ampersand = 73,
      Char_Caret = 74,
      Char_Colon = 57,
      Char_SemiColon = 58,
      Char_BackSlash = 75,
      Char_LeftBrace = 76,
      Char_RightBrace = 77,
      Char_LeftBracket = 59,
      Char_RightBracket = 60,
      Char_QuestionMark = 47,
      Char_Comma = 61,
      Char_Dot = 48,
      Char_LessThan = 62,
      Char_GreaterThan = 49,
      Char_Plus = 50,
      Char_Minus = 43,
      Char_Slash = 63,
      Char_Star = 64,
      Char_LeftParen = 51,
      Char_RightParen = 52,
      Char_Equal = 44,
      Char_EOF = 80;

      public final static String orderedTerminalSymbols[] = {
                 "",
                 "0",
                 "1",
                 "2",
                 "3",
                 "4",
                 "5",
                 "6",
                 "7",
                 "8",
                 "9",
                 "E",
                 "A",
                 "B",
                 "C",
                 "D",
                 "F",
                 "X",
                 "G",
                 "H",
                 "I",
                 "J",
                 "K",
                 "L",
                 "M",
                 "N",
                 "O",
                 "P",
                 "Q",
                 "R",
                 "S",
                 "T",
                 "U",
                 "V",
                 "W",
                 "Y",
                 "Z",
                 "_",
                 "AfterASCII",
                 "Sharp",
                 "DollarSign",
                 "DelimIdQt",
                 "SingleQuote",
                 "Minus",
                 "Equal",
                 "Space",
                 "VerticalBar",
                 "QuestionMark",
                 "Dot",
                 "GreaterThan",
                 "Plus",
                 "LeftParen",
                 "RightParen",
                 "LF",
                 "CR",
                 "ParamMark",
                 "HostVarPrfx",
                 "Colon",
                 "SemiColon",
                 "LeftBracket",
                 "RightBracket",
                 "Comma",
                 "LessThan",
                 "Slash",
                 "Star",
                 "HT",
                 "FF",
                 "DoubleQuote",
                 "Percent",
                 "Exclaimation",
                 "AtSign",
                 "BackQuote",
                 "Tilde",
                 "Ampersand",
                 "Caret",
                 "BackSlash",
                 "LeftBrace",
                 "RightBrace",
                 "StmtTerm",
                 "CtlCharNotWS",
                 "EOF"
             };

    public final static boolean isValidForParser = true;
}