package cfml.parsing.cfmentat.tag;

import net.htmlparser.jericho.EndTagType;
import net.htmlparser.jericho.StartTagTypeGenericImplementation;

 // note this has the same startdelimiter as processing instruction, so overrides it if registered
 final class StartTagTypeCfFunction extends GenericStartTagTypeCf {
	protected static final StartTagTypeCfFunction INSTANCE=new StartTagTypeCfFunction();

	private StartTagTypeCfFunction() {
		//super("CFSET","<cfset",">",null,true,false,false);
		super("CFML short tag","<cffunction",">",EndTagType.NORMAL,false,true,true);
	}
}

