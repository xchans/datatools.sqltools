/*******************************************************************************
 * Copyright (c) 2004, 2005 Sybase, Inc. and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sybase, Inc. - initial API and implementation
 *******************************************************************************/
package org.eclipse.datatools.sqltools.db.generic.parser;

public interface GenericSQLParserConstants {

  int EOF = 0;
  int SINGLE_LINE_COMMENT = 7;
  int COMMENT_CONTENT = 12;
  int INVALID_TOKEN = 13;
  int ADD = 14;
  int ALTER = 15;
  int AND = 16;
  int ANY = 17;
  int AS = 18;
  int ASC = 19;
  int AT = 20;
  int AUTHORIZATION = 21;
  int AVG = 22;
  int BEGIN = 23;
  int BETWEEN = 24;
  int BREAK = 25;
  int BROWSE = 26;
  int BULK = 27;
  int BY = 28;
  int CASCADE = 29;
  int CASE = 30;
  int CHECK = 31;
  int CHECKPOINT = 32;
  int CLOSE = 33;
  int CLUSTERED = 34;
  int COALESCE = 35;
  int COMMIT = 36;
  int COMPUTE = 37;
  int CONFIRM = 38;
  int CONNECT = 39;
  int CONSTRAINT = 40;
  int CONTINUE = 41;
  int CONTROLROW = 42;
  int CONVERT = 43;
  int COUNT = 44;
  int CREATE = 45;
  int CURRENT = 46;
  int CURSOR = 47;
  int DATABASE = 48;
  int DBCC = 49;
  int DEALLOCATE = 50;
  int DECLARE = 51;
  int DEFAULT_VAL = 52;
  int DELETE = 53;
  int DESC = 54;
  int DETERMINISTIC = 55;
  int DISK = 56;
  int DISTINCT = 57;
  int DOUBLE = 58;
  int DROP = 59;
  int DUMMY = 60;
  int DUMP = 61;
  int ELSE = 62;
  int END = 63;
  int ENDTRAN = 64;
  int ESCAPE = 65;
  int EXCEPT = 66;
  int EXCLUSIVE = 67;
  int EXEC = 68;
  int EXECUTE = 69;
  int EXISTS = 70;
  int EXIT = 71;
  int EXTERNAL = 72;
  int EVENT = 73;
  int FETCH = 74;
  int FILLFACTOR = 75;
  int FOR = 76;
  int FOREIGN = 77;
  int FROM = 78;
  int FUNC = 79;
  int FUNCTION = 80;
  int GO = 81;
  int GOTO = 82;
  int GRANT = 83;
  int GROUP = 84;
  int HAVING = 85;
  int HOLDLOCK = 86;
  int IDENTITY = 87;
  int IF = 88;
  int IN = 89;
  int INDEX = 90;
  int INOUT = 91;
  int INSERT = 92;
  int INSTALL = 93;
  int INTERSECT = 94;
  int INTO = 95;
  int IS = 96;
  int ISOLATION = 97;
  int JAR = 98;
  int JOIN = 99;
  int KEY = 100;
  int KILL = 101;
  int LEVEL = 102;
  int LIKE = 103;
  int LOAD = 104;
  int LOCK = 105;
  int MAX = 106;
  int MIN = 107;
  int MODIFY = 108;
  int NATIONAL = 109;
  int NOHOLDLOCK = 110;
  int NONCLUSTERED = 111;
  int NOT = 112;
  int NULL = 113;
  int NULLIF = 114;
  int OF = 115;
  int OFF = 116;
  int OFFSETS = 117;
  int ON = 118;
  int ONCE = 119;
  int ONLINE = 120;
  int ONLY = 121;
  int OPEN = 122;
  int OPTION = 123;
  int OR = 124;
  int ORDER = 125;
  int OUT = 126;
  int OUTPUT = 127;
  int OVER = 128;
  int PARTITION = 129;
  int PERM = 130;
  int PERMANENT = 131;
  int PLAN = 132;
  int PREPARE = 133;
  int PRIMARY = 134;
  int PRINT = 135;
  int PRIVILEGES = 136;
  int PROC = 137;
  int PROCEDURE = 138;
  int PROCESSEXIT = 139;
  int PROXY_TABLE = 140;
  int PUBLIC = 141;
  int QUIESCE = 142;
  int RAISERROR = 143;
  int READ = 144;
  int READPAST = 145;
  int READTEXT = 146;
  int RECONFIGURE = 147;
  int REFERENCES = 148;
  int REMOVE = 149;
  int REORG = 150;
  int REPLACE = 151;
  int REPLICATION = 152;
  int RETURN = 153;
  int RETURNS = 154;
  int REVOKE = 155;
  int ROLE = 156;
  int ROLLBACK = 157;
  int ROWCOUNT = 158;
  int ROWS = 159;
  int RULE = 160;
  int SAVE = 161;
  int SCHEMA = 162;
  int SELECT = 163;
  int SET = 164;
  int SETUSER = 165;
  int SHARED = 166;
  int SHUTDOWN = 167;
  int SOME = 168;
  int STATISTICS = 169;
  int STRINGSIZE = 170;
  int STRIPE = 171;
  int SUM = 172;
  int TABLE = 173;
  int TEMP = 174;
  int TEMPORARY = 175;
  int TEXTSIZE = 176;
  int TO = 177;
  int TRAN = 178;
  int TRANSACTION = 179;
  int TRIGGER = 180;
  int TRUNCATE = 181;
  int UNION = 182;
  int UNIQUE = 183;
  int UNPARTITION = 184;
  int UPDATE = 185;
  int USE = 186;
  int USER = 187;
  int USING = 188;
  int VALUES = 189;
  int VARYING = 190;
  int VIEW = 191;
  int WAITFOR = 192;
  int WHEN = 193;
  int WHERE = 194;
  int WHILE = 195;
  int WITH = 196;
  int WORK = 197;
  int WRITETEXT = 198;
  int OPENDESCRIPTION = 199;
  int CLOSEDESCRIPTION = 200;
  int DESCRIPTION = 201;
  int INTEGER_LITERAL = 202;
  int FLOATING_POINT_LITERAL = 203;
  int EXPONENT = 204;
  int SINGLE_STRING_LITERAL = 205;
  int DOUBLE_STRING_LITERAL = 206;
  int BINARY_LITERAL = 207;
  int HEXDIGIT = 208;
  int MONEY_LITERAL = 209;
  int ID = 210;
  int SQUARE_BRACKET_ID = 211;
  int VAR_NAME_BODY = 212;
  int VAR_NAME = 213;
  int LABEL = 214;
  int GLOBAL_VAR_NAME = 215;
  int TEMP_TABLE_NAME = 216;
  int LETTER = 217;
  int DIGIT = 218;
  int SYMBOL = 219;
  int CONCAT = 220;
  int COMMA = 221;
  int SEMICOLON = 222;
  int DOT = 223;
  int ROWTYPE = 224;
  int TILDE = 225;
  int LESS = 226;
  int LESSEQUAL = 227;
  int GREATER = 228;
  int GREATEREQUAL = 229;
  int EQUAL = 230;
  int NOTEQUAL = 231;
  int JOINPLUS = 232;
  int OPENPAREN = 233;
  int CLOSEPAREN = 234;
  int ASTERISK = 235;
  int SLASH = 236;
  int PLUS = 237;
  int MINUS = 238;
  int QUESTIONMARK = 239;
  int LEQJOIN = 240;
  int REQJOIN = 241;
  int JAVA_REF = 242;
  int UK_DOUBLE_PRECISION = 243;
  int UK_NCHAR_S = 244;
  int UK_NCHAR_S1 = 245;
  int UK_NO_SCROLL = 246;
  int UK_NVARCHAR_S = 247;
  int UK_NVARCHAR_S1 = 248;
  int UK_NVARCHAR_S2 = 249;
  int UK_SEMI_SENSITIVE = 250;
  int UK_VARCHAR_S = 251;
  int UK_VARCHAR_S1 = 252;

