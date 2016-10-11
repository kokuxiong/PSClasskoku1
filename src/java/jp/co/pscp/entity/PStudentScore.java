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
@Table(name = "p_student_score")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PStudentScore.findAll", query = "SELECT p FROM PStudentScore p"),
    @NamedQuery(name = "PStudentScore.findByStuId", query = "SELECT p FROM PStudentScore p WHERE p.pStudentScorePK.stuId = :stuId"),
    @NamedQuery(name = "PStudentScore.findByCouId", query = "SELECT p FROM PStudentScore p WHERE p.pStudentScorePK.couId = :couId"),
    @NamedQuery(name = "PStudentScore.findByScore", query = "SELECT p FROM PStudentScore p WHERE p.score = :score"),
    @NamedQuery(name = "PStudentScore.findByUpdDate", query = "SELECT p FROM PStudentScore p WHERE p.updDate = :updDate"),
    @NamedQuery(name = "PStudentScore.findByDelFlg", query = "SELECT p FROM PStudentScore p WHERE p.delFlg = :delFlg"),
    @NamedQuery(name = "PStudentScore.findByVersion", query = "SELECT p FROM PStudentScore p WHERE p.version = :version")})
public class PStudentScore implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PStudentScorePK pStudentScorePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "score")
    private short score;
    @Basic(optional = false)
    @NotNull
    @Column(name = "upd_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updDate;
    @Column(name = "del_flg")
    private Character delFlg;
    @Column(name = "version")
    private Integer version;

    public PStudentScore() {
    }

    public PStudentScore(PStudentScorePK pStudentScorePK) {
        this.pStudentScorePK = pStudentScorePK;
    }

    public PStudentScore(PStudentScorePK pStudentScorePK, short score, Date updDate) {
        this.pStudentScorePK = pStudentScorePK;
        this.score = score;
        this.updDate = updDate;
    }

    public PStudentScore(String stuId, int couId) {
        this.pStudentScorePK = new PStudentScorePK(stuId, couId);
    }

    public PStudentScorePK getPStudentScorePK() {
        return pStudentScorePK;
    }

    public void setPStudentScorePK(PStudentScorePK pStudentScorePK) {
        this.pStudentScorePK = pStudentScorePK;
    }

    public short getScore() {
        return score;
    }

    public void setScore(short score) {
        this.score = score;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    public Character getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Character delFlg) {
        this.delFlg = delFlg;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pStudentScorePK != null ? pStudentScorePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PStudentScore)) {
            return false;
        }
        PStudentScore other = (PStudentScore) object;
        if ((this.pStudentScorePK == null && other.pStudentScorePK != null) || (this.pStudentScorePK != null && !this.pStudentScorePK.equals(other.pStudentScorePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jp.co.pscp.entity.PStudentScore[ pStudentScorePK=" + pStudentScorePK + " ]";
    }
    
}
