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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import jp.co.pscp.util.ConstUtil;

/**
 *
 * @author 雄
 */
@Entity
@Table(name = "m_course")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MCourse.findAll", query = "SELECT m FROM MCourse m"),
    @NamedQuery(name = "MCourse.findByCouId", query = "SELECT m FROM MCourse m WHERE m.couId = :couId"),
    @NamedQuery(name = "MCourse.findByCouName", query = "SELECT m FROM MCourse m WHERE m.couName = :couName"),
    @NamedQuery(name = "MCourse.findByScore", query = "SELECT m FROM MCourse m WHERE m.score = :score"),
    @NamedQuery(name = "MCourse.findByRequired", query = "SELECT m FROM MCourse m WHERE m.required = :required"),
    @NamedQuery(name = "MCourse.findByUpdDate", query = "SELECT m FROM MCourse m WHERE m.updDate = :updDate"),
    @NamedQuery(name = "MCourse.findByDelFlg", query = "SELECT m FROM MCourse m WHERE m.delFlg = :delFlg"),
    @NamedQuery(name = "MCourse.findByVersion", query = "SELECT m FROM MCourse m WHERE m.version = :version")})
public class MCourse implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "cou_id")
    private int couId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "cou_name")
    private String couName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "score")
    private short score;
    @Basic(optional = false)
    @NotNull
    @Column(name = "required")
    private String required;
    @Basic(optional = false)
    @NotNull
    @Column(name = "upd_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updDate;
    @Column(name = "del_flg")
    private String delFlg;
    @Column(name = "version")
    @Version
    private int version;

    public MCourse() {
    }

    public MCourse(int couId) {
        this.couId = couId;
    }

    public MCourse(int couId, String couName, short score, String required, Date updDate) {
        this.couId = couId;
        this.couName = couName;
        this.score = score;
        this.required = required;
        this.updDate = updDate;
    }

    public int getCouId() {
        return couId;
    }

    public void setCouId(int couId) {
        this.couId = couId;
    }

    public String getCouName() {
        return couName;
    }

    public void setCouName(String couName) {
        this.couName = couName;
    }

    public short getScore() {
        return score;
    }

    public void setScore(short score) {
        this.score = score;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }



    @Override
    public String toString() {
        return "jp.co.pscp.entity.MCourse[ couId=" + couId + " ]";
    }
    public boolean getRequiredB(){
        return ConstUtil.REQUIRED_FLG_YES.equals(this.required);
    }
    public void setRequiredB(boolean flg){
        this.required=flg ? ConstUtil.REQUIRED_FLG_YES : ConstUtil.REQUIRED_FLG_NO;
    }
}
