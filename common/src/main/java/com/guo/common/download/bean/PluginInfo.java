package com.guo.common.download.bean;

import java.io.Serializable;

/**
 * Created by guoq on 2018/1/4.
 * 插件信息
 */

public class PluginInfo implements Serializable {

    private String pluginid;//	String	插件ID
    private String pluginname;//	String	插件名称	中文
    private String pluginfilename;//	String	插件文件名
    private int versioncode;//	int	插件版本号
    private String versionname;//	String	插件版本号
    private int defaultplugin;//	int	是否是默认插件 0 不是 1是
    private int ismustplugin;//	int	是否是必须插件  0 不是必须 1 必须
    private String applicationid;//	String	applicationid
    private String startpage;//	String	启动页面地址
    private String pluginpic;//	String	插件图片
    private String downurls;//	String	下载地址
    private boolean isdownload;
    private String pluginlocalpath;
    public String getPluginid() {
        return pluginid;
    }

    public void setPluginid(String pluginid) {
        this.pluginid = pluginid;
    }

    public String getPluginname() {
        return pluginname;
    }

    public void setPluginname(String pluginname) {
        this.pluginname = pluginname;
    }

    public String getPluginfilename() {
        return pluginfilename;
    }

    public void setPluginfilename(String pluginfilename) {
        this.pluginfilename = pluginfilename;
    }

    public int getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(int versioncode) {
        this.versioncode = versioncode;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public int getDefaultplugin() {
        return defaultplugin;
    }

    public void setDefaultplugin(int defaultplugin) {
        this.defaultplugin = defaultplugin;
    }

    public int getIsmustplugin() {
        return ismustplugin;
    }

    public void setIsmustplugin(int ismustplugin) {
        this.ismustplugin = ismustplugin;
    }

    public String getApplicationid() {
        return applicationid;
    }

    public void setApplicationid(String applicationid) {
        this.applicationid = applicationid;
    }

    public String getStartpage() {
        return startpage;
    }

    public void setStartpage(String startpage) {
        this.startpage = startpage;
    }

    public String getPluginpic() {
        return pluginpic;
    }

    public void setPluginpic(String pluginpic) {
        this.pluginpic = pluginpic;
    }

    public String getDownurls() {
        return downurls;
    }

    public void setDownurls(String downurls) {
        this.downurls = downurls;
    }

    public boolean isdownload() {
        return isdownload;
    }

    public void setIsdownload(boolean isdownload) {
        this.isdownload = isdownload;
    }

    public String getPluginlocalpath() {
        return pluginlocalpath;
    }

    public void setPluginlocalpath(String pluginlocalpath) {
        this.pluginlocalpath = pluginlocalpath;
    }

}
