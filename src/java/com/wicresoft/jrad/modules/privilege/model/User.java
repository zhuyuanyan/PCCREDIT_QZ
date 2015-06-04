// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   User.java

package com.wicresoft.jrad.modules.privilege.model;

import com.wicresoft.jrad.base.auth.IOrganization;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.database.model.BaseModel;
import java.util.Date;
import java.util.List;

// Referenced classes of package com.wicresoft.jrad.modules.privilege.model:
//			Organization, Department

public class User extends BaseModel
	implements IUser
{

	private static final long serialVersionUID = 1L;
	private Integer seqNo;
	private Integer userType;
	private String login;
	private String email;
	private String password;
	private String lastName;
	private String firstName;
	private String displayName;
	private Integer age;
	private String gender;
	private String idCard;
	private String mobile;
	private String phone;
	private String homeAddress;
	private String unitAddress;
	private String description;
	private Date createdTime;
	private String createdBy;
	private Date modifiedTime;
	private String modifiedBy;
	private Boolean isDeleted;
	private String remarks;
	private Integer userLevel;
	private String externalId;
	private String managerId;
	private IOrganization organization;
	private String groupId;
	private Department department;
	private List<Role> roles;
	private Integer isCdtMgr;

	public User()
	{
	}

	public IOrganization getOrganization()
	{
		return organization;
	}

	public void setOrganization(IOrganization organization)
	{
		this.organization = organization;
	}

	public Integer getSeqNo()
	{
		return seqNo;
	}

	public void setSeqNo(Integer seqNo)
	{
		this.seqNo = seqNo;
	}

	public Integer getUserType()
	{
		return userType;
	}

	public void setUserType(Integer userType)
	{
		this.userType = userType;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getLastName()
	{
		return lastName;
	}

	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public Integer getAge()
	{
		return age;
	}

	public void setAge(Integer age)
	{
		this.age = age;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = gender;
	}

	public String getIdCard()
	{
		return idCard;
	}

	public void setIdCard(String idCard)
	{
		this.idCard = idCard;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getHomeAddress()
	{
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress)
	{
		this.homeAddress = homeAddress;
	}

	public String getUnitAddress()
	{
		return unitAddress;
	}

	public void setUnitAddress(String unitAddress)
	{
		this.unitAddress = unitAddress;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Date getCreatedTime()
	{
		return createdTime;
	}

	public void setCreatedTime(Date createdTime)
	{
		this.createdTime = createdTime;
	}

	public String getCreatedBy()
	{
		return createdBy;
	}

	public void setCreatedBy(String createdBy)
	{
		this.createdBy = createdBy;
	}

	public Date getModifiedTime()
	{
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime)
	{
		this.modifiedTime = modifiedTime;
	}

	public String getModifiedBy()
	{
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy)
	{
		this.modifiedBy = modifiedBy;
	}

	public Boolean getIsDeleted()
	{
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted)
	{
		this.isDeleted = isDeleted;
	}

	public String getRemarks()
	{
		return remarks;
	}

	public void setRemarks(String remarks)
	{
		this.remarks = remarks;
	}

	public String getExternalId()
	{
		return externalId;
	}

	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	public String getManagerId()
	{
		return managerId;
	}

	public void setManagerId(String managerId)
	{
		this.managerId = managerId;
	}

	public Integer getUserLevel()
	{
		return userLevel;
	}

	public void setUserLevel(Integer userLevel)
	{
		this.userLevel = userLevel;
	}

	public String getGroupId()
	{
		return groupId;
	}

	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}

	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}

	public List<Role> getRoles() {
	    return this.roles;
	  }

	public void setRoles(List<Role> roles) {
	    this.roles = roles;
	}
	
	public void setIsCdtMgr(Integer isCdtMgr)
	{
		this.isCdtMgr = isCdtMgr;
	}

	public Integer getIsCdtMgr()
	{
		return isCdtMgr;
	}
}
