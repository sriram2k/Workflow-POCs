/* 
Script Name: Updating Asset Status
Author: Lucid
Version: 1.0
Version History: Updating Asset Status
Purpose: To update the status if the supervisor accepts/ rejects the proposal
*/

// updating asset status to "Supervisor Rejected"
assetApi.changeAsset(builders.get("ChangeAssetRequest")
	.id(item.getId())
	.statusId(getStatusIdByName(supervisorRejectedStatus).get(0).getId())
	.build())

// Sending notification to Customer
def usersIds = users.getUserIds("user(${startUser})");
def template = "${supervisorRejectedTemplate}";
if (usersIds.isEmpty()){
    loggerApi.warn("No users to send a mail to, no mail will be send");
} else if (template == null) {
    loggerApi.warn("No template for mail, no mail will be send");
} else {
    mail.sendMails(usersIds, template, null, execution);
}