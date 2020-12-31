import com.collibra.dgc.core.api.dto.instance.community.AddCommunityRequest
import com.collibra.dgc.core.api.dto.instance.domain.AddDomainRequest
import com.collibra.dgc.core.api.dto.instance.asset.AddAssetRequest
import com.collibra.dgc.core.api.dto.instance.relation.AddRelationRequest
import com.collibra.dgc.core.api.dto.instance.attribute.AddAttributeRequest
import com.collibra.dgc.core.api.dto.PagedResponse
import com.collibra.dgc.core.api.dto.instance.community.FindCommunitiesRequest
import com.collibra.dgc.core.api.dto.meta.domaintype.FindDomainTypesRequest
import com.collibra.dgc.core.api.dto.instance.asset.FindAssetRequest
import com.collibra.dgc.core.api.dto.MatchMode
import com.collibra.dgc.core.api.model.meta.type.AssetType
import com.collibra.dgc.core.api.dto.instance.attribute.FindAttributesRequest
import com.collibra.dgc.core.api.dto.meta.relationtype.FindRelationTypesRequest
import com.collibra.dgc.core.api.component.instance.AssetApi
import com.collibra.dgc.core.api.dto.instance.domain.FindDomainsRequest

loggerApi.info("Community Id" + communityId)
loggerApi.info("Community  Id Class" + communityId.getClass())

def domainsList = domainApi.findDomains(FindDomainsRequest.builder()
	.communityId(communityId)
	.build()).getResults()

def domainsListId = ""
loggerApi.info("domainList" + domainsList)
for(def domain : domainsList){
domainsListId = domainsListId + domain.getId() + ","
}



loggerApi.info("Domain list id" + domainsListId)
execution.setVariable("domains",domainsListId.substring(0,domainsListId.length()-1))
loggerApi.info("-------------------------- Domains Fetched Successfully -----------------------")

//

def assetsList = assetApi.findAssets(FindAssetsRequest.builder()
  .domainId(domainId)
  .build()).getResults()

def assetsListId = ""
loggerApi.info("Assets List : " + assetsList)

for(def asset : assetsList){
assetsListId = assetsListId + asset.getId() + ","
}

loggerApi.info("Domain list id" + domainsListId)
execution.setVariable("assets",assetsListId.substring(0,assetsListId.length()-1))

loggerApi.info("-------------------------- Assets Fetched Successfully -----------------------")


loggerApi.info("-------------------- Started---------------")

def attrList = attributeTypeApi.findAttributeTypes(FindAttributeTypesRequest.builder()
  .build()).getResults()

loggerApi.info("-------------------- Entering the for loop ---------------")
def attrId=""
for(def attr : attrList){
attrId = attrId + attr.getId() + ","
}

execution.setVariable("attributes",attrId.substring(0,attrId.length()-1))


def desAttributeId = attributeApi.addAttribute(AddAttributeRequest.builder()
.assetId(assetId)
.typeId(attributeId)
.value(description)
.build()).getId()

def com= communityApi.getCommunity(communityId)
execution.setVariable("comId",com.getName())

def dom= domainApi.getDomain(domainId)
execution.setVariable("domId",dom.getName())

def assId= assetApi.getAsset(assetId)
execution.setVariable("asetId",assId.getName())

def attr = attributeApi.getAttribute(desAttributeId).getValue()
execution.setVariable("attrId",attr)

