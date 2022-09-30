/**
 * 
 */
package com.cintap.transport.global.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author SurenderMogiloju
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DetailInfo {
	private List<S5DetailInfo> s5DetailInfoList = new ArrayList<>();
	
	public void addTos5DetailInfoList(S5DetailInfo s5DetailInfo) {
		s5DetailInfoList.add(s5DetailInfo);
	}
}
