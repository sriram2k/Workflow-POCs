import com.collibra.dgc.core.api.dto.user.AddUserRequest
import com.collibra.dgc.core.api.dto.instance.responsibility.AddResponsibilityRequest
import com.collibra.dgc.core.api.model.ResourceType
import com.collibra.dgc.core.api.dto.user.FindUsersRequest
import com.collibra.dgc.core.api.dto.MatchMode
import com.collibra.dgc.core.api.dto.role.FindRolesRequest
import com.collibra.dgc.core.api.dto.instance.responsibility.FindResponsibilitiesRequest
import com.collibra.dgc.core.api.component.instance.AssetApi
import com.collibra.dgc.core.api.dto.instance.comment.AddCommentRequest
import com.collibra.dgc.core.api.model.ResourceType
import com.collibra.dgc.core.api.dto.instance.attachment.AddAttachmentRequest
import com.collibra.dgc.core.api.dto.file.AddFileRequest
import com.collibra.dgc.core.api.model.file.FileInfo
import com.collibra.dgc.workflow.api.bean.WorkflowUtility
import com.collibra.dgc.workflow.api.exception.WorkflowException
import com.collibra.dgc.core.api.dto.instance.asset.ChangeAssetRequest
import com.collibra.dgc.core.api.dto.workflow.StartWorkflowInstancesRequest
import com.collibra.dgc.core.api.dto.instance.attachment.FindAttachmentsRequest
import com.collibra.dgc.core.api.dto.application.ApplicationInfo


// def user1 = userApi.addUser(AddUserRequest.builder()
// 	.firstName("test")
// 	.emailAddress("testUser1@lucid-green.com")
// 	.lastName("User1")
// 	.userName("testUser1")	
// 	.build())	


// def roles=roleApi.findRoles(FindRolesRequest.builder()
// 	.name("Business Steward")
// 	.nameMatchMode(MatchMode.EXACT)
// 	.build()).getResults()


// def users=userApi.findUsers(FindUsersRequest.builder()
// 	.name("Sriram Balachandran")
// 	.build()).getResults()


// def responsibility = responsibilityApi.addResponsibility(AddResponsibilityRequest.builder()
// 	.ownerId(string2Uuid("673c6c98-98e0-46ad-aba4-b2f2d92af203"))
// 	.roleId(roles.get(0).getId())
// 	.resourceId(string2Uuid("52e74ed5-f4cb-4738-9a0d-1fc43248ab0f"))
// 	.resourceType(ResourceType.Community)
// 	.build())


// def responsibilities= responsibilityApi.findResponsibilities(FindResponsibilitiesRequest.builder()
// 	.globalOnly(false)
// 	.ownerIds([string2Uuid("673c6c98-98e0-46ad-aba4-b2f2d92af203")])
// 	.resourceIds([string2Uuid("52e74ed5-f4cb-4738-9a0d-1fc43248ab0f")])
// 	.roleIds([roles.get(0).getId()])
// 	.build()).getResults()
// for(def r : responsibilities){
// 	loggerApi.info("------------------------ Sriram's "+r)
// }

//assetApi.removeAsset(string2Uuid("12722a7e-339e-4777-ac83-3471c74dcaac"))

//loggerApi.info("------------------ Asset deleted ------------------------------")


// commentApi.addComment(AddCommentRequest.builder()
// 	.baseResourceId(string2Uuid("e4ccfe7d-da9a-44b0-a04f-a5fdc0171085"))
// 	.baseResourceType(ResourceType.Asset)
// 	.content("Well Done, Comment Added using API")
// 	.build())


// listToCsv=execution.getVariable("hospital")
// //def csv=utility.toCsv(listToCsv)
// if(listToCsv==null){
// 	execution.setVariable("errormsg","Please enter the mandatory fields")
// }
// loggerApi.info("----------- CSV ------------------ " + listToCsv)


/*************************************************************************************************************************************/
def fileObj= execution.getVariable("file")

loggerApi.info("-----------------------"+ fileObj)

def fileDetails = fileApi.getFileInfo(string2Uuid(fileObj))
def fileContents = fileApi.getFileAsStream(string2Uuid(fileObj))

def extension=fileDetails.getExtension()
def attachment


//if(extension==".docx" || extension==".xlsx"){
	loggerApi.info("------------------- valid ---------------------")
	attachment = attachmentApi.addAttachment(AddAttachmentRequest.builder()
	.baseResourceId(string2Uuid("e4ccfe7d-da9a-44b0-a04f-a5fdc0171085"))
	.baseResourceType(ResourceType.Asset)
	.fileName(fileDetails.getName())
	.fileStream(fileContents)
	.build())
	loggerApi.info("---------------- "+ attachment)
// }
// else{
// 	loggerApi.info("------------------- in valid ---------------------")	
// }

loggerApi.info("---------------------------------------- file attached -----------------")

loggerApi.info("----------------------------- "+ attachment.getFile().getId())

def dgcBaseUrl = applicationApi.getInfo().getBaseUrl()

loggerApi.info("--------------------- " + dgcBaseUrl)


def attachmentId = attachment.getFile().getId()


loggerApi.info("-------------------------- File Details ---------"+fileDetails)
loggerApi.info("-------------------------- File Contents ---------"+fileContents)

def downloadLink= dgcBaseUrl+"rest/1.0/attachment/download/"+attachmentId

def hyperlink="<a download href="+downloadLink+">Download Attachment</a>"
execution.setVariable("hyperlink",hyperlink)

/*************************************************************************************************************************************/

// WorkflowException workflowException = new WorkflowException()
//String errorMessage = translation.getMessage("Couldn't find Users for the given role "+dataGovernanceAnalystRole)
//String errorTitle = translation.getMessage("usersNotFound");
// workflowException.setTitleMessage("Error Message");
// throw workflowException;


// def assetChnage = assetApi.changeAsset(ChangeAssetRequest.builder()
// 	.domainId(string2Uuid("9af4aae4-ebf0-405e-9fe7-ee0af60723de"))
// 	.id(string2Uuid("396374d8-58e7-4a6d-a534-05c26724d860"))
// 	.build())

// loggerApi.info("---------------------------Calling the workflow ----------------")

// workflowInstanceApi.startWorkflowInstances(StartWorkflowInstancesRequest.builder()
// 	.workflowDefinitionId(string2Uuid("56b88786-9d09-491e-a4c5-7693a540aebc"))
// 	.build())

// loggerApi.info("----------------------------- Next workflow caleed ---------------------")

// def errmsg="hello"+ "<br>"
// execution.setVariable("link",errmsg)

// def ast ="<p style=color:red>*</p>"
// execution.setVariable("ast",ast)

