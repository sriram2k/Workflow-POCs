import com.collibra.dgc.core.api.dto.instance.attribute.FindAttributesRequest
import com.collibra.dgc.core.api.dto.meta.attributetype.FindAttributeTypesRequest
import com.collibra.dgc.core.api.dto.meta.attributetype.AttributeKind
import com.collibra.dgc.core.api.dto.MatchMode

def attrList = attributeTypeApi.findAttributeTypes(FindAttributeTypesRequest.builder()
	.name("Database Type")
	.nameMatchMode(MatchMode.EXACT)
	.kind(AttributeKind.SINGLE_VALUE_LIST)  
  	.build()).getResults()


def allowedValues= attrList.get(0).getAllowedValues()

loggerApi.info("-----------------------------Atr List---------" + attrList)
loggerApi.info("-----------------------------Atr ID---------" + allowedValues)

def dynamicValues= [:]

for(def values: allowedValues){
	dynamicValues.put(values,values)	
}

loggerApi.info("-----------------------------Atr Map ---------" + dynamicValues)

execution.setVariable("dynamicValues",dynamicValues)

def db = execution.getVariable("dynamicValues")

loggerApi.info("-----------------------------Atr Map ---------" + db)

