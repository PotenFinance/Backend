package com.sub.potenfi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sub.potenfi.dto.ReportDTO;

@Mapper
public interface ReportMapper {

	List<ReportDTO> fetchMonthlyReport(String userId);

}
