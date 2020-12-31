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
import com.collibra.dgc.core.api.dto.meta.relationtype.FindRelationTypesRequest

def communityList = communityApi.findCommunities(FindCommunitiesRequest.builder()
  .name(communityName)
  .build()).getResults()

if(!communityList.isEmpty()){
  def removeCom = communityApi.removeCommunityInJob(communityList.get(0).getId())
}


def SqlCommunityUuid = communityApi.addCommunity(AddCommunityRequest.builder()
    .name(communityName)
    .description("BS SQL Community")
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

def physicalDomainUuid = domainApi.addDomain(AddDomainRequest.builder()
    .name(physicalDataDictDomainName)
        .communityId(SqlCommunityUuid)
    .typeId(getDomainTypeIdByName(physicalDataDictDomainId))
        .build()).getId()



def getAssetIdByName(String type){
  def assetList = assetTypeApi.findAssetTypes(FindAssetTypesRequest.builder()
  .name(type)
  .nameMatchMode(MatchMode.EXACT) 
  .build()).getResults()

  return assetList.get(0).getId()
}

def newServerAssetUuid = assetApi.addAsset(AddAssetRequest.builder()
           .name(serverName)
           .displayName(serverName)
           .typeId(getAssetIdByName(serverTypeId))
           .domainId(techDomainUuid)
           .build()).getId()




def newDbAssetUuid = assetApi.addAsset(AddAssetRequest.builder()
           .name(databaseName)
           .displayName(databaseName)
           .typeId(getAssetIdByName(databaseTypeId))
           .domainId(techDomainUuid)
           .build()).getId()

def cassetList = assetTypeApi.findAssetTypes(FindAssetTypesRequest.builder()
  .name(cloudTypeId)
  .build()).getResults()


def newCloudAssetUuid = assetApi.addAsset(AddAssetRequest.builder()
           .name(cloudName)
           .displayName(cloudName)
           .typeId(cassetList.get(0).getId())
           .domainId(techDomainUuid)
           .build()).getId()


def newSchemaAssetUuid = assetApi.addAsset(AddAssetRequest.builder()
           .name(schemaName)
           .displayName(schemaName)
           .typeId(getAssetIdByName(schemaTypeId))
           .domainId(physicalDomainUuid)
           .build()).getId()

def newTable1AssetUuid = assetApi.addAsset(AddAssetRequest.builder()
           .name("Book Price")
           .displayName("Book Price")
           .typeId(getAssetIdByName(tableTypeId))
           .domainId(physicalDomainUuid)
           .build()).getId()

def newTable2AssetUuid = assetApi.addAsset(AddAssetRequest.builder()
           .name("Author Details")
           .displayName("Author Details")
           .typeId(getAssetIdByName(tableTypeId))
           .domainId(physicalDomainUuid)
           .build()).getId()

def newTable3AssetUuid = assetApi.addAsset(AddAssetRequest.builder()
           .name("Publisher Details")
           .displayName("Publisher Details")
           .typeId(getAssetIdByName(tableTypeId))
           .domainId(physicalDomainUuid)
           .build()).getId()


def newColumn1AssetUuid = assetApi.addAsset(AddAssetRequest.builder()
           .name("BookId")
           .displayName("BookId")
           .typeId(getAssetIdByName(columnTypeId))
           .domainId(physicalDomainUuid)
           .build()).getId()

def newColumn2AssetUuid = assetApi.addAsset(AddAssetRequest.builder()
           .name("BookName")
           .displayName("BookName")
           .typeId(getAssetIdByName(columnTypeId))
           .domainId(physicalDomainUuid)
           .build()).getId()

def newColumn3AssetUuid = assetApi.addAsset(AddAssetRequest.builder()
           .name("BookPrice")
           .displayName("BookPrice")
           .typeId(getAssetIdByName(columnTypeId))
           .domainId(physicalDomainUuid)
           .build()).getId()

def newColumn4AssetUuid = assetApi.addAsset(AddAssetRequest.builder()
           .name("Author")
           .displayName("Author")
           .typeId(getAssetIdByName(columnTypeId))
           .domainId(physicalDomainUuid)
           .build()).getId()

def newColumn5AssetUuid = assetApi.addAsset(AddAssetRequest.builder()
           .name("Publisher")
           .displayName("Publisher")
           .typeId(getAssetIdByName(columnTypeId))
           .domainId(physicalDomainUuid)
           .build()).getId()


def getAttributeTypeIdByName(String type){
  def attrTypeList = attributeTypeApi.findAttributeTypes(builders.get("FindAttributeTypesRequest")
  .name(type)
  .nameMatchMode(MatchMode.EXACT) 
  .build()).getResults()

  return attrTypeList.get(0).getId()
}

def getCustomAttributeTypeIdByName(String type){
  def cusAttrTypeList = attributeTypeApi.findAttributeTypes(builders.get("FindAttributeTypesRequest")
  .name(type)
  .build()).getResults()

  return cusAttrTypeList.get(0).getId()
}

addCustomAttribute(newDbAssetUuid,pocNameCusAttr,"Sample SQL Structure")
addCustomAttribute(newDbAssetUuid,databaseTypeCusAttr,"SQL")

def addCustomAttribute(assetId,typeId,value){
  attributeApi.addAttribute(AddAttributeRequest.builder()
 .assetId(assetId)
 .typeId(getCustomAttributeTypeIdByName(typeId))
 .value(value)
 .build())  
}

def addAttribute(assetId,typeId,value){
  attributeApi.addAttribute(AddAttributeRequest.builder()
 .assetId(assetId)
 .typeId(getAttributeTypeIdByName(typeId))
 .value(value)
 .build())  
}


def getCustomRelationTypeIdByName(relationDetails){
  def cusrelList=relationDetails.split(',')
  def cusRelationTypeList = relationTypeApi.findRelationTypes(FindRelationTypesRequest.builder()
  .sourceTypeName(cusrelList[0])
  .targetTypeName(cusrelList[1])
  .role(cusrelList[2])
  .coRole(cusrelList[3])
  .build()).getResults()

  return cusRelationTypeList.get(0).getId()
}

def getRelationTypeIdByName(relationDetails){
  def relList=relationDetails.split(',')
  def relationTypeList = relationTypeApi.findRelationTypes(FindRelationTypesRequest.builder()
  .sourceTypeName(relList[0])
  .targetTypeName(relList[1])
  .role(relList[2])
  .coRole(relList[3])
  .build()).getResults()

  return relationTypeList.get(0).getId()
}


addCustomRelation(newServerAssetUuid,newCloudAssetUuid,getCustomRelationTypeIdByName(serverCloudRelation))
addCustomRelation(newServerAssetUuid,newDbAssetUuid,getCustomRelationTypeIdByName(serverDatabaseRelation))
addCustomRelation(newDbAssetUuid,newSchemaAssetUuid,getCustomRelationTypeIdByName(databaseSchemaRelation))

addRelation(newSchemaAssetUuid,newTable1AssetUuid,getCustomRelationTypeIdByName(schemaTableRelation))
addRelation(newSchemaAssetUuid,newTable2AssetUuid,getCustomRelationTypeIdByName(schemaTableRelation))
addRelation(newSchemaAssetUuid,newTable3AssetUuid,getCustomRelationTypeIdByName(schemaTableRelation))

addCustomRelation(newTable1AssetUuid,newColumn1AssetUuid,getCustomRelationTypeIdByName(tableColumnRelation))
addCustomRelation(newTable1AssetUuid,newColumn2AssetUuid,getCustomRelationTypeIdByName(tableColumnRelation))
addCustomRelation(newTable1AssetUuid,newColumn3AssetUuid,getCustomRelationTypeIdByName(tableColumnRelation))
addCustomRelation(newTable2AssetUuid,newColumn1AssetUuid,getCustomRelationTypeIdByName(tableColumnRelation))
addCustomRelation(newTable2AssetUuid,newColumn2AssetUuid,getCustomRelationTypeIdByName(tableColumnRelation))
addCustomRelation(newTable2AssetUuid,newColumn4AssetUuid,getCustomRelationTypeIdByName(tableColumnRelation))
addCustomRelation(newTable3AssetUuid,newColumn1AssetUuid,getCustomRelationTypeIdByName(tableColumnRelation))
addCustomRelation(newTable3AssetUuid,newColumn2AssetUuid,getCustomRelationTypeIdByName(tableColumnRelation))
addCustomRelation(newTable3AssetUuid,newColumn5AssetUuid,getCustomRelationTypeIdByName(tableColumnRelation))

def addRelation(sourceId,targetId,typeId){
  relationApi.addRelation(AddRelationRequest.builder()
 .sourceId(sourceId)
 .targetId(targetId)
 .typeId(typeId)
 .build())  
}

def addCustomRelation(sourceId,targetId,typeId){
  relationApi.addRelation(AddRelationRequest.builder()
 .sourceId(sourceId)
 .targetId(targetId)
 .typeId(typeId)
 .build())  
}