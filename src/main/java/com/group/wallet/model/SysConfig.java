package com.group.wallet.model;

import java.util.Date;

public class SysConfig {

	private Integer configid;

	private String configkey;

    private String configvalue;

    private Date configaddtime;

    private String configdesc;

    private Integer configtype;
    
    
    public Integer getConfigid() {
		return configid;
	}

	public void setConfigid(Integer configid) {
		this.configid = configid;
	}

    public String getConfigkey() {
        return configkey;
    }

    public void setConfigkey(String configkey) {
        this.configkey = configkey == null ? null : configkey.trim();
    }

    public String getConfigvalue() {
        return configvalue;
    }

    public void setConfigvalue(String configvalue) {
        this.configvalue = configvalue == null ? null : configvalue.trim();
    }

    public Date getConfigaddtime() {
        return configaddtime;
    }

    public void setConfigaddtime(Date configaddtime) {
        this.configaddtime = configaddtime;
    }

    public String getConfigdesc() {
        return configdesc;
    }

    public void setConfigdesc(String configdesc) {
        this.configdesc = configdesc == null ? null : configdesc.trim();
    }

    public Integer getConfigtype() {
        return configtype;
    }

    public void setConfigtype(Integer configtype) {
        this.configtype = configtype;
    }
}