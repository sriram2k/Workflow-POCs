import com.collibra.dgc.core.api.dto.instance.community.AddCommunityRequest
import com.collibra.dgc.core.api.dto.instance.asset.AddAssetRequest;
import com.collibra.dgc.core.api.dto.instance.attribute.AddAttributeRequest;
import com.collibra.dgc.core.api.dto.instance.relation.AddRelationRequest;
import com.collibra.dgc.core.api.dto.instance.domain.AddDomainRequest;
import com.collibra.dgc.core.api.dto.instance.community.FindCommunitiesRequest;
import com.collibra.dgc.core.api.dto.MatchMode
import com.collibra.dgc.core.api.dto.meta.assettype.FindAssetTypesRequest;
import com.collibra.dgc.core.api.dto.meta.domaintype.FindDomainTypesRequest;
import com.collibra.dgc.core.api.dto.meta.attributetype.FindAttributeTypesRequest;
import com.collibra.dgc.core.api.dto.meta.relationtype.FindRelationTypesRequest;
import com.collibra.dgc.core.api.component.instance.AttributeApi;

def communityList = communityApi.findCommunities(FindCommunitiesRequest.builder()
  .name(communityName)
  .build()).getResults()

if(!communityList.isEmpty()){
  def removeCom = communityApi.removeCommunityInJob(communityList.get(0).getId())
}


def SqlCommunityUuid = communityApi.addCommunity(AddCommunityRequest.builder()
    .name(communityName)
    .description(communityName)
    .build()).getId()

def communityList1 = communityApi.findCommunities(FindCommunitiesRequest.builder()
  .name(communityName)
  .build()).getResults()

def getDomainTypeIdByName(String type){
  def domainTypeList = domainTypeApi.findDomainTypes(FindDomainTypesRequest.builder()
  .name(type)
  .nameMatchMode(MatchMode.EXACT) 
  .build()).getResults()

  return domainTypeList.get(0).getId()
}

def techDomainUuid = domainApi.addDomain(AddDomainRequest.builder()
        .name(technologyAssetDomainName)
        .communityId(SqlCommunityUuid)
        .typeId(getDomainTypeIdByName(technologyAssetDomainId))
        .build()).getId()

def getAssetIdByName(String type){
  def assetList = assetTypeApi.findAssetTypes(FindAssetTypesRequest.builder()
  .name(type)
  .nameMatchMode(MatchMode.EXACT) 
  .build()).getResults()

  return assetList.get(0).getId()
}

def newDbAssetUuid = assetApi.addAsset(AddAssetRequest.builder()
           .name(databaseName)
           .displayName(databaseName)
           .typeId(getAssetIdByName(databaseTypeId))
           .domainId(techDomainUuid)
           .build()).getId()




 def  attrId= attributeApi.addAttribute(AddAttributeRequest.builder()
 .assetId(newDbAssetUuid)
 .typeId(string2Uuid("00000000-0000-0000-0000-000000003114"))
 .value(descriptionValue)
 .build()).getId()  


def attr = attributeApi.getAttribute(attrId).getValue()


execution.setVariable("descriptionDisplay",attr)

