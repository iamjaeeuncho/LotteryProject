package com.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageVO {
	public int mesgNo;
	public int userNo;
	public int chatNo;
	public String userName;
	public String content;
	public String createdMesg;
	public String updateMesg;
	public String status;
	@Override
	public String toString() {
		return "MessageVO [mesgNo=" + mesgNo + ", userNo=" + userNo + ", chatNo=" + chatNo + ", userName=" + userName
				+ ", content=" + content + ", createdMesg=" + createdMesg + ", updateMesg=" + updateMesg + ", status="
				+ status + "]";
	}

	
}
