/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 雄
 */
@Entity
@Table(name = "p_course_selection")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PCourseSelection.findAll", query = "SELECT p FROM PCourseSelection p"),
    @NamedQuery(name = "PCourseSelection.findByStuId", query = "SELECT p FROM PCourseSelection p WHERE p.pCourseSelectionPK.stuId = :stuId"),
    @NamedQuery(name = "PCourseSelection.findByTranId", query = "SELECT p FROM PCourseSelection p WHERE p.pCourseSelectionPK.tranId = :tranId"),
    @NamedQuery(name = "PCourseSelection.findByUpdDate", query = "SELECT p FROM PCourseSelection p WHERE p.updDate = :updDate"),
    @NamedQuery(name = "PCourseSelection.findByDelFlg", query = "SELECT p FROM PCourseSelection p WHERE p.delFlg = :delFlg"),
    @NamedQuery(name = "PCourseSelection.findByVersion", query = "SELECT p FROM PCourseSelection p WHERE p.version = :version")})
public class PCourseSelection implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PCourseSelectionPK pCourseSelectionPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "upd_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updDate;
    @Column(name = "del_flg")
    private String delFlg;
    @Column(name = "version")
    private Integer version;

    @OneToOne
    @JoinColumn(name = "tran_id", referencedColumnName = "cou_id", insertable = false, updatable = false)
    private MCourse course;

    @OneToOne
    @JoinColumns({
        @JoinColumn(name = "stu_id", referencedColumnName = "stu_id", insertable = false, updatable = false),
        @JoinColumn(name = "tran_id", referencedColumnName = "cou_id", insertable = false, updatable = false)
    }
    )
    private PStudentScore coursesSore;

    public PCourseSelection() {
    }

    public PCourseSelection(PCourseSelectionPK pCourseSelectionPK) {
        this.pCourseSelectionPK = pCourseSelectionPK;
    }

    public PCourseSelection(PCourseSelectionPK pCourseSelectionPK, Date updDate) {
        this.pCourseSelectionPK = pCourseSelectionPK;
        this.updDate = updDate;
    }

    public PCourseSelection(String stuId, int tranId) {
        this.pCourseSelectionPK = new PCourseSelectionPK(stuId, tranId);
    }

    public PCourseSelectionPK getPCourseSelectionPK() {
        return pCourseSelectionPK;
    }

    public void setPCourseSelectionPK(PCourseSelectionPK pCourseSelectionPK) {
        this.pCourseSelectionPK = pCourseSelectionPK;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public MCourse getCourse() {
        return course;
    }

    public void setCourse(MCourse course) {
        this.course = course;
    }

    public PStudentScore getCoursesSore() {
        return coursesSore;
    }

    public void setCoursesSore(PStudentScore coursesSore) {
        this.coursesSore = coursesSore;
    }

    

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pCourseSelectionPK != null ? pCourseSelectionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PCourseSelection)) {
            return false;
        }
        PCourseSelection other = (PCourseSelection) object;
        if ((this.pCourseSelectionPK == null && other.pCourseSelectionPK != null) || (this.pCourseSelectionPK != null && !this.pCourseSelectionPK.equals(other.pCourseSelectionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jp.co.pscp.entity.PCourseSelection[ pCourseSelectionPK=" + pCourseSelectionPK + " ]";
    }

}