  int DEFAULT = 0;
  int IN_SINGLE_LINE_COMMENT = 1;
  int IN_MULTI_LINE_COMMENT = 2;
  int DESCRIPTION_START_STATE = 3;
  int DESCRIPTION_STATE = 4;

  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\n\"",
    "\"\\r\"",
    "\"\\t\"",
    "\"\\f\"",
    "\"--\"",
    "<SINGLE_LINE_COMMENT>",
    "<token of kind 8>",
    "\"/*\"",
    "\"/*\"",
    "\"*/\"",
    "<COMMENT_CONTENT>",
    "\"!%^&\"",
    "\"add\"",
    "\"alter\"",
    "\"and\"",
    "\"any\"",
    "\"as\"",
    "\"asc\"",
    "\"at\"",
    "\"authorization\"",
    "\"avg\"",
    "\"begin\"",
    "\"between\"",
    "\"break\"",
    "\"browse\"",
    "\"bulk\"",
    "\"by\"",
    "\"cascade\"",
    "\"case\"",
    "\"check\"",
    "\"checkpoint\"",
    "\"close\"",
    "\"clustered\"",
    "\"coalesce\"",
    "\"commit\"",
    "\"compute\"",
    "\"confirm\"",
    "\"connect\"",
    "\"constraint\"",
    "\"continue\"",
    "\"controlrow\"",
    "\"convert\"",
    "\"count\"",
    "\"create\"",
    "\"current\"",
    "\"cursor\"",
    "\"database\"",
    "\"dbcc\"",
    "\"deallocate\"",
    "\"declare\"",
    "\"default\"",
    "\"delete\"",
    "\"desc\"",
    "\"deterministic\"",
    "\"disk\"",
    "\"distinct\"",
    "\"double\"",
    "\"drop\"",
    "\"dummy\"",
    "\"dump\"",
    "\"else\"",
    "\"end\"",
    "\"endtran\"",
    "\"escape\"",
    "\"except\"",
    "\"exclusive\"",
    "\"exec\"",
    "\"execute\"",
    "\"exists\"",
    "\"exit\"",
    "\"external\"",
    "\"event\"",
    "\"fetch\"",
    "\"fillfactor\"",
    "\"for\"",
    "\"foreign\"",
    "\"from\"",
    "\"func\"",
    "\"function\"",
    "\"go\"",
    "\"goto\"",
    "\"grant\"",
    "\"group\"",
    "\"having\"",
    "\"holdlock\"",
    "\"identity\"",
    "\"if\"",
    "\"in\"",
    "\"index\"",
    "\"inout\"",
    "\"insert\"",
    "\"install\"",
    "\"intersect\"",
    "\"into\"",
    "\"is\"",
    "\"isolation\"",
    "\"jar\"",
    "\"join\"",
    "\"key\"",
    "\"kill\"",
    "\"level\"",
    "\"like\"",
    "\"load\"",
    "\"lock\"",
    "\"max\"",
    "\"min\"",
    "\"modify\"",
    "\"national\"",
    "\"noholdlock\"",
    "\"nonclustered\"",
    "\"not\"",
    "\"null\"",
    "\"nullif\"",
    "\"of\"",
    "\"off\"",
    "\"offsets\"",
    "\"on\"",
    "\"once\"",
    "\"online\"",
    "\"only\"",
    "\"open\"",
    "\"option\"",
    "\"or\"",
    "\"order\"",
    "\"out\"",
    "\"output\"",
    "\"over\"",
    "\"partition\"",
    "\"perm\"",
    "\"permanent\"",
    "\"plan\"",
    "\"prepare\"",
    "\"primary\"",
    "\"print\"",
    "\"privileges\"",
    "\"proc\"",
    "\"procedure\"",
    "\"processexit\"",
    "\"proxy_table\"",
    "\"public\"",
    "\"quiesce\"",
    "\"raiserror\"",
    "\"read\"",
    "\"readpast\"",
    "\"readtext\"",
    "\"reconfigure\"",
    "\"references\"",
    "\"remove\"",
    "\"reorg\"",
    "\"replace\"",
    "\"replication\"",
    "\"return\"",
    "\"returns\"",
    "\"revoke\"",
    "\"role\"",
    "\"rollback\"",
    "\"rowcount\"",
    "\"rows\"",
    "\"rule\"",
    "\"save\"",
    "\"schema\"",
    "\"select\"",
    "\"set\"",
    "\"setuser\"",
    "\"shared\"",
    "\"shutdown\"",
    "\"some\"",
    "\"statistics\"",
    "\"stringsize\"",
    "\"stripe\"",
    "\"sum\"",
    "\"table\"",
    "\"temp\"",
    "\"temporary\"",
    "\"textsize\"",
    "\"to\"",
    "\"tran\"",
    "\"transaction\"",
    "\"trigger\"",
    "\"truncate\"",
    "\"union\"",
    "\"unique\"",
    "\"unpartition\"",
    "\"update\"",
    "\"use\"",
    "\"user\"",
    "\"using\"",
    "\"values\"",
    "\"varying\"",
    "\"view\"",
    "\"waitfor\"",
    "\"when\"",
    "\"where\"",
    "\"while\"",
    "\"with\"",
    "\"work\"",
    "\"writetext\"",
    "\"\\r\\n\"",
    "\"~\"",
    "<DESCRIPTION>",
    "<INTEGER_LITERAL>",
    "<FLOATING_POINT_LITERAL>",
    "<EXPONENT>",
    "<SINGLE_STRING_LITERAL>",
    "<DOUBLE_STRING_LITERAL>",
    "<BINARY_LITERAL>",
    "<HEXDIGIT>",
    "<MONEY_LITERAL>",
    "<ID>",
    "<SQUARE_BRACKET_ID>",
    "<VAR_NAME_BODY>",
    "<VAR_NAME>",
    "<LABEL>",
    "<GLOBAL_VAR_NAME>",
    "<TEMP_TABLE_NAME>",
    "<LETTER>",
    "<DIGIT>",
    "<SYMBOL>",
    "\"||\"",
    "\",\"",
    "\";\"",
    "\".\"",
    "\"%rowtype\"",
    "\"~\"",
    "\"<\"",
    "\"<=\"",
    "\">\"",
    "\">=\"",
    "\"=\"",
    "\"!=\"",
    "\"(+)\"",
    "\"(\"",
    "\")\"",
    "\"*\"",
    "\"/\"",
    "\"+\"",
    "\"-\"",
    "\"?\"",
    "\"*=\"",
    "\"=*\"",
    "\">>\"",
    "<UK_DOUBLE_PRECISION>",
    "<UK_NCHAR_S>",
    "<UK_NCHAR_S1>",
    "<UK_NO_SCROLL>",
    "<UK_NVARCHAR_S>",
    "<UK_NVARCHAR_S1>",
    "<UK_NVARCHAR_S2>",
    "<UK_SEMI_SENSITIVE>",
    "<UK_VARCHAR_S>",
    "<UK_VARCHAR_S1>",
    "\":\"",
    "\"!\"",
    "\"!>\"",
    "\"!<\"",
    "\"<>\"",
    "\"|\"",
    "\"&\"",
    "\"^\"",
    "\"%\"",
  };

}