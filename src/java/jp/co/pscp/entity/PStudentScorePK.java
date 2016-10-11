/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author 雄
 */
@Embeddable
public class PStudentScorePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "stu_id")
    private String stuId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cou_id")
    private int couId;

    public PStudentScorePK() {
    }

    public PStudentScorePK(String stuId, int couId) {
        this.stuId = stuId;
        this.couId = couId;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public int getCouId() {
        return couId;
    }

    public void setCouId(int couId) {
        this.couId = couId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stuId != null ? stuId.hashCode() : 0);
        hash += (int) couId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PStudentScorePK)) {
            return false;
        }
        PStudentScorePK other = (PStudentScorePK) object;
        if ((this.stuId == null && other.stuId != null) || (this.stuId != null && !this.stuId.equals(other.stuId))) {
            return false;
        }
        if (this.couId != other.couId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jp.co.pscp.entity.PStudentScorePK[ stuId=" + stuId + ", couId=" + couId + " ]";
    }
    
}
