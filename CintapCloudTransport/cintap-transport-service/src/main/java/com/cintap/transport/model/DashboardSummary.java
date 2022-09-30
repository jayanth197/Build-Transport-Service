package com.cintap.transport.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardSummary {
	private List<TransactoinSummary> lstTransactoinSummary = new ArrayList<>();
	private List<TransactionSourceSummary> lstTransactionSourceSummary = new ArrayList<>();
}
