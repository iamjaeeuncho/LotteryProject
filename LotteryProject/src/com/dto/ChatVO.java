package com.dto;

import java.sql.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatVO {
	public int chatNo;
	public int userNo;
	public String chatName;
	public String createdChat;
	public Date updateChat;
	public int lastChatMesg;
	public String status;
}
