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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 雄
 */
@Entity
@Table(name = "p_techer_course")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PTecherCourse.findAll", query = "SELECT p FROM PTecherCourse p"),
    @NamedQuery(name = "PTecherCourse.findByTranId", query = "SELECT p FROM PTecherCourse p WHERE p.tranId = :tranId"),
    @NamedQuery(name = "PTecherCourse.findByTchId", query = "SELECT p FROM PTecherCourse p WHERE p.tchId = :tchId"),
    @NamedQuery(name = "PTecherCourse.findByCouId", query = "SELECT p FROM PTecherCourse p WHERE p.couId = :couId"),
    @NamedQuery(name = "PTecherCourse.findByStartDate", query = "SELECT p FROM PTecherCourse p WHERE p.startDate = :startDate"),
    @NamedQuery(name = "PTecherCourse.findByEndTime", query = "SELECT p FROM PTecherCourse p WHERE p.endTime = :endTime"),
    @NamedQuery(name = "PTecherCourse.findByUpdDate", query = "SELECT p FROM PTecherCourse p WHERE p.updDate = :updDate"),
    @NamedQuery(name = "PTecherCourse.findByDelFlg", query = "SELECT p FROM PTecherCourse p WHERE p.delFlg = :delFlg"),
    @NamedQuery(name = "PTecherCourse.findByVersion", query = "SELECT p FROM PTecherCourse p WHERE p.version = :version")})
public class PTecherCourse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tran_id")
    private Integer tranId;
    @Size(max = 8)
    @Column(name = "tch_id")
    private String tchId;
    @Column(name = "cou_id")
    private Integer couId;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "end_time")
    @Temporal(TemporalType.DATE)
    private Date endTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "upd_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updDate;
    @Column(name = "del_flg")
    private Character delFlg;
    @Column(name = "version")
    private Integer version;

    public PTecherCourse() {
    }

    public PTecherCourse(Integer tranId) {
        this.tranId = tranId;
    }

    public PTecherCourse(Integer tranId, Date updDate) {
        this.tranId = tranId;
        this.updDate = updDate;
    }

    public Integer getTranId() {
        return tranId;
    }

    public void setTranId(Integer tranId) {
        this.tranId = tranId;
    }

    public String getTchId() {
        return tchId;
    }

    public void setTchId(String tchId) {
        this.tchId = tchId;
    }

    public Integer getCouId() {
        return couId;
    }

    public void setCouId(Integer couId) {
        this.couId = couId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
        hash += (tranId != null ? tranId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PTecherCourse)) {
            return false;
        }
        PTecherCourse other = (PTecherCourse) object;
        if ((this.tranId == null && other.tranId != null) || (this.tranId != null && !this.tranId.equals(other.tranId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jp.co.pscp.entity.PTecherCourse[ tranId=" + tranId + " ]";
    }
    
}
