// // // import com.collibra.dgc.core.api.dto.instance.complexrelation.AttributeValue
// // // import com.collibra.dgc.core.api.dto.instance.complexrelation.ComplexRelationLegRequest
import com.collibra.dgc.core.api.model.ResourceType

// // [string2Uuid("3df43a06-daa8-483e-8114-7fa0321bc326"),string2Uuid("10df848e-c383-447f-89d0-ad90394c5846")]
// def cr=complexRelationTypeApi.getComplexRelationType(string2Uuid("2f2303d6-4a94-48a1-85f7-4910d49a9763"))
// loggerApi.info("==================================>>>>>> Type :  " +cr.getSymbolData())
// loggerApi.info("==================================>>>>>> Attributes : " +cr.getAttributeTypes()*.getAttributeType()*.getName())
// loggerApi.info("==================================>>>>>> Leg Asset Type : " +cr.getLegTypes()*.getId())
// loggerApi.info("==================================>>>>>> Leg Asset Resource Type : " +cr.getLegTypes()*.getAssetType()*.getResourceType())
// loggerApi.info("==================================>>>>>> Leg Role : " +cr.getLegTypes()*.getRole())
// loggerApi.info("==================================>>>>>> Leg CoRole : " +cr.getLegTypes()*.getCoRole())
// loggerApi.info("==================================>>>>>> Leg Relation Type : " +cr.getLegTypes()*.getRelationTypeId())


map = [:]
//map.put(string2Uuid("0edf097a-0bb2-453a-bacc-16b735af67d5"),[AttributeValue.setValue(SQL)])

legAssetTypes=complexRelationTypeApi.getComplexRelationType(string2Uuid("2f2303d6-4a94-48a1-85f7-4910d49a9763")).getLegTypes()*.getAssetType()*.getId()
loggerApi.info("=--=-=-=-=-=-=-=-=-=-=-=-= " + legAssetTypes)

loggerApi.info("-------------------- cr map --> " + map)

complexRelationApi.addComplexRelation(builders.get("AddComplexRelationRequest")
.complexRelationTypeId(string2Uuid("2f2303d6-4a94-48a1-85f7-4910d49a9763"))
.legs([builders.get("ComplexRelationLegRequest").assetId(string2Uuid("0a4d7862-fab2-4155-a7f0-c24d3895ffdd")).legTypeId(string2Uuid("1c00df90-7d5e-45c4-877c-928321bdfe15")).build(),
	   builders.get("ComplexRelationLegRequest").assetId(string2Uuid("3df43a06-daa8-483e-8114-7fa0321bc326")).legTypeId(string2Uuid("6dc897a8-bac8-479a-91f3-752f5128f753")).build()])
.build()) 	
loggerApi.info("--------------------------- Complex relations added ----------------------------------")





// def exportedCR = complexRelationApi.exportExcelToFile(builders.get("ExportComplexRelationsToExcelRequest")
// 	.complexRelationTypeId(string2Uuid("2f2303d6-4a94-48a1-85f7-4910d49a9763"))
// 	.fileName("ExportComplexRelation")
// 	.sheetName("Complex Relation Details")
// 	.includeHeaderRow(true)
// 	.storeAsAttachment(true)
// 	.supportRoundtrip(true)
// 	.xlsx(true)
// 	.build())
// loggerApi.info("--------------------->>>>> File Details :"+exportedCR)
// loggerApi.info("--------------------->>>>> File Name :"+exportedCR.getName())
// loggerApi.info("--------------------->>>>> File Id :"+exportedCR.getId())
// loggerApi.info("--------------------->>>>> File Content Type :"+exportedCR.getContentType())
// loggerApi.info("--------------------->>>>> File Extension :"+exportedCR.getExtension())
// def exportedCrFileContents=fileApi.getFileAsStream(exportedCR.getId())
// attachment = attachmentApi.addAttachment(builders.get("AddAttachmentRequest")
//   .baseResourceId(string2Uuid("28905f68-94c1-4fec-9beb-08a99fe82c55"))
//   .baseResourceType(ResourceType.Community)
//   .fileName(exportedCR.getName())
//   .fileStream(exportedCrFileContents)
//   .build())
// loggerApi.info("----------------->>>>>> File Attached to Community <<<<<<--------------------")


