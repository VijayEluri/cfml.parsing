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

import com.naryx.tagfusion.cfm.engine.cfData;
import com.naryx.tagfusion.cfm.engine.cfmRunTimeException;

/**
 * This class is required when we have a variable which is 
 * found via brute force search. This example illustrates it's
 * need:
 * 
 * <cfset a["b.c"] = 2>
 * <cfset b = a.b.c>
 * <cfset a.b.c = 4>
 * 
 * In the second line we want 'b' to be assigned the value of a["b.c"]
 * but in the third line we want a new struct to be created.
 * 
 * This class implements the appropriate Get() and Set() methods
 * to support this behaviour. 
 */

public class combinedCFLData extends extIndirectReferenceData {
  
  private extIndirectReferenceData set;
  private cfLData get;
  
  public combinedCFLData( extIndirectReferenceData _set, cfLData _get, String _image ) throws cfmRunTimeException{
    super( _get, _image );
    exists = true;
    set = _set;
    get = _get;
  }
  
  
  // method not req'd so does nothing 
  public void addIndex_Bracket(cfData _index) {}

  //method not req'd so does nothing
  public void addIndex_Dot(cfData _index) {}


  public cfData Get(CFContext _context) throws cfmRunTimeException {
    return get.Get(_context);
  }

  public void Set(cfData val, CFContext _context) throws cfmRunTimeException {
    set.Set(val, _context);
  }

}
