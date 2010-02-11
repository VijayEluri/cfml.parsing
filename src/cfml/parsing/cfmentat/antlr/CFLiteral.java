/* 
 *  Copyright (C) 2000 - 2010 TagServlet Ltd
 *
 *  This file is part of Open BlueDragon (OpenBD) CFML Server Engine.
 *  
 *  OpenBD is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  Free Software Foundation,version 3.
 *  
 *  OpenBD is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *  
 *  You should have received a copy of the GNU General Public License
 *  along with OpenBD.  If not, see http://www.gnu.org/licenses/
 *  
 *  Additional permission under GNU GPL version 3 section 7
 *  
 *  If you modify this Program, or any covered work, by linking or combining 
 *  it with any of the JARS listed in the README.txt (or a modified version of 
 *  (that library), containing parts covered by the terms of that JAR, the 
 *  licensors of this Program grant you additional permission to convey the 
 *  resulting work. 
 *  README.txt @ http://www.openbluedragon.org/license/README.txt
 *  
 *  http://www.openbluedragon.org/
 */

package cfml.parsing.cfmentat.antlr;

import org.antlr.runtime.Token;

import com.naryx.tagfusion.cfm.engine.*;
import com.naryx.tagfusion.cfm.parser.CFMLLexer;

public class CFLiteral extends CFExpression implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private cfData val;
	private int kind;

	public CFLiteral( Token _t ) {
		super(_t);
		kind = _t.getType();
		switch (kind) {
			case CFMLLexer.FLOATING_POINT_LITERAL:
			case CFMLLexer.INTEGER_LITERAL:
				val = cfData.createNumber(_t.getText(), false);
				break;
			case CFMLLexer.STRING_LITERAL:
				// create a String, stripping off the surrounding quotes and
				// replacing any escaped quotes with a single quote
				String quote = _t.getText().substring(0, 1);
				String str = _t.getText().substring(1, _t.getText().length() - 1);
				str = str.replaceAll(quote + quote, quote);
				val = new cfStringData(str);
				break;
			case CFMLLexer.BOOLEAN_LITERAL:
				val = cfBooleanData.getcfBooleanData(_t.getText());
				break;
			case CFMLLexer.NULL:
				val = cfNullData.NULL;
				break;
			default:
				break;
		}
	}

	public byte getType() {
		return CFExpression.LITERAL;
	}

	public cfData Eval( CFContext context ) {
		return context._lastExpr = val;
	}

	public String Decompile( int indent ) {
		try {
			return val.getString();
		} catch (Exception e) {
			return "Couldn't get literal value";
		}

	}
}