// Attachemnt Link on Mail
def dgcBaseUrl = applicationApi.getInfo().getBaseUrl()
def id = string2Uuid("82e13c8b-8915-4d1b-91f9-591f7e937846")
def link= dgcBaseUrl+"rest/1.0/attachment/download/${id}"
def attachmentLink="<a href=${link}>Download Report</a>"
execution.setVariable("attachmentLink",attachmentLink)
mail.sendMails([string2Uuid("2f1e10f6-68b4-4e63-912a-336dc50f4a3d")], "remainder", "feedbackApprovalProcess", execution);
loggerApi.info("============ Mail Sent =========" + attachmentLink)

// Workbook Attachment
def relationTypeList = relationTypeApi.findRelationTypes(builders.get("FindRelationTypesRequest")
  .sourceTypeName("Database")
  .build()).getResults()

loggerApi.info("-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=--=-=-==- Relations " +relationTypeList)

def fileDetails = fileApi.getFileInfo(string2Uuid(file))
def fileContents = fileApi.getFileAsStream(string2Uuid(file))
def fileName= fileDetails.getName().toString()
def newFileName = "Report.xlsx"
loggerApi.info("======================== File Id " + file)
loggerApi.info("=====================>>>>> File Details " + fileDetails)


FileOutputStream fileOutputStream = new FileOutputStream(newFileName);

XSSFWorkbook newWorkBook = new XSSFWorkbook()
XSSFSheet newSheet = newWorkBook.createSheet("Changes")
XSSFRow newRow
XSSFCell newCell
def rowNum=0 

relationTypeList.each{ relation ->    
        newRow = newSheet.createRow(rowNum)
        newCell= newRow.createCell(0).setCellValue(relation.getSourceType().getName())
        newCell= newRow.createCell(1).setCellValue(relation.getRole())
        newCell= newRow.createCell(2).setCellValue(relation.getCoRole())
        newCell= newRow.createCell(3).setCellValue(relation.getTargetType().getName())
        rowNum+=1
}

newWorkBook.write(fileOutputStream)
fileOutputStream.close()

loggerApi.info("======== workbook after --> "+newWorkBook)



loggerApi.info("====================>>>>>>>>>> File Name : "+ newFileName)

def updatedFile = fileApi.addFile(builders.get("AddFileRequest")
    .name(newFileName)
    .fileStream(new FileInputStream(newFileName))
    .build())

loggerApi.info("==================== updated file "+ updatedFile.getId())
loggerApi.info("==================== updated file "+ updatedFile)


attachment = attachmentApi.addAttachment(builders.get("AddAttachmentRequest")
  .baseResourceId(string2Uuid("c7761162-8f7d-44d1-865c-59e50f391550"))
  .baseResourceType(ResourceType.Community)
  .fileName(updatedFile.getName())
  .fileStream(fileApi.getFileAsStream(updatedFile.getId()))
  .build())  

loggerApi.info("==================== File asstached ===============")



XSSFWorkbook workbook= new XSSFWorkbook(new FileInputStream(newFileName))

loggerApi.info("=====================>>>> Sheets Name -> "+ workbook.getSheetName(0))
loggerApi.info("=====================>>>> workbook type -> "+ workbook.getWorkbookType())
loggerApi.info("=====================>>>> No of sheets -> "+ workbook.getNumberOfSheets())
loggerApi.info("=====================>>>> No of cell styles -> "+ workbook.getNumCellStyles())
loggerApi.info("=====================>>>> Active Sheet -> "+ workbook.getSheetAt(workbook.getActiveSheetIndex()).getSheetName())

XSSFSheet sheets = workbook.getSheetAt(0);
XSSFRow row 
XSSFCell cell
def cellValue

for(def i=0;i<sheets.getLastRowNum();i++){
    row = sheets.getRow(i)
    loggerApi.info("================>>>> Row "+i)
    for(int j=0;j<4;j++){
        cellValue = row.getCell(j).getStringCellValue()
        loggerApi.info("======>>>> Cell Value = " + cellValue)
    }
}
loggerApi.info("===================>>>> End <<<<=================== ")