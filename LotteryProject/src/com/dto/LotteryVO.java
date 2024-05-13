package com.dto;

import lombok.Getter;
import lombok.Setter;
import oracle.sql.DATE;

@Getter
@Setter
public class LotteryVO {
	public int lotteryNo;
	public int userNo;
	public DATE createdAt;
	public DATE modifiedAt;
	public String category;
	public int lottery1;
	public int lottery2;
	public int lottery3;
	public int lottery4;
	public int lottery5;
	public int lottery6;
}
