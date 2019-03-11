package com.elasticsearch.root.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 隐患
 * 
 * @author Administrator
 *
 */
@Document(indexName = "safety_risk_info", type = "doc", indexStoreType = "fs", shards = 5, replicas = 1, refreshInterval = "-1")
public class SafetyRiskInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4584056519464396276L;
	private String checkLocation_country;
	private String checkLocation_other;
	private String checkLocation_province;
	private String checkLocation_town;
	private String checkLocation_city;
	private String checkLocation_street;
	private String checkLocation_county;
	private String checkLocation_houseNumber;
	private String checkLocation_location;
	private String checkLocation_floor;
	private String period;
	private String possibleAccidents;
	private String checkPerson_country;
	private String checkPerson_qualification;
	private String checkPerson_unit;
	private String checkPerson_major;
	private String checkPerson_jobTitle;
	private String checkPerson_name;
	private String riskLevel;
	private String unitName;
	private String dutyUnit;
	private String codeRule_sourceCode;
	private String codeRule_riskType;
	private String suggest;
	private String basis;
	@JsonProperty("docs.count")
	private String checkUnit_first;
	private String checkUnit_second;
	private String content;
	private String site;
	private String riskCategory_first;
	private String riskCategory_second;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date checkTime;
	private String post;
	private String riskPicture;
	private String riskDesc;
	private String riskSource_first;
	private String riskSource_second;
	private String id;
	private String organizationLevel;
	private String projectName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataSource_sourceDate;
	private String dataSource_serialNumber;
	private String dataSource_sourceSoft;
	private String dataSource_sourceType;
	private String dataSource_link;
	private String dataSource_copyrightUnit;
	private String dataSource_sourceName;
	private String dataSource_publishUrl;
	private Integer pageSize = 10;
	private Integer pageIndex = 1;

	public String getCheckLocation_country() {
		return checkLocation_country;
	}

	public void setCheckLocation_country(String checkLocation_country) {
		this.checkLocation_country = checkLocation_country;
	}

	public String getCheckLocation_other() {
		return checkLocation_other;
	}

	public void setCheckLocation_other(String checkLocation_other) {
		this.checkLocation_other = checkLocation_other;
	}

	public String getCheckLocation_province() {
		return checkLocation_province;
	}

	public void setCheckLocation_province(String checkLocation_province) {
		this.checkLocation_province = checkLocation_province;
	}

	public String getCheckLocation_town() {
		return checkLocation_town;
	}

	public void setCheckLocation_town(String checkLocation_town) {
		this.checkLocation_town = checkLocation_town;
	}

	public String getCheckLocation_city() {
		return checkLocation_city;
	}

	public void setCheckLocation_city(String checkLocation_city) {
		this.checkLocation_city = checkLocation_city;
	}

	public String getCheckLocation_street() {
		return checkLocation_street;
	}

	public void setCheckLocation_street(String checkLocation_street) {
		this.checkLocation_street = checkLocation_street;
	}

	public String getCheckLocation_county() {
		return checkLocation_county;
	}

	public void setCheckLocation_county(String checkLocation_county) {
		this.checkLocation_county = checkLocation_county;
	}

	public String getCheckLocation_houseNumber() {
		return checkLocation_houseNumber;
	}

	public void setCheckLocation_houseNumber(String checkLocation_houseNumber) {
		this.checkLocation_houseNumber = checkLocation_houseNumber;
	}

	public String getCheckLocation_location() {
		return checkLocation_location;
	}

	public void setCheckLocation_location(String checkLocation_location) {
		this.checkLocation_location = checkLocation_location;
	}

	public String getCheckLocation_floor() {
		return checkLocation_floor;
	}

	public void setCheckLocation_floor(String checkLocation_floor) {
		this.checkLocation_floor = checkLocation_floor;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getPossibleAccidents() {
		return possibleAccidents;
	}

	public void setPossibleAccidents(String possibleAccidents) {
		this.possibleAccidents = possibleAccidents;
	}

	public String getCheckPerson_country() {
		return checkPerson_country;
	}

	public void setCheckPerson_country(String checkPerson_country) {
		this.checkPerson_country = checkPerson_country;
	}

	public String getCheckPerson_qualification() {
		return checkPerson_qualification;
	}

	public void setCheckPerson_qualification(String checkPerson_qualification) {
		this.checkPerson_qualification = checkPerson_qualification;
	}

	public String getCheckPerson_unit() {
		return checkPerson_unit;
	}

	public void setCheckPerson_unit(String checkPerson_unit) {
		this.checkPerson_unit = checkPerson_unit;
	}

	public String getCheckPerson_major() {
		return checkPerson_major;
	}

	public void setCheckPerson_major(String checkPerson_major) {
		this.checkPerson_major = checkPerson_major;
	}

	public String getCheckPerson_jobTitle() {
		return checkPerson_jobTitle;
	}

	public void setCheckPerson_jobTitle(String checkPerson_jobTitle) {
		this.checkPerson_jobTitle = checkPerson_jobTitle;
	}

	public String getCheckPerson_name() {
		return checkPerson_name;
	}

	public void setCheckPerson_name(String checkPerson_name) {
		this.checkPerson_name = checkPerson_name;
	}

	public String getRiskLevel() {
		return riskLevel;
	}

	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getDutyUnit() {
		return dutyUnit;
	}

	public void setDutyUnit(String dutyUnit) {
		this.dutyUnit = dutyUnit;
	}

	public String getCodeRule_sourceCode() {
		return codeRule_sourceCode;
	}

	public void setCodeRule_sourceCode(String codeRule_sourceCode) {
		this.codeRule_sourceCode = codeRule_sourceCode;
	}

	public String getCodeRule_riskType() {
		return codeRule_riskType;
	}

	public void setCodeRule_riskType(String codeRule_riskType) {
		this.codeRule_riskType = codeRule_riskType;
	}

	public String getSuggest() {
		return suggest;
	}

	public void setSuggest(String suggest) {
		this.suggest = suggest;
	}

	public String getBasis() {
		return basis;
	}

	public void setBasis(String basis) {
		this.basis = basis;
	}

	public String getCheckUnit_first() {
		return checkUnit_first;
	}

	public void setCheckUnit_first(String checkUnit_first) {
		this.checkUnit_first = checkUnit_first;
	}

	public String getCheckUnit_second() {
		return checkUnit_second;
	}

	public void setCheckUnit_second(String checkUnit_second) {
		this.checkUnit_second = checkUnit_second;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getRiskCategory_first() {
		return riskCategory_first;
	}

	public void setRiskCategory_first(String riskCategory_first) {
		this.riskCategory_first = riskCategory_first;
	}

	public String getRiskCategory_second() {
		return riskCategory_second;
	}

	public void setRiskCategory_second(String riskCategory_second) {
		this.riskCategory_second = riskCategory_second;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getRiskPicture() {
		return riskPicture;
	}

	public void setRiskPicture(String riskPicture) {
		this.riskPicture = riskPicture;
	}

	public String getRiskDesc() {
		return riskDesc;
	}

	public void setRiskDesc(String riskDesc) {
		this.riskDesc = riskDesc;
	}

	public String getRiskSource_first() {
		return riskSource_first;
	}

	public void setRiskSource_first(String riskSource_first) {
		this.riskSource_first = riskSource_first;
	}

	public String getRiskSource_second() {
		return riskSource_second;
	}

	public void setRiskSource_second(String riskSource_second) {
		this.riskSource_second = riskSource_second;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrganizationLevel() {
		return organizationLevel;
	}

	public void setOrganizationLevel(String organizationLevel) {
		this.organizationLevel = organizationLevel;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getDataSource_sourceDate() {
		return dataSource_sourceDate;
	}

	public void setDataSource_sourceDate(Date dataSource_sourceDate) {
		this.dataSource_sourceDate = dataSource_sourceDate;
	}

	public String getDataSource_serialNumber() {
		return dataSource_serialNumber;
	}

	public void setDataSource_serialNumber(String dataSource_serialNumber) {
		this.dataSource_serialNumber = dataSource_serialNumber;
	}

	public String getDataSource_sourceSoft() {
		return dataSource_sourceSoft;
	}

	public void setDataSource_sourceSoft(String dataSource_sourceSoft) {
		this.dataSource_sourceSoft = dataSource_sourceSoft;
	}

	public String getDataSource_sourceType() {
		return dataSource_sourceType;
	}

	public void setDataSource_sourceType(String dataSource_sourceType) {
		this.dataSource_sourceType = dataSource_sourceType;
	}

	public String getDataSource_link() {
		return dataSource_link;
	}

	public void setDataSource_link(String dataSource_link) {
		this.dataSource_link = dataSource_link;
	}

	public String getDataSource_copyrightUnit() {
		return dataSource_copyrightUnit;
	}

	public void setDataSource_copyrightUnit(String dataSource_copyrightUnit) {
		this.dataSource_copyrightUnit = dataSource_copyrightUnit;
	}

	public String getDataSource_sourceName() {
		return dataSource_sourceName;
	}

	public void setDataSource_sourceName(String dataSource_sourceName) {
		this.dataSource_sourceName = dataSource_sourceName;
	}

	public String getDataSource_publishUrl() {
		return dataSource_publishUrl;
	}

	public void setDataSource_publishUrl(String dataSource_publishUrl) {
		this.dataSource_publishUrl = dataSource_publishUrl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getStartIndex() {
		return (pageIndex - 1) * pageSize;
	}

}