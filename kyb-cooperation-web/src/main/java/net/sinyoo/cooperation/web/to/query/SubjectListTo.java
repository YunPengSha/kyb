package net.sinyoo.cooperation.web.to.query;

import net.sinyoo.cooperation.comm.base.BaseDomain;
import net.sinyoo.cooperation.comm.model.Subject;
import net.sinyoo.cooperation.core.emnu.SubjectStatus;
import net.sinyoo.cooperation.web.to.SubjectStandardTo;
import net.sinyoo.cooperation.web.to.SubjectTo;

import java.util.List;

/**
 * 课题列表返回
 * 创建用户:     wangHui
 * 创建时间:     2017-03-20
 * Created by IntelliJ IDEA.
 */
public class SubjectListTo extends BaseDomain {

    private Integer pages = 0;

    private Integer totalSize = 0;

    private List<SubjectTo> subjectTos;


    public SubjectListTo() {
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    public List<SubjectTo> getSubjectTos() {
        return subjectTos;
    }

    public void setSubjectTos(List<SubjectTo> subjectTos) {
        this.subjectTos = subjectTos;
    }
}