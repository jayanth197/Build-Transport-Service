package com.cintap.transport.service;

import com.cintap.transport.model.DashboardReqParam;
import com.cintap.transport.model.DashboardSummary;

public interface DashboardService {
	DashboardSummary fetchDashboardSummary(DashboardReqParam otcDashboardReqParam);
}
