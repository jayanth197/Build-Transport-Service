/**
 * 
 */
package com.cintap.transport.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author SurenderMogiloju
 *
 */
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Pageable {
	private Integer pageNo; 
    private Integer pageSize;
    private Integer totalPages;
}
